package com.ebs.receiver.domain;

import com.ebs.receiver.util.PackUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Header {
	private String len;
	private String version;
	private String channel;
	private String timestamp;
	private String command;
	private String token;
	private String saleChannelNo;
	private String tradeType;
	private String betSource;
	private String agentId;
	
	public String getAgentId() {
		return agentId;
	}
	@JsonProperty("AgentId")
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getLen() {
		return len;
	}
	public void setLen(String len) {
		this.len = len;
	}
	
	public String getVersion() {
		return version;
	}
	@JsonProperty("Version")
	public void setVersion(String version) {
		this.version = version;
	}
	public String getChannel() {
		return channel;
	}
	@JsonProperty("Channel")
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getTimestamp() {
		return timestamp;
	}
	@JsonProperty("Timestamp")
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getCommand() {
		return command;
	}
	@JsonProperty("Command")
	public void setCommand(String command) {
		this.command = command.trim();
	}
	public String getToken() {
		return token;
	}
	@JsonProperty("Token")
	public void setToken(String token) {
		this.token = token;
	}
	public String getSaleChannelNo() {
		return saleChannelNo;
	}
	@JsonProperty("SaleChannelNo")
	public void setSaleChannelNo(String saleChannelNo) {
		this.saleChannelNo = saleChannelNo;
	}
	public String getTradeType() {
		return tradeType;
	}
	@JsonProperty("TradeType")
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType.trim();
	}
	public String getBetSource() {
		return betSource;
	}
	@JsonProperty("BetSource")
	public void setBetSource(String betSource) {
		this.betSource = betSource;
	}

	
	public String doPack()
	{
		StringBuffer sb=new StringBuffer();
		sb.append(PackUtil.fill(len, 4, "B","0"));
		sb.append(PackUtil.fill(tradeType, 6, "B"," "));
		sb.append(PackUtil.fill(command, 30, "B"," "));
		return sb.toString();
	}

}
