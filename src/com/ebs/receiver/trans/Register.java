package com.ebs.receiver.trans;

import org.apache.log4j.Logger;

import com.ebs.base.handler.TokenService;
import com.ebs.base.handler.UserService;
import com.ebs.receiver.comm.FuncUtils;
import com.ebs.receiver.conf.Configuration;
import com.ebs.receiver.domain.Status;
import com.ebs.receiver.domain.TYOrder;
import com.ebs.receiver.domain.UserInfo;
import com.ebs.receiver.util.JSONUtils;
import com.ebs.receiver.util.PackUtil;

public class Register {

	private static Logger logger = Logger.getLogger(Register.class);

	public String work(String msg) {
		try {
			logger.info(msg);
			TYOrder tyOrder = JSONUtils.json2pojo(PackUtil.getJsonBode(msg),
					TYOrder.class);
			UserService userService = new UserService();
			Status status = new Status();
			// 校验验证码是否超时
			String check[] = userService.checkCheckCode(
					tyOrder.getUserInfo().getUserName()).split("&");
			if (check[0].equals("true")) {
				// 校验验证码是否正确
				if (check[1].equals(tyOrder.getUserInfo().getCheckCode())) {
					// 判断用户是否已经注册
					if (!userService.checkUser(tyOrder.getUserInfo()
							.getUserName())) {
						// 更新密码加密
						tyOrder.setUserInfo(new UserService()
								.updateUserInfoPwd(tyOrder.getUserInfo()));
						UserInfo userInfo = userService.RegistPayUser(tyOrder
								.getUserInfo(), tyOrder.getHeader()
								.getChannel());

						if (userInfo == null) {
							userService.delUserInfo(tyOrder.getUserInfo()
									.getUserName(), tyOrder.getUserInfo()
									.getPwd());
							// 注册失败
							return FuncUtils.getErrorMsg("9999",
									Configuration.getGlobalMsg("MSG_9999"));
						}
						String usernum = "360102001270002"; // 测试账号
						String agentId = "000020";//
						userInfo.setUsernum(usernum);
						userInfo.setAgentId(agentId);
						boolean flag = userService.RegistPayUserCard(userInfo);
						if (!flag) {
							userService.delUserInfo(userInfo.getUserid());
							return FuncUtils.getErrorMsg("9999",
									Configuration.getGlobalMsg("MSG_9999"));
						}
						String token = new TokenService().getToken(
								userInfo.getUserName(), userInfo.getPwd());
						userInfo.setToken(token);
						// 设置 token值缓存
						new TokenService().addUserInfo(token, userInfo);
						tyOrder.setUserInfo(userInfo);
						status.setErrorCode("0000");
						status.setErrorMsg(Configuration
								.getGlobalMsg("MSG_0000"));
						tyOrder.setStatus(status);
						tyOrder.setHeader(null);
						return JSONUtils.obj2json(tyOrder);

					} else {
						// 用户已经注册无需注册
						return FuncUtils.getErrorMsg("0012",
								Configuration.getGlobalMsg("MSG_0012"));
					}

				} else {
					// 验证码错误
					return FuncUtils.getErrorMsg("0017",
							Configuration.getGlobalMsg("MSG_0017"));
				}
			} else {
				// 验证码超时
				return FuncUtils.getErrorMsg("0016",
						Configuration.getGlobalMsg("MSG_0016"));
			}

		} catch (Exception e) {
			logger.error(e);
			return FuncUtils.getErrorMsg("9999",
					Configuration.getGlobalMsg("MSG_9999"));
		}

	}

}
