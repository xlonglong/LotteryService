package com.ebs.receiver.thread;



import com.ebs.receiver.comm.QueueManager;


import com.ebs.receiver.domain.ResponseBean;
import com.ebs.receiver.domain.TaskBean;
import com.ebs.receiver.queue.MessageSyncQueue;

public class ExceptionServeThread extends Thread{


		private String ret;
		
		private TaskBean taskBean;

		public ExceptionServeThread(TaskBean taskBean,String ret) {
			this.taskBean = taskBean;
			this.ret=ret;
		}

		@Override
		public void run() {
			
			MessageSyncQueue resQueue = QueueManager.getResQueue();
				ResponseBean responseBean = new ResponseBean();
				responseBean.setContent(ret);
				// Source source = new Source();
				// Head head = JsonParseUtil.getHead(req);
				// source.setIp(head.getIp().trim());
				// source.setPort(session.)
				// responseBean.setSource(source);
				taskBean.setResponseBean(responseBean);
				// taskBean.setBeanStatus("1");
				resQueue.push(taskBean); 
		}
}
