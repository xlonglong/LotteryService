package com.ebs.receiver.socket.mina.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StartSotConServers {
	private static Log logger = LogFactory.getLog(StartSotConServers.class);

	/**
	 * 应用启动
	 */
	public void startListener() {
		try {

			logger.info("**********启动(短连接服务)*****start*****");
			if (!MinaSotConMonitor.getInstance().startListener()) {
				logger.info("**********启动(短连接服务)*****异常,请检查后重新启动*****");
				System.exit(1);
				return;
			}

			System.out.println(System.getProperty("user.dir"));

		} catch (Exception e) {
			logger.error("**********启动短连接服务异常**********", e);
		}
	}

	public void stopListener() {
		// TODO 补充以上没停止的

		// 略
		MinaSotConMonitor.getInstance().stopListener();
		logger.info("**********统一接口平台所有服务停止成功**********");
		System.exit(1);
	}

	private static StartSotConServers startSotConServers;

	public static StartSotConServers getInstance() {
		if (startSotConServers == null) {
			startSotConServers = new StartSotConServers();
		}
		return startSotConServers;
	}

	/*
	 * public static void main(String args[]) {
	 * StartSotConServers.getInstance().startListener(); }
	 */
}