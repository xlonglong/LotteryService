package com.ebs.receiver.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestMess {

	private String head;
	private String body;
	public String getHead() {
		return head;
	}
	@JsonProperty("header")
	public void setHead(String head) {
		this.head = head;
	}
	public String getBody() {
		return body;
	}
	@JsonProperty("body")
	public void setBody(String body) {
		this.body = body;
	}
	
	
	
}
