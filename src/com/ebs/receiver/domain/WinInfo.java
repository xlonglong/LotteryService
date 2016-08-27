package com.ebs.receiver.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WinInfo {

	private String  lotteryCode;
	private String periodCode;
	private List<WinInfoLevel>winInfoLevelList;
	private List<String>lotteryCodeList;
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
	public List<WinInfoLevel> getWinInfoLevelList() {
		return winInfoLevelList;
	}
	@JsonProperty("WinInfoList")
	public void setWinInfoLevelList(List<WinInfoLevel> winInfoLevelList) {
		this.winInfoLevelList = winInfoLevelList;
	}
	public List<String> getLotteryCodeList() {
		return lotteryCodeList;
	}
	@JsonProperty("LotteryCodeList")
	public void setLotteryCodeList(List<String> lotteryCodeList) {
		this.lotteryCodeList = lotteryCodeList;
	}
	
	
}
