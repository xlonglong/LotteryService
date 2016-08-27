package com.ebs.receiver.util;

import org.apache.log4j.Logger;

public class MD5Util {

	private static Logger logger=Logger.getLogger(MD5Util.class);
	public  static String getMD5(String msg)
	{
		try{
			MD5 md5func=new MD5();
			md5func.Init();
			md5func.Update(msg.getBytes("utf-8"));
		return	md5func.asHex();
			
		}catch(Exception e)
		{
			logger.error(e);
		}
		return "";
	}
}
