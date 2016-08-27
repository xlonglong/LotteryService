package com.ebs.receiver.trans;

import com.ebs.receiver.domain.Header;
import com.ebs.receiver.domain.TYOrder;
import com.ebs.receiver.domain.UserInfo;
import com.ebs.receiver.util.JSONUtils;

public class TestJson {

	
	public static void main(String[] args)
	{
		Header head=new Header();
		head.setBetSource("an");
		head.setChannel("1000");
		head.setCommand("1000");
		head.setSaleChannelNo("a0");
		head.setToken("1000");
//		
//		order.setHeader(head);
//	
		UserInfo userInfo=new UserInfo();
		userInfo.setUserid("53");
		TYOrder tyOrder=new TYOrder();
		tyOrder.setUserInfo(userInfo);
//		tyOrder.setHeader(head);
//		System.out.println(JSONUtils.obj2json(tyOrder));
		
		
		String bodyString=JSONUtils.obj2json(tyOrder);
		String headString=JSONUtils.obj2json(head);
		
		String requestMess=getRequest(headString,bodyString);
		
		System.out.println(requestMess);
		TYOrder order=JSONUtils.json2pojo(requestMess, TYOrder.class);
		
		System.out.println(order.getHeader().getChannel());
		
		System.out.println(headString);
	}
	
	public static String getRequest(String headString,String bodyString)
	{
		StringBuffer content=new StringBuffer();
		content.append("{\"Header\":");
		content.append(headString);
		if(!bodyString.equals(""))
		{
			content.append(",");
			content.append(bodyString.subSequence(1, bodyString.length()-1));
		}
		content.append("}");
		return content.toString();
	}
	
}
