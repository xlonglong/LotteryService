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
 * @since:2015��10��26������4:59:25		
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
			System.out.println("���ͺ�̨���ģ�[" + sRecBuf + "]");
			sRecBuf = sRecBuf + "\n\r";
			// дbuf
			out.write(sRecBuf.getBytes());
			// ��buf
			try
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				//����ɹ���
				sReturnBuf = br.readLine();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			System.out.println("��̨���ر��ģ�[" + sReturnBuf + "]");
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
