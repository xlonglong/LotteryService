package com.ebs.receiver.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ebs.receiver.domain.Header;
import com.ebs.receiver.domain.TYOrder;

public class PassMsgUtil {
	private static Log logger = LogFactory.getLog(PassMsgUtil.class);

	public static String getMsg(TYOrder order) {

		String content = "";
		try {
			if (order != null) {
				content = JSONUtils.obj2json(order);
				String len = String.valueOf(content.length());
				order.getHeader().setLen(len);
				content = order.getHeader().doPack() + content;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return content;
	}

	public static String getMsg(Header head, String bodyString) {
		StringBuffer content = new StringBuffer();
		try {
			if (head != null) {

				content.append("{\"Header\":");
				content.append(JSONUtils.obj2json(head));
				if (!bodyString.equals("")) {
					content.append(",");
					content.append(bodyString.subSequence(1,
							bodyString.length() - 1));
				}
				content.append("}");
				//String contentStr = new Base64().encode(content.toString().getBytes());
				String len = String.valueOf(content.length());
				head.setLen(len);
				
				return head.doPack() + content.toString();	
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return content.toString();

	}

	public static String getEncryBody(String mess, String channel) {
		DES des = new DES();

		return des.EncodeCBC(mess);
	}

	/**
	 * У��mac
	 * 
	 * @param msg
	 * @return
	 */
	public static boolean check(TYOrder order) {
		// // У��mac
		// String mac = order.getCheck();
		// String params = order.getParams();
		// logger.info(params);
		// logger.info("�����̺ţ�"+order.getHeader().getChannelcode());
		// Md5Token md5 = Md5Token.getInstance();
		// ChannelLottery channel = (ChannelLottery)
		// ChannelAddressManager.getMap().get(order.getHeader().getChannelcode());
		// if(channel==null){
		// logger.info("�����Ų�����");
		// return false;
		// }
		// String md5Mac = md5.getLongToken(channel.getMd5key() + params
		// + channel.getMd5string());
		// return mac.equals(md5Mac);
		return false;
	}

}
