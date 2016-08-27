package com.ebs.receiver.socket.mina.service;


import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.ebs.receiver.thread.RequestHandleThread;

public class MinaSotConHandler extends IoHandlerAdapter {
	
	private static Logger logger = Logger.getLogger(MinaSotConHandler.class);
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		logger.error("短连接收信息出现异常", cause);
		super.exceptionCaught(session, cause);
		
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
//		logger.info("接收一个web端连接,远程地址:" + session.getRemoteAddress());
	}

	// 当一个客户端关闭时
	@Override
	public void sessionClosed(IoSession session) {
//		logger.info("web端断开连接!");
	}

	// 当前置发送的消息到达时:
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
//		logger.info("开始接收web请求信息!");
		String req = (String) message;
		
		logger.info("MinaGot[" +req + "]");
		MinaSotConMonitor.getInstance().getReqHandlerExcutor().execute(new RequestHandleThread(req, session));
//		new RequestHandleThread(req, session).start(); 200ms
	}
	
}