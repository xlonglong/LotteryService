package com.ebs.receiver.queue;

import java.util.concurrent.ArrayBlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ebs.receiver.domain.TaskBean;



public class MessageSyncQueue {
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(MessageSyncQueue.class);

	// 队列中允许存放的最大数量
	private int CAPACITY = 2000;
	// 队列中允许存放的最小数量，低于此数要告警
	private int ALERT_CAPACITY = 1000;

	private ArrayBlockingQueue<TaskBean> msgQueue_m = null;

	// 初始化队列
	public MessageSyncQueue() {
		msgQueue_m = new ArrayBlockingQueue<TaskBean>(CAPACITY);
	}

	// 初始化队列
	public MessageSyncQueue(int max,int min) {
		this.CAPACITY = max;
		this.ALERT_CAPACITY = min;
		msgQueue_m = new ArrayBlockingQueue<TaskBean>(CAPACITY);
	}

	// 增加一个元素
	public synchronized boolean push(TaskBean msg) {
		return msgQueue_m.offer(msg);
	}

	// 
	public synchronized TaskBean pop() {
		TaskBean msg = null;
		if (!isEmpty()) {
			msg = msgQueue_m.poll();
		}
		return msg;
	}

	// 队列是否为空
	public synchronized boolean isEmpty() {
		return msgQueue_m.isEmpty();
	}

	public synchronized int getCount() {
		return msgQueue_m.size();
	}

	// 是否允许继续向队列中插入数据
	public synchronized boolean isToAlert() {
		return msgQueue_m.size() >= CAPACITY;
	}
	
	// 是否低于最小个数
	public synchronized boolean isWarn() {
		return msgQueue_m.size() < ALERT_CAPACITY;
	}
	
	public synchronized void clear() {
		if (!isEmpty()) {
			msgQueue_m.clear();
		}
	}
}
