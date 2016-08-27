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
public class UpdateAwardType {
	private static Logger logger = Logger.getLogger(UpdateAwardType.class);
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
	         if(tyOrder.getUserInfo()==null)
	         {
	        	 tyOrder.setUserInfo(new UserInfo());
	         }
	         tyOrder.getUserInfo().setUserid(userid); 
			UserInfo userInfo=tyOrder.getUserInfo();
			UserService userService=new UserService();
			boolean flag=userService.updateAwardType(userInfo.getUserid(), userInfo.getIsAwardType());
			
			if(!flag)
			{
				//用户名或者密码错误
				return FuncUtils.getErrorMsg("9999", Configuration.getGlobalMsg("MSG_9999"));
			}
			//更新成功
			Status status=new Status();
           status.setErrorCode("0000");
           status.setErrorMsg(Configuration.getGlobalMsg("MSG_0000"));
           tyOrder.setStatus(status);
           tyOrder.setStatus(status);
    	   tyOrder.setHeader(null);
         return JSONUtils.obj2json(tyOrder);
       }catch(Exception e){
			logger.error(e);
			return FuncUtils.getErrorMsg("9999", Configuration.getGlobalMsg("MSG_9999"));
		}
     
	}
		
}
