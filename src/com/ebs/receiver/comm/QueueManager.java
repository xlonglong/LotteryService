package com.ebs.receiver.comm;

import com.ebs.receiver.queue.MessageSyncQueue;

public class QueueManager {
	// 原始请求队列
	private static MessageSyncQueue reqQueue = null;
	// 经过预处理后的请求队列
	private static MessageSyncQueue pretreatRequestQueue = null;
	// 返回队列
	private static MessageSyncQueue resQueue = null;

	public static MessageSyncQueue getReqQueue() {
		if (null == reqQueue) {
			reqQueue = new MessageSyncQueue();
		}
		return reqQueue;
	}

	public static MessageSyncQueue getReqQueue(int max, int min) {
		if (null == reqQueue) {
			reqQueue = new MessageSyncQueue(max, min);
		}
		return reqQueue;
	}

	public static void setReqQueue(MessageSyncQueue reqQueue) {
		QueueManager.reqQueue = reqQueue;
	}

	public static MessageSyncQueue getPretreatRequestQueue() {
		if (null == pretreatRequestQueue) {
			pretreatRequestQueue = new MessageSyncQueue();
		}
		return pretreatRequestQueue;
	}

	public static MessageSyncQueue getPretreatRequestQueue(int max, int min) {
		if (null == pretreatRequestQueue) {
			pretreatRequestQueue = new MessageSyncQueue(max, min);
		}
		return pretreatRequestQueue;
	}

	public static void setPretreatRequestQueue(MessageSyncQueue pretreatRequestQueue) {
		QueueManager.pretreatRequestQueue = pretreatRequestQueue;
	}

	public static MessageSyncQueue getResQueue() {
		if (null == resQueue) {
			resQueue = new MessageSyncQueue();
		}
		return resQueue;
	}

	public static MessageSyncQueue getResQueue(int max, int min) {
		if (null == resQueue) {
			resQueue = new MessageSyncQueue(max, min);
		}
		return resQueue;
	}

	public static void setResQueue(MessageSyncQueue resQueue) {
		QueueManager.resQueue = resQueue;
	}
}
