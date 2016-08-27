package com.ebs.receiver.thread;

import java.util.Map;
import org.apache.log4j.Logger;

import com.ebs.receiver.conf.PropertiesContext;
import com.ebs.receiver.socket.mina.service.MinaSotConMonitor;
public class MonitorThread extends Thread {
	private static Logger logger = Logger.getLogger(MonitorThread.class);
	@Override
	public void run() {
		while (true) {
			try {
				Map<String, Thread> threadMap = MinaSotConMonitor.getInstance().getProcessThreadMap();
				try {
					Thread.sleep(Integer.parseInt(PropertiesContext.instance.getMonitor_time()));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//logger.info("线程数："+threadMap.size());
				for (Map.Entry<String, Thread> thread : threadMap.entrySet()) {
					String threadName = thread.getKey();
					Thread process = thread.getValue();
//					System.out.println("threadName:"+threadName+":"+process.isInterrupted());
					if(process == null || !process.isAlive()){ 
						//删除死掉的线程的key
						logger.info("删除死掉的线程的key:"+threadName);
						MinaSotConMonitor.getInstance().getProcessThreadMap().remove(threadName);
	//					System.out.println("threadName:"+threadName);
						if(threadName.equals("requestFiltrateThread")){
							logger.warn("*********重新启动过滤处理线程*********");
							Thread requestFiltrateThread = new RequestFiltrateThread();
							requestFiltrateThread.start();
							MinaSotConMonitor.getInstance().getProcessThreadMap().put("requestFiltrateThread",requestFiltrateThread);
						}else if(threadName.equals("requestDistributeThread")){
							logger.warn("*********重新启动转发处理线程*********");
							Thread requestDistributeThread = new RequestDistributeThread();
							requestDistributeThread.start();
							MinaSotConMonitor.getInstance().getProcessThreadMap().put("requestDistributeThread",requestDistributeThread);
						}else if(threadName.equals("responseHandlerThread")){
							logger.warn("*********重新启动响应处理线程*********");
							Thread responseHandlerThread = new ResponseHandleThread();
							responseHandlerThread.start();
							MinaSotConMonitor.getInstance().getProcessThreadMap().put("responseHandlerThread",responseHandlerThread);
//						}else if(threadName.equals("syncRevThread")){
//							logger.warn("*********重新启动接收前置推送报文监听线程*********");
//							Thread syncRevThread = new SotSyncRevThread();
//							syncRevThread.start();
//							MinaSotConMonitor.getInstance().getProcessThreadMap().put("syncRevThread",syncRevThread);
						}/*else if(threadName.equals("httpRequestFiltrateThread")){
							logger.warn("*********重新启动http响应处理线程*********");
							Thread httpResponseHandlerThread = new HttpResponseHandleThread();
							httpResponseHandlerThread.start();
							MinaSotConMonitor.getInstance().getProcessThreadMap().put("httpResponseHandlerThread",httpResponseHandlerThread);
						}else if(threadName.equals("httpRequestFiltrateThread")){
							logger.warn("*********重新启动http转发处理线程*********");
							Thread httpRequestDistributeThread = new HttpRequestDistributeThread();
							httpRequestDistributeThread.start();
							MinaSotConMonitor.getInstance().getProcessThreadMap().put("httpRequestDistributeThread",httpRequestDistributeThread);
						}else if(threadName.equals("httpRequestFiltrateThread")){
							logger.warn("*********重新启动http过滤处理线程*********");
							Thread httpRequestFiltrateThread = new HttpRequestFiltrateThread();
							httpRequestFiltrateThread.start();
							MinaSotConMonitor.getInstance().getProcessThreadMap().put("httpRequestFiltrateThread",httpRequestFiltrateThread);
						}*/else if(threadName.equals("mapHandleThread")){
							logger.warn("*********重新启动异步session清理线程*********");
							Thread mapHandleThread = new MapHandleThread();
							mapHandleThread.start();
							MinaSotConMonitor.getInstance().getProcessThreadMap().put("mapHandleThread",mapHandleThread);
						}else{
							logger.info("没有匹配的相应的线程["+threadName+"]未做处理");
						}
					}else{
						continue;
					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}