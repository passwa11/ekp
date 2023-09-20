package com.landray.kmss.km.imeeting.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class HttpUtil {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HttpUtil.class);
	
	public static String sendGet(String url) throws Exception {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000)
				.setSocketTimeout(15000).build());
		CloseableHttpResponse response = null;
		HttpEntity httpEntity = null;
		String result = "";
		try {
			logger.warn("boen请求调用开始：" + url);
			response = client.execute(httpGet);
			try {
				httpEntity = response.getEntity();
				result = EntityUtils.toString(httpEntity);
				logger.warn("boen接口调用：" + url + "\n result:" + result);
			} catch (Exception e) {
				logger.error("", e);
				logger.error("boen接口调用出错：" + url + "\n result:" + result);
			} finally {
				response.close();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			client.close();
		}
		return result;
	}

	public static String sendPost(String url, String content) throws Exception {
		String result = "";
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		// httpPost.setEntity(new UrlEncodedFormEntity(postParams, "utf-8"));
		httpPost.setHeader("Content-Type", "application/json");
		StringEntity postingString = new StringEntity(content, "utf-8");

		httpPost.setEntity(postingString);
		CloseableHttpResponse response = null;
		HttpEntity httpEntity = null;
		try {
			logger.warn("boen请求调用开始：" + url + "," + content);
			response = client.execute(httpPost);
			try {
				httpEntity = response.getEntity();
				result = EntityUtils.toString(httpEntity);
				logger.warn("boen接口调用：" + url + "\n result:" + result);
			} catch (Exception e) {
				logger.error("", e);
				logger.error("boen接口调用出错：" + url + "\n result:" + result);
			} finally {
				response.close();
			}
		} catch (Exception e) {
			logger.error("", e);
			logger.error("boen接口调用出错：" + url + "\n result:" + result);
		} finally {
			client.close();
		}
		return result;
	}

	public static String sendPut(String url, String content) throws IOException {

		String result = "";
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPut httpPut = new HttpPut(url);
		httpPut.setHeader("Content-Type", "application/json");
		StringEntity postingString = new StringEntity(content, "utf-8");

		httpPut.setEntity(postingString);
		CloseableHttpResponse response = null;
		HttpEntity httpEntity = null;
		try {
			logger.warn("boen请求调用开始：" + url + "," + content);
			response = client.execute(httpPut);
			try {
				httpEntity = response.getEntity();
				result = EntityUtils.toString(httpEntity);
				logger.warn("boen接口调用：" + url + "\n result:" + result);
			} catch (Exception e) {
				logger.error("", e);
				logger.error("boen接口调用出错：" + url + "\n result:" + result);

			} finally {
				response.close();
			}
		} catch (Exception e) {
			logger.error("", e);
			logger.error("boen接口调用出错：" + url + "\n result:" + result);
		} finally {
			client.close();
		}
		return result;
	}

	// 删除，还没验证
	public static String sendDelete(String url) throws Exception {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete(url);
		httpDelete.setConfig(RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000)
				.setSocketTimeout(15000).build());
		CloseableHttpResponse response = null;
		HttpEntity httpEntity = null;
		String result = "";
		try {
			response = client.execute(httpDelete);
			try {
				httpEntity = response.getEntity();
				result = EntityUtils.toString(httpEntity);
			} catch (Exception e) {
			} finally {
				response.close();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			client.close();
		}
		return result;
	}

}
