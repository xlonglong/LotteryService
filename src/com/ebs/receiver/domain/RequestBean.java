package com.ebs.receiver.domain;




public class RequestBean {
	private String webStreamid;
	private Source source;
	private String requestTime;
	private String content;
	private String requestType;
	private Destination destination;
	private LiveTime liveTime;
	private ConnectionInfo connectionInfo;
	private HttpConnectionInfo httpConnectionInfo;
	private String command;
	private Boolean isSync;
	private Boolean isNeedRecord;
	private String channel;
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
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
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public Destination getDestination() {
		return destination;
	}
	public void setDestination(Destination destination) {
		this.destination = destination;
	}
	public LiveTime getLiveTime() {
		return liveTime;
	}
	public void setLiveTime(LiveTime liveTime) {
		this.liveTime = liveTime;
	}
	public ConnectionInfo getConnectionInfo() {
		return connectionInfo;
	}
	public void setConnectionInfo(ConnectionInfo connectionInfo) {
		this.connectionInfo = connectionInfo;
	}
	public Boolean getIsSync() {
		return isSync;
	}
	public void setIsSync(Boolean isSync) {
		this.isSync = isSync;
	}
	public Boolean getIsNeedRecord() {
		return isNeedRecord;
	}
	public void setIsNeedRecord(Boolean isNeedRecord) {
		this.isNeedRecord = isNeedRecord;
	}
//	public String toString(){
//		return webStreamid+":"+requestTime+":"+requestType+":"+liveTime.getStartDateTime()+":"+liveTime.getEndDateTime();
//	}
	public HttpConnectionInfo getHttpConnectionInfo() {
		return httpConnectionInfo;
	}
	public void setHttpConnectionInfo(HttpConnectionInfo httpConnectionInfo) {
		this.httpConnectionInfo = httpConnectionInfo;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
}
