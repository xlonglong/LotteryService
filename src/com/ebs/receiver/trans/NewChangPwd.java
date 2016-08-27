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
import com.ebs.receiver.util.MD5Util;
import com.ebs.receiver.util.PackUtil;

public class NewChangPwd {

	private static Logger logger = Logger.getLogger(NewChangPwd.class);
	public String work(String msg) {
		try{
			logger.info(msg);
         TYOrder tyOrder = JSONUtils.json2pojo(PackUtil.getJsonBode(msg), TYOrder.class);
         String token=tyOrder.getHeader().getToken();
         String userid=new TokenService().getUseridByToken(token);
         if(userid==null)
         {
      	 //用户token值过期或者错误
        	 return FuncUtils.getErrorMsg("0032",Configuration.getGlobalMsg("MSG_0032")); 
         }
         //设置userid;
         tyOrder.getUserInfo().setUserid(userid);
          
         UserInfo userInfo=new UserService().getUserInfo(tyOrder.getUserInfo().getUserid());
          if(userInfo==null)
          {
        	  return FuncUtils.getErrorMsg("0011",Configuration.getGlobalMsg("MSG_0011"));
          }
          
          //查询成功
          //判断用户密码是否正确
       
          if(!userInfo.getPwd().equals(MD5Util.getMD5(tyOrder.getUserInfo().getOldPwd())))
          {
        	  return FuncUtils.getErrorMsg("0014",Configuration.getGlobalMsg("MSG_0014"));  
          }
          
          //更新用户密码
         if(!new UserService().updatePwd(tyOrder.getUserInfo().getUserid(), MD5Util.getMD5(tyOrder.getUserInfo().getPwd())))
         {
        	 //更新用户密码失败
        	 return FuncUtils.getErrorMsg("9999",Configuration.getGlobalMsg("MSG_9999"));   
         }
        
         Status status=new Status();
         status.setErrorCode("0000");
         status.setErrorMsg(Configuration.getGlobalMsg("MSG_0000"));
         tyOrder.setStatus(status);
         tyOrder.setHeader(null);
         return JSONUtils.obj2json(tyOrder);
		}catch(Exception e){
			logger.error(e);
		 return FuncUtils.getErrorMsg("9999",Configuration.getGlobalMsg("MSG_9999"));
		}
	}
	
	public static void main(String args[])
	{
		
		String mess="123456";
		System.out.println(MD5Util.getMD5(mess));
	}
}
