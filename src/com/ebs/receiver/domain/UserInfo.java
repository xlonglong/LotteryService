package com.ebs.receiver.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserInfo {

	private String userName;
	private String checkCode;
	private String pwd;
	private String name;
	private String userid;
	private String tel;
	private String email;
	private String idCard;
	private String score;
	private String coupons;
	private String balance;
	private String token;
	private String channel;
	private String oldPwd;
	private String agentId;
	private String usernum;
	private String pictureurl;
	private String nickName;
	private String registTime; //注册时间
	private String isAwardType;//返奖方式
	private String level;//推荐人等级
	private String RandomCode;//游戏系统随机码
	private String url;   //游戏端传入URL
	
	public String getUrl() {
		return url;
	}
	@JsonProperty("Url")
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRandomCode() {
		return RandomCode;
	}
	@JsonProperty("RandomCode")
	public void setRandomCode(String randomCode) {
		RandomCode = randomCode;
	}
	public String getUserName() {
		return userName;
	}
	@JsonProperty("UserName")
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCheckCode() {
		return checkCode;
	}
	@JsonProperty("CheckCode")
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	public String getPwd() {
		return pwd;
	}
	@JsonProperty("Pwd")
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	@JsonProperty("Name")
	public void setName(String name) {
		this.name = name;
	}
	public String getUserid() {
		return userid;
	}
	@JsonProperty("Userid")
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getTel() {
		return tel;
	}
	@JsonProperty("Tel")
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	@JsonProperty("Email")
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdCard() {
		return idCard;
	}
	@JsonProperty("IdCard")
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getScore() {
		return score;
	}
	@JsonProperty("Score")
	public void setScore(String score) {
		this.score = score;
	}
	public String getCoupons() {
		return coupons;
	}
	@JsonProperty("Coupons")
	public void setCoupons(String coupons) {
		this.coupons = coupons;
	}
	public String getBalance() {
		return balance;
	}
	@JsonProperty("Balance")
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getToken() {
		return token;
	}
	@JsonProperty("Token")
	public void setToken(String token) {
		this.token = token;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getOldPwd() {
		return oldPwd;
	}
	@JsonProperty("OldPwd")
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}
	public String getAgentId() {
		return agentId;
	}
	@JsonProperty("AgentId")
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getUsernum() {
		return usernum;
	}
	@JsonProperty("UserNum")
	public void setUsernum(String usernum) {
		this.usernum = usernum;
	}
	public String getPictureurl() {
		return pictureurl;
	}
	@JsonProperty("PictureUrl")	
	public void setPictureurl(String pictureurl) {
		this.pictureurl = pictureurl;
	}
	public String getNickName() {
		return nickName;
	}
	@JsonProperty("NickName")	
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRegistTime() {
		return registTime;
	}
	@JsonProperty("RegistTime")	
	public void setRegistTime(String registTime) {
		this.registTime = registTime;
	}
	public String getIsAwardType() {
		return isAwardType;
	}
	@JsonProperty("IsAwardType")	
	public void setIsAwardType(String isAwardType) {
		this.isAwardType = isAwardType;
	}
	public String getLevel() {
		return level;
	}
	@JsonProperty("Level")	
	public void setLevel(String level) {
		this.level = level;
	}

	
	
}
