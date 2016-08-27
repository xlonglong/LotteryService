package com.ebs.receiver.trans;

import java.util.Date;

import org.apache.log4j.Logger;

import com.ebs.receiver.comm.FuncUtils;
import com.ebs.receiver.conf.Configuration;
import com.ebs.receiver.domain.Server;
import com.ebs.receiver.domain.Status;
import com.ebs.receiver.domain.TYOrder;
import com.ebs.receiver.util.DateTool;
import com.ebs.receiver.util.JSONUtils;
public class GetDateTime {

	
	private static Logger logger = Logger.getLogger(GetDateTime.class);
	public String work(String msg) {
		
		try{
			logger.info(msg);
         TYOrder tyOrder = new TYOrder();
         Server server=new Server();
         server.setSystemDate(DateTool.parseDate2(new Date()));
         tyOrder.setServer(server);
         Status status=new Status();
         status.setErrorCode("0000");
         status.setErrorMsg(Configuration.getGlobalMsg("MSG_0000"));   
         tyOrder.setStatus(status);
         tyOrder.setHeader(null); 
         return JSONUtils.obj2json(tyOrder);
		}catch(Exception e){
			logger.error(e);
		return FuncUtils.getErrorMsg("9999",Configuration.getGlobalMsg("MSG_9999"));
		}
	}
		
	
}
