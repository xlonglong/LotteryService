package com.ebs.receiver.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Status {

	private String errorCode;
	private String errorMsg;
	public String getErrorCode() {
		return errorCode;
	}
	@JsonProperty("ErrorCode")
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	@JsonProperty("ErrorMsg")
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
