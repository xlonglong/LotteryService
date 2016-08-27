package com.ebs.receiver.util;

public class StringUtil {

	/*判断字符串是否为空
	* 为空返回true;
	* 不为空返回false;
	*/
	public static boolean isEmpty(String s)
	{
		if(s==null||s.length()<=0)
		{
			return true;
		}else{
			return false;
		}
	}
	
	
	/*判断字符串是否为空
	* 为空返回false;
	* 不为空返回true;
	*/
	public static boolean isNotEmpty(String s)
	{
		if(s==null||s.length()<=0)
		{
			return false;
		}else{
			return true;
		}
	}
	
}
