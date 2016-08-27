package com.ebs.receiver.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ebs.receiver.comm.QueueManager;
import com.ebs.receiver.init.SysDefine;
import com.ebs.receiver.socket.mina.service.MinaSotConMonitor;

public class ScannerThread extends Thread{
	private static Log logger = LogFactory.getLog(ScannerThread.class);

	@Override
	public void run() {
		while(true){
			try{
				logger.info("异步通知等待队列大小["+SysDefine.getMap().size()+"]");
				logger.info("程序监控队列["+MinaSotConMonitor.getInstance().getProcessThreadMap().size()+"]");
				logger.info("请求队列大小["+QueueManager.getReqQueue().getCount()+"]");
				logger.info("预处理队列大小["+QueueManager.getPretreatRequestQueue().getCount()+"]");
				logger.info("响应队列大小["+QueueManager.getResQueue().getCount()+"]");
				Thread.sleep(60*1000);
			}catch (Exception e) {
				continue;
				// TODO: handle exception
			}
		}
	}

}
