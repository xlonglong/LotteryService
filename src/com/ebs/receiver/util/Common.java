package com.ebs.receiver.util;

import org.apache.log4j.Logger;


public class Common {
	private static Logger logger = Logger.getLogger(Common.class.getName());

	public static boolean isNotEmptyAndNull(String str) {
		str = null != str ? str.trim() : null;
		return str != null && !"".equals(str) && !"null".equals(str.toLowerCase());
	}
	public static boolean isEmptyOrNull(String str) {
		return str == null || "".equals(str.trim());
	}

	public static String null2Empty(String string) {
		return string == null ? "" : string.trim();
	}
}
