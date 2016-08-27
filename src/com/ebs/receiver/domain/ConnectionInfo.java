package com.ebs.receiver.domain;

import org.apache.mina.core.session.IoSession;

public class ConnectionInfo {
	private IoSession session;

	public IoSession getSession() {
		return session;
	}

	public void setSession(IoSession session) {
		this.session = session;
	}
	
}
