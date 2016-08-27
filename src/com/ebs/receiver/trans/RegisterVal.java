package com.ebs.receiver.trans;

import org.apache.log4j.Logger;

import com.ebs.base.handler.UserService;
import com.ebs.receiver.comm.FuncUtils;
import com.ebs.receiver.conf.Configuration;
import com.ebs.receiver.domain.Status;
import com.ebs.receiver.domain.TYOrder;
import com.ebs.receiver.util.JSONUtils;
import com.ebs.receiver.util.PackUtil;

public class RegisterVal {

	private static Logger logger = Logger.getLogger(RegisterVal.class);


	public String work(String msg) {
		
		try{
			logger.info(msg);
         TYOrder tyOrder = JSONUtils.json2pojo(PackUtil.getJsonBode(msg), TYOrder.class);
         String userName=tyOrder.getUserInfo().getUserName();
        UserService userService=new UserService();
        Status status=new Status();
         if(userService.checkUser(userName))
         {
        	 status.setErrorCode("0012");
        	 status.setErrorMsg(Configuration.getGlobalMsg("MSG_0012"));
        	
         }else{
        	 //没有数据
        	 status.setErrorCode("0011");
        	 status.setErrorMsg(Configuration.getGlobalMsg("MSG_0011"));
         }
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
		System.out.println(Configuration.getGlobalMsg("MSG_0000"));
	}
	
	
}
