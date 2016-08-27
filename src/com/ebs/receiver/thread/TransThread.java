package com.ebs.receiver.thread;



import com.ebs.receiver.domain.TaskBean;
import com.ebs.receiver.trans.TransProcess;


public class TransThread extends Thread{
	
	private String tradeCode;
	private TaskBean taskBean;

	public TransThread(String tradeCode, TaskBean taskBean) {
		this.tradeCode = tradeCode;
		this.taskBean=taskBean;
	}

	@Override
	public void run() {
		
		TransProcess tr = new TransProcess(tradeCode,taskBean);
		tr.process();	
		
}
}