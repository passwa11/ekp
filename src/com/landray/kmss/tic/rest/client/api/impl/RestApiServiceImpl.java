package com.landray.kmss.tic.rest.client.api.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.parser.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.util.ClassUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.tic.rest.client.api.ICookieProvider;
import com.landray.kmss.tic.rest.client.api.IOAuthAccessToken;
import com.landray.kmss.tic.rest.client.api.IPrefixReqProvider;
import com.landray.kmss.tic.rest.client.config.RestAccessToken;
import com.landray.kmss.tic.rest.client.config.RestConfigStorage;
import com.landray.kmss.tic.rest.client.error.RestError;
import com.landray.kmss.tic.rest.client.error.RestErrorException;
import com.landray.kmss.tic.rest.client.error.RestErrorKeys;
import com.landray.kmss.tic.rest.client.http.apache.ApacheHttpClientBuilder;
import com.landray.kmss.tic.rest.client.http.apache.DefaultApacheHttpClientBuilder;
import com.landray.kmss.tic.rest.connector.model.TicRestCookieSetting;
import com.landray.kmss.tic.rest.connector.model.TicRestMain;
import com.landray.kmss.tic.rest.connector.model.TicRestPrefixReqSetting;
import com.landray.sso.client.oracle.StringUtil;


public class RestApiServiceImpl extends RestApiServiceAbstractImpl<CloseableHttpClient, HttpHost, RestErrorKeys> {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(RestApiServiceImpl.class);

	protected CloseableHttpClient httpClient;
	protected HttpHost httpProxy;
	protected RestErrorKeys restErrorKeys;
	
	//前置请求返回的参数
	protected String returnBOdy;

	@Override
	public CloseableHttpClient getRequestHttpClient() {
		return httpClient;
	}

	@Override
	public CloseableHttpClient getNewRequestHttpClient(
			DefaultApacheHttpClientBuilder defaultHttp) {
		if (null == defaultHttp) {
			defaultHttp = DefaultApacheHttpClientBuilder
					.getNewInstance();
		}
			if (this.restConfigStorage.getConnectionRequestTimeout() != 0) {
				defaultHttp.setConnectionRequestTimeout(
						this.restConfigStorage.getConnectionRequestTimeout());
			}
			if (this.restConfigStorage.getConnectionTimeout() != 0) {
				defaultHttp.setConnectionTimeout(
						this.restConfigStorage.getConnectionTimeout());
			}
			if (this.restConfigStorage.getSoTimeout() != 0) {
				defaultHttp.setSoTimeout(this.restConfigStorage.getSoTimeout());
			}
			if (this.restConfigStorage.getIdleConnTimeout() != 0) {
				defaultHttp.setIdleConnTimeout(
						this.restConfigStorage.getIdleConnTimeout());
			}
			if (this.restConfigStorage.getCheckWaitTime() != 0) {
				defaultHttp.setCheckWaitTime(
						this.restConfigStorage.getCheckWaitTime());
			}
			if (this.restConfigStorage.getMaxConnPerHost() != 0) {
				defaultHttp.setMaxConnPerHost(
						this.restConfigStorage.getMaxConnPerHost());
			}
			if (this.restConfigStorage.getMaxTotalConn() != 0) {
				defaultHttp.setMaxTotalConn(
						this.restConfigStorage.getMaxTotalConn());
			}



		if (StringUtil.isNotNull(this.restConfigStorage.getHttpProxyHost())
				&& this.restConfigStorage.getHttpProxyPort() > 0) {
			this.httpProxy = new HttpHost(
					this.restConfigStorage.getHttpProxyHost(),
					this.restConfigStorage.getHttpProxyPort());
			defaultHttp
					.httpProxyHost(this.restConfigStorage.getHttpProxyHost())
					.httpProxyPort(this.restConfigStorage.getHttpProxyPort())
					.httpProxyUsername(
							this.restConfigStorage.getHttpProxyUsername())
					.httpProxyPassword(
							this.restConfigStorage.getHttpProxyPassword());
		}

		return defaultHttp.build();
	}

	@Override
	public HttpHost getRequestHttpProxy() {
		return httpProxy;
	}

	@Override
	public RestErrorKeys getErrorKeys() {
		return restErrorKeys;
	}

	private String getAccessToken4Clazz() {
		try {
			IOAuthAccessToken oAuthAccessToken = (IOAuthAccessToken) com.landray.kmss.util.ClassUtils.forName(restConfigStorage.getAccessTokenClazz()).newInstance();
			String resultContent = oAuthAccessToken.getAccessToken();
			if (StringUtil.isNotNull(resultContent)) {
				RestAccessToken accessToken = RestAccessToken.fromJson(resultContent);
				this.restConfigStorage.updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
				return accessToken.getAccessToken();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}
	
	private String getCookies4Clazz(String cookieSettingClazz,
			JSONObject prefixReqResult, String url) {
		try {
			ICookieProvider provider = (ICookieProvider) com.landray.kmss.util.ClassUtils.forName(cookieSettingClazz)
					.newInstance();
			String resultContent = provider
					.getCookies(restConfigStorage.getTicRestMain() == null
							? null
							: restConfigStorage.getTicRestMain().getFdId(),
							prefixReqResult, url);
			if (StringUtil.isNotNull(resultContent)) {
				return resultContent;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}
	
	@Override
	public String handlePrefixReq() throws Exception {
		TicRestMain restMain=restConfigStorage.getTicRestMain();
		//先判断有前置请求
		if(restMain==null || !restMain.getFdPrefixReqEnable()){
			return "";
		}
		TicRestPrefixReqSetting prefixReq = restMain.getTicRestPrefixReqSetting();
		if(prefixReq==null){
			return "";
		}
		if (prefixReq.getFdUseCustCt()) {
			return getPrefixReq4Clazz(prefixReq.getFdPrefixReqSettingClazz(),restMain.getFdId());
		} else {
			String config = prefixReq.getFdPrefixReqSettingParam();
			JSONObject o = JSONObject.parseObject(config);
			String method = o.getString("method");
			String url = o.getString("url");
			String headers = o.getJSONArray("header").toString();
			if ("get".equals(method)) {
				return getResultByGet(url, headers).toJSONString();
			}
			String body = "";
			if ("post".equals(method)) {
				if (o.containsKey("body")) {
					String bd = o.getString("body");
					if(StringUtil.isNotNull(bd)){
						body = new String(Base64.decode(bd.getBytes("UTF-8")),
								"UTF-8");	
					}
				}
				return getResultByPost(url, body, headers).toJSONString();
			}
		}
		return "";
	}

	
	private String getPrefixReq4Clazz(String prefixReqSettingClazz,String param) {
		try {
			IPrefixReqProvider provider = (IPrefixReqProvider) com.landray.kmss.util.ClassUtils.forName(prefixReqSettingClazz)
					.newInstance();
			String resultContent = provider.getPrefixReq(param);
			if (StringUtil.isNotNull(resultContent)) {
				return resultContent;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}
	
	private String getBody4Clazz(String prefixReqSettingClazz) {
		try {
			TicRestMain ticRestMain=restConfigStorage.getTicRestMain();
			if(ticRestMain==null){
			   return "";
			}
			IPrefixReqProvider provider = (IPrefixReqProvider) com.landray.kmss.util.ClassUtils.forName(prefixReqSettingClazz)
					.newInstance();
			String resultContent = provider
					.getPrefixReq(ticRestMain.getFdId());
			if (StringUtil.isNotNull(resultContent)) {
				return resultContent;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}


	private String getAccessTokenByGet(String url,String headers) throws RestErrorException {
		try {
			HttpGet httpGet = new HttpGet(url);
			if (this.httpProxy != null) {
				RequestConfig config = RequestConfig.custom().setProxy(this.httpProxy).build();
				httpGet.setConfig(config);
			}
			
			if(StringUtil.isNotNull(headers)){
				JSONArray headerArr = JSONArray.parseArray(headers);
				for(int i=0;i<headerArr.size();i++) {
					String header = headerArr.getString(i);
					if(StringUtil.isNotNull(header)) {
						String[] kn = header.split(":");
						httpGet.addHeader(kn[0], kn[1]);
					}
				}
			}
			
			String resultContent = null;
			try (CloseableHttpClient httpclient = getNewRequestHttpClient(null);
					CloseableHttpResponse response = httpclient.execute(httpGet)) {
				resultContent = new BasicResponseHandler().handleResponse(response);
			} finally {
				httpGet.releaseConnection();
			}
			RestError error = RestError.fromJson(resultContent, restErrorKeys);
			if (error.hasError()) {
				throw new RestErrorException(error);
			}
			RestAccessToken accessToken = RestAccessToken.fromJson(resultContent);
			this.restConfigStorage.updateAccessToken(accessToken.getAccessToken(),
					accessToken.getExpiresIn());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return this.restConfigStorage.getAccessToken();
	}
	
	private String getAccessTokenByPost(String url,String body,String headers) throws RestErrorException {
		try {
			HttpPost httpPost = new HttpPost(url);
			if (this.httpProxy != null) {
				RequestConfig config = RequestConfig.custom().setProxy(this.httpProxy).build();
				httpPost.setConfig(config);
			}
			
			if(StringUtil.isNotNull(headers)){
				JSONArray headerArr = JSONArray.parseArray(headers);
				for(int i=0;i<headerArr.size();i++) {
					Object obj = headerArr.get(i);
					if (obj instanceof JSONObject) {
						JSONObject jsonObj = (JSONObject) obj;
						String name = jsonObj.getString("name");
						String value = jsonObj.getString("value");
						httpPost.addHeader(name, value);
					} else {
					String header = headerArr.getString(i);
						if (StringUtil.isNotNull(header)) {
							String[] kn = header.split(":");
							httpPost.addHeader(kn[0], kn[1]);
						}
					}
				}
			}
			
			if (StringUtil.isNotNull(body)) {
				StringEntity entity = new StringEntity(body, Consts.UTF_8);
				httpPost.setEntity(entity);
			}
			
			String resultContent = null;
			try (CloseableHttpClient httpclient = getNewRequestHttpClient(null);
					CloseableHttpResponse response = httpclient.execute(httpPost)) {
				resultContent = new BasicResponseHandler().handleResponse(response);
			} finally {
				httpPost.releaseConnection();
			}
			RestError error = RestError.fromJson(resultContent, restErrorKeys);
			if (error.hasError()) {
				throw new RestErrorException(error);
			}
			RestAccessToken accessToken = RestAccessToken.fromJson(resultContent);
			this.restConfigStorage.updateAccessToken(accessToken.getAccessToken(),
					accessToken.getExpiresIn());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return this.restConfigStorage.getAccessToken();
	}
	
	@Override
	public String getAccessToken(boolean forceRefresh) throws RestErrorException {
		if (this.restConfigStorage.isAccessTokenExpired() || forceRefresh) {
			synchronized (this.globalAccessTokenRefreshLock) {
				if (this.restConfigStorage.isAccessTokenExpired()) {

					if (StringUtil.isNotNull(restConfigStorage.getAccessTokenClazz())) {
						return getAccessToken4Clazz();
					} else {
						String url = this.restConfigStorage.getAccessTokenURL();
						if(url.indexOf("{")==0) {
							JSONObject o = JSONObject.parseObject(url);
							String method = o.getString("method");
							url = o.getString("url");
							String headers = o.getJSONArray("header")
									.toString();
							if("get".equals(method)) {
								return getAccessTokenByGet(url,null);
							}
							String body = "";
							if("post".equals(method)) {
								if(o.containsKey("body")) {
									JSONObject bd = o.getJSONObject("body");
									body = bd.toJSONString();
								}
								return getAccessTokenByPost(url, body, headers);
							}
						}else {
							return getAccessTokenByGet(url,null) ;
						}
					}
				}
			}
		}
		return this.restConfigStorage.getAccessToken();
	}

	@Override
	public String getCookiesTest(TicRestCookieSetting cookieSetting)
			throws Exception {
		if (cookieSetting.getFdUseCustCt()) {
			return getCookies4Clazz(cookieSetting.getFdCookieSettingClazz(),
					null, null);
		} else {
			String config = cookieSetting.getFdCookieSettingParam();
			JSONObject o = JSONObject.parseObject(config);
			String method = o.getString("method");
			String url = o.getString("url");
			String headers = o.getJSONArray("header").toString();
			if ("get".equals(method)) {
				return getCookiesByGet(url, headers);
			}
			String body = "";
			if ("post".equals(method)) {
				if (o.containsKey("body")) {
					String bd = o.getString("body");
					if (StringUtil.isNotNull(bd)) {
						body = new String(Base64.decode(bd.getBytes("UTF-8")),
							"UTF-8");
					}
				}
				return getCookiesByPost(url, body, headers);
			}
		}
		return null;
	}
	
	@Override
	public String getPrefixReqTest(TicRestPrefixReqSetting prefixReqSetting)
			throws Exception {
		if (prefixReqSetting.getFdUseCustCt()) {//Java类方法
			return getBody4Clazz(prefixReqSetting.getFdPrefixReqSettingClazz());
			                                         
		} else {//Http请求方法
			String config = prefixReqSetting.getFdPrefixReqSettingParam();
			JSONObject o = JSONObject.parseObject(config);
			String method = o.getString("method");
			String url = o.getString("url");
			String headers = o.getJSONArray("header").toString();
			if ("get".equals(method)) {
				return getResultByGet(url, headers).toJSONString();
			}
			String body = "";
			if ("post".equals(method)) {
				if (o.containsKey("body")) {
					String bd = o.getString("body");
					if (StringUtil.isNotNull(bd)) {
						body = new String(Base64.decode(bd.getBytes("UTF-8")),
							"UTF-8");
					}
				}
				return getResultByPost(url, body, headers).toJSONString();
			}
		}
		return null;
	}


	@Override
	public String getCookies(JSONObject prefixReqResult, String url)
			throws Exception {
		TicRestMain restMain = restConfigStorage.getTicRestMain();
		if (restMain==null || !restMain.getFdCookieEnable()) {
			return null;
		}
		TicRestCookieSetting cookieSetting = restMain.getTicRestCookieSetting();
		if (cookieSetting.getFdUseCustCt()) {
			return getCookies4Clazz(cookieSetting.getFdCookieSettingClazz(),
					prefixReqResult, url);
		} else {
			String config = cookieSetting.getFdCookieSettingParam();
			JSONObject o = JSONObject.parseObject(config);
			String method = o.getString("method");
			String url_cookie = o.getString("url");
			String headers = o.getJSONArray("header").toString();
			if ("get".equals(method)) {
				return getCookiesByGet(url_cookie, headers);
			}
			String body = "";
			if ("post".equals(method)) {
				if (o.containsKey("body")) {
					String bd = o.getString("body");
					if(StringUtil.isNotNull(bd)){
						body = new String(Base64.decode(bd.getBytes("UTF-8")),
								"UTF-8");
					}
				}
				return getCookiesByPost(url_cookie, body, headers);
			}
		}
		return null;
	}

	@Override
	public void initHttp(RestConfigStorage configStorage) {
		this.restConfigStorage = addRestConfigStorage(configStorage);
		this.restErrorKeys = new RestErrorKeys();
		restErrorKeys.setCodeKey("errcode");
		restErrorKeys.setSuccValue("0");
		restErrorKeys.setHeader(restConfigStorage.getHttpheader());
		
		// ApacheHttpClientBuilder apacheHttpClientBuilder =
		// this.restConfigStorage.getApacheHttpClientBuilder();
		ApacheHttpClientBuilder apacheHttpClientBuilder = null;
		if (null == apacheHttpClientBuilder) {
			DefaultApacheHttpClientBuilder defaultHttp = DefaultApacheHttpClientBuilder
					.getNewInstance();
			if (this.restConfigStorage.getConnectionRequestTimeout() != 0) {
				defaultHttp.setConnectionRequestTimeout(this.restConfigStorage.getConnectionRequestTimeout());
			}
			if (this.restConfigStorage.getConnectionTimeout() != 0) {
				defaultHttp.setConnectionTimeout(this.restConfigStorage.getConnectionTimeout());
			}
			if (this.restConfigStorage.getSoTimeout() != 0) {
				defaultHttp.setSoTimeout(this.restConfigStorage.getSoTimeout());
			}
			if (this.restConfigStorage.getIdleConnTimeout() != 0) {
				defaultHttp.setIdleConnTimeout(this.restConfigStorage.getIdleConnTimeout());
			}
			if (this.restConfigStorage.getCheckWaitTime() != 0) {
				defaultHttp.setCheckWaitTime(this.restConfigStorage.getCheckWaitTime());
			}
			if (this.restConfigStorage.getMaxConnPerHost() != 0) {
				defaultHttp.setMaxConnPerHost(this.restConfigStorage.getMaxConnPerHost());
			}
			if (this.restConfigStorage.getMaxTotalConn() != 0) {
				defaultHttp.setMaxTotalConn(this.restConfigStorage.getMaxTotalConn());
			}

			apacheHttpClientBuilder = defaultHttp;
		}

		if (StringUtil.isNotNull(this.restConfigStorage.getHttpProxyHost())
				&& this.restConfigStorage.getHttpProxyPort() > 0) {
			this.httpProxy = new HttpHost(this.restConfigStorage.getHttpProxyHost(),
					this.restConfigStorage.getHttpProxyPort());
			apacheHttpClientBuilder
					.httpProxyHost(this.restConfigStorage.getHttpProxyHost())
					.httpProxyPort(this.restConfigStorage.getHttpProxyPort())
					.httpProxyUsername(
							this.restConfigStorage.getHttpProxyUsername())
					.httpProxyPassword(
							this.restConfigStorage.getHttpProxyPassword());
		}

		this.httpClient = apacheHttpClientBuilder.build();
	}

	public static void main(String[] args) {
		HttpGet httpGet = new HttpGet("http://java.landray.com.cn");
		try {
			HttpClientBuilder httpClientBuilder = HttpClients.custom();
			CloseableHttpClient httpclient = httpClientBuilder.build();
			HttpClientContext context = HttpClientContext.create();
			logger.info("url:" + httpGet.getURI());
			CloseableHttpResponse response = httpclient.execute(httpGet,
					context);
			if (logger.isInfoEnabled()) {
				String resultContent = new BasicResponseHandler()
						.handleResponse(response);
				logger.info("resultContent:" + resultContent);
			}
			List<Cookie> cookies = context.getCookieStore().getCookies();
			for (int i = 0; i < cookies.size(); i++) {
				JSONObject o = new JSONObject(true);
				o.put("name", cookies.get(i).getName());
				o.put("value", cookies.get(i).getValue());
				o.put("domain", cookies.get(i).getDomain());
				o.put("path", cookies.get(i).getPath());
				if (cookies.get(i).getExpiryDate() != null) {
					o.put("expire", cookies.get(i).getExpiryDate().getTime());
				}
				System.out.println(o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpGet.releaseConnection();
		}
	}

	private String getCookiesByGet(String url, String headers)
			throws RestErrorException {
		JSONArray array = new JSONArray();
		try {
			HttpGet httpGet = new HttpGet(url);
			if (this.httpProxy != null) {
				RequestConfig config = RequestConfig.custom()
						.setProxy(this.httpProxy).build();
				httpGet.setConfig(config);
			}
			if (StringUtil.isNotNull(headers)) {
				JSONArray headerArr = JSONArray.parseArray(headers);
				for (int i = 0; i < headerArr.size(); i++) {
					JSONObject header = headerArr.getJSONObject(i);
					httpGet.addHeader(header.getString("name"),
							header.getString("value"));
				}
			}
			try {
				CloseableHttpClient httpclient = getNewRequestHttpClient(null);

				// CloseableHttpClient httpclient = HttpClients.custom()
				// .build();
				
				HttpClientContext context = HttpClientContext.create();

				logger.info("url:" + httpGet.getURI());
				CloseableHttpResponse response = httpclient.execute(httpGet,
						context);
				if (logger.isInfoEnabled()) {
					String resultContent = new BasicResponseHandler()
							.handleResponse(response);
					logger.info("resultContent:" + resultContent);
				}
				if (context.getCookieStore() != null) {
					List<Cookie> cookies = context.getCookieStore()
							.getCookies();
					for (int i = 0; i < cookies.size(); i++) {
						JSONObject o = new JSONObject(true);
						o.put("name", cookies.get(i).getName());
						o.put("value", cookies.get(i).getValue());
						o.put("domain", cookies.get(i).getDomain());
						o.put("path", cookies.get(i).getPath());
						if (cookies.get(i).getExpiryDate() != null) {
							o.put("expire",
									cookies.get(i).getExpiryDate().getTime());
						}
						array.add(o);
					}
				}
				logger.info("cookies:" + array.toString());
				return array.toString();
			} finally {
				httpGet.releaseConnection();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private String getBodyByGet2(String url, String headers)
			throws RestErrorException {
		try {
			HttpGet httpGet = new HttpGet(url);
			if (this.httpProxy != null) {
				RequestConfig config = RequestConfig.custom()
						.setProxy(this.httpProxy).build();
				httpGet.setConfig(config);
			}
			if (StringUtil.isNotNull(headers)) {
				JSONArray headerArr = JSONArray.parseArray(headers);
				for (int i = 0; i < headerArr.size(); i++) {
					JSONObject header = headerArr.getJSONObject(i);
					httpGet.addHeader(header.getString("name"),
							header.getString("value"));
				}
			}
			try {
				CloseableHttpClient httpclient = getNewRequestHttpClient(null);

				HttpClientContext context = HttpClientContext.create();

				logger.info("url:" + httpGet.getURI());
				CloseableHttpResponse response = httpclient.execute(httpGet,
						context);
				String resultContent = new BasicResponseHandler()
						.handleResponse(response);
                if (logger.isDebugEnabled()) {
					logger.info("Get请求响应:" + resultContent);
				}
				return resultContent;
			} finally {
				httpGet.releaseConnection();
			}
		} catch (IOException e) {
			logger.error("Get 请求异常",e);
			throw new RuntimeException(e);
		}
	}

	private JSONObject getResultByGet(String url, String headers)
			throws RestErrorException {
		try {
			HttpGet httpGet = new HttpGet(url);
			if (this.httpProxy != null) {
				RequestConfig config = RequestConfig.custom()
						.setProxy(this.httpProxy).build();
				httpGet.setConfig(config);
			}
			if (StringUtil.isNotNull(headers)) {
				JSONArray headerArr = JSONArray.parseArray(headers);
				for (int i = 0; i < headerArr.size(); i++) {
					JSONObject header = headerArr.getJSONObject(i);
					httpGet.addHeader(header.getString("name"),
							header.getString("value"));
				}
			}
			JSONObject result = new JSONObject(true);
			try {
				CloseableHttpClient httpclient = getNewRequestHttpClient(null);

				HttpClientContext context = HttpClientContext.create();

				logger.info("url:" + httpGet.getURI());
				CloseableHttpResponse response = httpclient.execute(httpGet,
						context);
				String resultContent = new BasicResponseHandler()
						.handleResponse(response);
				if (logger.isDebugEnabled()) {
					logger.info("Get请求响应:" + resultContent);
				}
				Header[] hs = response.getAllHeaders();
				if (hs != null) {
					JSONObject header = new JSONObject(true);
					for (int i = 0; i < hs.length; i++) {
						String name = hs[i].getName();
						String value = hs[i].getValue();
						header.put(name, value);
					}
					result.put("header", header);
				}

				List<Cookie> cookies = context.getCookieStore()
						.getCookies();
				if (cookies != null) {
					JSONObject cookie = new JSONObject(true);
					for (int i = 0; i < cookies.size(); i++) {
						JSONObject o = new JSONObject(true);
						String name = cookies.get(i).getName();
						String value = cookies.get(i).getValue();
						// o.put("domain", cookies.get(i).getDomain());
						// o.put("path", cookies.get(i).getPath());
						// if (cookies.get(i).getExpiryDate() != null) {
						// o.put("expire",
						// cookies.get(i).getExpiryDate().getTime());
						// }
						cookie.put(name, value);
					}
					result.put("cookie", cookie);
				}
				result.put("body", JSONObject.parseObject(resultContent));

				logger.debug("请求:" + url + "，结果：" + result);
				return result;
			} finally {
				httpGet.releaseConnection();
			}
		} catch (IOException e) {
			logger.error("Get 请求异常", e);
			throw new RuntimeException(e);
		}
	}

	private String getCookiesByPost(String url, String body, String headers)
			throws RestErrorException {
		JSONArray array = new JSONArray();
		try {
			HttpPost httpPost = new HttpPost(url);
			if (this.httpProxy != null) {
				RequestConfig config = RequestConfig.custom()
						.setProxy(this.httpProxy).build();
				httpPost.setConfig(config);
			}

			if (StringUtil.isNotNull(headers)) {
				JSONArray headerArr = JSONArray.parseArray(headers);
				for (int i = 0; i < headerArr.size(); i++) {
					JSONObject header = headerArr.getJSONObject(i);
					httpPost.addHeader(header.getString("name"),
							header.getString("value"));
				}
			}

			if (StringUtil.isNotNull(body)) {
				StringEntity entity = new StringEntity(body, Consts.UTF_8);
				httpPost.setEntity(entity);
			}

			String resultContent = null;
			try {
				CloseableHttpClient httpclient = getNewRequestHttpClient(null);
				HttpClientContext context = HttpClientContext.create();
				logger.info("url:" + httpPost.getURI());
				CloseableHttpResponse response = httpclient.execute(httpPost,
						context);
				if (logger.isInfoEnabled()) {
					resultContent = new BasicResponseHandler()
							.handleResponse(response);
					logger.info("resultContent:" + resultContent);
				}
				if (context.getCookieStore() != null) {
					List<Cookie> cookies = context.getCookieStore()
							.getCookies();
					for (int i = 0; i < cookies.size(); i++) {
						JSONObject o = new JSONObject(true);
						o.put("name", cookies.get(i).getName());
						o.put("value", cookies.get(i).getValue());
						o.put("domain", cookies.get(i).getDomain());
						o.put("path", cookies.get(i).getPath());
						if (cookies.get(i).getExpiryDate() != null) {
							o.put("expire",
									cookies.get(i).getExpiryDate().getTime());
						}
						array.add(o);
					}
				}
				logger.info("cookies:" + array.toString());
				return array.toString();
			} finally {
				httpPost.releaseConnection();
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	private String getBodyByPost2(String url, String body, String headers)
			throws RestErrorException {
		JSONArray array = new JSONArray();
		try {
			HttpPost httpPost = new HttpPost(url);
			if (this.httpProxy != null) {
				RequestConfig config = RequestConfig.custom()
						.setProxy(this.httpProxy).build();
				httpPost.setConfig(config);
			}

			if (StringUtil.isNotNull(headers)) {
				if (logger.isDebugEnabled()) {
					logger.debug("headers请求参数:" + body);
				}
				JSONArray headerArr = JSONArray.parseArray(headers);
				for (int i = 0; i < headerArr.size(); i++) {
					JSONObject header = headerArr.getJSONObject(i);
					httpPost.addHeader(header.getString("name"),
							header.getString("value"));
				}
			}

			if (StringUtil.isNotNull(body)) {
				if (logger.isDebugEnabled()) {
					logger.debug("post请求参数:" + body);
				}
				StringEntity entity = new StringEntity(body, Consts.UTF_8);
				httpPost.setEntity(entity);
			}

			String resultContent = null;
			try {
				CloseableHttpClient httpclient = getNewRequestHttpClient(null);
				HttpClientContext context = HttpClientContext.create();
				logger.info("url:" + httpPost.getURI());
				CloseableHttpResponse response = httpclient.execute(httpPost,
						context);
				resultContent = new BasicResponseHandler()
						.handleResponse(response);
				logger.info("resultContent:" + resultContent);
				if (logger.isDebugEnabled()) {
					logger.info("Post请求响应:" + resultContent);
				}
				return resultContent;
			} finally {
				httpPost.releaseConnection();
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private JSONObject getResultByPost(String url, String body, String headers)
			throws RestErrorException {
		JSONArray array = new JSONArray();
		try {
			HttpPost httpPost = new HttpPost(url);
			if (this.httpProxy != null) {
				RequestConfig config = RequestConfig.custom()
						.setProxy(this.httpProxy).build();
				httpPost.setConfig(config);
			}
            
			if (StringUtil.isNotNull(headers)) {
				if(logger.isDebugEnabled()){
					logger.debug("headers请求参数:"+body);
				}
				JSONArray headerArr = JSONArray.parseArray(headers);
				for (int i = 0; i < headerArr.size(); i++) {
					JSONObject header = headerArr.getJSONObject(i);
					httpPost.addHeader(header.getString("name"),
							header.getString("value"));
				}
			}

			if (StringUtil.isNotNull(body)) {
				if(logger.isDebugEnabled()){
					logger.debug("post请求参数:"+body);
				}
				StringEntity entity = new StringEntity(body, Consts.UTF_8);
				httpPost.setEntity(entity);
			}
			if (StringUtil.isNotNull(body)) {
				if (logger.isDebugEnabled()) {
					logger.debug("post请求参数:" + body);
				}
				String contentType = httpPost.getFirstHeader("Content-Type")
						.getValue();
				if (contentType != null && contentType
						.startsWith("application/x-www-form-urlencoded")) {
					httpPost.setEntity(new UrlEncodedFormEntity(
							buildNameValuePairs(body), "UTF-8"));
				} else {
					StringEntity entity = new StringEntity(body,
							Consts.UTF_8);
					httpPost.setEntity(entity);
				}
			}

			String resultContent = null;
			JSONObject result = new JSONObject(true);
			try {
				CloseableHttpClient httpclient = getNewRequestHttpClient(null);
				HttpClientContext context = HttpClientContext.create();
				logger.info("url:" + httpPost.getURI());
				CloseableHttpResponse response = httpclient.execute(httpPost,
						context);
				resultContent = new BasicResponseHandler()
						.handleResponse(response);
				logger.info("resultContent:" + resultContent);
				if (logger.isDebugEnabled()) {
					logger.info("Post请求响应:" + resultContent);
				}
				Header[] hs = response.getAllHeaders();
				if (hs != null) {
					JSONObject header = new JSONObject(true);
					for (int i = 0; i < hs.length; i++) {
						String name = hs[i].getName();
						String value = hs[i].getValue();
						header.put(name, value);
					}
					result.put("header", header);
				}

				List<Cookie> cookies = context.getCookieStore()
						.getCookies();
				if (cookies != null) {
					JSONObject cookie = new JSONObject(true);
					for (int i = 0; i < cookies.size(); i++) {
						JSONObject o = new JSONObject(true);
						String name = cookies.get(i).getName();
						String value = cookies.get(i).getValue();
						// o.put("domain", cookies.get(i).getDomain());
						// o.put("path", cookies.get(i).getPath());
						// if (cookies.get(i).getExpiryDate() != null) {
						// o.put("expire",
						// cookies.get(i).getExpiryDate().getTime());
						// }
						cookie.put(name, value);
					}
					result.put("cookie", cookie);
				}
				result.put("body", JSONObject.parseObject(resultContent));

				logger.debug("请求:" + url + "，结果：" + result);
				return result;
			} finally {
				httpPost.releaseConnection();
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


	public String getCookieTest() throws RestErrorException {

		if (StringUtil.isNotNull(restConfigStorage.getAccessTokenClazz())) {
			getAccessToken4Clazz();
		} else {
			String url = this.restConfigStorage.getAccessTokenURL();
			if (url.indexOf("{") == 0) {
				JSONObject o = JSONObject.parseObject(url);
				String method = o.getString("method");
				url = o.getString("url");
				// o.getJSONArray("header");
				if ("get".equals(method)) {
					return getAccessTokenByGet(url, null);
				}
				String body = "";
				if ("post".equals(method)) {
					if (o.containsKey("body")) {
						JSONObject bd = o.getJSONObject("body");
						body = bd.toJSONString();
					}
					return getAccessTokenByPost(url, body, null);
				}
			} else {
				return getAccessTokenByGet(url, null);
			}
		}
		return this.restConfigStorage.getAccessToken();
	}
	
	public String getPrefixReqTest() throws RestErrorException {

		if (StringUtil.isNotNull(restConfigStorage.getAccessTokenClazz())) {
			getAccessToken4Clazz();
		} else {
			String url = this.restConfigStorage.getAccessTokenURL();
			if (url.indexOf("{") == 0) {
				JSONObject o = JSONObject.parseObject(url);
				String method = o.getString("method");
				url = o.getString("url");
				// o.getJSONArray("header");
				if ("get".equals(method)) {
					return getAccessTokenByGet(url, null);
				}
				String body = "";
				if ("post".equals(method)) {
					if (o.containsKey("body")) {
						JSONObject bd = o.getJSONObject("body");
						body = bd.toJSONString();
					}
					return getAccessTokenByPost(url, body, null);
				}
			} else {
				return getAccessTokenByGet(url, null);
			}
		}
		return this.restConfigStorage.getAccessToken();
	}

	public String getReturnBOdy() {
		return returnBOdy;
	}

	public void setReturnBOdy(String returnBOdy) {
		this.returnBOdy = returnBOdy;
	}

	@Override
	public void close() throws Exception {
		if (httpClient != null) {
			httpClient.close();
		}
	}
	
	private List<NameValuePair> buildNameValuePairs(String postEntity) {
		List<NameValuePair> nameValuePairs = new ArrayList<>();
		if (StringUtil.isNull(postEntity)) {
			return nameValuePairs;
		}
		JSONObject obj = JSONObject.parseObject(postEntity, Feature.OrderedField);
		for (Object keyObj : obj.keySet()) {
			String key = (String) keyObj;
			Object value = obj.get(key);
			nameValuePairs.add(new BasicNameValuePair(key, (String) value));
		}
		return nameValuePairs;

	}

}
