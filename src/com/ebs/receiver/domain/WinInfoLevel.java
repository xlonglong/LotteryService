package com.ebs.receiver.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WinInfoLevel {

	private String lotteryCode;
	private String periodCode;
	private String sales;
	private String bonus;
	private String winCode;
/*	private String winTime;*/
	private String startTime;
	private String endTime;
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
	public String getSales() {
		return sales;
	}
	@JsonProperty("Sales")
	public void setSales(String sales) {
		this.sales = sales;
	}
	public String getBonus() {
		return bonus;
	}
	@JsonProperty("Bonus")
	public void setBonus(String bonus) {
		this.bonus = bonus;
	}
	public String getWinCode() {
		return winCode;
	}
	@JsonProperty("WinCode")
	public void setWinCode(String winCode) {
		this.winCode = winCode;
	}
	/*public String getWinTime() {
		return winTime;
	}
	@JsonProperty("WinTime")
	public void setWinTime(String winTime) {
		this.winTime = winTime;
	}*/
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
	
	
	
	
}
