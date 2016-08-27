package com.ebs.receiver.trans;

import java.util.Random;

import org.apache.log4j.Logger;

import com.ebs.base.handler.UserService;
import com.ebs.receiver.comm.FuncUtils;
import com.ebs.receiver.comm.SendSMSUp;
import com.ebs.receiver.conf.Configuration;
import com.ebs.receiver.domain.Status;
import com.ebs.receiver.domain.TYOrder;
import com.ebs.receiver.domainup.APIResult;
import com.ebs.receiver.util.JSONUtils;
import com.ebs.receiver.util.PackUtil;

public class GetCheckMaskCode {

	private static Logger logger = Logger.getLogger(GetBalance.class);

	public String work(String msg) {
		try {
			logger.info(msg);
			TYOrder tyOrder = JSONUtils.json2pojo(PackUtil.getJsonBode(msg),
					TYOrder.class);
			String tel = tyOrder.getUserInfo().getTel();
			String checkCode = String.valueOf(new Random().nextInt(999999));
			if (checkCode.length() < 6) {
				checkCode = "0" + checkCode;
			}
			boolean flag = new UserService().insertCheckCode(tel, checkCode);
			if (!flag) {
				return FuncUtils.getErrorMsg("0021",
						Configuration.getGlobalMsg("MSG_0021"));
			}
			// 查询成功
			APIResult apiResult = new SendSMSUp().SendCheckCode(tel, checkCode);
			if (apiResult != null) {
				if (!apiResult.getResult().equals("1")) {
					return FuncUtils.getErrorMsg("0021",
							Configuration.getGlobalMsg("MSG_0021"));
				}
			}
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
