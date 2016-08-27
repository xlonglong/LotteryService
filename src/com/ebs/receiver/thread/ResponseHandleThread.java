package com.ebs.receiver.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ebs.receiver.comm.FuncUtils;
import com.ebs.receiver.comm.JsonParseUtil;
import com.ebs.receiver.comm.QueueManager;
import com.ebs.receiver.conf.PropertiesContext;
import com.ebs.receiver.domain.Header;
import com.ebs.receiver.domain.RequestBean;
import com.ebs.receiver.domain.ResponseBean;
import com.ebs.receiver.domain.TaskBean;
import com.ebs.receiver.queue.MessageSyncQueue;

public class ResponseHandleThread extends Thread{

	private static Log logger = LogFactory.getLog(ResponseHandleThread.class);
	
	@Override
	public void run() {
		while (true){
			try{
				MessageSyncQueue resQueue = QueueManager.getResQueue();
				TaskBean taskBean = resQueue.pop();
				if(taskBean == null){
					try {
						Thread.sleep(Integer.parseInt(PropertiesContext.instance.getSleep_time()));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					// 1、解析;2、把返回发送给请求方。
					new ResponseDealThread(taskBean).start();
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}
	
	private class ResponseDealThread extends Thread{
		private TaskBean taskBean;
		
		public ResponseDealThread(TaskBean taskBean){
			this.taskBean = taskBean;
		}
		
		@Override
		public void run(){
			// 增加文件判断
			RequestBean requestBean = taskBean.getRequestBean();
//			logger.info("[" + requestBean.getWebStreamid() + "]进入响应线程");

			taskBean.setBeanStatus("2");

			ResponseBean responseBean = taskBean.getResponseBean();
			String retMsg = responseBean.getContent();
			Header head = JsonParseUtil.getHeader(retMsg);
			String tradetypecode = head.getTradeType().trim();
			String tradecode = head.getCommand().trim();
			//String trade = tradetypecode + tradecode;
//			String file_trade = PropertiesContext.instance.getFile_trade();
//			boolean fileFlag = false;
//			 
//			if (FuncUtils.checkStrNotNull(file_trade)) {
//				if (file_trade.indexOf(trade) != -1) {
//					fileFlag = true;
//				}
//			} else {
//				logger.warn("响应线程[<文件>配置为空]");
//				FuncUtils.packException(Global.CODE_21000003, Global.MSG_21000003, taskBean);
//			}
			
		//	Result result = JsonParseUtil.getResult(retMsg);
			// 判断返回是否成功
//			if (Global.SUCCESS_CODE.equals(result.getReturncode())) {
//				if (fileFlag) {
//					
//					String fileName = retMsg.substring(retMsg.indexOf("returnfilename:"));
//					fileName = fileName.substring(16, fileName.indexOf("\""));
//					String fileUrl = PropertiesContext.instance.getFile_dir();
//					String file_ip = PropertiesContext.instance.getFile_ip();
//					String file_getUtil = PropertiesContext.instance.getFile_getutil();
//					String port = PropertiesContext.instance.getFile_port();
//					String comStr = file_getUtil + " get " + fileUrl + fileName + " " + fileName + " " + file_ip + " " + port;
//					try {
//						
//						new GetFileUtil().fileClient(comStr);
//					} catch (IOException e) {
//						logger.error("获取" + fileName + "文件失败!");
//						FuncUtils.packException(Global.CODE_21000003, Global.MSG_21000003, taskBean);
//						e.printStackTrace();
//					}
//				}
//			}
			
			logger.info("[" + taskBean.getRequestBean().getWebStreamid() + "]MinaReturn");
//			Header header = JsonParseUtil.getHeader(retMsg);
//			if(header.getChannelcode().trim().equals(Global.SMS_CHNL)){
//				String destinationIp = PropertiesContext.instance.getSms_sender_address();
//				String destinationPort = PropertiesContext.instance.getSms_sender_port();
//				SendUtil.distrConn(destinationIp, destinationPort, retMsg);
//			}else{
			requestBean.getConnectionInfo().getSession().write(retMsg);
			try {
				sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			requestBean.getConnectionInfo().getSession().close(false);
//			}
		}
	}
}

	
	
	

