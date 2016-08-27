package com.ebs.receiver.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Lott {

	private String userid;
	private String orderId;
	private String orderTime;
	private String orderMoney;
	private String beginTime;
	private String endTime;
	private String lotteryCode;
	private String orderStatus;
	private String winningStatus;
	private String number; //追号期数
	private List<OrderInfo>lottList;
	private List<OrderInfo>list;
	private String periodCode;//期次
	private String type;//追号状态：0：中奖停止，1：中奖不停止
	private String catchNumber;//已经追的期次
	private String winMoney;//中奖金额
	private String count;//注数
	private String userName;  //用户名（手机号）
	private String profitMoney;  //盈利金额  profitMoney
	private String profitScale;	 //盈利率
	
	public String getProfitMoney() {
		return profitMoney;
	}
	@JsonProperty("ProfitMoney")
	public void setProfitMoney(String profitMoney) {
		this.profitMoney = profitMoney;
	}
	public String getProfitScale() {
		return profitScale;
	}
	@JsonProperty("ProfitScale")
	public void setProfitScale(String profitScale) {
		this.profitScale = profitScale;
	}
	public String getUserName() {
		return userName;
	}
	@JsonProperty("UserName")
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserid() {
		return userid;
	}
	@JsonProperty("Userid")
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getOrderId() {
		return orderId;
	}
	@JsonProperty("OrderId")
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderTime() {
		return orderTime;
	}
	@JsonProperty("OrderTime")
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	
	public String getOrderMoney() {
		return orderMoney;
	}
	@JsonProperty("OrderMoney")
	public void setOrderMoney(String orderMoney) {
		this.orderMoney = orderMoney;
	}
	public List<OrderInfo> getLottList() {
		return lottList;
	}
	@JsonProperty("LottList")
	public void setLottList(List<OrderInfo> lottList) {
		this.lottList = lottList;
	}
	public String getBeginTime() {
		return beginTime;
	}
	@JsonProperty("BeginTime")
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	@JsonProperty("EndTime")
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getLotteryCode() {
		return lotteryCode;
	}
	@JsonProperty("LotteryCode")
	public void setLotteryCode(String lotteryCode) {
		this.lotteryCode = lotteryCode;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	@JsonProperty("OrderStatus")
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getNumber() {
		return number;
	}
	@JsonProperty("Number")
	public void setNumber(String number) {
		this.number = number;
	}
	public String getWinningStatus() {
		return winningStatus;
	}
	@JsonProperty("WinningStatus")
	public void setWinningStatus(String winningStatus) {
		this.winningStatus = winningStatus;
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
	@JsonProperty("CatchInfoList")
	public void setList(List<OrderInfo> list) {
		this.list = list;
	}
	public String getPeriodCode() {
		return periodCode;
	}
	@JsonProperty("PeriodCode")
	public void setPeriodCode(String periodCode) {
		this.periodCode = periodCode;
	}
	public String getCatchNumber() {
		return catchNumber;
	}
	@JsonProperty("CatchNumber")
	public void setCatchNumber(String catchNumber) {
		this.catchNumber = catchNumber;
	}
	public String getWinMoney() {
		return winMoney;
	}
	@JsonProperty("WinMoney")
	public void setWinMoney(String winMoney) {
		this.winMoney = winMoney;
	}
	public String getCount() {
		return count;
	}
	@JsonProperty("Count")
	public void setCount(String count) {
		this.count = count;
	}
	
}
