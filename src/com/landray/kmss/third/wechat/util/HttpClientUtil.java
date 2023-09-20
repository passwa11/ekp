package com.landray.kmss.third.wechat.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;




public class HttpClientUtil {
	private static MultiThreadedHttpConnectionManager connectionManager;

	private static MultiThreadedHttpConnectionManager getConnectionManager() {
		if (connectionManager == null) {
			connectionManager = new MultiThreadedHttpConnectionManager();
		}
		return connectionManager;
	}

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(HttpClientUtil.class);
	
//	public static String httpPost2(String url) throws Exception{
//		String responseBody = "";
//		DefaultHttpClient httpclient = new DefaultHttpClient();
//		try {
//			HttpPost httpost = new HttpPost(
//					"http://java.landray.com.cn/j_acegi_security_check");
//			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//			nvps.add(new BasicNameValuePair("j_username", "lizh"));
//			nvps.add(new BasicNameValuePair("j_password", ""));
//
//			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
//			HttpResponse response = httpclient.execute(httpost);
//			httpost.abort();
//			int statusCode = response.getStatusLine().getStatusCode();
//			System.out.println("登录返回状态：" + statusCode);
//			URI uri = new URI(url);
//			HttpGet httpget = new HttpGet();
//			httpget.setURI(uri);
//			ResponseHandler<String> responseHandler = new BasicResponseHandler();
//			responseBody = httpclient.execute(httpget,
//						responseHandler);
//			System.out.println("------开始读取数据------");
//			System.out.println(responseBody);
//			System.out.println("______结束读取数据______");
//		} finally {
//			httpclient.getConnectionManager().shutdown();
//		}
//		return responseBody;
//	}
	
	private static DefaultHttpClient getHttpClient()
	throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost("http://localhost:8080/ekp/j_acegi_security_check");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
		nvps.add(new BasicNameValuePair("j_username", " "));
		nvps.add(new BasicNameValuePair("j_password", " "));
		
		httpost.setEntity(new UrlEncodedFormEntity(nvps, "gb2312"));
		HttpResponse response = httpclient.execute(httpost);
		httpost.abort();
		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		return httpclient;
}
	
	public static void main(String[] args){
		try {
			getHttpClient();
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
//		 HttpClient client = new HttpClient();
//	     NameValuePair[] nameValuePairs = {
//	          new NameValuePair("j_username", "lizh"),
//	          new NameValuePair("j_password", "1")
//	     };
//	     PostMethod postMethod = new PostMethod("http://localhost:8080/ekp/j_acegi_security_check");
//	     postMethod.setRequestBody(nameValuePairs);
//	     int stats = 0;
//	        try {
//	            stats = client.executeMethod(postMethod);
//	            System.out.println(stats);
//	        } catch (HttpException e) {
//	            e.printStackTrace();
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//	        postMethod.releaseConnection();//这里最好把之前的资源放掉
//	   
//	        CookieSpec cookiespec = CookiePolicy.getDefaultSpec();   
//	        
//	        Cookie[] cookies = cookiespec.match("landray.com.cn", 80, "/" , false , client.getState().getCookies());
//	        for (Cookie cookie : cookies) {
//	            System.out.println(cookie.getName() + "##" + cookie.getValue());
//	        }
//	        
//	        HttpMethod method = null;
//	        String encode = "utf-8";//页面编码,按访问页面改动
//	        String referer = "http://java.landray.com.cn";//http://www.163.com
//	        String mailUrl = "http://mail.landray.com.cn/mail/lizh.nsf/iNotes/Proxy/?OpenDocument&Form=s_ReadViewEntries&PresetFields=FolderName;($Inbox),UnreadOnly;1&count=-1";
//	        method = new GetMethod(mailUrl);//后续操作
//	        method.getParams().setParameter("http.useragent","Mozilla/4.0 (compatible; MSIE 5.5; Windows 98)");
//	        method.setRequestHeader("Referer", referer);
//	 
//	        client.getParams().setContentCharset(encode);
//	        client.getParams().setSoTimeout(300000);
//	        client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(10, true));
//	   
//	        try {
//	            stats = client.executeMethod(method);
//	            
//	        } catch (HttpException e) {
//	            e.printStackTrace();
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//	        if (stats == HttpStatus.SC_OK) {
////	        	try {
////					System.out.println(method.getResponseBodyAsString());
////				} catch (IOException e) {
////					// TODO 自动生成 catch 块
////					e.printStackTrace();
////				}
//	            System.out.println("提交成功!");
//	             
//	        }
 
	}
	
	public static String httpPost(String url) {
		PostMethod post = null;
		String resString = "";
		try {
			HttpClient httpClient = new HttpClient(getConnectionManager());
			// 设置超时
			httpClient.setConnectionTimeout(4000);
			httpClient.setTimeout(4000);
			// 设置http头
			List<Header> headers = new ArrayList<Header>();
			headers.add(new Header("User-Agent", "ucweb"));
			httpClient.getHostConfiguration().getParams()
					.setParameter("http.default-headers", headers);
			post = new PostMethod(url);
			
			int result = httpClient.executeMethod(post);
			if (result == 200) {
				resString = post.getResponseBodyAsString();
			} else {
				resString = post.getResponseBodyAsString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (post != null) {
				post.releaseConnection();
			}
		}
		return resString;
	}
	
	
	public static String httpGet(String url) {
		GetMethod  getMethod = null;
		String resString = "";
		try {
			HttpClient httpClient = new HttpClient(getConnectionManager());
			// 设置超时
			httpClient.setConnectionTimeout(4000);
			httpClient.setTimeout(4000);
			// 设置http头
			List<Header> headers = new ArrayList<Header>();
			headers.add(new Header("User-Agent", "ucweb"));
			httpClient.getHostConfiguration().getParams()
					.setParameter("http.default-headers", headers);
			getMethod = new GetMethod(url);
			
			int result = httpClient.executeMethod(getMethod);
			if (result == 200) {
				resString = getMethod.getResponseBodyAsString();
			} else {
				resString = getMethod.getResponseBodyAsString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
		}
		return resString;
	}
	
	public static String httpPost(String url,Cookie cookies) {
		PostMethod post = null;
		String resString = "";
		try {
			HttpClient httpClient = new HttpClient(getConnectionManager());
			// 设置超时
			httpClient.setConnectionTimeout(4000);
			httpClient.setTimeout(4000);
			// 设置http头
			List<Header> headers = new ArrayList<Header>();
			headers.add(new Header("User-Agent", "ucweb"));
			headers.add(new Header("Cookie", cookies.getValue()));
			httpClient.getHostConfiguration().getParams()
					.setParameter("http.default-headers", headers);
			//httpClient.getState().addCookies(cookies);
			post = new PostMethod(url);
			
			int result = httpClient.executeMethod(post);
			if (result == 200) {
				resString = post.getResponseBodyAsString();
			} else {
				resString = post.getResponseBodyAsString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(cookies.getValue(),e);
		} finally {
			if (post != null) {
				post.releaseConnection();
			}
		}
		return resString;
	}
	
	
	public static String httpPost2(String url,String jessionId) {
		PostMethod post = null;
		String resString = "";
		try {
			HttpClient httpClient = new HttpClient(getConnectionManager());
			// 设置超时
			httpClient.setConnectionTimeout(4000);
			httpClient.setTimeout(4000);
			// 设置http头
			List<Header> headers = new ArrayList<Header>();
			headers.add(new Header("User-Agent", "ucweb"));
			headers.add(new Header("Cookie", jessionId));
			httpClient.getHostConfiguration().getParams()
					.setParameter("http.default-headers", headers);
			post = new PostMethod(url);
			
			int result = httpClient.executeMethod(post);
			if (result == 200) {
				resString = post.getResponseBodyAsString();
			} else {
				resString = post.getResponseBodyAsString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (post != null) {
				post.releaseConnection();
			}
		}
		return resString;
	}
}
