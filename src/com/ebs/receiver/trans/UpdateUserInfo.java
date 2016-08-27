package com.ebs.receiver.trans;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ebs.base.handler.TokenService;
import com.ebs.base.handler.UserService;
import com.ebs.receiver.comm.FuncUtils;
import com.ebs.receiver.conf.Configuration;
import com.ebs.receiver.domain.Status;
import com.ebs.receiver.domain.TYOrder;
import com.ebs.receiver.domain.UserInfo;
import com.ebs.receiver.util.JSONUtils;
import com.ebs.receiver.util.PackUtil;
import com.ebs.receiver.util.RedisUtil;
import com.ebs.receiver.util.StringUtil;

public class UpdateUserInfo {

	public static Logger logger=Logger.getLogger(UpdateUserInfo.class);
	public String work(String msg)
	{
		logger.info(msg);
		try{
		UserService userService=new UserService();
		TYOrder tyOrder = JSONUtils.json2pojo(PackUtil.getJsonBode(msg), TYOrder.class);
		 String token=tyOrder.getHeader().getToken();
		 Map<String,String>userMap=RedisUtil.getMap(token);
      //   String userid=new TokenService().getUseridByToken(token);
		 String userid=userMap.get("userid");
         if(userid==null)
         {
      	 //用户token值过期或者错误
        	 return FuncUtils.getErrorMsg("0032",Configuration.getGlobalMsg("MSG_0032")); 
         }
         //设置userid;
         if(tyOrder.getUserInfo()==null)
         {
        	 tyOrder.setUserInfo(new UserInfo());
         }
         tyOrder.getUserInfo().setUserid(userid); 
		
		//判断用户是否已经注册
		UserInfo userInfo=userService.getUserInfo(tyOrder.getUserInfo().getUserid());
		if(userInfo==null)
		{
			 return FuncUtils.getErrorMsg("0011",Configuration.getGlobalMsg("MSG_0011")); 
		}
		
		if(StringUtil.isNotEmpty(tyOrder.getUserInfo().getTel()))
		{
			userInfo.setUserName(tyOrder.getUserInfo().getTel());
		}
		if(StringUtil.isNotEmpty(tyOrder.getUserInfo().getName()))
		{
			userInfo.setName(tyOrder.getUserInfo().getName());
		}
		if(StringUtil.isNotEmpty(tyOrder.getUserInfo().getIdCard()))
		{
			userInfo.setIdCard(tyOrder.getUserInfo().getIdCard());
		}
		
		boolean flag=userService.updateUserInfo(userInfo);
		if(!flag)
		{
			//更新用户信息异常
			return FuncUtils.getErrorMsg("9999",Configuration.getGlobalMsg("MSG_9999")); 	
		}
		
		//获取缓存内容，更新缓存
		
		if(StringUtil.isNotEmpty(tyOrder.getUserInfo().getTel()))
		{
			userMap.put("userName", tyOrder.getUserInfo().getTel());
			userMap.put("tel", tyOrder.getUserInfo().getTel());
		}else{
			userInfo.setTel(userMap.get("userName"));
		}
		if(StringUtil.isNotEmpty(tyOrder.getUserInfo().getName()))
		{
			userMap.put("name", userInfo.getName());
		}
		if(StringUtil.isNotEmpty(tyOrder.getUserInfo().getIdCard()))
		{
			userMap.put("idCard", userInfo.getIdCard());
		}
		//更新缓存
		RedisUtil.setMap(token, userMap);
		
		Status status=new Status();
		status.setErrorCode("0000");
		status.setErrorMsg(Configuration.getGlobalMsg("MSG_0000"));
		tyOrder.setStatus(status);
		tyOrder.setUserInfo(userInfo);
		tyOrder.setHeader(null);
		return JSONUtils.obj2json(tyOrder);
		}catch(Exception e)
		{
			//数据处理异常
			return FuncUtils.getErrorMsg("9999",Configuration.getGlobalMsg("MSG_9999")); 		
		}
		
	}
	
}
