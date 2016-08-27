package com.ebs.receiver.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderInfo {

	private String orderId;
	private String lotteryCode;
	private String periodCode;
	private String orderTime;
	private String orderStatus;
	private String winningStatus;
	private String number;
	private String playType;
	private String multiple;
	private String count;
	private String money;
	private String winCode;//开奖号码
	private String winMoney; //中奖金额
	private String lotteryName;//彩种名称
	private String orderType;//订单类型
	private String catchNumber;//已追多少期
    private String type;//追号状态，0：中奖后停止，1：不停止
	private List<OrderInfo>list;
	
	private String margins;
	private String totalCastMoney;
	private String profitAmount;
	private String awardFee;
	
	private String userName;
	
	
	public String getUserName() {
		return userName;
	}
	@JsonProperty("UserName")
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMargins() {
		return margins;
	}
	@JsonProperty("Margins")
	public void setMargins(String margins) {
		this.margins = margins;
	}
	public String getTotalCastMoney() {
		return totalCastMoney;
	}
	@JsonProperty("TotalCastMoney")
	public void setTotalCastMoney(String totalCastMoney) {
		this.totalCastMoney = totalCastMoney;
	}
	public String getProfitAmount() {
		return profitAmount;
	}
	@JsonProperty("ProfitAmount")
	public void setProfitAmount(String profitAmount) {
		this.profitAmount = profitAmount;
	}
	public String getAwardFee() {
		return awardFee;
	}
	@JsonProperty("AwardFee")
	public void setAwardFee(String awardFee) {
		this.awardFee = awardFee;
	}
	public String getOrderId() {
		return orderId;
	}
	@JsonProperty("OrderId")
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getLotteryCode() {
		return lotteryCode;
	}
	@JsonProperty("LotteryCode")
	public void setLotteryCode(String lotteryCode) {
		this.lotteryCode = lotteryCode;
	}
	public String getPeriodCode() {
		return periodCode;
	}
	@JsonProperty("PeriodCode")
	public void setPeriodCode(String periodCode) {
		this.periodCode = periodCode;
	}
	public String getNumber() {
		return number;
	}
	@JsonProperty("Number")
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPlayType() {
		return playType;
	}
	@JsonProperty("PlayType")
	public void setPlayType(String playType) {
		this.playType = playType;
	}
	public String getMultiple() {
		return multiple;
	}
	@JsonProperty("Multiple")
	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}
	public String getCount() {
		return count;
	}
	@JsonProperty("Count")
	public void setCount(String count) {
		this.count = count;
	}
	public String getMoney() {
		return money;
	}
	@JsonProperty("Money")
	public void setMoney(String money) {
		this.money = money;
	}
	public String getOrderTime() {
		return orderTime;
	}
	@JsonProperty("OrderTime")
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	@JsonProperty("OrderStatus")
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getWinningStatus() {
		return winningStatus;
	}
	@JsonProperty("WinningStatus")
	public void setWinningStatus(String winningStatus) {
		this.winningStatus = winningStatus;
	}
	public String getWinCode() {
		return winCode;
	}
	@JsonProperty("WinCode")
	public void setWinCode(String winCode) {
		this.winCode = winCode;
	}
	public String getWinMoney() {
		return winMoney;
	}
	@JsonProperty("WinMoney")
	public void setWinMoney(String winMoney) {
		this.winMoney = winMoney;
	}
	public String getLotteryName() {
		return lotteryName;
	}
	@JsonProperty("LotteryName")
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	public String getOrderType() {
		return orderType;
	}
	@JsonProperty("OrderType")
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getCatchNumber() {
		return catchNumber;
	}
	@JsonProperty("CatchNumber")
	public void setCatchNumber(String catchNumber) {
		this.catchNumber = catchNumber;
	}
	public String getType() {
		return type;
	}
	@JsonProperty("Type")
	public void setType(String type) {
		this.type = type;
	}
	public List<OrderInfo> getList() {
		return list;
	}
	@JsonProperty("LottList")
	public void setList(List<OrderInfo> list) {
		this.list = list;
	}
	
}
