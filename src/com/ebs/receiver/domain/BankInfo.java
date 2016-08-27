package com.ebs.receiver.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BankInfo {

	private String userid; //用户id
	private String bankName;//银行卡名称
	private String bankCardNum;//银行卡号
	private String isAwardCard;//1:设为返奖卡，0：不设置
	private String isWithDrawCard;//1:设为提现卡，0：不设置
	private List<BankInfo>bankInfoList;//卡列表
	private String type;//修改卡的类型：1:设置为返奖卡，0：设置为提现卡
	private String dateTime;
	private String url;//图片
	private String checkCode;//短信验证码
	public String getUserid() {
		return userid;
	}
	@JsonProperty("Userid")
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getBankName() {
		return bankName;
	}
	@JsonProperty("BankName")
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankCardNum() {
		return bankCardNum;
	}
	@JsonProperty("BankCardNum")
	public void setBankCardNum(String bankCardNum) {
		this.bankCardNum = bankCardNum;
	}
	public String getIsAwardCard() {
		return isAwardCard;
	}
	@JsonProperty("IsAwardCard")
	public void setIsAwardCard(String isAwardCard) {
		this.isAwardCard = isAwardCard;
	}
	public String getIsWithDrawCard() {
		return isWithDrawCard;
	}
	@JsonProperty("IsWithDrawCard")
	public void setIsWithDrawCard(String isWithDrawCard) {
		this.isWithDrawCard = isWithDrawCard;
	}
	public List<BankInfo> getBankInfoList() {
		return bankInfoList;
	}
	@JsonProperty("BankInfoList")
	public void setBankInfoList(List<BankInfo> bankInfoList) {
		this.bankInfoList = bankInfoList;
	}
	public String getType() {
		return type;
	}
	@JsonProperty("Type")
	public void setType(String type) {
		this.type = type;
	}
	public String getDateTime() {
		return dateTime;
	}
	@JsonProperty("DateTime")
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getUrl() {
		return url;
	}
	@JsonProperty("URL")
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCheckCode() {
		return checkCode;
	}
	@JsonProperty("CheckCode")
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	
	
}
