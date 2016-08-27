package com.ebs.receiver.trans;

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
import com.ebs.receiver.util.StringUtil;

public class Login {
	private static Logger logger = Logger.getLogger(Login.class);
	public String work(String msg) {
		try{
			logger.info(msg);
			TYOrder tyOrder = JSONUtils.json2pojo(PackUtil.getJsonBode(msg), TYOrder.class);
			  UserInfo userInfo=tyOrder.getUserInfo();
			UserService userService=new UserService();
		 
			if(StringUtil.isEmpty(userInfo.getUserName())||StringUtil.isEmpty(userInfo.getPwd()))
			{
				//用户名或者密码错误
				return FuncUtils.getErrorMsg("0014", Configuration.getGlobalMsg("MSG_0014"));
			}
			
			//更新密码加密
			tyOrder.setUserInfo(new UserService().updateUserInfoPwd(tyOrder.getUserInfo()));
			userInfo=tyOrder.getUserInfo();
			String userName=userInfo.getUserName();
			userInfo=userService.getUserInfo(userInfo.getUserName(), userInfo.getPwd());
			
			if(userInfo==null)
			{
				//判断用户是否注册
				if(!userService.checkUser(userName))
				{
					//用户未注册
					return FuncUtils.getErrorMsg("0011", Configuration.getGlobalMsg("MSG_0011"));	
				}
				
				//用户名或者密码错误
				return FuncUtils.getErrorMsg("0014", Configuration.getGlobalMsg("MSG_0014"));
			}
			//用户登录成功
			Status status=new Status();
            status.setErrorCode("0000");
            status.setErrorMsg(Configuration.getGlobalMsg("MSG_0000"));
            tyOrder.setStatus(status);
            String token=new TokenService().getToken(userInfo.getUserName(),userInfo.getPwd());
            logger.info("生成用户Token============="+token);
    	 	userInfo.setToken(token);
    	 	tyOrder.setUserInfo(userInfo);
    	 	tyOrder.setHeader(null);
    	 	 //设置 token值缓存
   		 new TokenService().addUserInfo(token, userInfo);
         return JSONUtils.obj2json(tyOrder);
         
		}catch(Exception e){
			logger.error(e);
			return FuncUtils.getErrorMsg("9999", Configuration.getGlobalMsg("MSG_9999"));
		}
     
	}
		
}
