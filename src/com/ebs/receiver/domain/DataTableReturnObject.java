/**
 * 
 */
package com.ebs.receiver.domain;

/**
 * @author xlonglong
 * @since:2015年8月12日下午5:15:43		
 * @version V1.0
 */
public class DataTableReturnObject {
	private long iTotalRecords;
	private long iTotalDisplayRecords;
	private String sEcho;
	private String[][] aaData;
	
	public DataTableReturnObject(long totalRecords, long totalDisplayRecords, String echo, String[][] d) {
		this.setiTotalRecords(totalRecords);
		this.setiTotalDisplayRecords(totalDisplayRecords);
		this.setsEcho(echo);
		this.setAaData(d);
	}

	public void setiTotalRecords(long iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public long getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalDisplayRecords(long iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public long getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public String getsEcho() {
		return sEcho;
	}

	public void setAaData(String[][] aaData) {
		this.aaData = aaData;
	}

	public String[][] getAaData() {
		return aaData;
	}
}
