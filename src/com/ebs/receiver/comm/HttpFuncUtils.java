package com.ebs.receiver.comm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ebs.receiver.domain.Status;
import com.ebs.receiver.domain.TYOrder;
import com.ebs.receiver.util.GsonUtil;
import com.ebs.receiver.util.JSONUtils;
import com.ebs.receiver.util.PassMsgUtil;


public class HttpFuncUtils {
private static Log logger = LogFactory.getLog(HttpFuncUtils.class);
	
	public static boolean checkStrNotNull(String value) {
    	return (value != null &&  !"null".equals(value) && !"".equals(value));
    }
	
	/***************************************************************************
	 * 方法名:getStringLength 
	 * 功能描述:获取字符的长度,包括汉字的长度 
	 * 参数说明: 返回参数:返回长度 
	 * @throws UnsupportedEncodingException 
	 **************************************************************************/
	public static int getStringLength(String s1) {
		if (null == s1 || s1.equals("")) {
			return 0;
		}
		byte[] byteArray;
		try {
			byteArray = s1.getBytes("GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return 0;
		}
		return byteArray.length;
	}
	
	/***********************************************************
	方法名:stringOfReplaceChar
	功能描述:去掉某些字符，或替代某些字符
	 ************************************************************/
	public static String replaceStr(String str, String regex, String replacement) {
		if (str == null) {
			return str;
		} else {
			return str.replaceAll(regex, replacement);
		}

	}
	
	/**
	 * 对一个字符串的绝对长度进行拆解(如果遇到汉字字符会把它当作两个字符处理)
	 */
	public static String absoluteString(String s, int start, int end) {

		if (s == null || s.equals("")) {
			return "";
		}
		try {
			byte[] byteArray = s.getBytes("GBK");
			byte[] byteArrayCopy = new byte[end - start];
			System.arraycopy(byteArray, start, byteArrayCopy, 0, end - start);
			return new String(byteArrayCopy, "GBK");
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * 对一个字符串的绝对长度进行拆解(如果遇到汉字字符会把它当作两个字符处理)
	 */
	public static String subStringEasy(String s, int pos, int length) {

		if (s == null || s.equals("")) {
			return "";
		}
		try {
			byte[] byteArray = s.getBytes("GBK");
			byte[] byteArrayCopy = new byte[length];
			System.arraycopy(byteArray, pos, byteArrayCopy, 0, length);
			return new String(byteArrayCopy, "GBK");
		} catch (Exception e) {
			return "";
		}
	}
	
	public static void packException(String retCode, String description,HttpServletResponse response) {

		  String ret="";
		  Status status=new Status();
		  status.setErrorCode(retCode);
		  status.setErrorMsg(description);
		  
		  TYOrder tyOrder=new TYOrder();
		  tyOrder.setStatus(status);
	      ret=JSONUtils.obj2json(tyOrder);
		try {
			response.getWriter().write(ret.toString());
			logger.info("响应统一平台：["+ret.toString()+"]");
		} catch (IOException e) {
			logger.error("http连接异常", e);
		}
		
    }
	
	public static String getIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("x-forwarded-for");  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}  
	
	public static void main(String[] args){
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ip=addr.getHostAddress().toString();//��ñ���IP
//		String sync_port = PropertiesContext.instance.getSync_listen_port();
		System.out.println(ip);
	}
}
