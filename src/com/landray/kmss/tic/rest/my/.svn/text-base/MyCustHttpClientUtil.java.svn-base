package com.landray.kmss.tic.rest.my;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

/**
 * 发送http请求
 * 
 * @author Administrator
 * 
 */
public class MyCustHttpClientUtil {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(MyCustHttpClientUtil.class);

	/**
	 * 发送Get请求
	 * 
	 * @param url
	 *            请求地址
	 * @param resultJsonKey
	 * @param clazz
	 * @return
	 */
	public static JSONObject httpGet(String url, String resultJsonKey) {
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		Builder builder = RequestConfig.custom();
		HttpHost httpHost = getHttpHost();
		builder.setSocketTimeout(2000).setConnectTimeout(2000).setConnectionRequestTimeout(2000);
		if (httpHost != null) {
			builder.setProxy(httpHost);
		}
		RequestConfig requestConfig = builder.build();
		httpGet.setConfig(requestConfig);
		try {
			response = httpClient.execute(httpGet, new BasicHttpContext());

			if (response.getStatusLine().getStatusCode() != 200) {
				logger.debug(
						"request url failed, http code=" + response.getStatusLine().getStatusCode() + ", url=" + url);
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");

				JSONObject result = JSONObject.fromObject(resultStr);
				return result;
			}
		} catch (Exception e) {
			logger.debug("request url=" + url + ", exception, msg=" + e.getMessage());
			logger.error("", e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}

			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}
		return null;
	}

	public static JSONObject httpPost(String url, JSONObject requestJsonObject)
			throws UnsupportedEncodingException {
		return httpPost(url, requestJsonObject, 5000,null);
	}
	
	public static JSONObject httpPost(String url, JSONObject requestJsonObject,Map<String,String> headers)
			throws UnsupportedEncodingException {
		return httpPost(url, requestJsonObject, 5000,headers);
	}

	public static JSONObject httpPost(String url, JSONObject requestJsonObject, int timeout,Map<String,String> headers)
			throws UnsupportedEncodingException {
		return httpPost(url,requestJsonObject.toString(), timeout,headers);
	}
	
	public static JSONObject httpPost(String url, String requestString, int timeout,Map<String,String> headers)
			throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();

		Builder builder = RequestConfig.custom();
		HttpHost httpHost = getHttpHost();
		if (timeout != 0) {
			builder.setSocketTimeout(timeout).setConnectTimeout(timeout);
		}
		if (httpHost != null) {
			builder.setProxy(httpHost);
		}
		RequestConfig requestConfig = builder.build();

		httpPost.setConfig(requestConfig);
		if(headers==null) {
			httpPost.addHeader("Content-Type", "application/json");
		}else {
			for(String key:headers.keySet()) {
				httpPost.addHeader(key, headers.get(key));
			}
		}
		
		logger.debug("url:" + url);
		logger.debug("postData=" + requestString);
		StringEntity requestEntity = new StringEntity(requestString, "utf-8");
		httpPost.setEntity(requestEntity);
		
		try {
			response = httpClient.execute(httpPost, new BasicHttpContext());
			
			if (response.getStatusLine().getStatusCode() != 200) {
				logger.debug(
						"request url failed, http code=" + response.getStatusLine().getStatusCode() + ", url=" + url);
				JSONObject result = new JSONObject();
				result.accumulate("errcode", ""+response.getStatusLine().getStatusCode());
				result.accumulate("errmsg", ""+response.getStatusLine().getStatusCode());				
				return result;
			}

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");
				logger.debug("resultStr=" + resultStr);
				JSONObject result = JSONObject.fromObject(resultStr);
				return result;
			}
		} catch (Exception e) {
			logger.debug("request url=" + url + ", exception, msg=" + e.getMessage());
			logger.error("", e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static String uploadMedia(String url, File file) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
		httpPost.setConfig(requestConfig);

		HttpEntity requestEntity = MultipartEntityBuilder.create()
				.addPart("media", new FileBody(file, ContentType.APPLICATION_OCTET_STREAM, file.getName())).build();
		httpPost.setEntity(requestEntity);

		try {
			response = httpClient.execute(httpPost, new BasicHttpContext());

			if (response.getStatusLine().getStatusCode() != 200) {

				logger.debug(
						"request url failed, http code=" + response.getStatusLine().getStatusCode() + ", url=" + url);
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");

				JSONObject result = JSONObject.fromObject(resultStr);
				return result.toString();
			}
		} catch (IOException e) {
			logger.debug("request url=" + url + ", exception, msg=" + e.getMessage());
			logger.error("", e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}
		return null;
	}

	private static HttpHost getHttpHost() {
		return null;
	}

}