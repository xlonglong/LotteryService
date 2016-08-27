/**
 * 
 */
package com.ebs.receiver.comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author xlonglong
 * @since:2015年10月26日下午4:59:25		
 * @version V1.0
 */
public class SendUtil {

	private static Log log = LogFactory.getLog(SendUtil.class);


	public synchronized static String send(String sRecBuf, String host,
			String sport) {
		Socket s = null;
		String sReturnBuf = "";

		try {
			int port = Integer.parseInt(sport);
			s = new Socket(host, port);
			InputStream in = s.getInputStream();
			OutputStream out = s.getOutputStream();
			System.out.println("发送后台报文：[" + sRecBuf + "]");
			sRecBuf = sRecBuf + "\n\r";
			// 写buf
			out.write(sRecBuf.getBytes());
			// 读buf
			try
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				//试验成功的
				sReturnBuf = br.readLine();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			System.out.println("后台返回报文：[" + sReturnBuf + "]");
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			try {
				if (s != null)
					s.close();
			} catch (IOException e) {
				System.out.println("Error:" + e.toString());
			}
		}
		return sReturnBuf;
	}
}
