package com.ebs.receiver.thread;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ebs.receiver.domain.RequestBean;
import com.ebs.receiver.init.SysDefine;


public class MapHandleThread extends Thread {

	private static Log logger = LogFactory.getLog(MapHandleThread.class);

	@Override
	public void run() {
//		Map map = PropertiesContext.instance.getMap();
		Map map = SysDefine.getMap();

		while (true) {
			
			try {
				Thread.sleep(1000 * 50);

				long nowTime = System.currentTimeMillis();

				for (Object key : map.keySet()) {
					Thread.sleep(1);
					RequestBean rh = (RequestBean) map.get(key);
					if (nowTime
							- Long.parseLong(rh.getLiveTime()
									.getStartDateTime()) > 6*60*1000) {
						map.remove(key);
						logger.info("清除流水" + key + " 的session");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}
	
	public static void main(String[] args) {
		long nowTime = System.currentTimeMillis();
		try {
			Thread.sleep(30*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long nowTime2 = System.currentTimeMillis();
		logger.info(nowTime2-nowTime);
		
	}
}
