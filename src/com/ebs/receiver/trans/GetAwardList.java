package com.ebs.receiver.trans;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ebs.base.handler.LotteryService;
import com.ebs.receiver.comm.FuncUtils;
import com.ebs.receiver.conf.Configuration;
import com.ebs.receiver.domain.LotteryInfo;
import com.ebs.receiver.domain.Status;
import com.ebs.receiver.domain.TYOrder;
import com.ebs.receiver.util.JSONUtils;
import com.ebs.receiver.util.PackUtil;
import com.ebs.receiver.util.StringUtil;

public class GetAwardList {

	private static Logger logger = Logger.getLogger(GetBalance.class);

	public String work(String msg) {
		try {
			logger.info(msg);
			TYOrder tyOrder = JSONUtils.json2pojo(PackUtil.getJsonBode(msg),
					TYOrder.class);
			List<LotteryInfo> lotteryInfoList = tyOrder.getLotteryInfo()
					.getLotteryList();
			List<LotteryInfo> list = new ArrayList<LotteryInfo>();
			for (int i = 0; i < lotteryInfoList.size(); i++) {
				if (StringUtil.isNotEmpty(lotteryInfoList.get(i)
						.getLotteryCode())) {

					LotteryInfo lotteryInfo = new LotteryService()
							.getLotteryInfo(lotteryInfoList.get(i)
									.getLotteryCode(), lotteryInfoList.get(i)
									.getPeriodCode());

					if (lotteryInfo != null) {
						list.add(lotteryInfo);
					}
				}
			}

			if (list.size() == 0) {
				return FuncUtils.getErrorMsg("0001",
						Configuration.getGlobalMsg("MSG_0001"));
			}
			// 查询成功

			tyOrder.getLotteryInfo().setLotteryList(list);
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
