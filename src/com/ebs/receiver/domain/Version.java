package com.ebs.receiver.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Version {

	private String app;
	private String version;
	private String url;
	private String isupdate;
	private String systime;
	private String siteId;	//门店号
	private String issuedate; //版本发布日期
	private String remark;	//更新内容描述
	
	public String getIssuedate() {
		return issuedate;
	}
	@JsonProperty("Issuedate")
	public void setIssuedate(String issuedate) {
		this.issuedate = issuedate;
	}
	public String getRemark() {
		return remark;
	}
	@JsonProperty("Remark")
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSiteId() {
		return siteId;
	}
	@JsonProperty("SiteId")
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getApp() {
		return app;
	}
	@JsonProperty("App")
	public void setApp(String app) {
		this.app = app;
	}
	public String getVersion() {
		return version;
	}
	@JsonProperty("Version")
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUrl() {
		return url;
	}
	@JsonProperty("Url")
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIsupdate() {
		return isupdate;
	}
	@JsonProperty("Isupdate")
	public void setIsupdate(String isupdate) {
		this.isupdate = isupdate;
	}
	public String getSystime() {
		return systime;
	}
	@JsonProperty("Systime")
	public void setSystime(String systime) {
		this.systime = systime;
	}
}
