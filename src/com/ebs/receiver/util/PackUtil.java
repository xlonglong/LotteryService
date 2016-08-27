package com.ebs.receiver.util;

public class PackUtil {
	/***
	 * 
	 * @param str ��Ҫ���㳤�� �Ĵ���String
	 * @param len ������Ŀ�곤��
	 * @param fillType A:after ��str֮���� B:before ��str֮ǰ����
	 * @param fillStr  ����ʱ�õ����ַ�
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
	 * @param str ��Ҫ���㳤�� �Ĵ���String
	 * @param len ������Ŀ�곤��
	 * @param fillStr  ����ʱ�õ����ַ�
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
