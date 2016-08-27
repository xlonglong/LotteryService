package com.ebs.receiver.trans;

import java.util.Date;

import org.apache.log4j.Logger;

import com.ebs.base.handler.CatchOrderService;
import com.ebs.base.handler.TokenService;
import com.ebs.base.handler.UserService;
import com.ebs.receiver.comm.FuncUtils;
import com.ebs.receiver.conf.Configuration;
import com.ebs.receiver.domain.Header;
import com.ebs.receiver.domain.Lott;
import com.ebs.receiver.domain.Status;
import com.ebs.receiver.domain.TYOrder;
import com.ebs.receiver.util.DateTool;
import com.ebs.receiver.util.JSONUtils;
import com.ebs.receiver.util.PackUtil;
import com.ebs.receiver.util.RandomCode;
import com.lottery.anteCode.MoneyVerification;

public class CatchCaipiaoTjs {

	private static Logger logger = Logger.getLogger(CatchCaipiaoTjs.class);

	public String work(String msg) {
		try {
			logger.info(msg);
			TYOrder tyOrder = JSONUtils.json2pojo(PackUtil.getJsonBode(msg), TYOrder.class);
			Header header = tyOrder.getHeader();
			Lott lott = tyOrder.getLott();

			String token = header.getToken();
			String userid = new TokenService().getUseridByToken(token);
			String userName = new TokenService().getUserNameByToken(token);
			lott.setUserName(userName);
			if (userid == null || userName == null) {
				// 用户token值过期或者错误
				return FuncUtils.getErrorMsg("0032", Configuration.getGlobalMsg("MSG_0032"));
			}
			// 判断用户是否实名制
			UserService userService = new UserService();
			boolean cardFlag = userService.checkIdCardByUserId(userid);
			if (!cardFlag) {
				// 用户未实名制
				return FuncUtils.getErrorMsg("0121", Configuration.getGlobalMsg("MSG_0121"));
			}
			// 判断
			boolean awardFlag = userService.checkAwardCardByUserId(userid);
			if (!awardFlag) {
				// 用户未绑定返奖卡
				return FuncUtils.getErrorMsg("0122", Configuration.getGlobalMsg("MSG_0122"));
			}

			if (Double.parseDouble(lott.getOrderMoney()) > 20000) {
				// 单笔订单超过2万元，请删减分批投注！
				return FuncUtils.getErrorMsg("0035", Configuration.getGlobalMsg("MSG_0035"));
			}
			// 校验金额是否正确
			double money = 0.0;
			double totalMoney = 0.0;
			MoneyVerification verif = new MoneyVerification();
			for (int i = 0; i < lott.getLottList().size(); i++) {
				String m = verif.handler(lott.getLottList().get(i).getNumber(), "1", lott.getLotteryCode(), lott.getLottList().get(i).getPlayType());
				if (m == null) {
					// 注码格式错误
					return FuncUtils.getErrorMsg("0034", Configuration.getGlobalMsg("MSG_0034"));
				}

				money += Double.parseDouble(m);
			}

			for (int i = 0; i < lott.getList().size(); i++) {
				totalMoney += money * Integer.parseInt(lott.getList().get(i).getMultiple());
			}
			if (totalMoney != Double.parseDouble(lott.getOrderMoney())) {
				// 订单金额错误
				return FuncUtils.getErrorMsg("0023", Configuration.getGlobalMsg("MSG_0023"));
			}

			// 获取订单号
			String orderId = "XSLZH-" + DateTool.parseDate9(new Date()) + RandomCode.getRandomCode();
			lott.setOrderId(orderId);
			lott.setOrderTime(DateTool.parseDate2(new Date()));
			// 插入订单信息
			if (!new CatchOrderService().insertLottInfo(lott, header)) {
				// 插入订单失败
				return FuncUtils.getErrorMsg("9999", Configuration.getGlobalMsg("MSG_9999"));
			}
			// 查询成功
			// tyOrder.setLotteryInfo(lotteryInfo);
			Status status = new Status();
			status.setErrorCode("0000");
			status.setErrorMsg(Configuration.getGlobalMsg("MSG_0000"));
			tyOrder.setStatus(status);
			tyOrder.setLott(lott);
			tyOrder.setHeader(null);
			return JSONUtils.obj2json(tyOrder);
		} catch (Exception e) {
			logger.error(e);
			return FuncUtils.getErrorMsg("9999", Configuration.getGlobalMsg("MSG_9999"));
		}
	}
}
