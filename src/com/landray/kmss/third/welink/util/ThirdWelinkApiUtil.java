package com.landray.kmss.third.welink.util;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Locale;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.entity.ContentType;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.third.welink.service.IThirdWelinkPersonMappingService;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.breaker.CircuitBreaker;
import com.landray.kmss.util.breaker.CircuitBreakerConfig;
import com.landray.kmss.util.breaker.OpenCircuitException;
import com.landray.kmss.util.breaker.ProtectedAction;

public class ThirdWelinkApiUtil {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdWelinkApiUtil.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static String doExecHttpPost(String url, String body, String token)
			throws Exception {
		String result = "";
		PostMethod postMethod = null;
		RequestEntity entity;
		try {
			Protocol myHTTPS = new Protocol("https",
					new MySecureProtocolSocketFactory(), 443);
			Protocol.registerProtocol("https", myHTTPS);
			postMethod = new PostMethod(url);
			postMethod.addRequestHeader(new Header("Accept-Charset","UTF-8"));
			postMethod.addRequestHeader(new Header("Content-Type","application/json"));
			if (StringUtil.isNotNull(token)) {
				postMethod.addRequestHeader(
						new Header("x-wlk-Authorization", token));
			}
			entity = new StringRequestEntity(body,
					ContentType.APPLICATION_JSON.toString(), "UTF-8");
			postMethod.setRequestEntity(entity);
			HttpClient httpClient = new HttpClient();
			httpClient.setConnectionTimeout(5000);
			httpClient.setTimeout(5000);

			// httpClient.getHostConfiguration().setProxy("127.0.0.1", 8888);

			httpClient.executeMethod(postMethod);
			byte[] bytes = postMethod.getResponseBody();
			result = new String(bytes, "UTF-8");
			logger.debug("url:" + url + "---" + body + "---" + result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("url:" + url, e);
			throw e;
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}

		return result;
	}

	public static String doExecHttpGet(String url, String token)
			throws Exception {
		String result = "";
		GetMethod getMethod = null;
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
						new Header("x-wlk-Authorization", token));
			}
			HttpClient httpClient = new HttpClient();
			httpClient.setConnectionTimeout(5000);
			httpClient.setTimeout(5000);
			httpClient.executeMethod(getMethod);
			byte[] bytes = getMethod.getResponseBody();
			result = new String(bytes, "UTF-8");
			logger.debug("url:" + url + "---" + result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("url:" + url, e);
			throw e;
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
		}

		return result;
	}

	public static String doExecHttpPut(String url, String body, String token)
			throws Exception {
		String result = "";
		PutMethod putMethod = null;
		RequestEntity entity;
		try {
			Protocol myHTTPS = new Protocol("https",
					new MySecureProtocolSocketFactory(), 443);
			Protocol.registerProtocol("https", myHTTPS);
			putMethod = new PutMethod(url);
			putMethod.addRequestHeader(new Header("Accept-Charset", "UTF-8"));
			putMethod.addRequestHeader(
					new Header("Content-Type", "application/json"));
			if (StringUtil.isNotNull(token)) {
				putMethod.addRequestHeader(
						new Header("x-wlk-Authorization", token));
			}
			if (StringUtil.isNotNull(body)) {
				entity = new StringRequestEntity(body,
						ContentType.APPLICATION_JSON.toString(), "UTF-8");
				putMethod.setRequestEntity(entity);
			}
			HttpClient httpClient = new HttpClient();
			httpClient.setConnectionTimeout(5000);
			httpClient.setTimeout(5000);

			// httpClient.getHostConfiguration().setProxy("127.0.0.1", 8888);

			httpClient.executeMethod(putMethod);
			byte[] bytes = putMethod.getResponseBody();
			result = new String(bytes, "UTF-8");
			logger.debug("url:" + url + "---" + body + "---" + result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("url:" + url, e);
			throw e;
		} finally {
			if (putMethod != null) {
				putMethod.releaseConnection();
			}
		}

		return result;
	}

	public static String doExecHttpDel(String url, String token)
			throws Exception {
		String result = "";
		DeleteMethod delMethod = null;
		try {
			Protocol myHTTPS = new Protocol("https",
					new MySecureProtocolSocketFactory(), 443);
			Protocol.registerProtocol("https", myHTTPS);
			delMethod = new DeleteMethod(url);
			delMethod.addRequestHeader(new Header("Accept-Charset", "UTF-8"));
			delMethod.addRequestHeader(
					new Header("Content-Type", "application/json"));
			if (StringUtil.isNotNull(token)) {
				delMethod.addRequestHeader(
						new Header("x-wlk-Authorization", token));
			}

			HttpClient httpClient = new HttpClient();
			httpClient.setConnectionTimeout(5000);
			httpClient.setTimeout(5000);
			// httpClient.getHostConfiguration().setProxy("127.0.0.1", 8888);

			httpClient.executeMethod(delMethod);
			byte[] bytes = delMethod.getResponseBody();
			result = new String(bytes, "UTF-8");
			logger.debug("url:" + url + "---" + result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("url:" + url, e);
			throw e;
		} finally {
			if (delMethod != null) {
				delMethod.releaseConnection();
			}
		}

		return result;
	}

	public static List<String> getWelinkUserIdList(List<String> ekpIds)
			throws Exception {
		IThirdWelinkPersonMappingService thirdWelinkPersonMappingService = (IThirdWelinkPersonMappingService) SpringBeanUtil
				.getBean("thirdWelinkPersonMappingService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock(
				" thirdWelinkPersonMapping.fdWelinkUserId ");
		hqlInfo.setWhereBlock(
				"thirdWelinkPersonMapping.fdWelinkUserId is not null and " +
						HQLUtil.buildLogicIN(
								"thirdWelinkPersonMapping.fdEkpPerson.fdId",
								ekpIds));
		List<String> list = thirdWelinkPersonMappingService.findList(hqlInfo);

		if (list == null) {
			return null;
		}
		return list;
	}

	public static String getViewUrl(SysNotifyTodo todo) {
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
			breaker = new CircuitBreaker("ThirdWelinkBreaker", config);
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

	public static String execHttpPut(final String url, final String body,
			final String token)
			throws Exception {
		try {
			Object result = getCircuitBreaker().execute(new ProtectedAction() {
				@Override
				public Object execute() throws Exception {
					return doExecHttpPut(url, body, token);
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

	public static String execHttpDel(final String url, final String token)
			throws Exception {
		try {
			Object result = getCircuitBreaker().execute(new ProtectedAction() {
				@Override
				public Object execute() throws Exception {
					return doExecHttpDel(url, token);
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
