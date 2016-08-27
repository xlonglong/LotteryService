package com.ebs.receiver.util;

public class PackUtil {
	/***
	 * 
	 * @param str 需要补足长度 的传入String
	 * @param len 补足后的目标长度
	 * @param fillType A:after 在str之后补足 B:before 在str之前补足
	 * @param fillStr  补足时用到的字符
	 * @return String
	 * ***/
	public static String fill(String str,int len,String fillType,String fillStr){
		String retstr = "";
		StringBuffer sb = new StringBuffer();
		if(null == str){
			str = "";
		}
		if(str.length()<=len){
			for (int i = 0; i < len-str.length(); i++) {
				sb.append(fillStr);
			}
			 
			if(null != fillType){
				if(fillType.equals("B")){
					retstr = sb.toString() + str ;
				}else
				if(fillType.equals("A")){
					retstr = str + sb.toString();
				}else{
					retstr = sb.toString() + str ;
				}
			}
		}else{
			retstr = sb.append(str.substring(0, len)).toString();
			}
		
		return retstr;
	}
	/*** 
	 * @param str 需要补足长度 的传入String
	 * @param len 补足后的目标长度
	 * @param fillStr  补足时用到的字符
	 * @return String
	 * ***/
	public static String fill(String str,int len, String fillStr){
		String retstr = "";
		StringBuffer sb = new StringBuffer();
		if(null == str){
			str = "";
		}
		if(str.length()<=len){
			for (int i = 0; i < len-str.length(); i++) {
				sb.append(fillStr);
			}
			retstr = sb.toString() + str ;
		}else{
			retstr = sb.append(str.substring(0, len)).toString();
			}
		
		return retstr;
	}

	public static String subString(String src, int begin, int end) {
		byte[] bytetmp = new byte[end - begin];
		BytesCopy(src.getBytes(), bytetmp, begin, end, 0);
		return new String(bytetmp);
	}
	public static void BytesCopy(byte[] src, byte[] des, int srcBegin,
			int srcEnd, int desBegin) {
		int val = 0;
		for (int i = srcBegin; i < srcEnd; i++) {
			des[desBegin + val] = src[i];
			val++;
		}

	}
	public static String getJsonBode(String src){
		return src.substring(src.indexOf("{"),src.length());
	}
	public static void main(String[] args) {
	}
}
