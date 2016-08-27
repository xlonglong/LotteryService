package com.ebs.receiver.domain;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TYOrder {

	private Header header;
	private Version version;
	private Status status;
    private UserInfo userInfo; 
    private Server server;
    private WinInfo winInfo;
    private PageInfo pageInfo;
    private LotteryInfo lotteryInfo;
    private Lott lott;// 彩票票信息
    private MsgInfo msgInfo;
    
    private BankInfo bankInfo;
    private PayInfo payInfo;
    
    private ExtensionInfo extensionInfo ; //推广信息
	public Header getHeader() {
		return header;
	}
	@JsonProperty("Header")
	public void setHeader(Header header) {
		this.header = header;
	}

	public Version getVersion() {
		return version;
	}
	@JsonProperty("Version")
	public void setVersion(Version version) {
		this.version = version;
	}
	public Status getStatus() {
		return status;
	}
	@JsonProperty("Status")
	public void setStatus(Status status) {
		this.status = status;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	@JsonProperty("UserInfo")
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public Server getServer() {
		return server;
	}
	@JsonProperty("Server")
	public void setServer(Server server) {
		this.server = server;
	}
	public WinInfo getWinInfo() {
		return winInfo;
	}
	@JsonProperty("WinInfo")
	public void setWinInfo(WinInfo winInfo) {
		this.winInfo = winInfo;
	}
	public PageInfo getPageInfo() {
		return pageInfo;
	}
	@JsonProperty("PageInfo")
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	public LotteryInfo getLotteryInfo() {
		return lotteryInfo;
	}
	@JsonProperty("LotteryInfo")
	public void setLotteryInfo(LotteryInfo lotteryInfo) {
		this.lotteryInfo = lotteryInfo;
	}
	public Lott getLott() {
		return lott;
	}
	@JsonProperty("Lott")
	public void setLott(Lott lott) {
		this.lott = lott;
	}
	public MsgInfo getMsgInfo() {
		return msgInfo;
	}
	@JsonProperty("MsgInfo")
	public void setMsgInfo(MsgInfo msgInfo) {
		this.msgInfo = msgInfo;
	}
	public BankInfo getBankInfo() {
		return bankInfo;
	}
	@JsonProperty("BankInfo")
	public void setBankInfo(BankInfo bankInfo) {
		this.bankInfo = bankInfo;
	}
	public PayInfo getPayInfo() {
		return payInfo;
	}
	@JsonProperty("PayInfo")
	public void setPayInfo(PayInfo payInfo) {
		this.payInfo = payInfo;
	}
	public ExtensionInfo getExtensionInfo() {
		return extensionInfo;
	}
	@JsonProperty("ExtensionInfo")
	public void setExtensionInfo(ExtensionInfo extensionInfo) {
		this.extensionInfo = extensionInfo;
	}
	
	
	
}
