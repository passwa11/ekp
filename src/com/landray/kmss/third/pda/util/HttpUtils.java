package com.landray.kmss.third.pda.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;

/**
 * @author menglei
 * @dateSep 25, 2013
 */
public final class HttpUtils {
	
	private static  int TIME_OUT_CONNECTION = 30000;  // 30秒  设置连接超时时间(单位毫秒)
    private static  int TIME_OUT_SOTIMEOUT = 120000;  // 120秒 设置读数据超时时间(单位毫秒)
    
	public static final String UTF8 = "utf-8";            // 字符编码 UTF-8
    
	private HttpUtils() {

	}

	/**
	 * 获得发起请求的ID地址
	 * 
	 * @Title: getRequestIP
	 * @return String 返回类型
	 */
	public static String getRequestIP(javax.servlet.http.HttpServletRequest req) {

		String ip = req.getHeader("X-Cluster-Client-Ip");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("x-forwarded-for");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 将IP地址转换成10进制整数
	 * 
	 * @param strIp
	 * @return
	 */
	public static String convertIp(String strIp) {
		long[] ip = new long[4];
		int position1 = strIp.indexOf(".");
		int position2 = strIp.indexOf(".", position1 + 1);
		int position3 = strIp.indexOf(".", position2 + 1);
		ip[0] = Long.parseLong(strIp.substring(0, position1));
		ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(strIp.substring(position3 + 1));
		return String.valueOf((ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8)+ ip[3]);
	}

	/**
	 * Initiates the request
	 * 
	 * @param reqUrl
	 * @return
	 */
	public static String postRequest(String reqUrl) {
		DataInputStream in = null;
		StringBuilder out = new StringBuilder();
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			in = new DataInputStream(connection.getInputStream());
			byte[] buffer = new byte[4096];
			int count = 0;
			while ((count = in.read(buffer)) > 0) {
				out.append(new String(buffer, 0, count));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					in = null;
				}
			}
		}
		return out.toString();
	}

	/**
	 * Initiates the request
	 * 
	 * @param xml
	 * @param url
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String postRequest(String xml, String url) {
		String result = "";
		HttpClient httpClient = new HttpClient();
		PostMethod post = new PostMethod(url);
		try {
			post.setRequestHeader("Content-Type", "text/xml; charset=GB2312");
			post.setRequestBody(xml);
			httpClient.executeMethod(post);
			if (post.getStatusCode() != HttpStatus.SC_OK) {
				post.releaseConnection();
				return result;
			}
			InputStream resStream = post.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(resStream));
			StringBuilder resBuffer = new StringBuilder();
			String resTemp = "";
			while ((resTemp = br.readLine()) != null) {
				resBuffer.append(resTemp);
			}
			result = resBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (post != null) {
				post.releaseConnection();
			}
		}
		return result;
	}
	
	public static String postMethod(String url, NameValuePair[] params,String charset) {
		String result = "";
		HttpClient httpClient = new HttpClient();

		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIME_OUT_CONNECTION);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIME_OUT_SOTIMEOUT);

		PostMethod post = new PostMethod(url);
		try {
			post.setRequestHeader("Content-Type", "text/xml; charset="+ charset);
			post.addParameters(params);
			httpClient.executeMethod(post);
			if (post.getStatusCode() != HttpStatus.SC_OK) {
				post.releaseConnection();
				return result;
			}
			InputStream resStream = post.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(resStream, charset));
			StringBuilder resBuffer = new StringBuilder();
			String resTemp = "";
			while ((resTemp = br.readLine()) != null) {
				resBuffer.append(resTemp);
			}
			result = resBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (post != null) {
				post.releaseConnection();
				((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
			}
		}
		return result;
	}
	
	 public static String postMethod(String url, Map<String, String> paramMap,String charset) throws IOException {
		PostMethod postMethod = new PostMethod(url);
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			postMethod.setParameter(entry.getKey(), entry.getValue());
		}
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIME_OUT_CONNECTION);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIME_OUT_SOTIMEOUT);
		try {
			httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
			httpClient.executeMethod(postMethod);
			return postMethod.getResponseBodyAsString();
		} catch (HttpException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
				((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
			}
		}
	}

	public static String getMethod(String url, String charset) throws IOException {
		GetMethod getMethod = new GetMethod(url);
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIME_OUT_CONNECTION);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIME_OUT_SOTIMEOUT);
		try {
			httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
			httpClient.executeMethod(getMethod);
			return getMethod.getResponseBodyAsString();
		} catch (HttpException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
				((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
			}
		}
	}
	
	/**
	 * @Description: 将字符串 s 进行 enc编码
	 * @param s  待编码字符串内容 比如：a
	 * @param enc 待编码类型 比如：UTF-8
	 * @return
	 */
	public static String encode(String s, String enc) {
		String value = "";
		try {
			if (StringUtils.isNotEmpty(s) && StringUtils.isNotEmpty(enc)) {
				value = java.net.URLEncoder.encode(s, enc);
			}
		} catch (UnsupportedEncodingException e) {
			return value;
		}
		return value;
	}
	
	/**
	 * @Description: 将字符串 s 进行 enc解码
	 * @param s  待解码字符串内容 比如：a
	 * @param enc 待解码类型 比如：UTF-8
	 * @return
	 */
	public static String decode(String s, String enc) {
		String value = "";
		try {
			if (StringUtils.isNotEmpty(s) && StringUtils.isNotEmpty(enc)) {
				value = java.net.URLDecoder.decode(s, enc);
			}
		} catch (UnsupportedEncodingException e) {
			return value;
		}
		return value;
	}
	
	public static void main(String[] args)throws IOException{
		
    	Map<String, String> paramMap = new HashMap<String,String>();
    	paramMap.put("broadcast", "");
    	paramMap.put("apiKey", apiKey);
    	paramMap.put("username", username);
    	paramMap.put("title", title);
    	paramMap.put("message", message);
    	paramMap.put("uri", uri);
    	HttpUtils.postMethod(PUSH_MESSAGE_SERVER_URL, paramMap, UTF8);
    	
    }
    
    //http://127.0.0.1:8080/Androidpn/index.jsp
    //http://127.0.0.1:8080/Androidpn/notification.do?action=send
    public static String PUSH_MESSAGE_SERVER_URL= "http://127.0.0.1:8080/Androidpn/notification.do?action=send";
    //public static String PUSH_MESSAGE_SERVER_URL= "http://127.0.0.1:7070/notification.do?action=send";
    
	public static final String broadcast = "";
	public static final String apiKey = "1234567890";
	public static final String username = "menglei";
	public static final String title = "message notification";
	public static final String message ="message body";
	public static final String uri ="http://www.hao123.com";

}
