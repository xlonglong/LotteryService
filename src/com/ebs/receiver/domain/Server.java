package com.ebs.receiver.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Server {

	private String systemDate;

	public String getSystemDate() {
		return systemDate;
	}
	@JsonProperty("SystemDate")
	public void setSystemDate(String systemDate) {
		this.systemDate = systemDate;
	}
	
	
}
