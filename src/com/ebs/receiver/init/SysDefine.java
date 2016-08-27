package com.ebs.receiver.init;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class SysDefine {
	private static Map map = null;
	private static int capacity = 20000;
	public synchronized static Map getMap() {
		if(map==null){
			map = new ConcurrentHashMap(capacity);
		}
		return map;
	}

	public synchronized void setMap(Map map) {
		SysDefine.map = map;
	}
	
	

}
