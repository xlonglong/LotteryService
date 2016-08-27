package com.ebs.receiver.domain;

import javax.servlet.http.HttpServletResponse;


public class HttpConnectionInfo {
	private HttpServletResponse response;

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	
}
