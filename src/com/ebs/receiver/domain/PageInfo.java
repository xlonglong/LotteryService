package com.ebs.receiver.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PageInfo {
  private String begin;
  private String num;
  private String totalNum;
public String getBegin() {
	return begin;
}
@JsonProperty("Begin")
public void setBegin(String begin) {
	this.begin = begin;
}
public String getNum() {
	return num;
}
@JsonProperty("Num")
public void setNum(String num) {
	this.num = num;
}
public String getTotalNum() {
	return totalNum;
}
@JsonProperty("TotalNum")
public void setTotalNum(String totalNum) {
	this.totalNum = totalNum;
}
  
  
}
