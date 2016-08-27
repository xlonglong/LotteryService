package com.ebs.receiver.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgInfo {

	private String userid;
	private String msg;
	private String dateTime;
	private String msgId;
	private String pointNum;
	private String commentNum;
	private String name;
	private String pictureUrl;
	private String queryType;
	private List<MsgInfo>infoList;
	private String fatherid;
	private String noAllowId;
	private String isPoint;
	private String reportType;
	private String reportMsg;
	private String fileName;
	private String nickName;
	private String tel;
	private String type;
	private String title;
	private String content;
	
	public String getUserid() {
		return userid;
	}
	@JsonProperty("Userid")
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getMsg() {
		return msg;
	}
	@JsonProperty("Msg")
	//@JsonSerialize(using = StringUnicodeSerializer.class) 
	//@JsonDeserialize(using = StringUnicodeDeserializer.class)
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getDateTime() {
		return dateTime;
	}
	@JsonProperty("DateTime")
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getMsgId() {
		return msgId;
	}
	@JsonProperty("MsgId")
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getPointNum() {
		return pointNum;
	}
	@JsonProperty("PointNum")
	public void setPointNum(String pointNum) {
		this.pointNum = pointNum;
	}
	public String getCommentNum() {
		return commentNum;
	}
	@JsonProperty("CommentNum")
	public void setCommentNum(String commentNum) {
		this.commentNum = commentNum;
	}
	public String getName() {
		return name;
	}
	@JsonProperty("Name")
	public void setName(String name) {
		this.name = name;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	@JsonProperty("PictureUrl")
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public String getQueryType() {
		return queryType;
	}
	@JsonProperty("QueryType")
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public List<MsgInfo> getInfoList() {
		return infoList;
	}
	@JsonProperty("InfoList")
	public void setInfoList(List<MsgInfo> infoList) {
		this.infoList = infoList;
	}
	public String getFatherid() {
		return fatherid;
	}
	public void setFatherid(String fatherid) {
		this.fatherid = fatherid;
	}
	public String getNoAllowId() {
		return noAllowId;
	}
	@JsonProperty("NoAllowId")
	public void setNoAllowId(String noAllowId) {
		this.noAllowId = noAllowId;
	}
	public String getIsPoint() {
		return isPoint;
	}
	@JsonProperty("IsPoint")
	public void setIsPoint(String isPoint) {
		this.isPoint = isPoint;
	}
	public String getReportType() {
		return reportType;
	}
	@JsonProperty("ReportType")
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getReportMsg() {
		return reportMsg;
	}
	@JsonProperty("ReportMsg")
	public void setReportMsg(String reportMsg) {
		this.reportMsg = reportMsg;
	}
	public String getFileName() {
		return fileName;
	}
	@JsonProperty("FileName")
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getNickName() {
		return nickName;
	}
	@JsonProperty("NickName")
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getTel() {
		return tel;
	}
	@JsonProperty("Tel")
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getType() {
		return type;
	}
	@JsonProperty("Type")
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	@JsonProperty("Title")
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	@JsonProperty("Content")
	public void setContent(String content) {
		this.content = content;
	}
	
}
