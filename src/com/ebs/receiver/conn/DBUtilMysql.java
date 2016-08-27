package com.ebs.receiver.conn;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;
import javax.sql.RowSet;

import org.apache.commons.dbcp.BasicDataSource;

import com.ebs.receiver.conf.PropertiesContext;

import sun.jdbc.rowset.CachedRowSet;


public class DBUtilMysql {
	  private static DataSource dataSource;

      public static Connection getConnection() throws SQLException {
          if (dataSource == null)
              dataSource = setupDataSource();
          Connection conn = dataSource.getConnection();
          conn.setAutoCommit(true);
          return conn;
      }

      public static void closeDataSource() {
          if (dataSource != null)
              try {
                  ((BasicDataSource)dataSource).close();
              } catch (SQLException e) {
                  e.printStackTrace();
              }
      }

      public static RowSet execQuery(String sql) {
          Connection conn = null;
          Statement stmt = null;
          CachedRowSet crs = null;

          try {
              conn = getConnection();
              stmt = conn.createStatement();
              crs = new CachedRowSet();
              crs.populate(stmt.executeQuery(sql));
          } catch (SQLException e) {
              e.printStackTrace();
              crs = null;
          } finally {
              try {stmt.close();} catch(Exception e) {}
              try {conn.close();} catch(Exception e) {}
          }

          return crs;
      }
      public static int execUpdate(String sql) throws SQLException {
          Connection conn = getConnection();
          Statement stmt = conn.createStatement();

          try {
              int rtn = stmt.executeUpdate(sql);
              if(!conn.getAutoCommit()) conn.commit();
              return rtn;
          } finally {
              try {stmt.close();} catch(Exception e) {}
              try {conn.close();} catch(Exception e) {}
          }
      }

      /**
       * ���Զ��ύ�� ִ�з���
       * @param sql
       * @param conn
       * @return
       * @throws SQLException
       */
		public static int execUpdateNoCommit(String sql,Connection conn,Statement stmt)throws SQLException{
          conn.setAutoCommit(false);
          int rtn = -1;
              rtn = stmt.executeUpdate(sql);
              
          	return rtn;
      }
      
      public static void close(Connection conn){
      	if(conn!=null){
      		try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					conn = null;
				}
      	}
      		
      }
      
//      static String driver = ConfigManager.getInstance().getConfigItem("db.driver");
//      static String url = ConfigManager.getInstance().getConfigItem("db.url");
//      static String usr = ConfigManager.getInstance().getConfigItem("db.username");
//      static String pwd = ConfigManager.getInstance().getConfigItem("db.password");
    static String driver = PropertiesContext.getInstance().getDriver();
    static String url = PropertiesContext.getInstance().getUrl();
    static String usr =PropertiesContext.getInstance().getUsr();
    static String pwd =PropertiesContext.getInstance().getPwd();
    static int maxActive=Integer.parseInt(PropertiesContext.getInstance().getMaxActive());  
    static int maxIdle= Integer.parseInt(PropertiesContext.getInstance().getMaxIdle()) ;
      public static String validationQuary = "select * from caipiao_award";
      static String isValiable="false";
      static int messageCount = 0;

       public static BasicDataSource setupDataSource() {

              BasicDataSource ds = new BasicDataSource();
              ds.setDriverClassName(driver);
              //ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
              ds.setUsername(usr);
              ds.setPassword(pwd);
              ds.setUrl(url);
              ds.setTestOnBorrow(true);
              ds.setValidationQuery(validationQuary);
              ds.setMaxActive(maxActive);
             ds.setMaxIdle(maxIdle);
//	        dataSource = ds;
              return ds;
          }


      public static void setDataSource(DataSource ds) {
          dataSource = ds;
      }

      public static String getIsValiable()
      {
          return isValiable;
      }

      public static String getUrl()
      {
          return url;
      }

      public static int getMessageCount()
      {
          return messageCount;
      }

      public static void setMessageCount(int msgCount)
      {
          messageCount = msgCount;
      }
}
