package com.ebs.receiver.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ebs.receiver.thread.ServletRequestHandleThread;

@SuppressWarnings("serial")
public class LotteryServlet extends HttpServlet {

	private static Logger log = Logger.getLogger(LotteryServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	/**
	 * 处理POST请求
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		String jsonInStr = sb.toString();
		if (jsonInStr.substring(0, 1).equals("=")) {
			System.out.println(jsonInStr);
			jsonInStr = URLDecoder.decode(sb.toString(), "UTF-8");
			jsonInStr = jsonInStr.substring(jsonInStr.indexOf("{"));
		}
		log.info("收到统一平台消息:" + jsonInStr);
		new ServletRequestHandleThread(jsonInStr, response).run();
	}

}
