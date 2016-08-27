package com.ebs.receiver.domain;


public class ResponseBean {
	private String webStreamid;
	private Source source;
	private String responseTime;
	private String content;
	private Destination destination;
	private ConnectionInfo connectionInfo;
	private HttpConnectionInfo httpConnectionInfo;
	private Boolean isNeedToDeal;
	private Boolean isNeedGetRecord;
	public String getWebStreamid() {
		return webStreamid;
	}
	public void setWebStreamid(String webStreamid) {
		this.webStreamid = webStreamid;
	}
	public Source getSource() {
		return source;
	}
	public void setSource(Source source) {
		this.source = source;
	}
	public String getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Destination getDestination() {
		return destination;
	}
	public void setDestination(Destination destination) {
		this.destination = destination;
	}
	public ConnectionInfo getConnectionInfo() {
		return connectionInfo;
	}
	public void setConnectionInfo(ConnectionInfo connectionInfo) {
		this.connectionInfo = connectionInfo;
	}
	public Boolean getIsNeedToDeal() {
		return isNeedToDeal;
	}
	public void setIsNeedToDeal(Boolean isNeedToDeal) {
		this.isNeedToDeal = isNeedToDeal;
	}
	public Boolean getIsNeedGetRecord() {
		return isNeedGetRecord;
	}
	public void setIsNeedGetRecord(Boolean isNeedGetRecord) {
		this.isNeedGetRecord = isNeedGetRecord;
	}
	public HttpConnectionInfo getHttpConnectionInfo() {
		return httpConnectionInfo;
	}
	public void setHttpConnectionInfo(HttpConnectionInfo httpConnectionInfo) {
		this.httpConnectionInfo = httpConnectionInfo;
	}
	
}
