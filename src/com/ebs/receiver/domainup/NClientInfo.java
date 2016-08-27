package com.ebs.receiver.domainup;

public class NClientInfo {
	
	private String AgentId;		//代理商编号
	private String Balance;		//账户余额
	private String CardId;    //用户卡号
	private String ClientId;		//客户编号
	private String ClientName;
	private String CreateTime; //注册时间
	private String IDNumber;		//身份证号码
	private String LockFee;		//冻结金额
	private String Points;     //积分
	private String SiteId;		//门店编号
	private String Telephone;		//手机号
	private String ValidFee;		//可用金额
	private String RandomCode;		//游戏随机码
	
	public String getRandomCode() {
		return RandomCode;
	}
	public void setRandomCode(String randomCode) {
		RandomCode = randomCode;
	}
	public String getAgentId() {
		return AgentId;
	}
	public void setAgentId(String agentId) {
		AgentId = agentId;
	}
	public String getBalance() {
		return Balance;
	}
	public void setBalance(String balance) {
		Balance = balance;
	}
	public String getCardId() {
		return CardId;
	}
	public void setCardId(String cardId) {
		CardId = cardId;
	}
	public String getClientId() {
		return ClientId;
	}
	public void setClientId(String clientId) {
		ClientId = clientId;
	}
	public String getClientName() {
		return ClientName;
	}
	public void setClientName(String clientName) {
		ClientName = clientName;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public String getIDNumber() {
		return IDNumber;
	}
	public void setIDNumber(String iDNumber) {
		IDNumber = iDNumber;
	}
	public String getLockFee() {
		return LockFee;
	}
	public void setLockFee(String lockFee) {
		LockFee = lockFee;
	}
	public String getPoints() {
		return Points;
	}
	public void setPoints(String points) {
		Points = points;
	}
	public String getSiteId() {
		return SiteId;
	}
	public void setSiteId(String siteId) {
		SiteId = siteId;
	}
	public String getTelephone() {
		return Telephone;
	}
	public void setTelephone(String telephone) {
		Telephone = telephone;
	}
	public String getValidFee() {
		return ValidFee;
	}
	public void setValidFee(String validFee) {
		ValidFee = validFee;
	}

}
