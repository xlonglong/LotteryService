package com.ebs.receiver.domainup;

import java.util.List;

public class NClient {
	
	private List<NClientInfo> DetailList;
	private String RandomCode;
	
	public String getRandomCode() {
		return RandomCode;
	}

	public void setRandomCode(String randomCode) {
		RandomCode = randomCode;
	}

	public List<NClientInfo> getDetailList() {
		return DetailList;
	}

	public void setDetailList(List<NClientInfo> detailList) {
		DetailList = detailList;
	}
	
}
