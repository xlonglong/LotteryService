package com.ebs.receiver.test;

import com.ebs.receiver.domain.TYOrder;
import com.ebs.receiver.domain.Version;
import com.ebs.receiver.thread.ServletRequestHandleThread;
import com.ebs.receiver.util.JSONUtils;

public class TestService {

	public static void main(String args[])
	{
		
		String mess="{\"Status\": {\"ErrorCode\":\"0019\",\"ErrorMsg\":\"无效的接口名称\"}}";
		new TestService().getResMess(mess);
	}
	
	public String getResMess(String ret)
	{
		String msg="";
		try{
		TYOrder tyOrder=JSONUtils.json2pojo(ret, TYOrder.class);
		System.out.println(tyOrder.getStatus().getErrorMsg());
		//tyOrder.setHeader(null);
		Version version=new Version();
		version.setApp("123456");
		tyOrder.setVersion(version);
		msg=JSONUtils.obj2json(tyOrder);
		System.out.println(msg);
		}catch(Exception e){
			System.out.println(e);
		}
		return msg;
		
	}
}
