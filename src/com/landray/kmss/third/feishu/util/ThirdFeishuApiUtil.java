package com.landray.kmss.third.feishu.util;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.third.feishu.model.ThirdFeishuConfig;
import com.landray.kmss.third.feishu.service.IThirdFeishuPersonMappingService;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.breaker.CircuitBreaker;
import com.landray.kmss.util.breaker.CircuitBreakerConfig;
import com.landray.kmss.util.breaker.OpenCircuitException;
import com.landray.kmss.util.breaker.ProtectedAction;

public class ThirdFeishuApiUtil {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdFeishuApiUtil.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static String doExecHttpPost(String url, String body, String token)
			throws Exception {
		String result = "";
		PostMethod postMethod = null;
		RequestEntity entity;
		long start = System.currentTimeMillis();
		HttpClient httpClient = null;
		try {
			Protocol myHTTPS = new Protocol("https",
					new MySecureProtocolSocketFactory(), 443);
			Protocol.registerProtocol("https", myHTTPS);
			postMethod = new PostMethod(url);
			postMethod.addRequestHeader(new Header("Accept-Charset","UTF-8"));
			postMethod.addRequestHeader(new Header("Content-Type",
					"application/json; charset=utf-8"));
			if (StringUtil.isNotNull(token)) {
				postMethod.addRequestHeader(
						new Header("Authorization", "Bearer " + token));
			}
			entity = new StringRequestEntity(body,
					ContentType.APPLICATION_JSON.toString(), "UTF-8");
			postMethod.setRequestEntity(entity);
			httpClient = new HttpClient();
			httpClient.setConnectionTimeout(5000);
			httpClient.setTimeout(5000);

			// httpClient.getHostConfiguration().setProxy("127.0.0.1", 8888);

			httpClient.executeMethod(postMethod);
			byte[] bytes = postMethod.getResponseBody();
			result = new String(bytes, "UTF-8");
			long end = System.currentTimeMillis();
			logger.debug("url:" + url + "---" + body + "---" + result + "---耗时："
					+ (end - start) + "ms");
		} catch (Exception e) {
			// e.printStackTrace();
			long end = System.currentTimeMillis();
			logger.error("url:" + url + "---" + body + "---" + result + "---耗时："
					+ (end - start) + "ms" + "，错误信息:" + e.getMessage());
			throw e;
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
			if (httpClient != null) {
				((SimpleHttpConnectionManager) httpClient
						.getHttpConnectionManager()).shutdown();
			}
		}

		return result;
	}

	public static String doExecHttpGet(String url, String token)
			throws Exception {
		String result = "";
		GetMethod getMethod = null;
		HttpClient httpClient = null;
		long start = System.currentTimeMillis();
		try {
			Protocol myHTTPS = new Protocol("https",
					new MySecureProtocolSocketFactory(), 443);
			Protocol.registerProtocol("https", myHTTPS);
			getMethod = new GetMethod(url);
			getMethod.addRequestHeader(new Header("Accept-Charset", "UTF-8"));
			getMethod.addRequestHeader(
					new Header("Content-Type", "application/json"));
			if (StringUtil.isNotNull(token)) {
				getMethod.addRequestHeader(
						new Header("Authorization", "Bearer " + token));
			}
			httpClient = new HttpClient();
			httpClient.setConnectionTimeout(5000);
			httpClient.setTimeout(5000);
			httpClient.executeMethod(getMethod);
			byte[] bytes = getMethod.getResponseBody();
			result = new String(bytes, "UTF-8");
			long end = System.currentTimeMillis();
			logger.debug("url:" + url + "---" + result + "---耗时："
					+ (end - start) + "ms");
		} catch (Exception e) {
			// e.printStackTrace();
			long end = System.currentTimeMillis();
			logger.error("url:" + url + "---" + result + "---耗时："
					+ (end - start) + "ms" + "，错误信息:" + e.getMessage());
			throw e;
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
			if (httpClient != null) {
				((SimpleHttpConnectionManager) httpClient
						.getHttpConnectionManager()).shutdown();
			}
		}

		return result;
	}


	public static List<String> getFeishuUserIdList(List<String> ekpIds)
			throws Exception {
		IThirdFeishuPersonMappingService thirdFeishuPersonMappingService = (IThirdFeishuPersonMappingService) SpringBeanUtil
				.getBean("thirdFeishuPersonMappingService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock(
				" thirdFeishuPersonMapping.fdEmployeeId ");
		hqlInfo.setWhereBlock(
				"thirdFeishuPersonMapping.fdEmployeeId is not null and " +
						HQLUtil.buildLogicIN(
								"thirdFeishuPersonMapping.fdEkp.fdId",
								ekpIds));
		List<String> list = thirdFeishuPersonMappingService.findList(hqlInfo);

		if (list == null) {
			return null;
		}
		return list;
	}

	public static Map<String, String> getFeishuUserIdMap(List<String> ekpIds)
			throws Exception {
		IThirdFeishuPersonMappingService thirdFeishuPersonMappingService = (IThirdFeishuPersonMappingService) SpringBeanUtil
				.getBean("thirdFeishuPersonMappingService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock(
				" thirdFeishuPersonMapping.fdEmployeeId, thirdFeishuPersonMapping.fdEkp.fdId");
		hqlInfo.setWhereBlock(
				"thirdFeishuPersonMapping.fdEmployeeId is not null and " +
						HQLUtil.buildLogicIN(
								"thirdFeishuPersonMapping.fdEkp.fdId",
								ekpIds));
		List list = thirdFeishuPersonMappingService.findList(hqlInfo);

		if (list == null) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			Object[] o = (Object[]) list.get(i);
			map.put((String) o[0], (String) o[1]);
		}
		return map;
	}

	public static String getViewUrl(SysNotifyTodo todo) throws Exception {
		String viewUrl = null;
		String domainName = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
		if (StringUtil.isNotNull(todo.getFdId())) {
			viewUrl = "/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId="
					+ todo.getFdId();
			if (StringUtils.isNotEmpty(domainName)) {
				viewUrl = domainName + viewUrl;
			} else {
				viewUrl = StringUtil.formatUrl(viewUrl);
			}
		} else {
			viewUrl = StringUtil.formatUrl(todo.getFdLink(), domainName);
		}

		viewUrl = viewUrl.substring(viewUrl.indexOf("://") + 3);
		viewUrl = viewUrl.substring(viewUrl.indexOf("/") + 1);
		String appId = ThirdFeishuConfig.newInstance().getFeishuAppid();

		// https://applink.feishu.cn/client/web_app/open?appId=xxx&mode=window&path=/aaa&id=100

		viewUrl = "https://applink.feishu.cn/client/web_app/open?appId=" + appId
				+ "&path="
				+ viewUrl.replaceFirst("\\?",
						"&");

		return viewUrl;
	}

	public static String getModelName(SysNotifyTodo todo) {
		String fdLang = todo.getFdLang();
		Locale locale = SysLangUtil.getLocaleByShortName(fdLang);
		if (locale == null) {
			locale = ResourceUtil.getLocaleByUser();
		}
		String modelName = todo.getFdModelName();
		if (StringUtil.isNull(modelName)) {
			return todo.getFdAppName();
		}
		SysDictModel sysDict = SysDataDict.getInstance().getModel(modelName);
		if (sysDict != null) {
			String modelName_display = ResourceUtil
					.getStringValue(sysDict.getMessageKey(), null, locale);
			if (StringUtil.isNotNull(modelName_display)) {
				modelName = modelName_display;
			}
		}
		return modelName;
	}

	public static String getValueByLang(String key, String bundle,
			String lang) {
		Locale locale = null;
		if (StringUtil.isNotNull(lang)
				&& lang.contains("-")) {
			locale = new Locale(lang.split("-")[0],
					lang.split("-")[1]);
		}
		return ResourceUtil.getStringValue(key, bundle, locale);
	}

	private static CircuitBreaker breaker = null;

	public static CircuitBreaker getCircuitBreaker() {
		if (breaker == null) {
			CircuitBreakerConfig config = new CircuitBreakerConfig();
			// config.openTimeout = 600000l;
			// config.failureCount2Open = 2;
			breaker = new CircuitBreaker("ThirdFeishuBreaker", config);
		}
		return breaker;
	}

	public static String execHttpPost(final String url, final String body,
			final String token)
			throws Exception {
		try {
			Object result = getCircuitBreaker().execute(new ProtectedAction() {
				@Override
				public Object execute() throws Exception {
					return doExecHttpPost(url, body, token);
				}

				@Override
				public boolean isBreakException(Exception e) {
					if (e instanceof IOException || e instanceof HttpException
							|| e instanceof ConnectException
							|| e instanceof SocketTimeoutException
							|| e instanceof UnknownHostException) {
						return true;
					}
					return false;
				}
			});
			return (String) result;
		} catch (OpenCircuitException e) {
			logger.error("熔断器处于打开状态", e);
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	public static String execHttpGet(final String url, final String token)
			throws Exception {
		try {
			Object result = getCircuitBreaker().execute(new ProtectedAction() {
				@Override
				public Object execute() throws Exception {
					return doExecHttpGet(url, token);
				}

				@Override
				public boolean isBreakException(Exception e) {
					if (e instanceof IOException || e instanceof HttpException
							|| e instanceof ConnectException
							|| e instanceof SocketTimeoutException
							|| e instanceof UnknownHostException) {
						return true;
					}
					return false;
				}
			});
			return (String) result;
		} catch (OpenCircuitException e) {
			logger.error("熔断器处于打开状态", e);
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	public static String execHttpPatch(final String url, final String body,
			final String token)
			throws Exception {
		try {
			Object result = getCircuitBreaker().execute(new ProtectedAction() {
				@Override
				public Object execute() throws Exception {
					return doExecHttpPatch(url, body, token);
				}

				@Override
				public boolean isBreakException(Exception e) {
					if (e instanceof IOException || e instanceof HttpException
							|| e instanceof ConnectException
							|| e instanceof SocketTimeoutException
							|| e instanceof UnknownHostException) {
						return true;
					}
					return false;
				}
			});
			return (String) result;
		} catch (OpenCircuitException e) {
			logger.error("熔断器处于打开状态", e);
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	public static DefaultHttpClient getNewHttpClient() {
		try {
			KeyStore trustStore = null;
			EasySSLSocketFactory sf = null;
			trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			sf = new EasySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(
					SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http",
					PlainSocketFactory.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));
			ClientConnectionManager ccm = new ThreadSafeClientConnManager(
					params, registry);
			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}

	public static String doExecHttpPatch(String url, String body, String token)
			throws Exception {

		String result = "";
		HttpPatch httpPatch = new HttpPatch(url);
		long start = System.currentTimeMillis();
		DefaultHttpClient httpClient = null;
		try {
			// Protocol myHTTPS = new Protocol("https",
			// new MySecureProtocolSocketFactory(), 443);
			// Protocol.registerProtocol("https", myHTTPS);

			httpClient = getNewHttpClient();
			httpClient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);// 连接时间
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					5000);// 数据传输时间
			httpPatch.setHeader("Content-type",
					"application/json; charset=utf-8");
			httpPatch.setHeader("Charset", HTTP.UTF_8);
			httpPatch.setHeader("Accept", "application/json");
			httpPatch.setHeader("Accept-Charset", HTTP.UTF_8);
			if (StringUtil.isNotNull(token)) {
				httpPatch.setHeader("Authorization", "Bearer " + token);
			}
			StringEntity entity = new StringEntity(body, HTTP.UTF_8);
			httpPatch.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPatch);
			result = EntityUtils.toString(response.getEntity());

			long end = System.currentTimeMillis();
			logger.debug("url:" + url + "---" + body + "---" + result + "---耗时："
					+ (end - start) + "ms");
		} catch (Exception e) {
			// e.printStackTrace();
			long end = System.currentTimeMillis();
			logger.error("url:" + url + "---" + body + "---" + result + "---耗时："
					+ (end - start) + "ms" + "，错误信息:" + e.getMessage());
			throw e;
		} finally {
			if (httpPatch != null) {
				httpPatch.releaseConnection();
			}
			if (httpClient != null) {
				httpClient.close();
			}
		}
		return result;
	}

	public static String doExecHttpDelete(String url, String token)
			throws Exception {

		String result = "";
		HttpDelete httpDelete = new HttpDelete(url);
		long start = System.currentTimeMillis();
		DefaultHttpClient httpClient = null;
		try {
			// Protocol myHTTPS = new Protocol("https",
			// new MySecureProtocolSocketFactory(), 443);
			// Protocol.registerProtocol("https", myHTTPS);

			httpClient = getNewHttpClient();
			httpClient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);// 连接时间
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					5000);// 数据传输时间
			httpDelete.setHeader("Content-type",
					"application/json; charset=utf-8");
			httpDelete.setHeader("Charset", HTTP.UTF_8);
			httpDelete.setHeader("Accept", "application/json");
			httpDelete.setHeader("Accept-Charset", HTTP.UTF_8);
			if (StringUtil.isNotNull(token)) {
				httpDelete.setHeader("Authorization", "Bearer " + token);
			}
			HttpResponse response = httpClient.execute(httpDelete);
			result = EntityUtils.toString(response.getEntity());

			long end = System.currentTimeMillis();
			logger.debug("url:" + url + "---" + result + "---耗时："
					+ (end - start) + "ms");
		} catch (Exception e) {
			// e.printStackTrace();
			long end = System.currentTimeMillis();
			logger.error("url:" + url + "---" + result + "---耗时："
					+ (end - start) + "ms" + "，错误信息:" + e.getMessage());
			throw e;
		} finally {
			if (httpDelete != null) {
				httpDelete.releaseConnection();
			}
			if (httpClient != null) {
				httpClient.close();
			}
		}
		return result;
	}

	public static String execHttpDelete(final String url,
			final String token)
			throws Exception {
		try {
			Object result = getCircuitBreaker().execute(new ProtectedAction() {
				@Override
				public Object execute() throws Exception {
					return doExecHttpDelete(url, token);
				}

				@Override
				public boolean isBreakException(Exception e) {
					if (e instanceof IOException || e instanceof HttpException
							|| e instanceof ConnectException
							|| e instanceof SocketTimeoutException
							|| e instanceof UnknownHostException) {
						return true;
					}
					return false;
				}
			});
			return (String) result;
		} catch (OpenCircuitException e) {
			logger.error("熔断器处于打开状态", e);
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

}
