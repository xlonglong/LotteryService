package com.ebs.receiver.socket.mina.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.ebs.receiver.comm.QueueManager;
import com.ebs.receiver.conf.PropertiesContext;
import com.ebs.receiver.thread.MapHandleThread;
import com.ebs.receiver.thread.MonitorThread;
import com.ebs.receiver.thread.RequestDistributeThread;
import com.ebs.receiver.thread.RequestFiltrateThread;
import com.ebs.receiver.thread.ResponseHandleThread;
import com.ebs.receiver.thread.ScannerThread;
import com.ebs.receiver.thread.SotSyncRevThread;
/**
 * 短连接服务
 * 初始化一些数据
 * @author：yangyj    
 * @since:2012-6-19 
 * @version:1.0
 */
public class MinaSotConMonitor {


	private static MinaSotConMonitor minaSotConMonitor;
	
	private static Log log = LogFactory.getLog(MinaSotConMonitor.class);
	private SocketAcceptor acceptor;
	private Map<String,Thread> processThreadMap = new HashMap<String,Thread>(); 
	
	private int receiveServicePort;
	private int messageQueueMaxValue;
	private int messageQueueMinValue;
	private int reqHandlerThreadNum;
	private int messageMaxLength;
	private int short_connection;
	
	private ExecutorService reqHandlerExcutor;
	
	
	public Map<String,Thread> getProcessThreadMap() {
		return processThreadMap;
	}

	public ExecutorService getReqHandlerExcutor() {
		return reqHandlerExcutor;
	}

	private MinaSotConMonitor(){
		PropertiesContext propertiesContext = new PropertiesContext();
		propertiesContext.setPropertiesContext(propertiesContext);
		receiveServicePort = Integer.parseInt(propertiesContext.getMina_listen_port());
		messageQueueMaxValue = Integer.parseInt(propertiesContext.getMsgque_max_len());
		messageQueueMinValue = Integer.parseInt(propertiesContext.getMsgque_min_len());
		reqHandlerThreadNum = Integer.parseInt(propertiesContext.getReqhndl_thrd_num());
		messageMaxLength = Integer.parseInt(propertiesContext.getMessage_maxlen());
		short_connection = Integer.parseInt(propertiesContext.getShort_connection());
	}

	public static MinaSotConMonitor getInstance() {
		if (minaSotConMonitor == null) {
			minaSotConMonitor = new MinaSotConMonitor();
		}
		return minaSotConMonitor;
	}
	
	public boolean startListener() {
		boolean isSuc = false;
		try {
			// 服务器端绑定的端口
			acceptor = new NioSocketAcceptor();
			acceptor.setReuseAddress(true);
			// 设置最大同时连接个数
			acceptor.setBacklog(short_connection);
			acceptor.getSessionConfig().setTcpNoDelay(true);    
//			acceptor.getSessionConfig().setReadBufferSize(messageMaxLength);
			acceptor.getSessionConfig().setReceiveBufferSize(messageMaxLength);
			acceptor.getSessionConfig().setSendBufferSize(messageMaxLength);
			// 创建接收数据的过滤器
			DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
			// 设定这个过滤器将一行一行(/r/n)的读取数据
			TextLineCodecFactory textLineCodecFactory = new TextLineCodecFactory(Charset.forName("UTF-8"));
			textLineCodecFactory.setDecoderMaxLineLength(messageMaxLength);
			chain.addLast("myChin", new ProtocolCodecFilter(textLineCodecFactory));

			acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
			
			chain.addLast("threadPool", new ExecutorFilter(Executors.newCachedThreadPool()));
			// 设定服务器端的消息处理器:
			acceptor.setHandler(new MinaSotConHandler());
			// 绑定端口,启动服务
			log.info("*********Mina SotCon server is Listing on:= " + receiveServicePort +"*********");	
			acceptor.bind(new InetSocketAddress(receiveServicePort));
			// 初始化消息队列
			//sotcket
			log.info("*********初始化请求消息队列*********");
			QueueManager.setReqQueue(QueueManager.getReqQueue(messageQueueMaxValue, messageQueueMinValue));
			log.info("*********初始化响应消息队列*********");
			QueueManager.setResQueue(QueueManager.getResQueue(messageQueueMaxValue, messageQueueMinValue));
			log.info("*********初始化预请求消息队列*********");
			QueueManager.setPretreatRequestQueue(QueueManager.getPretreatRequestQueue(messageQueueMaxValue, messageQueueMinValue));
			
			//Http
			/*log.info("*********初始化请求消息队列*********");
			HttpQueueManager.setReqQueue(HttpQueueManager.getReqQueue(messageQueueMaxValue, messageQueueMinValue));
			log.info("*********初始化响应消息队列*********");
			HttpQueueManager.setResQueue(HttpQueueManager.getResQueue(messageQueueMaxValue, messageQueueMinValue));
			log.info("*********初始化预请求消息队列*********");
			HttpQueueManager.setPretreatRequestQueue(HttpQueueManager.getPretreatRequestQueue(messageQueueMaxValue, messageQueueMinValue));*/
			
			log.info("*********初始化队列处理线程池*********");
			reqHandlerExcutor = Executors.newFixedThreadPool(reqHandlerThreadNum);
			
			//启动相关线程
			//socket
			log.info("*********启动预队列处理线程*********");
			Thread requestFiltrateThread = new RequestFiltrateThread();
			requestFiltrateThread.start();
			processThreadMap.put("requestFiltrateThread",requestFiltrateThread);
			log.info("*********启动转发处理线程*********");
			Thread requestDistributeThread = new RequestDistributeThread();
			requestDistributeThread.start();
			processThreadMap.put("requestDistributeThread",requestDistributeThread);
			log.info("*********启动响应处理线程*********");
			Thread responseHandlerThread = new ResponseHandleThread();
			responseHandlerThread.start();
			processThreadMap.put("responseHandlerThread",responseHandlerThread);
			
			//http
			/*log.info("*********启动http预队列处理线程*********");
			Thread httpRequestFiltrateThread = new HttpRequestFiltrateThread();
			httpRequestFiltrateThread.start();
			processThreadMap.put("httpRequestFiltrateThread",httpRequestFiltrateThread);
			log.info("*********启动http转发处理线程*********");
			Thread httpRequestDistributeThread = new HttpRequestDistributeThread();
			httpRequestDistributeThread.start();
			processThreadMap.put("httpRequestDistributeThread",httpRequestDistributeThread);
			log.info("*********启动http响应处理线程*********");
			Thread httpResponseHandlerThread = new HttpResponseHandleThread();
			httpResponseHandlerThread.start();
			processThreadMap.put("httpResponseHandlerThread",httpResponseHandlerThread);*/
			
			
			
			
			
			//log.info("*********启动接收前置推送报文监听线程["+Integer.parseInt(PropertiesContext.getInstance().getSync_listen_port())+"]*********");
		/*	Thread syncRevThread = new SotSyncRevThread();
			syncRevThread.start();*/
//			processThreadMap.put("syncRevThread",syncRevThread);
			log.info("*********启动异步session清理线程*********");
			Thread mapHandleThread = new MapHandleThread();
			mapHandleThread.start();
			processThreadMap.put("mapHandleThread",mapHandleThread);
			
			log.info("*********启动监控线程*********");
			Thread monitorThread = new MonitorThread();
			monitorThread.start();
			log.info("*********启动系统资源扫描线程*********");
			new ScannerThread().start();
			//Process.initSchedule(); //启动定时器
			
			isSuc = true;
			
		} catch (IOException e1) {
			isSuc = false;
			log.error("启动(MinaSotConListrener)异常!", e1);
		}
		return isSuc;
	}
	
	public void stopListener() {
		log.info("*********Mina SotCon server is disposed*********");
		acceptor.dispose();
		log.info("*********销毁消息队列*********");
		QueueManager.getReqQueue().clear();
		QueueManager.getResQueue().clear();
		QueueManager.getPretreatRequestQueue().clear();
		log.info("*********销毁消息请求接收线程池*********");
		reqHandlerExcutor.shutdownNow();
		log.info("*********销毁消息处理线程*********");
		Set set = processThreadMap.entrySet();
		Iterator it = set.iterator();
		while(it.hasNext()){
			Thread process = (Thread) it.next();
			if(process!=null){
				process.interrupt();
			}else{
				continue;
			}	
		}
	}
}
