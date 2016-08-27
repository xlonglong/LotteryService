package com.ebs.receiver.trans;

import java.util.List;

import org.apache.log4j.Logger;

import com.ebs.base.handler.LottService;
import com.ebs.base.handler.UserService;
import com.ebs.receiver.comm.FuncUtils;
import com.ebs.receiver.conf.Configuration;
import com.ebs.receiver.domain.Lott;
import com.ebs.receiver.domain.OrderInfo;
import com.ebs.receiver.domain.Status;
import com.ebs.receiver.domain.TYOrder;
import com.ebs.receiver.domain.UserInfo;
import com.ebs.receiver.util.JSONUtils;
import com.ebs.receiver.util.PackUtil;

public class PayMent {

	private static Logger logger=Logger.getLogger(PayMent.class);
	public String work(String msg)
	{
		try{
			
			TYOrder tyOrder = JSONUtils.json2pojo(PackUtil.getJsonBode(msg), TYOrder.class);
	         Lott lott=tyOrder.getLott();	
	         
	         if(!new LottService().isOrderPayment(lott.getOrderId()))
	         {
	        	  return FuncUtils.getErrorMsg("0024", Configuration.getGlobalMsg("MSG_0024"));
	         }
	         //判断订单是否可以支付
	          List<OrderInfo>orderList=new LottService().getOrderInfo(lott.getOrderId());
	         if(orderList.isEmpty())
	         { //订单不存在
	        	 return FuncUtils.getErrorMsg("0022", Configuration.getGlobalMsg("MSG_0022"));
	         }
	         UserInfo userInfo=new UserService().getUserInfo(lott.getUserid());
	         
	         if(userInfo==null)
	         {
	        	 //支付账户不存在
	        	 return FuncUtils.getErrorMsg("0011", Configuration.getGlobalMsg("MSG_0011"));
	         }
	         lott.setLottList(orderList);
	         tyOrder.setLott(lott);
	         tyOrder.setUserInfo(userInfo);
	         
	         Status status=new Status();
	         status.setErrorCode("0000");
	         status.setErrorMsg(Configuration.getGlobalMsg("MSG_0000"));
	       tyOrder.setStatus(status);	
	       tyOrder.setHeader(null);
	       return JSONUtils.obj2json(tyOrder);
	         
			
		}catch(Exception e){
			logger.error(e);
			return FuncUtils.getErrorMsg("9999", Configuration.getGlobalMsg("MSG_9999"));
		}
		
	}
	
}
