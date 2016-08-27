package com.ebs.receiver.domain;


public class TaskBean {
	private RequestBean requestBean;
	private ResponseBean responseBean;
	private TaskBean preTaskBean;
	private String beanStatus;
	private String flag;
	public RequestBean getRequestBean() {
		return requestBean;
	}
	public void setRequestBean(RequestBean requestBean) {
		this.requestBean = requestBean;
	}
	public ResponseBean getResponseBean() {
		return responseBean;
	}
	public void setResponseBean(ResponseBean responseBean) {
		this.responseBean = responseBean;
	}
	public TaskBean getPreTaskBean() {
		return preTaskBean;
	}
	public void setPreTaskBean(TaskBean preTaskBean) {
		this.preTaskBean = preTaskBean;
	}
	public String getBeanStatus() {
		return beanStatus;
	}
	public void setBeanStatus(String beanStatus) {
		this.beanStatus = beanStatus;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
