package com.ebs.receiver.trans;

import org.apache.log4j.Logger;

import com.ebs.receiver.comm.FuncUtils;
import com.ebs.receiver.comm.QueueManager;
import com.ebs.receiver.conf.Configuration;
import com.ebs.receiver.domain.ResponseBean;
import com.ebs.receiver.domain.TaskBean;
import com.ebs.receiver.queue.MessageSyncQueue;

import java.lang.reflect.Method;
public class TransProcess {
  
	private static Logger log=Logger.getLogger(TransProcess.class);
	private String RequestType;
	private TaskBean taskBean;
	
	public TransProcess(String tradeCode,TaskBean taskBean)
	{
		this.RequestType=tradeCode;
		this.taskBean=taskBean;
	}
	/***
	 * 根据前置 RequestType 来确定是哪个交易 ex: 80010011
	 * 
	 * **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void process() {
		String msg = taskBean.getRequestBean().getContent();
		String res = "";
		try {
			Class trade = Class.forName("com.ebs.receiver.trans."
					+ this.RequestType);
			Method mpro = trade.getDeclaredMethod("work", String.class);
			res = (String) mpro.invoke(trade.newInstance(), msg);

		} catch (Exception e) {
			// TODO Auto-generated catch block

			res = FuncUtils.getErrorMsg("0019",
					Configuration.getGlobalMsg("MSG_0019"));
			log.info("响应前置：[" + res + "]");
			log.error("系统异常", e);
		}
		MessageSyncQueue resQueue = QueueManager.getResQueue();
		ResponseBean responseBean = new ResponseBean();
		responseBean.setContent(res);
		taskBean.setResponseBean(responseBean);
		resQueue.push(taskBean);
	}
	
	
	
}
