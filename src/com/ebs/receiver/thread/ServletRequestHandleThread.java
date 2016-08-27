package com.ebs.receiver.thread;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ebs.receiver.comm.HttpFuncUtils;
import com.ebs.receiver.conf.Configuration;
import com.ebs.receiver.conf.PropertiesContext;
import com.ebs.receiver.domain.Header;
import com.ebs.receiver.domain.RequestMess;
import com.ebs.receiver.util.DES;
import com.ebs.receiver.util.EmojiUtil;
import com.ebs.receiver.util.JSONUtils;
import com.ebs.receiver.util.PassMsgUtil;

public class ServletRequestHandleThread extends Thread {
	private static Log logger = LogFactory.getLog(ServletRequestHandleThread.class);
	private String reqMsg;
	private HttpServletResponse response;

	public ServletRequestHandleThread(String reqMsg, HttpServletResponse response) {
		this.reqMsg = reqMsg;
		this.response = response;
	}

	@Override
	public void run() {
		try {
			RequestMess requestMess = JSONUtils.json2pojo(reqMsg, RequestMess.class);
			// 解密及解析
			Header header = null;
			String bodyString = "";
			String bodyStr = "";
			try {
				header = JSONUtils.json2pojo(new DES().DecodeCBC(requestMess.getHead()), Header.class);
				System.out.println(requestMess.getBody());
				bodyString = new DES().DecodeCBC(requestMess.getBody().trim());
				bodyStr = EmojiUtil.resolveToByteFromEmoji(bodyString);
			} catch (Exception e) {
				logger.error(e);
				HttpFuncUtils.packException("0010", Configuration.getGlobalMsg("MSG_0010"), response);
				return;
			}
			String content = PassMsgUtil.getMsg(header, bodyStr);
			System.out.println(content);
			// 组包
			if (content.equals("")) {
				HttpFuncUtils.packException("0007", Configuration.getGlobalMsg("MSG_0007"), response);
				return;
			}
			String ret = "";
			Socket socket = null;
			try {
				logger.info("发送前置:[" + content + "]");
				String address = PropertiesContext.instance.getLott_address();
				String port = PropertiesContext.instance.getLott_port();
				socket = new Socket(address, Integer.parseInt(port));
				socket.setSoLinger(true, 180);
				socket.setSoTimeout(180 * 1000);
				socket.setReceiveBufferSize(Integer.parseInt(PropertiesContext.instance.getChnl_maxlen()));
				InputStream input = socket.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(input, "UTF-8"));
				OutputStream output = socket.getOutputStream();
				content = content + "\r\n";
				output.write(content.getBytes("UTF-8"));
				output.flush();

				// 读取服务端返回数据
				ret = br.readLine();
				socket.close();
				logger.info("前置返回：[" + ret + "]");
				ret = new String(EmojiUtil.resolveToEmojiFromByte(ret));
				ret = ret.substring(ret.indexOf("{"), ret.length());
				requestMess.setBody(new DES().EncodeCBC(ret));
				ret = JSONUtils.obj2json(requestMess);
				response.getWriter().write(ret);

				logger.info("响应统一接入平台：[" + ret + "]");
			} catch (Exception e) {
				e.printStackTrace();
				HttpFuncUtils.packException("9999", Configuration.getGlobalMsg("MSG_9999"), response);
				logger.error("发送到具体前置机异常，报文：[" + content + "]");
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (Exception e) {
						socket = null;
						logger.error("发送到具体前置机结束关闭session异常：[" + e.getMessage() + "]");
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			HttpFuncUtils.packException("9999", Configuration.getGlobalMsg("MSG_9999"), response);
		}
	}

}
