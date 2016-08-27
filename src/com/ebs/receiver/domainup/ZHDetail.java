package com.ebs.receiver.domainup;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ZHDetail {
	
	private String DetailId;
	private String PeriodId;
	private String Codes;
	private String OrderMoney;
	private String Margins;
	private String TotalCastMoney;
	private String ProfitAmount;
	private String AwardFee;
	public String getDetailId() {
		return DetailId;
	}
	@JsonProperty("DetailId")
	public void setDetailId(String detailId) {
		DetailId = detailId;
	}
	public String getPeriodId() {
		return PeriodId;
	}
	@JsonProperty("PeriodId")
	public void setPeriodId(String periodId) {
		PeriodId = periodId;
	}
	public String getCodes() {
		return Codes;
	}
	@JsonProperty("Codes")
	public void setCodes(String codes) {
		Codes = codes;
	}
	public String getOrderMoney() {
		return OrderMoney;
	}
	@JsonProperty("OrderMoney")
	public void setOrderMoney(String orderMoney) {
		OrderMoney = orderMoney;
	}
	public String getMargins() {
		return Margins;
	}
	@JsonProperty("Margins")
	public void setMargins(String margins) {
		Margins = margins;
	}
	public String getTotalCastMoney() {
		return TotalCastMoney;
	}
	@JsonProperty("TotalCastMoney")
	public void setTotalCastMoney(String totalCastMoney) {
		TotalCastMoney = totalCastMoney;
	}
	public String getProfitAmount() {
		return ProfitAmount;
	}
	@JsonProperty("ProfitAmount")
	public void setProfitAmount(String profitAmount) {
		ProfitAmount = profitAmount;
	}
	public String getAwardFee() {
		return AwardFee;
	}
	@JsonProperty("AwardFee")
	public void setAwardFee(String awardFee) {
		AwardFee = awardFee;
	}
	
	

}
