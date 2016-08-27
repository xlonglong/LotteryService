package com.ebs.receiver.trans;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.ebs.base.handler.CatchOrderService;
import com.ebs.receiver.comm.FuncUtils;
import com.ebs.receiver.conf.Configuration;
import com.ebs.receiver.domain.Lott;
import com.ebs.receiver.domain.OrderInfo;
import com.ebs.receiver.domain.Status;
import com.ebs.receiver.domain.TYOrder;
import com.ebs.receiver.util.JSONUtils;
import com.ebs.receiver.util.PackUtil;

public class GetCatchOrder {

	private static Logger logger = Logger.getLogger(GetCatchOrder.class);

	public String work(String msg) {
		try {
			logger.info(msg);
			TYOrder tyOrder = JSONUtils.json2pojo(PackUtil.getJsonBode(msg),
					TYOrder.class);
			Lott lott = tyOrder.getLott();
			List<OrderInfo> list = new CatchOrderService().getOrderInfo(lott
					.getOrderId());
					
			List<OrderInfo> infoList = new ArrayList<OrderInfo>();
			if (!list.isEmpty()){
			String periodCode = list.get(0).getPeriodCode();
			int num = 0;
			int count=0;
			for (int i = 0; i < list.size(); i++) {
				
				if (!periodCode.equals(list.get(i).getPeriodCode())) {
					break;
				}
				num++;
				count+=Integer.parseInt(list.get(i).getCount())/Integer.parseInt(list.get(i).getMultiple());
			}
			lott.setCount(String.valueOf(count));
			for (int j = 0; j < list.size() / num; j++) {
				OrderInfo info = new OrderInfo();
				List<OrderInfo> listInfo = new ArrayList<OrderInfo>();
				listInfo = list.subList((j * num), num * (j + 1));
				info.setLotteryCode(listInfo.get(0).getLotteryCode());
				info.setPeriodCode(listInfo.get(0).getPeriodCode());
				info.setOrderTime(listInfo.get(0).getOrderTime());
				info.setMultiple(listInfo.get(0).getMultiple());
				info.setWinCode(listInfo.get(0).getWinCode());
				info.setWinningStatus(listInfo.get(0).getWinningStatus());
				List<OrderInfo>oList=new ArrayList<OrderInfo>();
				Double amount=0.0;
				Double money=0.0;
				for (int m = 0; m < listInfo.size(); m++) {
					OrderInfo oInfo=new OrderInfo();
					oInfo.setNumber(listInfo.get(m).getNumber());
					oInfo.setPlayType(listInfo.get(m).getPlayType());
					oInfo.setOrderStatus(listInfo.get(m).getOrderStatus());
					oInfo.setMoney(listInfo.get(m).getMoney());
					oInfo.setWinningStatus(listInfo.get(m).getWinningStatus());
					oList.add(oInfo);
					amount+=Double.parseDouble(listInfo.get(m).getWinMoney());
					money+=Double.parseDouble(listInfo.get(m).getMoney());
				}
				info.setList(oList);
				info.setWinMoney(String.valueOf(amount));
				info.setMoney(String.valueOf(money));
				infoList.add(info);
			}
			
			// 查询成功
			
			OrderInfo oInfo=new CatchOrderService().getCatchInfo(lott.getOrderId());
			
			if(oInfo!=null)
			{
				lott.setOrderMoney(oInfo.getMoney());
				lott.setOrderStatus(oInfo.getOrderStatus());
				lott.setType(oInfo.getType());
				lott.setNumber(oInfo.getNumber());
				lott.setCatchNumber(oInfo.getCatchNumber());
				lott.setOrderTime(oInfo.getOrderTime());
				lott.setWinMoney(oInfo.getWinMoney());
				lott.setWinningStatus(oInfo.getWinningStatus());
			}
				tyOrder.setLott(lott);//lott
			}
			tyOrder.getLott().setList(infoList);//CatchInfoList
			Status status = new Status();
			status.setErrorCode("0000");
			status.setErrorMsg(Configuration.getGlobalMsg("MSG_0000"));
			tyOrder.setStatus(status);
			tyOrder.setHeader(null);
			return JSONUtils.obj2json(tyOrder);
		} catch (Exception e) {
			logger.error(e);
			return FuncUtils.getErrorMsg("9999",
					Configuration.getGlobalMsg("MSG_9999"));
		}
	}

}
