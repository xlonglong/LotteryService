package com.ebs.receiver.conf;

import java.util.ResourceBundle;

public final class Configuration {

	private static final String ADMIN_MESSAGE = "messages_admin";
	private static final String GLOBAL_MESSAGE = "Lottery";

	private static ResourceBundle adminRb = null;
	private static ResourceBundle globalRb = null;

	public Configuration() {
	}

	public static String getUserMsg(final String key) {
		if (adminRb == null) {
			adminRb = ResourceBundle.getBundle(ADMIN_MESSAGE);
		}
		return adminRb.getString(key);
	}

	public static String getGlobalMsg(final String key) {
		if (globalRb == null) {
			globalRb = ResourceBundle.getBundle(GLOBAL_MESSAGE);
		}
		return globalRb.getString(key);
	}
}
