package com.ebs.receiver.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.ebs.receiver.comm.JsonParseUtil;
import com.ebs.receiver.comm.QueueManager;
import com.ebs.receiver.conf.PropertiesContext;
import com.ebs.receiver.domain.Header;
import com.ebs.receiver.domain.ResponseBean;
import com.ebs.receiver.domain.TaskBean;
import com.ebs.receiver.queue.MessageSyncQueue;

public class SendFrontServeThread extends Thread{
	private static Logger logger = Logger.getLogger(SendFrontServeThread.class);
	private static int len = Integer.parseInt(PropertiesContext.instance
			.getChnl_maxlen());
	// private static Map reqmap = new HashMap();
	private String ip;
	private String port;
	private TaskBean taskBean;

	public SendFrontServeThread(String ip, String port, TaskBean taskBean) {
		this.ip = ip;
		this.port = port;
		this.taskBean = taskBean;
	}

	@Override
	public void run() {
		String ret = "";
		Socket socket = null;
		String reqMsg = taskBean.getRequestBean().getContent();
		String webStreamid = taskBean.getRequestBean().getWebStreamid();
		try {

			Header head = JsonParseUtil.getHeader(reqMsg);
//			String streamId = head.getStreamid();
//			String tradeType = head.getTradetypecode().trim();
//			String tradeCode = head.getTradecode().trim();
		//	String trade = tradeType + tradeCode;
//			String sync_trade = PropertiesContext.instance.getSync_trade();
//			boolean syncFlag = false;

//			if (FuncUtils.checkStrNotNull(sync_trade)) {
//				if ((trade != null) && (sync_trade.indexOf(trade) != -1))
//					syncFlag = true;
//			} else {
//				logger.warn("分发线程[<sync>配置为空]");
//				FuncUtils.packException("21000003", "销售系统内部错误", this.taskBean);
//			}
//			logger.debug("channelcode["+head.getChannelcode().trim()+"]");
//			//当是统一平台发过来的请求是不需要放到异步等待队列里
//			if (syncFlag) {
//				logger.debug("准备放入异步队列");
////				Map map = PropertiesContext.instance.getMap();
//				Map map = SysDefine.getMap();
//				RequestBean requestBean = this.taskBean.getRequestBean();
//
//				map.put(streamId, requestBean);
//				logger.debug("已经放入异步队列"+"【"+streamId+"】");
//				//启动一个线程去清理放入异步队列的请求
//				new ClearMapThread(streamId).start();
//			}

			// 发送给前置
			socket = new Socket(ip, Integer.parseInt(port));
			socket.setSoLinger(true, 180);
			socket.setSoTimeout(180 * 1000);
			socket.setReceiveBufferSize(len);

			InputStream input = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(input, "UTF-8"));
			OutputStream output = socket.getOutputStream();
			logger.info("[" + webStreamid + "]成功连接具体前置机,地址:[" + ip + "],端口:["+ port + "]");
//			logger.info("[" + webStreamid + "]成功连接具体前置机,地址:[" + ip + "],端口:["+ port + "],发送报文:[" + reqMsg + "]");

			reqMsg = reqMsg + "\r\n";
			output.write(reqMsg.getBytes("UTF-8"));
			output.flush();
			// 读取服务端返回数据
			byte[] temp = new byte[len];
			// int r = input.read(temp);
			ret = br.readLine();
			// 去最后一个回车
			// ret = new String(temp,0,r-1);

//			logger.info("[" + webStreamid + "]成功接收具体前置机,地址:[" + ip + "],端口:["
//					+ port + "],返回报文:[" + ret + "]");

//			if (syncFlag) {
//				logger.info("成功接收具体前置机,地址:[" + ip + "],端口:[" + port+ "],异步交易返回报文:[" + ret + "]");
//				Result result = JsonParseUtil.getResult(ret);
//				// 判断返回是否成功
//				if (!Global.SUCCESS_CODE.equals(result.getReturncode())) {
//					String sconnMsg = SendUtil.dirsendTO("127.0.0.1", PropertiesContext.getInstance().getSync_listen_port(),ret.substring(4));
//					logger.info("异步转同步发送短连接投注受理失败2:[" + sconnMsg + "]");
//				}
//			
//			} else {

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

		//	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("发送到具体前置机异常，报文：[" + reqMsg + "]");
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (Exception e) {
					socket = null;
					logger.error("发送到具体前置机结束关闭session异常：[" + e.getMessage()
							+ "]");
				}
			}
		}
	}
}
