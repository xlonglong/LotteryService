package com.ebs.receiver.domain;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LotteryInfo {

	private String lotteryCode;
	private String periodCode;
	private String startTime;
	private String endTime;
	private String winNum;
	private String status;
	private List<LotteryInfo>lotteryList;
	private Map<String,Object>omissionMap;
	
	private String isPlusAward;//是否加奖
	private String poolMoney;//奖池
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
	public String getStartTime() {
		return startTime;
	}
	@JsonProperty("StartTime")
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	@JsonProperty("EndTime")
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getWinNum() {
		return winNum;
	}
	@JsonProperty("winNum")
	public void setWinNum(String winNum) {
		this.winNum = winNum;
	}
	public String getStatus() {
		return status;
	}
	@JsonProperty("Status")
	public void setStatus(String status) {
		this.status = status;
	}
	public List<LotteryInfo> getLotteryList() {
		return lotteryList;
	}
	@JsonProperty("LotteryList")
	public void setLotteryList(List<LotteryInfo> lotteryList) {
		this.lotteryList = lotteryList;
	}
	public String getIsPlusAward() {
		return isPlusAward;
	}
	@JsonProperty("IsPlusAward")
	public void setIsPlusAward(String isPlusAward) {
		this.isPlusAward = isPlusAward;
	}
	public String getPoolMoney() {
		return poolMoney;
	}
	@JsonProperty("PoolMoney")
	public void setPoolMoney(String poolMoney) {
		this.poolMoney = poolMoney;
	}
	public Map<String, Object> getOmissionMap() {
		return omissionMap;
	}
	@JsonProperty("OmissionMap")
	public void setOmissionMap(Map<String, Object> omissionMap) {
		this.omissionMap = omissionMap;
	}
	
	
	
}
