package com.ebs.receiver.trans;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.RowSet;

import org.apache.log4j.Logger;

import com.ebs.receiver.comm.FuncUtils;
import com.ebs.receiver.conf.Configuration;
import com.ebs.receiver.conn.DBUtilMysql;
import com.ebs.receiver.domain.Status;
import com.ebs.receiver.domain.TYOrder;
import com.ebs.receiver.domain.Version;
import com.ebs.receiver.util.DateTool;
import com.ebs.receiver.util.JSONUtils;
import com.ebs.receiver.util.PackUtil;

public class GetNewVersion {

	private static Logger logger = Logger.getLogger(GetNewVersion.class);
	public String work(String msg) {
		String r = "";
		
		try{
			logger.info(msg);
         TYOrder tyOrder = JSONUtils.json2pojo(PackUtil.getJsonBode(msg), TYOrder.class);
         String betSource=tyOrder.getHeader().getBetSource();
         Map<String,Object>map=getMap(betSource);
         String siteId = tyOrder.getVersion().getSiteId();
         Status status=new Status();
         if(map!=null)
         {
        	 Version version=new Version();
        	 version.setVersion(map.get("appversion").toString());
        	 String url=map.get("downurl").toString();
        	 if(betSource.equals("android")){
        		 url+="/"+betSource+"/"+Configuration.getGlobalMsg("appname").toString()+"-"+siteId+"-"
            			 +version.getVersion()+".apk";
        	 }else if(betSource.equals("iOS")){
        		 url+="/"+betSource+"/"+Configuration.getGlobalMsg("appname").toString()+"-"+siteId+"-"
            			 +version.getVersion()+".ipa";
        	 }
        	 version.setUrl(url);
        	 version.setIsupdate(map.get("isupdate").toString());
        	 version.setSystime(DateTool.parseDate2(new Date()));
        	 version.setIssuedate(map.get("issuedate").toString());
        	 version.setRemark(map.get("remark").toString());
        	 tyOrder.setVersion(version);
        	 status.setErrorCode("0000");
        	 status.setErrorMsg(Configuration.getGlobalMsg("MSG_0000"));
         }else{
        	 //没有数据
        	 status.setErrorCode("0001");
        	 status.setErrorMsg(Configuration.getGlobalMsg("MSG_0001"));
         }
         tyOrder.setStatus(status);
         tyOrder.setHeader(null);
         r=JSONUtils.obj2json(tyOrder);
		}catch(Exception e){
			logger.error(e);
			r=FuncUtils.getErrorMsg("9999",Configuration.getGlobalMsg("MSG_9999"));
		}
         return r;
	}
	public Map<String,Object> getMap(String betSource)
	{
		
		try{
			Map<String,Object>map=new HashMap<String,Object>();
			String sql="select appversion,downurl,isupdate,issuedate,remark from appupdate where appname=\""+betSource.trim()+"\" ;";
			logger.info(sql);
			RowSet rs = DBUtilMysql.execQuery(sql);
			while (rs != null & rs.next()) {
            map.put("appversion", rs.getString(1));
            map.put("downurl", rs.getString(2));
            map.put("isupdate", rs.getString(3));
            map.put("issuedate", rs.getString(4));
            map.put("remark", rs.getString(5));
            return map;
        }
		 return null;
		}catch(Exception e){
			logger.error(e);
			return null;
		}
		
	}
	
	public static void main(String args[])
	{
		System.out.println(Configuration.getGlobalMsg("MSG_0000"));
	}
}
