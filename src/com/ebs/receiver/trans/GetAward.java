package com.ebs.receiver.trans;

import org.apache.log4j.Logger;

import com.ebs.base.handler.LotteryService;
import com.ebs.receiver.comm.FuncUtils;
import com.ebs.receiver.conf.Configuration;
import com.ebs.receiver.domain.LotteryInfo;
import com.ebs.receiver.domain.Status;
import com.ebs.receiver.domain.TYOrder;
import com.ebs.receiver.util.JSONUtils;
import com.ebs.receiver.util.PackUtil;

public class GetAward {

	private static Logger logger = Logger.getLogger(GetBalance.class);
	public String work(String msg) {
		try{
			logger.info(msg);
         TYOrder tyOrder = JSONUtils.json2pojo(PackUtil.getJsonBode(msg), TYOrder.class);
        
         LotteryInfo lotteryInfo=new LotteryService().getLotteryInfo(tyOrder.getLotteryInfo().getLotteryCode(),tyOrder.getLotteryInfo().getPeriodCode());

          if(lotteryInfo==null)
          {
        	  return FuncUtils.getErrorMsg("0001",Configuration.getGlobalMsg("MSG_0001"));
          }
          //查询成功
         tyOrder.setLotteryInfo(lotteryInfo);
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
