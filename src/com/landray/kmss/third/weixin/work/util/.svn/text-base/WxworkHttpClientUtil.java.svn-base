package com.landray.kmss.third.weixin.work.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.work.constant.WxworkConstant;
import com.landray.kmss.util.breaker.CircuitBreaker;
import com.landray.kmss.util.breaker.OpenCircuitException;
import com.landray.kmss.util.breaker.ProtectedAction;
import com.landray.sso.client.oracle.StringUtil;

/**
 * 发送http请求
 * 
 * @author 唐有炜
 *
 */
public class WxworkHttpClientUtil {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WxworkHttpClientUtil.class);

	private static CircuitBreaker breaker = new CircuitBreaker(
			"WxworkCircuitBreaker");

	/**
	 * 发送Get请求
	 * 
	 * @param url
	 *            请求地址
	 * @param resultJsonKey
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T httpGet(final String url, final String resultJsonKey,
			final Class<T> clazz) {
		return httpGet(url, resultJsonKey, clazz, 10000);
	}

	/**
	 * 发送Get请求
	 * 
	 * @param url
	 *            请求地址
	 * @param resultJsonKey
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T httpGet(final String url, final String resultJsonKey,
			final Class<T> clazz, final int socketTimeout) {
		T result = null;
		try {
			result = (T) breaker.execute(new ProtectedAction() {
				@Override
				public Object execute() throws Exception {
					// 该方法为受熔断器保护的方法
					// 声明为final的变量，在这里可以直接获取
					return innerHttpGet(url, resultJsonKey, clazz,
							socketTimeout);
				}

				@Override
				public boolean isBreakException(Exception e) {
					// 当execute方法触发，并且有错误抛出时，该方法将被触发
					// 根据错误类型，return
					// true表示需要触发熔断器的熔断功能，一般自己程序的错误不需要触发熔断，远程服务无法连接的时候才触发熔断
					if (e instanceof HttpException
							|| e instanceof SocketTimeoutException
							|| e instanceof ConnectException) {
						return true;
					}
					return false;
				}
			});
		} catch (OpenCircuitException e) {
			// 要么在这里做错误处理，要么不捕获该错误
			// 当熔断器生效时抛出该错误，action.execute方法不会被触发
			logger.error("与微信服务器通信暂停....");
			throw new RuntimeException(e);
		} catch (Exception e) {
			// 要么在这里做错误处理，要么不捕获该错误
			// action.execute方法抛出错误的时候，在这里可以捕获
			throw new RuntimeException(e);
		}
		return result;
	}

	public static <T> T innerHttpGet(String url, String resultJsonKey,
			Class<T> clazz, int socketTimeout) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;

		Builder builder = RequestConfig.custom();
		HttpHost httpHost = getHttpHost();
		builder.setSocketTimeout(socketTimeout).setConnectTimeout(6000)
				.setConnectionRequestTimeout(2000);
		if (httpHost != null) {
			builder.setProxy(httpHost);
		}
		RequestConfig requestConfig = builder.build();

		// 创建HttpClient
		CloseableHttpClient httpClient = createSSLHttpClient();

		httpGet.setConfig(requestConfig);
		try {
			logger.debug(
					"url:" + url + "   resultJsonKey:" + resultJsonKey);
			response = httpClient.execute(httpGet, new BasicHttpContext());

			if (response.getStatusLine().getStatusCode() != 200) {
				logger.debug("request url failed, http code="
						+ response.getStatusLine().getStatusCode() + ", url="
						+ url);
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");

				JSONObject result = JSON.parseObject(resultStr);
				logger.debug("result=" + result.toString());

				if (result.getInteger("errcode") == 0) {
					// 成功
					if (StringUtils.isEmpty(resultJsonKey)) {
						return JSON.parseObject(resultStr, clazz);
					}
					return result.getObject(resultJsonKey, clazz);
				} else {
					logger.warn("Wx GET失败...");
					logger.warn("request url=" + url + ",return value=");
					logger.warn(resultStr);
					return JSON.parseObject(resultStr, clazz);
				}
			}
		} catch (Exception e) {
			logger.debug("request url=" + url + ", exception, msg="
					+ e.getMessage());
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

	@SuppressWarnings("unchecked")
	public static <T> T httpPost(final String url,
			final Object requestJsonObject, final String resultJsonKey,
			final Class<T> clazz)
			throws UnsupportedEncodingException {
		T result = null;
		try {
			result = (T) breaker.execute(new ProtectedAction() {
				@Override
				public Object execute() throws Exception {
					// 该方法为受熔断器保护的方法
					// 声明为final的变量，在这里可以直接获取
					return httpPost(url, requestJsonObject, resultJsonKey,
							clazz, 2000);
				}

				@Override
				public boolean isBreakException(Exception e) {
					// 当execute方法触发，并且有错误抛出时，该方法将被触发
					// 根据错误类型，return
					// true表示需要触发熔断器的熔断功能，一般自己程序的错误不需要触发熔断，远程服务无法连接的时候才触发熔断
					if (e instanceof HttpException
							|| e instanceof SocketTimeoutException
							|| e instanceof ConnectException) {
						return true;
					}
					return false;
				}
			});
		} catch (OpenCircuitException e) {
			// 要么在这里做错误处理，要么不捕获该错误
			// 当熔断器生效时抛出该错误，action.execute方法不会被触发
			logger.error("与微信服务器通信暂停....");
			throw new RuntimeException(e);
		} catch (Exception e) {
			// 要么在这里做错误处理，要么不捕获该错误
			// action.execute方法抛出错误的时候，在这里可以捕获
			throw new RuntimeException(e);
		}
		return result;
	}

	public static <T> T httpPost(String url, Object requestJsonObject,
			String resultJsonKey, Class<T> clazz, int timeout)
			throws Exception {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		// 创建httpClient
		CloseableHttpClient httpClient = createSSLHttpClient();

		Builder builder = RequestConfig.custom();
		HttpHost httpHost = getHttpHost();
		builder.setSocketTimeout(10000).setConnectTimeout(5000);
		if (httpHost != null) {
			builder.setProxy(httpHost);
		}
		RequestConfig requestConfig = builder.build();

		httpPost.setConfig(requestConfig);
		httpPost.addHeader("Content-Type", "application/json");

		logger.debug("url:" + url);
		logger.debug("postData=" + JSON.toJSONString(requestJsonObject));
		StringEntity requestEntity = new StringEntity(
				JSON.toJSONString(requestJsonObject), "utf-8");
		httpPost.setEntity(requestEntity);

		try {
			response = httpClient.execute(httpPost, new BasicHttpContext());

			if (response.getStatusLine().getStatusCode() != 200) {
				logger.error("request url failed, http code="
						+ response.getStatusLine().getStatusCode() + ", url="
						+ url);
				return null;
			}

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");
				logger.debug("返回结果："+resultStr);
				JSONObject result = JSON.parseObject(resultStr);

				if (result.getInteger("errcode") == 0) {
					// 成功
					if (StringUtils.isEmpty(resultJsonKey)) {
						return JSON.parseObject(resultStr, clazz);
					}
					return result.getObject(resultJsonKey, clazz);
				} else {
					logger.warn("Wx POST失败...");
					logger.warn("request url=" + url + ",return value=");
					logger.warn(resultStr);
					return JSON.parseObject(resultStr, clazz);
				}
			}
		} catch (Exception e) {
			logger.debug("request url=" + url + ", exception, msg="
					+ e.getMessage());
			logger.error("", e);
			throw e;
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
		String proxy = WxworkConstant.WXWORK_PROXY;
		if (StringUtil.isNotNull(proxy)) {
			String host = proxy.split(":")[0];
			int port = Integer.parseInt(proxy.split(":")[1]);
			return new HttpHost(host, port);
		} else {
			return null;
		}
	}

	public static String uploadMedia(String url, File file) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		// 创建httpClient
		CloseableHttpClient httpClient = createSSLHttpClient();
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(30000).setConnectTimeout(10000).build();
		httpPost.setConfig(requestConfig);

		HttpEntity requestEntity = MultipartEntityBuilder.create()
				.addPart("media",
						new FileBody(file, ContentType.APPLICATION_OCTET_STREAM,
								file.getName()))
				.build();
		httpPost.setEntity(requestEntity);

		try {
			response = httpClient.execute(httpPost, new BasicHttpContext());

			if (response.getStatusLine().getStatusCode() != 200) {

				logger.debug("request url failed, http code="
						+ response.getStatusLine().getStatusCode()
						+ ", url=" + url);
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");

				JSONObject result = JSON.parseObject(resultStr);
				if (result.getInteger("errcode") == 0) {
					// 成功
					// result.remove("errcode");
					// result.remove("errmsg");
					return result.toString();
				} else {
					logger.debug("request url=" + url + ",return value=");
					logger.debug(resultStr);
					// int errCode = result.getInteger("errcode");
					// String errMsg = result.getString("errmsg");
					return result.toString();
				}
			}
		} catch (IOException e) {
			logger.debug("request url=" + url + ", exception, msg="
					+ e.getMessage());
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

	/**
	 * 创建支持SSL协议的HttpClient
	 * 
	 * @return
	 */
	private static CloseableHttpClient createSSLHttpClient() {
		CloseableHttpClient client = null;

		// 设置ssl兼容协议版本
		SSLConnectionSocketFactory sslsf = null;
		try {
			SSLContext sslContext = new SSLContextBuilder()
					.loadTrustMaterial(null, new TrustStrategy() {
						@Override
                        public boolean isTrusted(X509Certificate[] chain,
                                                 String authType) throws CertificateException {
							return true;
						}
					}).build();
			sslsf = new SSLConnectionSocketFactory(
					sslContext,
					new String[] { "TLSv1.2", "TLSv1.1", "TLSv1" },
					null,
					SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			SSLSocketFactory factory = sslContext.getSocketFactory();
			SSLSocket socket = (SSLSocket) factory.createSocket();
			String[] protocols = socket.getSupportedProtocols();

			SocketConfig socketConfig = SocketConfig.custom()
					.setSoKeepAlive(false)
					.setSoLinger(1)
					.setSoReuseAddress(true)
					.setSoTimeout(15000)
					.setTcpNoDelay(true).build();

			client = HttpClients.custom()
					.setSSLSocketFactory(sslsf)
					.setDefaultSocketConfig(socketConfig)
					.build();

		} catch (Exception e) {
			logger.error("创建SSLConnectionSocketFactory失败", e);
			e.printStackTrace();
		}

		// 创建httpclient
		if (client == null) {
			logger.error("创建支持SSL的HttpClient失败，创建普通的HttpClient");
			client = HttpClients.createDefault();
		}
		return client;
	}

	private static HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
		@Override
		public boolean retryRequest(IOException exception,
				int executionCount, HttpContext context) {
			return false;
		}
	};
}
