package com.ebs.receiver.trans;

import java.util.List;


import org.apache.log4j.Logger;

import com.ebs.base.handler.LotteryService;
import com.ebs.receiver.comm.FuncUtils;
import com.ebs.receiver.conf.Configuration;
import com.ebs.receiver.domain.PageInfo;
import com.ebs.receiver.domain.Status;
import com.ebs.receiver.domain.TYOrder;
import com.ebs.receiver.domain.WinInfoLevel;
import com.ebs.receiver.util.JSONUtils;
import com.ebs.receiver.util.PackUtil;
import com.junbao.hf.utils.common.StringUtils;

public class GetKaiJiangInfo {

	
	private static Logger logger = Logger.getLogger(GetKaiJiangInfo.class);
	public String work(String msg) {
		try{
			logger.info(msg);
         TYOrder tyOrder = JSONUtils.json2pojo(PackUtil.getJsonBode(msg), TYOrder.class);
         PageInfo pageInfo=tyOrder.getPageInfo();
         if(pageInfo==null)
         {
        	 pageInfo=new PageInfo();
         }
         if(StringUtils.isEmptyOrNullByTrim(pageInfo.getBegin()))
         {
        	 pageInfo.setBegin("1");
         }
         if(StringUtils.isEmptyOrNullByTrim(pageInfo.getNum()))
         {
        	 pageInfo.setNum("10");
         }
         
         List<WinInfoLevel>list=new LotteryService().getWinInfoLevel(tyOrder.getWinInfo().getLotteryCode(),tyOrder.getWinInfo().getPeriodCode(),pageInfo);
         
         if(list.isEmpty())
         {
        	 return FuncUtils.getErrorMsg("0001",Configuration.getGlobalMsg("MSG_0001"));
         }
         
         //查询成功
         tyOrder.getWinInfo().setWinInfoLevelList(list);
         
         //
         int num=new LotteryService().getWinInfoLevelNum(tyOrder.getWinInfo().getLotteryCode(),tyOrder.getWinInfo().getPeriodCode());
         pageInfo.setTotalNum(String.valueOf(num));
         tyOrder.setPageInfo(pageInfo);
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
	
	
	public static void main(String args[])
	{
		
	}
	
}
