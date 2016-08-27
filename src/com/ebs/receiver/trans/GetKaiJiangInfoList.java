package com.ebs.receiver.trans;

import java.util.ArrayList;
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

public class GetKaiJiangInfoList {
    private static Logger logger = Logger.getLogger(GetKaiJiangInfo.class);
	public String work(String msg) {
		try{
		logger.info(msg);
         TYOrder tyOrder = JSONUtils.json2pojo(PackUtil.getJsonBode(msg), TYOrder.class);
        List<String>lotteryCodeList=tyOrder.getWinInfo().getLotteryCodeList();
        
        List<WinInfoLevel>list=new ArrayList<WinInfoLevel>();
        
        for(int i=0;i<lotteryCodeList.size();i++)
        {
        	PageInfo pageInfo=new PageInfo();
        	pageInfo.setBegin("1");
        	pageInfo.setNum("1");
            List<WinInfoLevel>list1=new LotteryService().getWinInfoLevel(lotteryCodeList.get(i),"",pageInfo);
        	if(list1.size()>0){
        		list.add(list1.get(0));
        	}
            
        }
          
         if(list.isEmpty())
         {
        	 return FuncUtils.getErrorMsg("0001",Configuration.getGlobalMsg("MSG_0001"));
         }
         
         //查询成功
         tyOrder.getWinInfo().setWinInfoLevelList(list);
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
