package com.ebs.receiver.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jconfig.ConfigurationManager;

import com.ebs.receiver.comm.DateUtil;
import com.ebs.receiver.comm.FuncUtils;
import com.ebs.receiver.comm.JsonParseUtil;
import com.ebs.receiver.comm.QueueManager;
import com.ebs.receiver.conf.Configuration;
import com.ebs.receiver.conf.PropertiesContext;
import com.ebs.receiver.domain.Header;
import com.ebs.receiver.domain.RequestBean;
import com.ebs.receiver.domain.TaskBean;
import com.ebs.receiver.queue.MessageSyncQueue;
/**
 * 
 * 对原始请求队列中的TaskBean进行预处理 目的是实现如下一些功能： 1、日终交易暂停 2、维护交易打回
 * 
 */
public class RequestFiltrateThread extends Thread {
	private static Log logger = LogFactory.getLog(RequestFiltrateThread.class);

	@Override
	public void run() {
		while (true) {
			try {
				MessageSyncQueue reqQueue = QueueManager.getReqQueue();
				TaskBean taskBean = reqQueue.pop();
				if (taskBean == null) {
					try {
//						System.out.println(PropertiesContext.instance.getSleep_time());
						Thread.sleep(Integer.parseInt(PropertiesContext.instance.getSleep_time()));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					new FiltrateDealThread(taskBean, reqQueue).start();
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	private class FiltrateDealThread extends Thread {
		private TaskBean taskBean;
		private MessageSyncQueue reqQueue;

		public FiltrateDealThread(TaskBean taskBean, MessageSyncQueue reqQueue) {
			this.taskBean = taskBean;
			this.reqQueue = reqQueue;
		}

		@Override
		public void run() {
			// 1、处理;2、放入预处理请求队列
			MessageSyncQueue pretreatRequestQueue = QueueManager
					.getPretreatRequestQueue();
			boolean pushRs = false;
			RequestBean requestBean = taskBean.getRequestBean();
//			logger.info("[" + requestBean.getWebStreamid() + "]进入过滤线程");

			long nowTime = System.currentTimeMillis();
			long endTime = Long.parseLong(requestBean.getLiveTime()
					.getEndDateTime());

			taskBean.setBeanStatus("1");

			String kickback_begin = PropertiesContext.instance
					.getKickback_begin();
			String kickback_end = PropertiesContext.instance.getKickback_end();
			String kickback_trade = PropertiesContext.instance.getKickback_trade();

			if (requestBean.getContent().startsWith("@")) {
				String command = requestBean.getContent().substring(1);
				logger.info(command);
				if (command.equals("reflash")) {
					logger.info("重新加载配置文件");
					PropertiesContext.setCm(ConfigurationManager.getInstance());
					PropertiesContext.setConfiguration(ConfigurationManager
							.getConfiguration("ebs"));
					PropertiesContext propertiesContext = new PropertiesContext();
					PropertiesContext.setInstance(propertiesContext);
					requestBean.getConnectionInfo().getSession().write(
							"10000000");
					requestBean.getConnectionInfo().getSession().close(false);
				}
				return;
			}

			// 判断超时
			if (endTime - nowTime > 0) {
				// 判断交易类型
				Header head = JsonParseUtil.getHeader(requestBean
						.getContent());
				String tradetypecode = head.getTradeType().trim();
				String tradecode = head.getCommand().trim();
				String trade =  tradecode;
				logger.info("-----------trade-----------"+trade);
				// 判断交易区间
				if (DateUtil.isAllowTradeTime(kickback_begin, kickback_end)) {
					
			        String province_kickback_state = PropertiesContext.instance.getProvince_kickback_state();
			        //此处判断在系统维护时间外因上游渠道故障或升级等原因需要额外打回交易
					if("true".equals(province_kickback_state)){
						String province_kickback_begin = PropertiesContext.instance.getProvince_kickback_begin();
						String province_kickback_end = PropertiesContext.instance.getProvince_kickback_end();
						if(!DateUtil.isAllowTradeTime(province_kickback_begin, province_kickback_end)){
							String province_kickback_trade = PropertiesContext.instance.getProvince_kickback_trade();
							if(FuncUtils.checkStrNotNull(province_kickback_trade)){
								if (province_kickback_trade.indexOf(trade) != -1) {
									// 打回交易，返回
									FuncUtils.packException("0020", Configuration.getGlobalMsg("MSG_0020"), taskBean);
									return;
								}
							}
						}
					}

					boolean allowFlag = false;

					String allow_trade = PropertiesContext.instance.getAllow();
					if (FuncUtils.checkStrNotNull(allow_trade)) {
						if (allow_trade.indexOf(trade) != -1) {
							allowFlag = true;
						}
					} else {
						logger.warn("过滤线程[<allow>配置为空]");
						FuncUtils.packException("9999",
								Configuration.getGlobalMsg("MSG_9999"), taskBean);
					}
					if (!allowFlag) {
						logger.info("[" + requestBean.getWebStreamid()
								+ "]被过滤,报文为：["
										+ requestBean.getContent() + "]");
						FuncUtils.packException("0019",
								Configuration.getGlobalMsg("MSG_0019"), taskBean);
					}else{
//						taskBean.setBeanStatus(Global.STATUS_1+"");
						pushRs = pretreatRequestQueue.push(taskBean);
						if (!pushRs) {
							logger.error("过滤线程[" + taskBean.getRequestBean().getWebStreamid() 
									+ "]插入队列失败,当前队列数:" + pretreatRequestQueue.getCount());
							FuncUtils.packException("0018", Configuration.getGlobalMsg("MSG_0018"), taskBean);
							return;
						}
					}
				}else{
					if (FuncUtils.checkStrNotNull(kickback_trade)) {
						// 剔除交易从配置文件获取，具体交易待定
						kickback_trade = PropertiesContext.getInstance().getKickback_trade();
						String kickbacksms_trade = PropertiesContext.getInstance().getKickbacksms_trade();
						if (FuncUtils.checkStrNotNull(kickback_trade)) { 
							// 判断是否需要打回
							if (kickback_trade.indexOf(trade) != -1) {
								// 打回交易，返回
								FuncUtils.packException("0020", Configuration.getGlobalMsg("MSG_0020"), taskBean);
								return;
							} else {// 不需要打回，继续执行
//								taskBean.setBeanStatus(Global.STATUS_1+"");
								pushRs = pretreatRequestQueue.push(taskBean);
								if (!pushRs) {
									logger.error("过滤线程[" + taskBean.getRequestBean().getWebStreamid() 
											+ "]插入队列失败,当前队列数:" + pretreatRequestQueue.getCount());
									FuncUtils.packException("0018", Configuration.getGlobalMsg("MSG_0018"), taskBean);
									return;
								}
							}
						} 
					}else {
						pushRs = pretreatRequestQueue.push(taskBean);
						if (!pushRs) {
							logger.error("过滤线程["  + taskBean.getRequestBean().getWebStreamid() 
									+ "]插入队列失败,当前队列数:"  + pretreatRequestQueue.getCount());
							FuncUtils.packException("0018", Configuration.getGlobalMsg("MSG_0018"), taskBean);
							return;
						}
					}
//					logger.info("[" + requestBean.getWebStreamid() + "]"+"��ʱ��β����?��,����Ϊ��[" + requestBean.getContent() + "]");
//			        FuncUtils.packException(Global.CODE_21000006, Global.MSG_21000006, taskBean);
				}
			}else{
				logger.info("报文超时，"+"报文为：[" + requestBean.getContent() + "]");
		        FuncUtils.packException("0004", Configuration.getGlobalMsg("MSG_0004"), taskBean);
			}
		}
	}
}
