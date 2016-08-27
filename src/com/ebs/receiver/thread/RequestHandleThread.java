package com.ebs.receiver.thread;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.session.IoSession;

import com.junbao.hf.utils.common.DateUtils;
import com.ebs.receiver.comm.FuncUtils;
import com.ebs.receiver.comm.JsonParseUtil;
import com.ebs.receiver.comm.QueueManager;
import com.ebs.receiver.conf.Configuration;
import com.ebs.receiver.conf.PropertiesContext;
import com.ebs.receiver.domain.ConnectionInfo;
import com.ebs.receiver.domain.Header;
import com.ebs.receiver.domain.LiveTime;
import com.ebs.receiver.domain.RequestBean;
import com.ebs.receiver.domain.TaskBean;
import com.ebs.receiver.queue.MessageSyncQueue;

/**
 * 
 * 完成mina接收到的请求转换成TaskBean放入reqQueue中
 */
public class RequestHandleThread extends Thread {
	private static Log logger = LogFactory.getLog(RequestHandleThread.class);

	private String reqMsg;
	private IoSession session;

	public RequestHandleThread(String reqMsg, IoSession session) {
		this.reqMsg = reqMsg;
		this.session = session;
	}

	@Override
	public void run() {
		Header head = JsonParseUtil.getHeader(reqMsg);
		// 处理	
		String inQueueTime = DateUtils.getFormatCurrDate("yyyyMMddHHmmss");

		TaskBean taskBean = new TaskBean();
		RequestBean requestBean = new RequestBean();
		ConnectionInfo connInfo = new ConnectionInfo();
		connInfo.setSession(session);
		requestBean.setConnectionInfo(connInfo);
		requestBean.setContent(reqMsg);
		requestBean.setRequestTime(inQueueTime);
		requestBean.setIsNeedRecord(true);

		LiveTime liveTime = new LiveTime();
		liveTime.setStartDateTime(String.valueOf(System.currentTimeMillis()));
		long timeout = Long.parseLong(PropertiesContext.instance.getTimeout());
		long endDateTime = Long.parseLong(liveTime.getStartDateTime())+ timeout;
		liveTime.setEndDateTime(String.valueOf(endDateTime));
		requestBean.setLiveTime(liveTime);
		// 同步
		requestBean.setIsSync(true);

		taskBean.setRequestBean(requestBean);

		taskBean.setBeanStatus("0");
		MessageSyncQueue reqQueue = QueueManager.getReqQueue();

		if (reqQueue.isToAlert()) {
			FuncUtils.packException("0018",Configuration.getGlobalMsg("MSG_0018"), taskBean);
		}
		try {
			boolean pushRs = reqQueue.push(taskBean);
			if (!pushRs) {
				logger.error("(短连接)接收["
//						+ taskBean.getRequestBean().getSource().getSourceName()
						+ "]入队列MessageSyncQueue是失败,当前队列数:" + reqQueue.getCount()
							+ "请求时间:" +  taskBean.getRequestBean().getRequestTime());
				FuncUtils.packException("0018",Configuration.getGlobalMsg("MSG_0018"), taskBean);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}
}
