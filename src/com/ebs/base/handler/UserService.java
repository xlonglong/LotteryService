package com.ebs.base.handler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.RowSet;

import org.apache.log4j.Logger;

import com.ebs.receiver.comm.HttpSender;
import com.ebs.receiver.conf.Configuration;
import com.ebs.receiver.conf.PropertiesContext;
import com.ebs.receiver.conn.DBUtilMysql;
import com.ebs.receiver.domain.UserInfo;
import com.ebs.receiver.domainup.NClientInfo;
import com.ebs.receiver.domainup.NClientInfoData;
import com.ebs.receiver.util.DateTool;
import com.ebs.receiver.util.GsonUtil;
import com.ebs.receiver.util.MD5Util;
import com.ebs.receiver.util.RedisUtil;
import com.ebs.receiver.util.StringUtil;


public class UserService {
	private Logger logger = Logger.getLogger(UserService.class);

	// 判断用户是否注册
	public boolean checkUser(String userName) {
		try {
			String sql = "select userid from pay_user where userName=\""
					+ userName.trim() + "\" ;";
			logger.info(sql);
			RowSet rs = DBUtilMysql.execQuery(sql);
			while (rs != null & rs.next()) {
				if (!StringUtil.isEmpty(rs.getString(1))) {
					return true;
				} else {
					return false;
				}
			}
			return false;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}

	// 判断用户是否注册
	public boolean checkUserByUserid(String userid) {
		try {
			String sql = "select userid from pay_user where userid=\""
					+ userid.trim() + "\" ;";
			logger.info(sql);
			RowSet rs = DBUtilMysql.execQuery(sql);
			while (rs != null & rs.next()) {
				if (!StringUtil.isEmpty(rs.getString(1))) {
					return true;
				} else {
					return false;
				}
			}
			return false;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}

	// 判断用户校验码是否正确和超时
	public String checkCheckCode(String tel) {
		try {
			String sql = "select expireTime,checkCode from caipiao_usercheckcode where userName=\""
					+ tel + "\""

					+ " order by id desc limit 1";
			logger.info(sql);
			RowSet rs = DBUtilMysql.execQuery(sql);
			Date now = new Date();
			while (rs != null & rs.next()) {
				if ((DateTool.strintToDate2(rs.getString(1)).getTime() > (new Date(
						now.getTime() - 5 * 60 * 1000)).getTime())) {
					return true + "&" + rs.getString(2);
				} else {
					return false + "&";
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return false + "&";
	}

	public UserInfo RegistPayUser(UserInfo userInfo, String channel)
			throws SQLException {

		// 用户注册
		try {
			StringBuffer sql = new StringBuffer("insert into pay_user("
					+ "channel ," + "userName ," + "pwd," + "name ," + "idCard"
					+ ") value (\"");
			sql.append(channel);
			sql.append("\",\"");
			sql.append(userInfo.getUserName());
			sql.append("\",\"");
			sql.append(userInfo.getPwd());
			sql.append("\",\"");
			if (StringUtil.isNotEmpty(userInfo.getName())) {// 用户名不为空
				sql.append(userInfo.getName());
				sql.append("\",\"");
			} else {
				// 用户名为空
				sql.append("\",\"");
			}

			if (StringUtil.isNotEmpty(userInfo.getName())) {// 身份证号不为空
				sql.append(userInfo.getIdCard());
				sql.append("\"");
			} else {
				// 身份证为空
				sql.append("\"");
			}
			sql.append(");");

			logger.info("注册用户：" + sql.toString());

			DBUtilMysql.execUpdate(sql.toString());
			return getUserInfo(userInfo.getUserName(), userInfo.getPwd());
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	public boolean RegistPayUserCard(UserInfo userInfo) throws SQLException {

		Connection conn = null;
		try {
			// 用户注册
			conn = DBUtilMysql.getConnection();
			Statement st = conn.createStatement();
			StringBuffer sql = new StringBuffer(
					"insert into pay_user_cardinfo(" + "userid ," + "agentId ,"
							+ "usernum ," + "tel ," + "nickid" + ") value (");
			sql.append(userInfo.getUserid());
			sql.append(" ,\"");
			sql.append(userInfo.getAgentId());
			sql.append("\",\"");
			sql.append(userInfo.getUsernum());
			sql.append("\",\"");
			sql.append(userInfo.getTel());
			sql.append("\",\"");
			sql.append(userInfo.getTel());
			sql.append("\");");

			logger.info("注册用户：" + sql.toString());

			DBUtilMysql.execUpdateNoCommit(sql.toString(), conn, st);

			// 添加用户佣金汇率表

			StringBuffer sql1 = new StringBuffer(
					"insert into pay_user_commission_rate(Userid)value(");
			sql1.append(userInfo.getUserid());
			sql1.append(");");

			logger.info("注册汇率表：" + sql1.toString());

			DBUtilMysql.execUpdateNoCommit(sql1.toString(), conn, st);
			conn.commit();
			conn.close();
			return true;

		} catch (Exception e) {
			logger.error(e);
			return false;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 通过用户名和密码获取用户信息

	public UserInfo getUserInfo(String userName, String pwd) {
		try {
			StringBuffer sql = new StringBuffer(
					"select a.userid,a.channel,a.userName,a.pwd,a.name,a.idCard,a.balance,a.score,b.agentId ,b.pictureurl ,b.nickid ,a.registTime, b.IsAwardType from pay_user a left join pay_user_cardinfo b on a.userid=b.userid where a.userName=\"");
			sql.append(userName);
			sql.append("\" and a.pwd=\"");
			sql.append(pwd);
			sql.append("\";");
			logger.info("查询用户信息：" + sql.toString());
			UserInfo userInfo = null;
			RowSet rs = DBUtilMysql.execQuery(sql.toString());
			while (rs != null & rs.next()) {
				userInfo = new UserInfo();
				userInfo.setUserid(rs.getString(1));
				userInfo.setChannel(rs.getString(2));
				userInfo.setUserName(rs.getString(3));
				userInfo.setTel(rs.getString(3));
				userInfo.setPwd(rs.getString(4));
				userInfo.setName(rs.getString(5));
				userInfo.setIdCard(rs.getString(6));
				userInfo.setBalance(rs.getString(7));
				userInfo.setScore(rs.getString(8));
				/* userInfo.setCoupons(rs.getString(9)); */
				userInfo.setAgentId(rs.getString(9));

				String pictureUrl = rs.getString(10);
				if (StringUtil.isNotEmpty(pictureUrl)) {
					pictureUrl = Configuration.getGlobalMsg("pictureUrl") + "/"
							+ rs.getString(1) + "/" + pictureUrl;

				}
				userInfo.setPictureurl(pictureUrl);
				userInfo.setNickName(rs.getString(11));
				userInfo.setRegistTime(rs.getString(12));
				userInfo.setIsAwardType(rs.getString(13));

			}
			return userInfo;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	// 通过用户id获取用户信息

	public UserInfo getUserInfoById(String token, String userid) {
		try {
			StringBuffer sql = new StringBuffer(
					"select a.userid,a.channel,a.userName,a.pwd,a.name,a.idCard,a.balance,a.score,b.agentId ,b.pictureurl ,b.nickid from pay_user a left join pay_user_cardinfo b on a.userid=b.userid where a.userid=\"");
			sql.append(userid);
			sql.append("\" ;");

			logger.info("查询用户信息：" + sql.toString());
			UserInfo userInfo = null;
			RowSet rs = DBUtilMysql.execQuery(sql.toString());
			while (rs != null & rs.next()) {
				userInfo = new UserInfo();
				userInfo = new UserInfo();
				userInfo.setUserid(rs.getString(1));
				userInfo.setChannel(rs.getString(2));
				userInfo.setUserName(rs.getString(3));
				userInfo.setPwd(rs.getString(4));
				userInfo.setName(rs.getString(5));
				userInfo.setIdCard(rs.getString(6));
				//userInfo.setBalance(rs.getString(7));
				//userInfo.setScore(rs.getString(8));
				/* userInfo.setCoupons(rs.getString(9)); */
				userInfo.setAgentId(rs.getString(9));

				String pictureUrl = rs.getString(10);
				if (StringUtil.isNotEmpty(pictureUrl)) {
					pictureUrl = Configuration.getGlobalMsg("pictureUrl") + "/"
							+ rs.getString(1) + "/" + pictureUrl;

				}
				userInfo.setPictureurl(pictureUrl);
				userInfo.setNickName(rs.getString(11));

			}
			logger.info("getUserInfoById================="+token);
			NClientInfo nClientInfo = getUserInfoUp(token);
			userInfo.setScore(nClientInfo.getPoints());  			//积分
			userInfo.setBalance(nClientInfo.getValidFee()); 		//账户余额
			return userInfo;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	// 通过用户id获取用户信息

		public UserInfo getUserInfo(String userid) {
			try {
				StringBuffer sql = new StringBuffer(
						"select a.userid,a.channel,a.userName,a.pwd,a.name,a.idCard,a.balance,a.score,b.agentId ,b.pictureurl ,b.nickid from pay_user a left join pay_user_cardinfo b on a.userid=b.userid where a.userid=\"");
				sql.append(userid);
				sql.append("\" ;");

				logger.info("查询用户信息：" + sql.toString());
				UserInfo userInfo = null;
				RowSet rs = DBUtilMysql.execQuery(sql.toString());
				while (rs != null & rs.next()) {
					userInfo = new UserInfo();
					userInfo = new UserInfo();
					userInfo.setUserid(rs.getString(1));
					userInfo.setChannel(rs.getString(2));
					userInfo.setUserName(rs.getString(3));
					userInfo.setPwd(rs.getString(4));
					userInfo.setName(rs.getString(5));
					userInfo.setIdCard(rs.getString(6));
					userInfo.setBalance(rs.getString(7));
					userInfo.setScore(rs.getString(8));
					/* userInfo.setCoupons(rs.getString(9)); */
					userInfo.setAgentId(rs.getString(9));

					String pictureUrl = rs.getString(10);
					if (StringUtil.isNotEmpty(pictureUrl)) {
						pictureUrl = Configuration.getGlobalMsg("pictureUrl") + "/"
								+ rs.getString(1) + "/" + pictureUrl;
					}
					userInfo.setPictureurl(pictureUrl);
					userInfo.setNickName(rs.getString(11));
				}
				/*NClientInfo nClientInfo = getUserInfoUp(token);
				userInfo.setScore(nClientInfo.getPoints());  			//积分
				userInfo.setBalance(nClientInfo.getValidFee());*/ 		//账户余额
				return userInfo;
			} catch (Exception e) {
				logger.error(e);
				return null;
			}
		}

	// 插入验证码

	public boolean insertCheckCode(String tel, String checkCode) {
		try {
			String sql = "insert into caipiao_usercheckcode(userName,checkCode,expireTime) values('"
					+ tel
					+ "','"
					+ checkCode
					+ "','"
					+ DateTool.parseDate2(new Date()) + "')";

			logger.error(sql);
			DBUtilMysql.execUpdate(sql);
			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}

	// 更新用户密码

	public boolean updatePwd(String userid, String pwd) {
		try {
			StringBuffer sql = new StringBuffer("update pay_user set pwd=\"");
			sql.append(pwd);
			sql.append("\" where userid=\"");
			sql.append(userid);
			sql.append("\" ;");
			logger.info("修改密码：" + sql.toString());
			DBUtilMysql.execUpdate(sql.toString());
			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}

	// 更新用户密码通过userName
	public boolean updatePwdByUserName(String userName, String pwd) {
		try {
			StringBuffer sql = new StringBuffer("update pay_user set pwd=\"");
			sql.append(pwd);
			sql.append("\" where userName=\"");
			sql.append(userName);
			sql.append("\" ;");
			logger.info("修改密码：" + sql.toString());
			DBUtilMysql.execUpdate(sql.toString());
			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}

	// 密码加密
	public UserInfo updateUserInfoPwd(UserInfo userInfo) {
		String pwd = MD5Util.getMD5(userInfo.getPwd());
		userInfo.setPwd(pwd);
		return userInfo;
	}

	// 更新用户信息

	public boolean updateUserInfo(UserInfo userInfo) {
		try {
			StringBuffer sql = new StringBuffer(
					"UPDATE pay_user p, pay_user_cardinfo pc set ");
			if (StringUtil.isNotEmpty(userInfo.getUserName())) {
				sql.append(" p.userName=\"");
				sql.append(userInfo.getUserName());
				sql.append("\" ");
			}
			if (StringUtil.isNotEmpty(userInfo.getUserName())) {
				sql.append(",");
				sql.append(" pc.tel=\"");
				sql.append(userInfo.getUserName());
				sql.append("\" ");
			}
			if (StringUtil.isNotEmpty(userInfo.getName())) {
				sql.append(",");
				sql.append(" p.Name=\"");
				sql.append(userInfo.getName());
				sql.append("\"");
			}

			if (StringUtil.isNotEmpty(userInfo.getIdCard())) {
				sql.append(",");
				sql.append(" p.idCard=\"");
				sql.append(userInfo.getIdCard());
				sql.append("\"");
			}

			sql.append(" where p.userid=\"");
			sql.append(userInfo.getUserid());
			sql.append("\" AND p.userid=pc.userid;");

			logger.info("更新用户信息：" + sql.toString());
			DBUtilMysql.execUpdate(sql.toString());
			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}

	}

	public void delUserInfo(String userid) {
		try {
			StringBuffer sql = new StringBuffer(
					"delete from pay_user where userid=");
			sql.append(userid);
			sql.append(" ;");
			logger.info(sql.toString());
			DBUtilMysql.execUpdate(sql.toString());
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public void delUserInfo(String userName, String pwd) {
		try {
			StringBuffer sql = new StringBuffer(
					"delete from pay_user where userName=\"");
			sql.append(userName);
			sql.append("\"  and pwd=\"");
			sql.append(pwd);
			sql.append("\" ;");
			logger.info(sql.toString());
			DBUtilMysql.execUpdate(sql.toString());
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public boolean updateAwardType(String userid, String isAwardType) {

		try {
			StringBuffer sql = new StringBuffer(
					"update pay_user_cardinfo set IsAwardType=\"");
			sql.append(isAwardType);
			sql.append("\" where userid=\"");
			sql.append(userid);
			sql.append("\" ;");
			logger.info(sql.toString());
			DBUtilMysql.execUpdate(sql.toString());
			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}
	
	// 判断用户是否实名制
		public boolean checkIdCardByUserId(String userId) {
			try {
				String sql = "select idCard from pay_user where userid=\""
						+ userId.trim() + "\" ;";
				logger.info(sql);
				RowSet rs = DBUtilMysql.execQuery(sql);
				while (rs != null & rs.next()) {
					if (StringUtil.isNotEmpty(rs.getString(1))) {
						return true;
					} else {
						return false;
					}
				}
				return false;
			} catch (Exception e) {
				logger.error(e);
				return false;
			}
		}
		
		// 判断用户是否绑定返奖卡
		public boolean checkAwardCardByUserId(String userId) {
			try {
				String sql = "select isAwardCard from pay_user_bankinfo where isAwardCard=1 and userid=\""
						+ userId.trim() + "\" ;";
				logger.info(sql);
				RowSet rs = DBUtilMysql.execQuery(sql);
				while (rs != null & rs.next()) {
					if (StringUtil.isNotEmpty(rs.getString(1))) {
						return true;
					} else {
						return false;
					}
				}
				return false;
			} catch (Exception e) {
				logger.error(e);
				return false;
			}
		}
	/**
	 * =======分割线=============新增上游获取用户信息======================
	 * @return
	 */
	public NClientInfo getUserInfoUp(String token){
		
		NClientInfo user = new NClientInfo();
		int points = 0;
		float validFee = 0;
		logger.info("getUserInfoUp================="+token);
		NClientInfoData data = new UserService().getNClientInfo(token);
		for(NClientInfo info: data.getData().getDetailList()){
			points +=  Integer.parseInt(info.getPoints());
			validFee += Float.parseFloat(info.getValidFee());
		}
		user.setPoints(String.valueOf(points));
		user.setValidFee(String.valueOf(validFee));
		
		return user;
		
	}
	
	/**
	 * 通过接口向上游服务端获取用户信息
	 * @return
	 */
	public NClientInfoData getNClientInfo(String token) {
		try {
			String url = PropertiesContext.getInstance().getUp_url() + "NetworkBet/GetNClientInfo";
			String token_sh = RedisUtil.getValue("token_sh");
			String QueryId = getUserNameByToken(token);
			//String token_sh = "370819648";
			String QueryType = "2";
			String AgentId = "";
			Map<String, Object> reqMap = new HashMap<String, Object>();
			reqMap.put("Token", token_sh);
			reqMap.put("QueryType", QueryType);
			reqMap.put("QueryId", QueryId);
			reqMap.put("AgentId", AgentId);
			String mess = HttpSender.doGet(url, reqMap);
			logger.info(mess);
			return getApiResult(mess);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	public UserInfo getRandomCodeUp(String tel){
		
		UserInfo user = new UserInfo();
		String token_sh = RedisUtil.getValue("token_sh");
		NClientInfoData data = new UserService().getRandomCode(tel);
		if(data.getResult().equals("1")){
			user.setRandomCode(data.getData().getRandomCode());
			user.setUrl(PropertiesContext.getInstance().getUp_url());
			user.setToken(token_sh);
		}
		return user;
	}
	
	/**
	 * 通过接口向上游服务端获取游戏随机码
	 * @return
	 */
	public NClientInfoData getRandomCode(String tel) {
		try {
			String url = PropertiesContext.getInstance().getUp_url() + "NetworkBet/GetRandomCode";
			String token_sh = RedisUtil.getValue("token_sh");
			//String token_sh = "767477151";
			Map<String,Object>reqMap=new HashMap<String,Object>();
			reqMap.put("PhoneNo", tel);
			reqMap.put("Token", token_sh);
			String mess = HttpSender.doGet(url, reqMap);
			logger.info(mess);
			return getApiResult(mess);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	//通过token获取userName
		public String getUserNameByToken(String token)
		{
			if(StringUtil.isNotEmpty(token))
			{
				String userName=RedisUtil.getValueFromMap(token, "userName");
				return userName;
			}
			return null;
		}
	private NClientInfoData getApiResult(String mess) {
		NClientInfoData apiResult = null;
		try {
			apiResult = GsonUtil.getInstense().fromJson(mess, NClientInfoData.class);
		} catch (Exception e) {
			logger.error(e);
		}

		return apiResult;
	}

	public static void main(String args[]) {
		UserInfo data = new UserInfo();
		String tel ="13616533022";
		data = new UserService().getRandomCodeUp(tel);
		System.out.println(data.getRandomCode());
	}
}
