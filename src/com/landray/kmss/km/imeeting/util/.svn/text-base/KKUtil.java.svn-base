package com.landray.kmss.km.imeeting.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.landray.kmss.km.imeeting.integrate.kk.IMeetingKKPluginUtil;
import com.landray.kmss.km.imeeting.integrate.kk.interfaces.IMeetingKKPlugin;
import com.landray.kmss.km.imeeting.integrate.kk.interfaces.IMeetingKKProvider;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

public class KKUtil {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(KKUtil.class);


	public static Boolean isKkVideoMeetingEnable() throws Exception {
		Boolean flag = false;
		List<IMeetingKKPlugin> plugins = IMeetingKKPluginUtil.getExtensionList();
		if (plugins != null && plugins.size() > 0) {
			for (IMeetingKKPlugin plugin : plugins) {
				String fdKey = plugin.getKey();
				if (StringUtil.isNotNull(fdKey) && "ali".equals(fdKey)) {
					IMeetingKKProvider provider = plugin.getProvider();
					if (provider.isEnabled()) {
						flag = true;
					}
				}
			}
		}
		return flag && KmImeetingConfigUtil.isVideoMeetingEnable();
	}

	public static String getKKUrl() throws Exception {
		List<IMeetingKKPlugin> plugins = IMeetingKKPluginUtil.getExtensionList();
		if (plugins != null && plugins.size() > 0) {
			for (IMeetingKKPlugin plugin : plugins) {
				String fdKey = plugin.getKey();
				if (StringUtil.isNotNull(fdKey) && "ali".equals(fdKey)) {
					IMeetingKKProvider provider = plugin.getProvider();
					if (StringUtil.isNotNull(provider.getUrl())) {
						String Url = provider.getUrl();
						if (Url.endsWith("/")) {
							Url = Url.substring(0, Url.length() - 1);
						}
						return Url;
					}
				}
			}
		}
		return null;
	}

	public static String getKKUrlHttps() throws Exception {
		String url = KKUtil.getKKUrl();
		if (StringUtil.isNotNull(url)) {
			if (url.endsWith("/")) {
				url = url.substring(0, url.lastIndexOf("/"));
			}
			if (StringUtil.isNotNull(url) && !url.startsWith("https")) {
				String[] arr = url.split(":");
				if (arr.length == 3) {
					url = "https:" + arr[1];
					String portStr = arr[2];
					String[] portArr = portStr.split("/");
					if (portArr.length > 1) {
						url += ":8443" + portStr.substring(portStr.indexOf("/"), portStr.length());
					} else {
						url += ":8443";
					}
				}
				if (arr.length == 2) {
					url = "https:";
					String portStr = arr[1];
					if (portStr.indexOf("/", 3) > 0) {
						url += portStr.substring(0, portStr.indexOf("/", 3)) + ":8443"
								+ portStr.substring(portStr.indexOf("/", 3), portStr.length());
					} else {
						url += portStr + ":8443";
					}
				}
			}
		}
		return url;
	}

	public static void main(String[] args) throws Exception {
		String url = "http://192.168.1.100/";
		if (url.endsWith("/")) {
			url = url.substring(0, url.lastIndexOf("/"));
		}
		if (StringUtil.isNotNull(url) && !url.startsWith("https")) {
			String[] arr = url.split(":");
			if (arr.length == 3) {
				url = "https:" + arr[1];
				String portStr = arr[2];
				String[] portArr = portStr.split("/");
				if (portArr.length > 1) {
					System.out.println(portStr.indexOf("/"));
					url += ":8443" + portStr.substring(portStr.indexOf("/"), portStr.length());
					System.out.println(url);
				} else {
					url += ":8443";
					System.out.println(url);
				}
			}
			if (arr.length == 2) {
				url = "https:";
				String portStr = arr[1];
				if (portStr.indexOf("/", 3) > 0) {
					System.out.println(portStr.indexOf("/", 3));
					url += portStr.substring(0, portStr.indexOf("/", 3)) + ":8443"
							+ portStr.substring(portStr.indexOf("/", 3), portStr.length());
					System.out.println(url);
				} else {
					url += portStr + ":8443";
					System.out.println(url);
				}
			}
		}
	}



	public static JSONObject getKKToken() throws Exception {
		List<IMeetingKKPlugin> plugins = IMeetingKKPluginUtil.getExtensionList();
		JSONObject token = new JSONObject();
		if (plugins != null && plugins.size() > 0) {
			for (IMeetingKKPlugin plugin : plugins) {
				String fdKey = plugin.getKey();
				if (StringUtil.isNotNull(fdKey) && "ali".equals(fdKey)) {
					IMeetingKKProvider provider = plugin.getProvider();
					token = provider.getToken();
				}
			}
		}
		return token;
	}

	public static String sendPost(String url, String content) throws Exception {
		String result = "";
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/json");
		StringEntity postingString = new StringEntity(content, "utf-8");
		httpPost.setEntity(postingString);
		CloseableHttpResponse response = null;
		HttpEntity httpEntity = null;
		try {
			logger.warn("kk阿里云会议同步调用开始：" + url + "," + content);
			response = client.execute(httpPost);
			try {
				httpEntity = response.getEntity();
				result = EntityUtils.toString(httpEntity);
				logger.warn("kk阿里云会议接口调用：" + url + "\n result:" + result);
			} catch (Exception e) {
				logger.error("", e);
				logger.error("kk阿里云会议同步调用出错：" + url + "\n result:" + result);
			} finally {
				response.close();
			}
		} catch (Exception e) {
			logger.error("", e);
			logger.error("kk阿里云会议同步调用出错：" + url + "\n result:" + result);
		} finally {
			client.close();
		}
		return result;
	}
}
