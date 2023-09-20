package com.landray.kmss.third.ding.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.constant.DingErrorCodeConstant;
import com.landray.kmss.third.ding.ratelimit.RatelimitProtectedAction;
import com.landray.kmss.util.breaker.CircuitBreaker;
import com.landray.kmss.util.breaker.OpenCircuitException;
import com.landray.kmss.util.breaker.ProtectedAction;
import com.landray.sso.client.oracle.StringUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 发送http请求，钉钉新接口，成功没有code状态参数
 * 
 * @author 陈火旺
 * 
 */
public class DingApiHttpClientUtil {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingApiHttpClientUtil.class);

	private static CircuitBreaker breaker = new CircuitBreaker("DingApiCircuitBreaker");

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
	public static <T> T httpGet(final String ratelimitKey, final String url,String token, final String resultJsonKey, final Class<T> clazz) {
		T result = null;
		try {
			logger.debug("钉钉接口  httpGet url:" + url);
			result = (T) breaker.execute(new ProtectedAction() {
				@Override
				public Object execute() throws Exception {
					// 该方法为受熔断器保护的方法
					// 声明为final的变量，在这里可以直接获取
					return innerHttpGet(ratelimitKey, url,token, resultJsonKey, clazz);
				}

				@Override
				public boolean isBreakException(Exception e) {
					// 当execute方法触发，并且有错误抛出时，该方法将被触发
					// 根据错误类型，return
					// true表示需要触发熔断器的熔断功能，一般自己程序的错误不需要触发熔断，远程服务无法连接的时候才触发熔断
					if (e instanceof HttpException || e instanceof SocketTimeoutException
							|| e instanceof ConnectException) {
						return true;
					}
					return false;
				}
			});
		} catch (OpenCircuitException e) {
			// 要么在这里做错误处理，要么不捕获该错误
			// 当熔断器生效时抛出该错误，action.execute方法不会被触发
			logger.error("与钉钉服务器通信暂停....");
			throw new RuntimeException(e);
		} catch (Exception e) {
			// 要么在这里做错误处理，要么不捕获该错误
			// action.execute方法抛出错误的时候，在这里可以捕获
			throw new RuntimeException(e);
		}
		// 接口调用频率限制
		//limitInvokeTimes(result);
		return result;
	}

	public static <T> T innerHttpGet(String ratelimitKey, String url,String token, String resultJsonKey, Class<T> clazz) throws Exception {
		try {
			return (T) ThirdDingTalkRateLimitUtil.executeRateLimit(ratelimitKey+"-get", new RatelimitProtectedAction(){
				@Override
				public Object execute() throws Exception {
					return doInnerHttpGet(url,token,resultJsonKey,clazz);
				}
			});
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public static <T> T doInnerHttpGet(String url,String token, String resultJsonKey, Class<T> clazz) {
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = createSSLClientDefault();

		Builder builder = RequestConfig.custom();
		HttpHost httpHost = getHttpHost();
		builder.setSocketTimeout(30000).setConnectTimeout(10000).setConnectionRequestTimeout(2000);
		if (httpHost != null) {
			builder.setProxy(httpHost);
		}
		RequestConfig requestConfig = builder.build();
		httpGet.setConfig(requestConfig);
		httpGet.addHeader("Content-Type", "application/json");
		httpGet.addHeader("x-acs-dingtalk-access-token",token);
		try {
			response = httpClient.execute(httpGet, new BasicHttpContext());
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				if (clazz.equals(ByteArrayOutputStream.class)) {
					// System.out.println("---------这个是ByteArrayOutputStream-----------");
					InputStream fis = entity.getContent();
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					byte[] buffer = new byte[1024 * 4];
					int n = 0;
					while (-1 != (n = fis.read(buffer))) {
						output.write(buffer, 0, n);
					}
					return (T) output;
				}
				String resultStr = EntityUtils.toString(entity, "utf-8");
				logger.debug("resultStr:"+resultStr);

				JSONObject result = JSON.parseObject(resultStr);
				if (result.containsKey("code")&&!"0".equals(result.getString("code"))) {
					logger.error("DING GET失败...");
					logger.error("request url=" + url + ",return value="+resultStr);
					logger.debug("request url=" + url + ",return value=");
					return JSON.parseObject(resultStr, clazz);
				} else {
					if (StringUtils.isEmpty(resultJsonKey)) {
						return JSON.parseObject(resultStr, clazz);
					}
					return result.getObject(resultJsonKey, clazz);
				}
			}
		} catch (Exception e) {
			logger.error("请求(" + url + ")发生异常：", e);
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
	public static <T> T httpPostAuth(final String url, final Object requestJsonObject, final String resultJsonKey,
			final Class<T> clazz, final String auth) throws UnsupportedEncodingException {
		T result = null;
		try {
			result = (T) breaker.execute(new ProtectedAction() {
				@Override
				public Object execute() throws Exception {
					// 该方法为受熔断器保护的方法
					// 声明为final的变量，在这里可以直接获取
					return innerHttpPostAuth(url, requestJsonObject, resultJsonKey, clazz, auth);
				}

				@Override
				public boolean isBreakException(Exception e) {
					// 当execute方法触发，并且有错误抛出时，该方法将被触发
					// 根据错误类型，return
					// true表示需要触发熔断器的熔断功能，一般自己程序的错误不需要触发熔断，远程服务无法连接的时候才触发熔断
					if (e instanceof HttpException || e instanceof SocketTimeoutException
							|| e instanceof ConnectException) {
						return true;
					}
					return false;
				}
			});
		} catch (OpenCircuitException e) {
			// 要么在这里做错误处理，要么不捕获该错误
			// 当熔断器生效时抛出该错误，action.execute方法不会被触发
			logger.error("与钉钉服务器通信暂停....",e);
			throw new RuntimeException(e);
		} catch (Exception e) {
			// 要么在这里做错误处理，要么不捕获该错误
			// action.execute方法抛出错误的时候，在这里可以捕获
			throw new RuntimeException(e);
		}
		// 接口调用频率限制
		//limitInvokeTimes(result);
		return result;
	}

	public static <T> T innerHttpPostAuth(String url, Object requestJsonObject, String resultJsonKey, Class<T> clazz,
			String auth) throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = createSSLClientDefault();

		Builder builder = RequestConfig.custom();
		HttpHost httpHost = getHttpHost();
		builder.setSocketTimeout(30000).setConnectTimeout(10000).setConnectionRequestTimeout(2000);
		if (httpHost != null) {
			builder.setProxy(httpHost);
		}
		RequestConfig requestConfig = builder.build();
		httpPost.setConfig(requestConfig);
		httpPost.addHeader("Content-Type", "application/json");
		logger.debug("url:" + url + ",postData=" + JSON.toJSONString(requestJsonObject));
		StringEntity requestEntity = new StringEntity(JSON.toJSONString(requestJsonObject), "utf-8");
		httpPost.setEntity(requestEntity);
		if (StringUtil.isNotNull(auth)) {
			httpPost.addHeader("Authorization", "Basic " + Base64.encodeBase64String(auth.getBytes()));
		}
		try {
			response = httpClient.execute(httpPost, new BasicHttpContext());
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");
				JSONObject result = JSON.parseObject(resultStr);
				// 接口调用频率限制
				//limitInvokeTimes(result);
				if (result.containsKey("code")&&!"0".equals(result.getString("code"))) {
					logger.error("DING POST失败...");
					logger.error("request url=" + url + ",return value="+resultStr);
					// 失败
					return result.getObject(resultJsonKey, clazz);
				} else {
					if (StringUtils.isEmpty(resultJsonKey)) {
						return JSON.parseObject(resultStr, clazz);
					}
					return JSON.parseObject(resultStr, clazz);
				}
			}
		} catch (Exception e) {
			logger.error("请求(" + url + ")发生异常：", e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}

			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T httpPost(final String ratelimitKey, final String url, String token,final Object requestJsonObject, final String resultJsonKey,
			final Class<T> clazz) throws UnsupportedEncodingException {
		T result = null;
		try {
			logger.debug("钉钉接口 httpPost url:" + url);
			result = (T) breaker.execute(new ProtectedAction() {
				@Override
				public Object execute() throws Exception {
					// 该方法为受熔断器保护的方法
					// 声明为final的变量，在这里可以直接获取
					return httpPost(ratelimitKey, url,token, requestJsonObject, resultJsonKey, clazz, 2000);
				}

				@Override
				public boolean isBreakException(Exception e) {
					// 当execute方法触发，并且有错误抛出时，该方法将被触发
					// 根据错误类型，return
					// true表示需要触发熔断器的熔断功能，一般自己程序的错误不需要触发熔断，远程服务无法连接的时候才触发熔断
					if (e instanceof HttpException || e instanceof SocketTimeoutException
							|| e instanceof ConnectException) {
						return true;
					}
					return false;
				}
			});
		} catch (OpenCircuitException e) {
			// 要么在这里做错误处理，要么不捕获该错误
			// 当熔断器生效时抛出该错误，action.execute方法不会被触发
			logger.error("与钉钉服务器通信暂停....");
			throw new RuntimeException(e);
		} catch (Exception e) {
			// 要么在这里做错误处理，要么不捕获该错误
			// action.execute方法抛出错误的时候，在这里可以捕获
			throw new RuntimeException(e);
		}
		// 接口调用频率限制
		//limitInvokeTimes(result);
		return result;
	}

	public static <T> T httpPut(final String ratelimitKey, final String url, String token,final Object requestJsonObject, final String resultJsonKey,
								 final Class<T> clazz) throws UnsupportedEncodingException {
		T result = null;
		try {
			logger.debug("钉钉接口 httpPost url:" + url);
			result = (T) breaker.execute(new ProtectedAction() {
				@Override
				public Object execute() throws Exception {
					// 该方法为受熔断器保护的方法
					// 声明为final的变量，在这里可以直接获取
					return httpPut(ratelimitKey, url,token, requestJsonObject, resultJsonKey, clazz, 2000);
				}

				@Override
				public boolean isBreakException(Exception e) {
					// 当execute方法触发，并且有错误抛出时，该方法将被触发
					// 根据错误类型，return
					// true表示需要触发熔断器的熔断功能，一般自己程序的错误不需要触发熔断，远程服务无法连接的时候才触发熔断
					if (e instanceof HttpException || e instanceof SocketTimeoutException
							|| e instanceof ConnectException) {
						return true;
					}
					return false;
				}
			});
		} catch (OpenCircuitException e) {
			// 要么在这里做错误处理，要么不捕获该错误
			// 当熔断器生效时抛出该错误，action.execute方法不会被触发
			logger.error("与钉钉服务器通信暂停....");
			throw new RuntimeException(e);
		} catch (Exception e) {
			// 要么在这里做错误处理，要么不捕获该错误
			// action.execute方法抛出错误的时候，在这里可以捕获
			throw new RuntimeException(e);
		}
		// 接口调用频率限制
		//limitInvokeTimes(result);
		return result;
	}

	public static <T> T httpPut(String ratelimitKey,String url,String token, Object requestJsonObject, String resultJsonKey, Class<T> clazz,
								int timeout) throws Exception {
		try {
			return (T) ThirdDingTalkRateLimitUtil.executeRateLimit(ratelimitKey+"-put", new RatelimitProtectedAction(){
				@Override
				public Object execute() throws Exception {
					return doHttpPut(url,token,requestJsonObject,resultJsonKey,clazz,timeout);
				}
			});
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public static <T> T doHttpPut(String url,String token, Object requestJsonObject, String resultJsonKey, Class<T> clazz,
								 int timeout) throws UnsupportedEncodingException {
		HttpPut httpPut = new HttpPut(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = createSSLClientDefault();

		Builder builder = RequestConfig.custom();
		HttpHost httpHost = getHttpHost();
		builder.setSocketTimeout(30000).setConnectTimeout(10000).setConnectionRequestTimeout(2000);
		if (httpHost != null) {
			builder.setProxy(httpHost);
		}
		RequestConfig requestConfig = builder.build();
        httpPut.setConfig(requestConfig);
		httpPut.addHeader("Content-Type", "application/json");
		httpPut.addHeader("x-acs-dingtalk-access-token",token);
		logger.debug("url:" + url + ",postData=" + JSON.toJSONString(requestJsonObject));
		StringEntity requestEntity = new StringEntity(JSON.toJSONString(requestJsonObject), "utf-8");
		httpPut.setEntity(requestEntity);
		try {
			response = httpClient.execute(httpPut, new BasicHttpContext());
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");

				JSONObject result = JSON.parseObject(resultStr);
				// 接口调用频率限制
				//limitInvokeTimes(result);

				if (result.containsKey("code")&&!"0".equals(result.getString("code"))) {
					logger.error("DING POST失败...");
					logger.error("request url=" + url + ",return value="+resultStr);
					// 失败
					return JSON.parseObject(resultStr, clazz);
				} else {
					//成功
					if (StringUtils.isEmpty(resultJsonKey)) {
						return JSON.parseObject(resultStr, clazz);
					}
					return JSON.parseObject(resultStr, clazz);
				}
			}
		} catch (Exception e) {
			logger.error("请求(" + url + ")发生异常：", e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("", e);
					e.printStackTrace();
				}
			}

			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					logger.error("", e);
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static <T> T httpDelete(final String ratelimitKey, final String url, String token, final String resultJsonKey,
								final Class<T> clazz) throws UnsupportedEncodingException {
		T result = null;
		try {
			logger.debug("钉钉接口 httpPost url:" + url);
			result = (T) breaker.execute(new ProtectedAction() {
				@Override
				public Object execute() throws Exception {
					// 该方法为受熔断器保护的方法
					// 声明为final的变量，在这里可以直接获取
					return httpDelete(ratelimitKey, url,token, resultJsonKey, clazz, 2000);
				}

				@Override
				public boolean isBreakException(Exception e) {
					// 当execute方法触发，并且有错误抛出时，该方法将被触发
					// 根据错误类型，return
					// true表示需要触发熔断器的熔断功能，一般自己程序的错误不需要触发熔断，远程服务无法连接的时候才触发熔断
					if (e instanceof HttpException || e instanceof SocketTimeoutException
							|| e instanceof ConnectException) {
						return true;
					}
					return false;
				}
			});
		} catch (OpenCircuitException e) {
			// 要么在这里做错误处理，要么不捕获该错误
			// 当熔断器生效时抛出该错误，action.execute方法不会被触发
			logger.error("与钉钉服务器通信暂停....");
			throw new RuntimeException(e);
		} catch (Exception e) {
			// 要么在这里做错误处理，要么不捕获该错误
			// action.execute方法抛出错误的时候，在这里可以捕获
			throw new RuntimeException(e);
		}
		// 接口调用频率限制
		//limitInvokeTimes(result);
		return result;
	}

	public static <T> T httpDelete(final String ratelimitKey,String url,String token, String resultJsonKey, Class<T> clazz,
								   int timeout) throws Exception {
		try {
			return (T) ThirdDingTalkRateLimitUtil.executeRateLimit(ratelimitKey+"-delete", new RatelimitProtectedAction(){
				@Override
				public Object execute() throws Exception {
					return doHttpDelete(url,token,resultJsonKey,clazz,timeout);
				}
			});
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public static <T> T doHttpDelete(String url,String token, String resultJsonKey, Class<T> clazz,
								 int timeout) throws UnsupportedEncodingException {

		HttpDelete httpDelete = new HttpDelete(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = createSSLClientDefault();

		Builder builder = RequestConfig.custom();
		HttpHost httpHost = getHttpHost();
		builder.setSocketTimeout(30000).setConnectTimeout(10000).setConnectionRequestTimeout(2000);
		if (httpHost != null) {
			builder.setProxy(httpHost);
		}
		RequestConfig requestConfig = builder.build();
		httpDelete.setConfig(requestConfig);
		httpDelete.addHeader("Content-Type", "application/json");
		httpDelete.addHeader("x-acs-dingtalk-access-token",token);
		try {
			response = httpClient.execute(httpDelete, new BasicHttpContext());
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");

				JSONObject result = JSON.parseObject(resultStr);
				// 接口调用频率限制
				//limitInvokeTimes(result);

				if (result.containsKey("code")&&!"0".equals(result.getString("code"))) {
					logger.error("DING POST失败...");
					logger.error("request url=" + url + ",return value="+resultStr);
					// 失败
					return JSON.parseObject(resultStr, clazz);
				} else {
					//成功
					if (StringUtils.isEmpty(resultJsonKey)) {
						return JSON.parseObject(resultStr, clazz);
					}
					return JSON.parseObject(resultStr, clazz);
				}
			}
		} catch (Exception e) {
			logger.error("请求(" + url + ")发生异常：", e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("", e);
					e.printStackTrace();
				}
			}

			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					logger.error("", e);
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static <T> T httpPost(String ratelimitKey, String url,String token, Object requestJsonObject, String resultJsonKey, Class<T> clazz,
						 int timeout) throws Exception {
		try {
			return (T) ThirdDingTalkRateLimitUtil.executeRateLimit(ratelimitKey+"-post", new RatelimitProtectedAction(){
				@Override
				public Object execute() throws Exception {
					return doHttpPost(url,token,requestJsonObject,resultJsonKey,clazz,timeout);
				}
			});
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public static <T> T doHttpPost(String url,String token, Object requestJsonObject, String resultJsonKey, Class<T> clazz,
			int timeout) throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = createSSLClientDefault();

		Builder builder = RequestConfig.custom();
		HttpHost httpHost = getHttpHost();
		builder.setSocketTimeout(30000).setConnectTimeout(10000).setConnectionRequestTimeout(2000);
		if (httpHost != null) {
			builder.setProxy(httpHost);
		}
		RequestConfig requestConfig = builder.build();
		httpPost.setConfig(requestConfig);
		httpPost.addHeader("Content-Type", "application/json");
		httpPost.addHeader("x-acs-dingtalk-access-token",token);
		logger.debug("url:" + url + ",postData=" + JSON.toJSONString(requestJsonObject));
		StringEntity requestEntity = new StringEntity(JSON.toJSONString(requestJsonObject), "utf-8");
		httpPost.setEntity(requestEntity);
		try {
			response = httpClient.execute(httpPost, new BasicHttpContext());
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");

				JSONObject result = JSON.parseObject(resultStr);
				// 接口调用频率限制
				//limitInvokeTimes(result);

				if (result.containsKey("code")&&!"0".equals(result.getString("code"))) {
					logger.error("DING POST失败...");
					logger.error("request url=" + url + ",return value="+resultStr);
					// 失败
					return JSON.parseObject(resultStr, clazz);
				} else {
					//成功
					if (StringUtils.isEmpty(resultJsonKey)) {
						return JSON.parseObject(resultStr, clazz);
					}
					return result.getObject(resultJsonKey, clazz);
				}
			}
		} catch (Exception e) {
			logger.error("请求(" + url + ")发生异常：", e);
			throw new RuntimeException(e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("", e);
					e.printStackTrace();
				}
			}

			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					logger.error("", e);
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private static HttpHost getHttpHost() {
		String proxy = DingConstant.DING_PORXY;
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
		CloseableHttpClient httpClient = createSSLClientDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
		httpPost.setConfig(requestConfig);

		HttpEntity requestEntity = MultipartEntityBuilder.create()
				.addPart("media", new FileBody(file, ContentType.APPLICATION_OCTET_STREAM, file.getName())).build();
		httpPost.setEntity(requestEntity);

		try {
			response = httpClient.execute(httpPost, new BasicHttpContext());

			if (response.getStatusLine().getStatusCode() != 200) {

				logger.error(
						"request url failed, http code=" + response.getStatusLine().getStatusCode() + ", url=" + url);
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");

				JSONObject result = JSON.parseObject(resultStr);
				if (result.getInteger("errcode") == 0) {
					// 成功
					return result.toString();
				} else {
					logger.error("request url=" + url + ",return value=");
					logger.error(resultStr);
					return result.toString();
				}
			}
		} catch (IOException e) {
			logger.error("request url=" + url + ", exception, msg=" + e.getMessage());
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
	 * 接口调用频率限制
	 * 
	 * https://ding-doc.dingtalk.com/doc#/faquestions/rftpfg
	 * 
	 * @param result
	 *            钉钉接口返回结果
	 * @throws Exception
	 */
	private static <T> void limitInvokeTimes(T result) {
		// 接口无返回数据，直接跳过
		if (null == result) {
			return;
		}
		try {
			int errorCode = 0;
			if (result instanceof net.sf.json.JSONObject) {
				errorCode = ((net.sf.json.JSONObject) result).getInt("errcode");
			} else if (result instanceof JSONObject) {
				errorCode = ((JSONObject) result).getIntValue("errcode");
			}
			if (DingErrorCodeConstant.API_INVOKE_TIME_SECOND == errorCode) {
				// 休眠1秒
				Thread.sleep(1000);
			} else if (DingErrorCodeConstant.API_INVOKE_TIME_MINUTE == errorCode
					|| DingErrorCodeConstant.API_INVOKE_SINGLE_TIME_MINUTE == errorCode
					|| DingErrorCodeConstant.MSG_SEND_OA_TIME_MINUTE == errorCode
					|| DingErrorCodeConstant.MSG_SEND_TOTAL_TIME_MINUTE == errorCode) {
				// 休眠1分钟
				Thread.sleep(60 * 1000);
			}
		} catch (InterruptedException e) {
			logger.error("接口休眠异常", e);
		}
	}

	public static CloseableHttpClient createSSLClientDefault() {
		CloseableHttpClient client = null;
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				@Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			client = HttpClients.custom().setSslcontext(sslContext).setHostnameVerifier(new AllowAllHostnameVerifier())
					.build();
		} catch (Exception e) {
			logger.error("创建忽略证书的Httpclient失败，则默认创建正常的Httpclient", e);
		}
		if (client == null) {
            client = HttpClients.createDefault();
        }
		return client;
	}


}
