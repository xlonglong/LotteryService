package com.ebs.receiver.thread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
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

import com.ebs.receiver.conf.PropertiesContext;
import com.ebs.receiver.socket.mina.service.SotSyncRevHandler;

public class SotSyncRevThread extends Thread {
	private static Log logger = LogFactory.getLog(SotSyncRevThread.class);
	private SocketAcceptor acceptor = new NioSocketAcceptor();

	@Override
	public void run() {
		try {
			acceptor.setReuseAddress(true);
			acceptor.getSessionConfig().setReceiveBufferSize(4096);
			acceptor.getSessionConfig().setSendBufferSize(4096);
			// ����������ݵĹ�����
			DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
			// �趨�����������һ��һ��(/r/n)�Ķ�ȡ���
			TextLineCodecFactory textLineCodecFactory = new TextLineCodecFactory(
					Charset.forName("UTF-8"));
			chain
					.addLast("sync", new ProtocolCodecFilter(
							textLineCodecFactory));
			textLineCodecFactory.setDecoderMaxLineLength(4096);
			acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
			chain.addLast("threadPool", new ExecutorFilter(Executors
					.newCachedThreadPool()));
			// �趨�������˵���Ϣ������:
			acceptor.setHandler(new SotSyncRevHandler());
			int receiveServicePort = Integer.parseInt(PropertiesContext
					.getInstance().getSync_listen_port());
			// �󶨶˿�,��������

			acceptor.bind(new InetSocketAddress(receiveServicePort));
			logger.info("*********sot sync server is listening on:= "
					+ receiveServicePort + "*********");
		} catch (NumberFormatException e) {
			logger.error("�������첽���������쳣:" + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			logger.error("�������첽���������쳣:" + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (Exception e) {
			logger.error("�������첽���������쳣:" + e.getMessage());
			e.printStackTrace();
			// TODO: handle exception
		}

	}

}
