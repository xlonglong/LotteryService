package com.ebs.receiver.comm;
import com.ebs.receiver.domain.Header;

/**
 * Json������ ʵ�ֹ��ܣ� 1����ȡ����ͷ 2����ȡ������ 3����װ����
 * 
 * @author yangyj
 * @since:2012-6-25
 */
public class JsonParseUtil {
	private static String  machineName = "000";// Configuration.getGlobalMsg("sys.machineName");//��λ������
	/**
	 * ��ȡ����ͷ
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Header getHeader(String jsonStr) {
		Header header = new Header();
		int pos = 0;
		String str = FuncUtils.subStringEasy(jsonStr, pos, 4);
		header.setLen(str);
		pos += 4;
		str = FuncUtils.subStringEasy(jsonStr, pos, 6);
		pos += 6;
		header.setTradeType(str);
		str = FuncUtils.subStringEasy(jsonStr, pos, 30);
		pos += 30;
		header.setCommand(str);
//		str = FuncUtils.subStringEasy(jsonStr, pos, 6);
//		pos += 6;
//		header.setChannelcode(str);
//		str = FuncUtils.subStringEasy(jsonStr, pos, 15);
//		pos += 15;
//		header.setIp(str);
//		str = FuncUtils.subStringEasy(jsonStr, pos, 32);
//		pos += 32;
//		header.setMac(str);
		return header;
	}

}