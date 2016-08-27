package com.ebs.receiver.trans;

import java.util.List;

import org.apache.log4j.Logger;

import com.ebs.base.handler.LottService;
import com.ebs.base.handler.TokenService;
import com.ebs.receiver.comm.FuncUtils;
import com.ebs.receiver.conf.Configuration;
import com.ebs.receiver.domain.Lott;
import com.ebs.receiver.domain.OrderInfo;
import com.ebs.receiver.domain.Status;
import com.ebs.receiver.domain.TYOrder;
import com.ebs.receiver.util.JSONUtils;
import com.ebs.receiver.util.PackUtil;

public class GetOrder {

	private static Logger logger = Logger.getLogger(GetBalance.class);
	public String work(String msg) {
		try{
			logger.info(msg);
         TYOrder tyOrder = JSONUtils.json2pojo(PackUtil.getJsonBode(msg), TYOrder.class);
         String token = tyOrder.getHeader().getToken();
         Lott lott=tyOrder.getLott();
         String userName = new TokenService().getUserNameByToken(token);
         logger.info(userName+"++++userName+++++++");
         lott.setUserName(userName);
         List<OrderInfo>list=new LottService().getOrderInfo(lott.getOrderId());  
         
        if(list.isEmpty())
        {
        	 return FuncUtils.getErrorMsg("0001",Configuration.getGlobalMsg("MSG_0001"));
        }
         //查询成功
         tyOrder.getLott().setLottList(list);
         Status status=new Status();
         status.setErrorCode("0000");
         status.setErrorMsg(Configuration.getGlobalMsg("MSG_0000"));
         tyOrder.setStatus(status);
         tyOrder.setLott(lott);
         tyOrder.setHeader(null);
         return JSONUtils.obj2json(tyOrder);
		}catch(Exception e){
			logger.error(e);
		 return FuncUtils.getErrorMsg("9999",Configuration.getGlobalMsg("MSG_9999"));
		}
	}
}
