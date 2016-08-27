package com.ebs.receiver.trans;
import java.util.List;

import org.apache.log4j.Logger;

import com.ebs.base.handler.LottService;
import com.ebs.base.handler.TokenService;
import com.ebs.receiver.comm.FuncUtils;
import com.ebs.receiver.conf.Configuration;
import com.ebs.receiver.domain.Lott;
import com.ebs.receiver.domain.OrderInfo;
import com.ebs.receiver.domain.PageInfo;
import com.ebs.receiver.domain.Status;
import com.ebs.receiver.domain.TYOrder;
import com.ebs.receiver.util.JSONUtils;
import com.ebs.receiver.util.PackUtil;
import com.junbao.hf.utils.common.StringUtils;

public class GetOrders {
	private static Logger logger = Logger.getLogger(GetOrders.class);
	public String work(String msg) {
		try{
			logger.info(msg);
         TYOrder tyOrder = JSONUtils.json2pojo(PackUtil.getJsonBode(msg), TYOrder.class);
         String token=tyOrder.getHeader().getToken();
         String userid=new TokenService().getUseridByToken(token);
         if(userid==null)
         {
      	 //用户token值过期或者错误
        	 return FuncUtils.getErrorMsg("0032",Configuration.getGlobalMsg("MSG_0032")); 
         }
          Lott lott=tyOrder.getLott();
          if(lott==null)
          {
        	  lott=new Lott();
          }
          lott.setUserid(userid);
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
         List<OrderInfo>list=new LottService().getOrderInfoList(lott,pageInfo);  
         
        if(list.isEmpty())
        {
        	 return FuncUtils.getErrorMsg("0001",Configuration.getGlobalMsg("MSG_0001"));
        }
        
        int totalNum=new LottService().getOrderInfoListNum(lott);
        pageInfo.setTotalNum(String.valueOf(totalNum));
     
          //查询成功
         tyOrder.getLott().setLottList(list);
         tyOrder.setPageInfo(pageInfo);
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
