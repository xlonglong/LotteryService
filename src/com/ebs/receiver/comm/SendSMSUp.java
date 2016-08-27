package com.ebs.receiver.comm;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ebs.receiver.conf.PropertiesContext;
import com.ebs.receiver.domainup.APIResult;
import com.ebs.receiver.util.GsonUtil;
import com.ebs.receiver.util.RedisUtil;



public class SendSMSUp {
	
private static String Url = PropertiesContext.getInstance().getUp_url() + "NetworkBet/SGM";
private static Logger logger = Logger.getLogger(SendSMSUp.class);	
	

	
	public APIResult  SendCheckCode(String mobile,String mobile_code) {
		String ret="";
		String token = RedisUtil.getValue("token_sh");
		//String token="628198882";
	    String contents = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人"); 
	    Map<String,String>reqMap=new HashMap<String,String>();
		reqMap.put("token", token);
		reqMap.put("mobileNo", mobile);
		reqMap.put("contents", contents);
		ret=HttpSender.post(Url, reqMap);
		System.out.println(ret);
		return getApiResult(ret);
		
	}
	
	private APIResult getApiResult(String mess) {
		APIResult apiResult = null;
		try {
			apiResult = GsonUtil.getInstense().fromJson(mess,
					APIResult.class);
		} catch (Exception e) {
			logger.error(e);
		}

		return apiResult;
	}
	
	/*public static void main(String[] args) {
		System.out.println(SendCheckCode("13616533022","238946"));
	}*/

}
