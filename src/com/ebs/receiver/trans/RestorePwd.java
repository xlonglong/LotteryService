package com.ebs.receiver.trans;

import org.apache.log4j.Logger;

import com.ebs.base.handler.UserService;
import com.ebs.receiver.comm.FuncUtils;
import com.ebs.receiver.conf.Configuration;
import com.ebs.receiver.domain.Status;
import com.ebs.receiver.domain.TYOrder;
import com.ebs.receiver.util.JSONUtils;
import com.ebs.receiver.util.PackUtil;

public class RestorePwd {
 private Logger logger=Logger.getLogger(RestorePwd.class);
	public String work(String msg) {
		try{
			logger.info(msg);
			TYOrder tyOrder = JSONUtils.json2pojo(PackUtil.getJsonBode(msg), TYOrder.class);
			UserService userService=new UserService();
        Status status=new Status();
        //校验验证码是否超时
        String check[]=userService.checkCheckCode(tyOrder.getUserInfo().getUserName()).split("&");
        if(check[0].equals("true")){
        	//校验验证码是否正确
        	if(check[1].equals(tyOrder.getUserInfo().getCheckCode()))
        	{
        	 //判断用户是否已经注册
        		if(userService.checkUser(tyOrder.getUserInfo().getUserName()))
        		{
        			//更新密码加密
        			tyOrder.setUserInfo(new UserService().updateUserInfoPwd(tyOrder.getUserInfo()));
        			
        		boolean flag=userService.updatePwdByUserName(tyOrder.getUserInfo().getUserName(),tyOrder.getUserInfo().getPwd());	
        		 if(flag)
        		 {
        			 status.setErrorCode("0000");
                	 status.setErrorMsg(Configuration.getGlobalMsg("MSG_0000"));
                	 tyOrder.setStatus(status);  
                	  tyOrder.setHeader(null);
                	 return JSONUtils.obj2json(tyOrder);
        		 }else{
        			 //注册失败
        			 return FuncUtils.getErrorMsg("9999",Configuration.getGlobalMsg("MSG_9999"));
        		 }
        		}else{
        			//用户未注册
               	 return FuncUtils.getErrorMsg("0011",Configuration.getGlobalMsg("MSG_0011"));
        		}
        		
        		
        	}else{
        		//验证码错误
           	 return FuncUtils.getErrorMsg("0017",Configuration.getGlobalMsg("MSG_0017"));
        	}
          }else{
        	//验证码超时
          	 return FuncUtils.getErrorMsg("0016",Configuration.getGlobalMsg("MSG_0016"));
          }
        
     
		}catch(Exception e){
			logger.error(e);
			return FuncUtils.getErrorMsg("9999",Configuration.getGlobalMsg("MSG_9999"));
		}
         
	}
}
