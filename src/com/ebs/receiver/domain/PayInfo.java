package com.ebs.receiver.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PayInfo {

	private String orderId;
	private String type;
	private String payType;
    private String charge;
    private String dateTime;
    private String channel;//支付类型：1:支付宝，2：微信
    private String currency;//币种类型：中文：cny
    private String clientIp;//客户端地址
    private String userid;//用户id
    private String operType;
    private String money;
    
    private List<PayInfo>payList;
    
    private String cardNum;//账号
	public String getOrderId() {
		return orderId;
	}
	@JsonProperty("OrderId")
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getType() {
		return type;
	}
	@JsonProperty("Type")
	public void setType(String type) {
		this.type = type;
	}
	public String getPayType() {
		return payType;
	}
	@JsonProperty("PayType")
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getCharge() {
		return charge;
	}
	@JsonProperty("Charge")
	public void setCharge(String charge) {
		this.charge = charge;
	}
	public String getDateTime() {
		return dateTime;
	}
	@JsonProperty("DateTime")
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getChannel() {
		return channel;
	}
	@JsonProperty("Channel")
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getCurrency() {
		return currency;
	}
	@JsonProperty("Currency")
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getClientIp() {
		return clientIp;
	}
	@JsonProperty("ClientIp")
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public String getOperType() {
		return operType;
	}
	@JsonProperty("OperType")
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getMoney() {
		return money;
	}
	@JsonProperty("Money")
	public void setMoney(String money) {
		this.money = money;
	}
	public List<PayInfo> getPayList() {
		return payList;
	}
	@JsonProperty("PayList")
	public void setPayList(List<PayInfo> payList) {
		this.payList = payList;
	}
	public String getUserid() {
		return userid;
	}
	@JsonProperty("Userid")
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getCardNum() {
		return cardNum;
	}
	@JsonProperty("CardNum")
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
   
	
	
}
