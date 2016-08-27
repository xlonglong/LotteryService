package com.ebs.receiver.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

//推广信息
public class ExtensionInfo {

	private String userid;//用户信息
	private String referee;//推荐人信息
	private String commission;//佣金
	private String levelNum;//盟友总人数
	private String level1Num;//一级盟友人数
	private String level2Num;//二级盟友人数
	private String level3Num;//三级盟友人数
	private List<UserInfo> refereeInfoList;
	private String level;
	private String nickName;
	public String getUserid() {
		return userid;
	}
	@JsonProperty("Userid")
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getReferee() {
		return referee;
	}
	@JsonProperty("Referee")
	public void setReferee(String referee) {
		this.referee = referee;
	}
	public String getCommission() {
		return commission;
	}
	@JsonProperty("Commission")
	public void setCommission(String commission) {
		this.commission = commission;
	}
	public String getLevelNum() {
		return levelNum;
	}
	@JsonProperty("LevelNum")
	public void setLevelNum(String levelNum) {
		this.levelNum = levelNum;
	}
	public String getLevel1Num() {
		return level1Num;
	}
	@JsonProperty("Level1Num")
	public void setLevel1Num(String level1Num) {
		this.level1Num = level1Num;
	}
	public String getLevel2Num() {
		return level2Num;
	}
	@JsonProperty("Level2Num")
	public void setLevel2Num(String level2Num) {
		this.level2Num = level2Num;
	}
	public String getLevel3Num() {
		return level3Num;
	}
	@JsonProperty("Level3Num")
	public void setLevel3Num(String level3Num) {
		this.level3Num = level3Num;
	}
	public List<UserInfo> getRefereeInfoList() {
		return refereeInfoList;
	}
	@JsonProperty("RefereeInfoList")
	public void setRefereeInfoList(List<UserInfo> refereeInfoList) {
		this.refereeInfoList = refereeInfoList;
	}
	public String getLevel() {
		return level;
	}
	@JsonProperty("Level")
	public void setLevel(String level) {
		this.level = level;
	}
	public String getNickName() {
		return nickName;
	}
	@JsonProperty("NickName")
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	
	
}
