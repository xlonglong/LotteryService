package com.ebs.receiver.comm;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.mina.core.session.IoSession;

import com.ebs.receiver.conf.Configuration;
import com.ebs.receiver.domain.TaskBean;


public class FuncUtils {

	
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
	
	public static void packException(String retCode, String description, TaskBean taskBean) {
//		String retMsg = taskBean.getRequestBean().getContent();
		IoSession session = taskBean.getRequestBean().getConnectionInfo().getSession();
		//String str = retMsg.substring(0, retMsg.lastIndexOf("}"));
		String str="{";
		StringBuffer sb = new StringBuffer(str);
		sb.append("\"Status\": {\"ErrorCode\":\"").append(
				retCode + "\",\"ErrorMsg\":\"").append(
				description + "\"").append("}}");
		session.write(sb.toString());
		session.close(false);
    }
	public static String getErrorMsg(String retcode,String retmsgs){
		String str = "{";
		StringBuffer sb = new StringBuffer(str);
		sb.append("\"Status\": {\"ErrorCode\":\"").append(
				retcode + "\",\"ErrorMsg\":\"").append(
						retmsgs + "\"").append("}}");
		return sb.toString();
	}
	
	public static void main(String[] args){

	System.out.println(getErrorMsg("0011","接口错误"));
		
	}
}
