package com.ebs.receiver.thread;

import com.ebs.receiver.comm.FuncUtils;
import com.ebs.receiver.comm.JsonParseUtil;
import com.ebs.receiver.comm.QueueManager;
import com.ebs.receiver.conf.Configuration;
import com.ebs.receiver.conf.PropertiesContext;
import com.ebs.receiver.domain.Destination;
import com.ebs.receiver.domain.Header;
import com.ebs.receiver.domain.RequestBean;
import com.ebs.receiver.domain.ResponseBean;
import com.ebs.receiver.domain.TaskBean;
import com.ebs.receiver.queue.MessageSyncQueue;
/**
 * 
 * 对预处理队列中的请求TaskBean进行处理 实现以下功能： 
 * 1、读取配置 
 * 2、进行分发、等待同步返回 
 * 3、收到返回后解析放入返回队列中
 * 
 */
public class RequestDistributeThread extends Thread {

	@Override
	public void run() {
		
		while (true){
			try{
				MessageSyncQueue preReqQueue = QueueManager.getPretreatRequestQueue();
				TaskBean taskBean = preReqQueue.pop();
				if(taskBean == null){
					try {
						Thread.sleep(Integer.parseInt(PropertiesContext.instance.getSleep_time()));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					// 1、处理;2、发给前置
					RequestBean requestBean = taskBean.getRequestBean();
					String reqMsg = requestBean.getContent();
					Header head = JsonParseUtil.getHeader(reqMsg);
					String tradetypecode = head.getTradeType().trim();
					
					String tradecode = head.getCommand().trim();
					//tradecode = tradetypecode + tradecode;
					
//					if(Global.CAT_CHNL.contains(head.getChannelcode().trim())&&(PropertiesContext.instance.getUniisync_trade().contains(tradecode))){
//						
//					}else{
//						String sync_trade = PropertiesContext.instance.getSync_trade();
//						if(sync_trade.contains(tradecode)){
//							InetAddress addr = null;
//							try {
//								addr = InetAddress.getLocalHost();
//							} catch (UnknownHostException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//							String ip=addr.getHostAddress().toString();//��ñ���IP
//							String sync_port = PropertiesContext.instance.getSync_listen_port();
////							reqMsg = taskBean.getRequestBean().getContent() + "@@" + ip + "@@" + sync_port;
//							taskBean.getRequestBean().setContent(reqMsg);
//						}
//					}
					Destination des = new Destination();
					if(tradetypecode.equals("BASE")){
					
					des.setDestinationName(tradetypecode);
					new TransThread(tradecode,taskBean).run();
					}
					else if (tradetypecode.equals(PropertiesContext.instance.getInfo_trade())) {
						//待定从配置文件获取
						String address = PropertiesContext.instance.getInfo_address();
						String port = PropertiesContext.instance.getInfo_port();
						des.setIp(address);
						des.setPort(port);
	//					ConnectUtil.distrConn(PropertiesContext.instance.getBase_address(), PropertiesContext.instance.getBase_port(), taskBean);
						new SendFrontServeThread(address, port, taskBean).start();
					}
					else if (tradetypecode.equals(PropertiesContext.instance.getAcct_trade())) {
						//待定从配置文件获取
						String address = PropertiesContext.instance.getAcct_address();
						String port = PropertiesContext.instance.getAcct_port();
						des.setIp(address);
						des.setPort(port);
	//					ConnectUtil.distrConn(PropertiesContext.instance.getBase_address(), PropertiesContext.instance.getBase_port(), taskBean);
						new SendFrontServeThread(address, port, taskBean).start();
					}else{
						
						// 无效接口名称
						String ret=FuncUtils.getErrorMsg("0019", Configuration.getGlobalMsg("MSG_0019"));
						new ExceptionServeThread(taskBean,ret).start();
						
						
					}
					
					
					//else if (PropertiesContext.instance.getAccount_trade().contains(tradetypecode)) {
//						String address = PropertiesContext.instance.getAccount_address();
//						String port = PropertiesContext.instance.getAccount_port();
//						des.setIp(address);
//						des.setPort(port);
//						new SendFrontServeThread(address, port, taskBean).start();
//					} else if (PropertiesContext.instance.getBiz_trade().contains(tradetypecode)) {
//						String address = PropertiesContext.instance.getBiz_address();
//						String port = PropertiesContext.instance.getBiz_port();
//						des.setIp(address);
//						des.setPort(port);
//						new SendFrontServeThread(address, port, taskBean).start();
//					} 
//					
//					else if (tradetypecode.equals(PropertiesContext.instance.getOper_trade())) {
//						String address = PropertiesContext.instance.getOper_address();
//						String port = PropertiesContext.instance.getOper_port();
//						des.setIp(address);
//						des.setPort(port);
//						new SendFrontServeThread(address, port, taskBean).start();
//					} else if (PropertiesContext.instance.getAgent_trade().contains(tradetypecode)) {
//						String address = PropertiesContext.instance.getAgent_address();
//						String port = PropertiesContext.instance.getAgent_port();
//						des.setIp(address);
//						des.setPort(port);
//						new SendFrontServeThread(address, port, taskBean).start();
//					}else if (tradetypecode.equals(PropertiesContext.instance.getAlipay_trade())) {
//						String address = PropertiesContext.instance.getAlipay_address();
//						String port = PropertiesContext.instance.getAlipay_port();
//						des.setIp(address);
//						des.setPort(port);
//						new SendFrontServeThread(address, port, taskBean).start();
//					}else if (PropertiesContext.instance.getGuaka_trade().equals(tradetypecode)) {
//						String address = PropertiesContext.instance.getGuaka_address();
//						String port = PropertiesContext.instance.getGuaka_port();
//						des.setIp(address);
//						des.setPort(port);
//						new SendFrontServeThread(address, port, taskBean).start();
//					} else if (tradetypecode.equals(PropertiesContext.instance.getLife_trade())) {
//						String address = PropertiesContext.instance.getLife_address();
//						String port = PropertiesContext.instance.getLife_port();
//						des.setIp(address);
//						des.setPort(port);
//						new SendFrontServeThread(address, port, taskBean).start();
//					} else if (tradetypecode.equals(PropertiesContext.instance.getSffast_trade())) {
//						String address = PropertiesContext.instance.getSffast_address();
//						String port = PropertiesContext.instance.getSffast_port();
//						des.setIp(address);
//						des.setPort(port);
//						new SendFrontServeThread(address, port, taskBean).start();
//					} else if (PropertiesContext.instance.getKzfh_trade().contains(tradetypecode)) {
//						String address = PropertiesContext.instance.getKzfh_address();
//						String port = PropertiesContext.instance.getKzfh_port();
//						des.setIp(address);
//						des.setPort(port);
//						new SendFrontServeThread(address, port, taskBean).start();
//					} else if (PropertiesContext.instance.getSw_trade().equals(tradetypecode)) {
//						String address = PropertiesContext.instance.getSw_address();
//						String port = PropertiesContext.instance.getSw_port();
//						des.setIp(address);
//						des.setPort(port);
//						new SendFrontServeThread(address, port, taskBean).start();
//					} else {
//						logger.info("没有认领的交易2["+tradetypecode+"]["+requestBean.getWebStreamid()+"]");
//					}
					
					
					
					requestBean.setDestination(des);
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}
	

}
