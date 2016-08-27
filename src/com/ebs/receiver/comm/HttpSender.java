package com.ebs.receiver.comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;  
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.protocol.HTTP;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.ParseException;  
import org.apache.http.client.ClientProtocolException;  
import org.apache.http.client.HttpClient;


public class HttpSender {
	private static Logger logger = Logger.getLogger(HttpSender.class);

	public static String post(String url, Map<String, String> params) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;

		logger.info("create httppost:" + url);
		HttpPost post = postForm(url, params);
		body = invoke(httpclient, post);
		httpclient.getConnectionManager().shutdown();
		return body;

	}

	private static HttpPost postForm(String url, Map<String, String> params) {

		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, params.get(key)));
		}

		try {
			logger.info("set utf-8 form entity to httppost");
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return httpost;
	}

	private static String invoke(DefaultHttpClient httpclient,
			HttpUriRequest httpost) {

		HttpResponse response = sendRequest(httpclient, httpost);
		String body = paseResponse(response);

		return body;
	}
	private static String paseResponse(HttpResponse response) {  
        logger.info("get response from http server..");  
        HttpEntity entity = response.getEntity();  
          
        logger.info("response status: " + response.getStatusLine());  
        String charset = EntityUtils.getContentCharSet(entity);  
        logger.info(charset);  
          
        String body = null;  
        try {  
            body = EntityUtils.toString(entity);  
            logger.info(body);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
          
        return body;  
    } 
	 private static HttpResponse sendRequest(DefaultHttpClient httpclient,  
	            HttpUriRequest httpost) {  
	        logger.info("execute post...");  
	        HttpResponse response = null;  
	          
	        try {  
	            response = httpclient.execute(httpost);  
	        } catch (ClientProtocolException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        return response;  
	    }  
	 
	 
	 
	public String executeGet(String url) {
		BufferedReader in = null;

		String content = null;
		try {
			// ����HttpClient
			HttpClient client = new DefaultHttpClient();
			// ʵ��HTTP����
			HttpGet request = new HttpGet();
			logger.info("����url=" + url);

			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);

			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			content = sb.toString();
		} catch (Exception e) {
			logger.error(e);
		} finally {
			if (in != null) {
				try {
					in.close();// ���Ҫ�ر�BufferedReader
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}

		}
		return content;
	}  
	 
	 
	 public static String doGet(String url, Map<String, Object> params) {  
	        String apiUrl = url;  
	        StringBuffer param = new StringBuffer();  
	        int i = 0;  
	        for (String key : params.keySet()) {  
	            if (i == 0)  
	                param.append("?");  
	            else  
	                param.append("&");  
	            param.append(key).append("=").append(params.get(key));  
	            i++;  
	        }  
	        apiUrl += param;  
	        String result = null;  
	        HttpClient httpclient = new DefaultHttpClient();  
	        try {  
	            HttpGet httpPost = new HttpGet(apiUrl);  
	            HttpResponse response = httpclient.execute(httpPost);  
	            int statusCode = response.getStatusLine().getStatusCode();  
	  
	            System.out.println("ִ��״̬�� : " + statusCode);  
	  
	            HttpEntity entity = response.getEntity();  
	            if (entity != null) {  
	                InputStream instream = entity.getContent();  
	                result = IOUtils.toString(instream, "UTF-8");  
	            }  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        return result;  
	    }  
	 
}
