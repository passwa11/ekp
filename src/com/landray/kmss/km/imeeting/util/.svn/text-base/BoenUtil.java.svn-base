package com.landray.kmss.km.imeeting.util;

import java.util.List;

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

import com.landray.kmss.km.imeeting.integrate.boen.IMeetingBoenPluginUtil;
import com.landray.kmss.km.imeeting.integrate.boen.interfaces.IMeetingBoenPlugin;
import com.landray.kmss.km.imeeting.integrate.boen.interfaces.IMeetingBoenProvider;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

public class BoenUtil {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(BoenUtil.class);


	public static Boolean isBoenEnable() throws Exception {
		Boolean flag = false;
		List<IMeetingBoenPlugin> plugins = IMeetingBoenPluginUtil.getExtensionList();
		if (plugins != null && plugins.size() > 0) {
			for (IMeetingBoenPlugin plugin : plugins) {
				String fdKey = plugin.getKey();
				if (StringUtil.isNotNull(fdKey) && "boen".equals(fdKey)) {
					IMeetingBoenProvider provider = plugin.getProvider();
					if (provider.isEnabled()) {
						flag = true;
					}
				}
			}
		}

		return flag;
	}
	
	public static String getBoenUrl() throws Exception {
		List<IMeetingBoenPlugin> plugins = IMeetingBoenPluginUtil.getExtensionList();
		if (plugins != null && plugins.size() > 0) {
			for (IMeetingBoenPlugin plugin : plugins) {
				String fdKey = plugin.getKey();
				if (StringUtil.isNotNull(fdKey) && "boen".equals(fdKey)) {
					IMeetingBoenProvider provider = plugin.getProvider();
					if (StringUtil.isNotNull(provider.getUrl())) {
						return provider.getUrl();
					}
				}
			}
		}
		return null;
	}

	public static JSONObject getTopOrg() throws Exception {
		List<IMeetingBoenPlugin> plugins = IMeetingBoenPluginUtil.getExtensionList();
		JSONObject topOrg = new JSONObject();
		if (plugins != null && plugins.size() > 0) {
			for (IMeetingBoenPlugin plugin : plugins) {
				String fdKey = plugin.getKey();
				if (StringUtil.isNotNull(fdKey) && "boen".equals(fdKey)) {
					IMeetingBoenProvider provider = plugin.getProvider();
					topOrg = provider.getTopOrg();
				}
			}
		}
		return topOrg;
	}

	public static String getUnitAdmin() throws Exception {
		List<IMeetingBoenPlugin> plugins = IMeetingBoenPluginUtil.getExtensionList();
		String fdUnitAdminId = "";
		if (plugins != null && plugins.size() > 0) {
			for (IMeetingBoenPlugin plugin : plugins) {
				String fdKey = plugin.getKey();
				if (StringUtil.isNotNull(fdKey) && "boen".equals(fdKey)) {
					IMeetingBoenProvider provider = plugin.getProvider();
					fdUnitAdminId = provider.getAdmin();
				}
			}
		}
		return fdUnitAdminId;
	}

	public static String getBoenToken() throws Exception {
		List<IMeetingBoenPlugin> plugins = IMeetingBoenPluginUtil.getExtensionList();
		String token = "";
		if (plugins != null && plugins.size() > 0) {
			for (IMeetingBoenPlugin plugin : plugins) {
				String fdKey = plugin.getKey();
				if (StringUtil.isNotNull(fdKey) && "boen".equals(fdKey)) {
					IMeetingBoenProvider provider = plugin.getProvider();
					token = provider.getToken();
				}
			}
		}
		return token;
	}



	public static String sendGet(String url) throws Exception {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(RequestConfig.custom().setConnectTimeout(20000).setConnectionRequestTimeout(20000)
				.setSocketTimeout(50000).build());
		httpGet.setHeader("token", BoenUtil.getBoenToken());
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



	public static String sendPost(String url, String content)
			throws Exception {
		String result = "";
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		// httpPost.setEntity(new UrlEncodedFormEntity(postParams, "utf-8"));
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setHeader("token", BoenUtil.getBoenToken());
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


	public static String sendPut(String url, String content)
			throws Exception {
		String result = "";
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPut httpPut = new HttpPut(url);
		httpPut.setHeader("Content-Type", "application/json");
		StringEntity postingString = new StringEntity(content,
				"utf-8");
		httpPut.setHeader("token", BoenUtil.getBoenToken());
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

	public static String sendDelete(String url) throws Exception {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete(url);
		httpDelete.setConfig(RequestConfig.custom().setConnectTimeout(5000)
				.setConnectionRequestTimeout(5000)
				.setSocketTimeout(15000).build());
		httpDelete.setHeader("token", BoenUtil.getBoenToken());
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
