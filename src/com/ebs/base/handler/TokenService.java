package com.ebs.base.handler;

import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.ebs.receiver.conf.Configuration;
import com.ebs.receiver.domain.UserInfo;
import com.ebs.receiver.util.Base64;
import com.ebs.receiver.util.DateTool;
import com.ebs.receiver.util.MD5Util;
import com.ebs.receiver.util.MapUtils;
import com.ebs.receiver.util.RedisUtil;
import com.ebs.receiver.util.StringUtil;
public class TokenService {

	 Logger logger=Logger.getLogger(TokenService.class);
	public  String getToken(String userName,String pwd)
	{
		try{
			String token=userName+pwd+DateTool.parseDate9(new Date())
					     +new Random().nextInt(99999);
			token=MD5Util.getMD5(token);
			token= Base64.encode(token.getBytes());
			return token;
		}catch(Exception e){
			logger.error(e);
		}
		
		return "";
	}
	
	//添加用户信息到缓存
	public void addUserInfo(String token,UserInfo userInfo)
	{
		Map<String, Object>map=(MapUtils.beanToMap(userInfo));
	    map.put("tokenTime", DateTool.parseDate2(new Date())); 
	    logger.info("更新用户token缓存============="+token);
	    //更新缓存
	    RedisUtil.setMap(token, MapUtils.ObjectMapToMap(map));
	    //设置过期时间
	    int seconds=Integer.parseInt(Configuration.getGlobalMsg("tokendiffTime"));
	    RedisUtil.expire(token, seconds);
	}
	
	
	//通过token获取userid
	public String getUseridByToken(String token)
	{
		if(StringUtil.isNotEmpty(token))
		{
			String userid=RedisUtil.getValueFromMap(token, "userid");
			return userid;
		}
		return null;
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
	
	
	
	public static void main(String args[])
	{
		String userName="18668000823";
		UserInfo userInfo=new UserInfo();
		userInfo.setUserName(userName);
		//new TokenService().addUserInfo("", userInfo);
		
		Map<String, Object>map=(MapUtils.beanToMap(userInfo));
	    map.put("tokenTime", DateTool.parseDate2(new Date())); 
	    
	    for (Object o : map.keySet()) {
	    	   System.out.println("key=" + o + " value=" + map.get(o));
	    	  }
	}
}
