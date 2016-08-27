package com.ebs.receiver.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ebs.receiver.socket.mina.service.StartSotConServers;


@SuppressWarnings("serial")
public class LotteryStartServlet extends HttpServlet{
	private static Logger log = Logger.getLogger(LotteryStartServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}


	/**
	 * ¥¶¿ÌPOST«Î«Û
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
	}


	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		StartSotConServers.getInstance().startListener();
	}
}