package com.ebs.receiver.socket.mina.service;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.ebs.receiver.domain.RequestBean;
import com.ebs.receiver.domain.TaskBean;
import com.ebs.receiver.init.SysDefine;

/**
* 接受转发机服务
* @author：yangyj
* @since：2012-6-19  
* @version:1.0
*/
public class SotSyncRevHandler extends IoHandlerAdapter {

	private static Log logger = LogFactory.getLog(SotSyncRevHandler.class);

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		logger.error("接收信息出现异常", cause);
		super.exceptionCaught(session, cause);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
//		logger.debug("接入ACCT前置机连接,远程地址:" + session.getRemoteAddress());
	}

	// 当一个客户端关闭时
	@Override
	public void sessionClosed(IoSession session) {
//		logger.debug("断开ACCT前置机连接!");
	}

	public static void main(String[] args) {
		String d = "  20141203225214HXBUSI  200016000008  192.168.1.1392633856c8c552451cbdf043e5d1d04f9{\"header\":{\"streamid\":\"20141203225214\",\"tradetypecode\":\"HXBUSI\",\"tradecode\":\"200016\",\"channelcode\":\"000008\",\"ip\":\"192.168.1.139\",\"mac\":\"2633856c8c552451cbdf043e5d1d04f9\"},\"version\":{\"code\":\"1.0\",\"subcode\":\"0\"},\"order\":{\"orderid\":\"96863380561\"},\"payinfo\":{\"request\":\"\",\"response\":\"\",\"retcode\":\"0\",\"type\":\"1\"},\"result\":{\"returncode\":\"8011\"},\"actor\":{\"orgcode\":\"ZJ\",\"code\":\"18768451234\",\"password\":\"\",\"type\":\"\"}}";
		System.out.println(d.substring(0,16));
	}
	
	// 消息到达时:
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		String ret = (String) message;
		logger.info("收到的推送报文：[" + ret + "]");
		String streamId = ret.substring(0,16);
		//String streamId = ret.substring(4,20);
//		logger.info("[" + streamId + "]进入前置推送线程");
//		Map map = PropertiesContext.instance.getMap();
		Map map = SysDefine.getMap();
		logger.info("流水号为：[" + streamId + "] 取到的信息为：【"+map.get(streamId)+"】");
		if(map.get(streamId)==null){
			logger.info("流水号为：[" + streamId + "] 没取到消息。。。。");
			RequestBean requestBean = (RequestBean)map.get("http"+streamId);
			if(requestBean != null){
				map.remove(streamId);
				TaskBean taskBean = new TaskBean();
				taskBean.setRequestBean(requestBean);
				requestBean.getHttpConnectionInfo().getResponse().getWriter().write(ret);
				requestBean.getHttpConnectionInfo().getResponse().getWriter().close();
//				logger.info(map.size());
				logger.info("通过短连接监听发送到web的报文：[" + ret + "]");
			}
		}else{
			logger.info("流水号为：[" + streamId + "] 已取到消息。。。。");
			RequestBean requestBean = (RequestBean)map.get(streamId);
			if(requestBean != null){
				map.remove(streamId);
				TaskBean taskBean = new TaskBean();
				taskBean.setRequestBean(requestBean);
				requestBean.getConnectionInfo().getSession().write(ret);
				requestBean.getConnectionInfo().getSession().close(false);
//				logger.info(map.size());
				logger.info("通过短连接监听发送到web的报文：[" + ret + "]");
			}
			session.write("SUCCESS");
			session.close(false);
		}
		
		
	}

}



