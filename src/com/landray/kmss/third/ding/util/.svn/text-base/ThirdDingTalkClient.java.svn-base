package com.landray.kmss.third.ding.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.DingTalkConstants;
import com.dingtalk.api.DingTalkSignatureUtil;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.sso.client.oracle.StringUtil;
import com.taobao.api.ApiException;
import com.taobao.api.ApiRuleException;
import com.taobao.api.Constants;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.FileItem;
import com.taobao.api.TaobaoParser;
import com.taobao.api.TaobaoRequest;
import com.taobao.api.TaobaoResponse;
import com.taobao.api.TaobaoUploadRequest;
import com.taobao.api.internal.parser.json.ObjectJsonParser;
import com.taobao.api.internal.util.HttpResponseData;
import com.taobao.api.internal.util.RequestParametersHolder;
import com.taobao.api.internal.util.StringUtils;
import com.taobao.api.internal.util.TaobaoHashMap;
import com.taobao.api.internal.util.TaobaoLogger;
import com.taobao.api.internal.util.TaobaoUtils;
import com.taobao.api.internal.util.WebV2Utils;
import com.taobao.api.internal.util.WebV2Utils.TrustAllTrustManager;

/**
 * 钉钉客户端
 * <pre>
 * 继承关系及代码与DefaultDingTalkClient相同，区别在于添加了代理功能及调用的是对应可传入代理参数的WebV2Utils.doPost方法。
 * <b>
 * ！！！注意：！！！
 * ！！！若升级了taobao-sdk-java.jar，且新版本修改了DefaultDingTalkClient类，则此类也必须同步进行修改！！！
 * </b>
 * </pre>
 */
public class ThirdDingTalkClient extends DefaultTaobaoClient implements DingTalkClient {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingTalkClient.class);
	private static boolean ignoreSSLCheck = true; // 忽略SSL检查
	private static boolean ignoreHostCheck = true; // 忽略HOST检查
	private static final String DEFAULT_CHARSET = Constants.CHARSET_UTF8;
	public ThirdDingTalkClient(String serverUrl) {
		super(serverUrl, null, null);
	}

	@Override
    public <T extends TaobaoResponse> T execute(TaobaoRequest<T> request, String accessKey, String accessSecret) throws ApiException {
		return execute(request, accessKey, accessSecret, null,null);
	}

	@Override
    public <T extends TaobaoResponse> T execute(TaobaoRequest<T> request, String accessKey, String accessSecret, String suiteTicket) throws ApiException {
		return execute(request, accessKey, accessSecret, suiteTicket,null);
	}

	@Override
    public <T extends TaobaoResponse> T execute(TaobaoRequest<T> request,
                                                String session) throws ApiException {
		try {
			return ThirdDingTalkRateLimitUtil.executeRateLimit(request, session,
					this);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ApiException(e.getMessage());
		}
	}

	public <T extends TaobaoResponse> T doExecute(TaobaoRequest<T> request,
												  String session) throws ApiException {
		logger.info("调用接口：" + this.getRequestUrl() + "，开始时间："
				+ System.currentTimeMillis());
		if (request.getTopApiCallType() == null || request.getTopApiCallType()
				.equals(DingTalkConstants.CALL_TYPE_TOP)) {
			return super.execute(request, session);
		} else {
			return executeOApi(request, session);
		}
	}


	@Override
    public <T extends TaobaoResponse> T execute(TaobaoRequest<T> request, String accessKey, String accessSecret, String suiteTicket, String corpId) throws ApiException {
		if(request.getTopApiCallType() == null || request.getTopApiCallType().equals(DingTalkConstants.CALL_TYPE_TOP)) {
			return super.execute(request,null);
		} else {
			return executeOApi(request,null, accessKey, accessSecret,suiteTicket, corpId);
		}
	}

	private <T extends TaobaoResponse> T executeOApi(TaobaoRequest<T> request, String session) throws ApiException {
		return executeOApi(request, session, null, null, null, null);
	}

	@SuppressWarnings("unchecked")
	private <T extends TaobaoResponse> T executeOApi(TaobaoRequest<T> request, String session, String accessKey, String accessSecret, String suiteTicket, String corpId) throws ApiException {
		long start = System.currentTimeMillis();
		// 构建响应解释器
		TaobaoParser<T> parser = null;
		if (this.needEnableParser) {
			parser = new ObjectJsonParser<T>(request.getResponseClass(), true);
		}

		// 本地校验请求参数
		if (this.needCheckRequest) {
			try {
				request.check();
			} catch (ApiRuleException e) {
				T localResponse = null;
				try {
					localResponse = request.getResponseClass().newInstance();
				} catch (Exception xe) {
					throw new ApiException(xe);
				}
				localResponse.setErrorCode(e.getErrCode());
				localResponse.setMsg(e.getErrMsg());
				return localResponse;
			}
		}

		RequestParametersHolder requestHolder = new RequestParametersHolder();
		TaobaoHashMap appParams = new TaobaoHashMap(request.getTextParams());
		requestHolder.setApplicationParams(appParams);

		// 添加协议级请求参数
		TaobaoHashMap protocalMustParams = new TaobaoHashMap();
		protocalMustParams.put(DingTalkConstants.ACCESS_TOKEN, session);
		requestHolder.setProtocalMustParams(protocalMustParams);

		try {
			String fullUrl;
			// 签名优先
			if(accessKey != null) {
				Long timestamp = System.currentTimeMillis();
				// 验证签名有效性
				String canonicalString = DingTalkSignatureUtil.getCanonicalStringForIsv(timestamp, suiteTicket);
				String signature = DingTalkSignatureUtil.computeSignature(accessSecret, canonicalString);
				Map<String, String > ps = new HashMap<String, String>();
				ps.put("accessKey", accessKey);
				ps.put("signature", signature);
				ps.put("timestamp", timestamp+"");
				if(suiteTicket != null) {
					ps.put("suiteTicket", suiteTicket);
				}
				if(corpId != null){
					ps.put("corpId", corpId);
				}

				String queryStr =DingTalkSignatureUtil.paramToQueryString(ps, "utf-8");
				if (this.serverUrl.indexOf("?") > 0) {
					fullUrl = this.serverUrl + "&"+queryStr;
				} else {
					fullUrl = this.serverUrl + "?"+queryStr;
				}
			}else{
				if (this.serverUrl.indexOf("?") > 0) {
					fullUrl = this.serverUrl + (session != null && session.length() > 0 ? ("&access_token=" + session) : "");
				} else {
					fullUrl = this.serverUrl + (session != null && session.length() > 0 ? ("?access_token=" + session) : "");
				}
			}

			HttpResponseData data = null;
			// 是否需要压缩响应
			if (this.useGzipEncoding) {
				request.getHeaderMap().put(Constants.ACCEPT_ENCODING, Constants.CONTENT_ENCODING_GZIP);
			}
			
			// 创建代理服务器  
			Proxy proxy = null;
			String dingProxy = DingConstant.DING_PORXY;
			if (StringUtil.isNotNull(dingProxy)) {
				String host = dingProxy.split(":")[0];
				int port = Integer.parseInt(dingProxy.split(":")[1]);
				logger.debug("代理配置dingProxy host============="+host + ",port:"+ port);
				InetSocketAddress addr = new InetSocketAddress(host, port);
				proxy = new Proxy(Proxy.Type.HTTP, addr);
			}
			if("GET".equals(request.getTopHttpMethod())) {
				data = doGet(fullUrl, appParams,DEFAULT_CHARSET ,connectTimeout, readTimeout,proxy);
			} else {
				// 是否需要上传文件
				if (request instanceof TaobaoUploadRequest) {
					TaobaoUploadRequest<T> uRequest = (TaobaoUploadRequest<T>) request;
					Map<String, FileItem> fileParams = TaobaoUtils.cleanupMap(uRequest.getFileParams());
					data = ThirdDingWebV2Utils.doPost(fullUrl, appParams,
							fileParams, Constants.CHARSET_UTF8, connectTimeout,
							readTimeout, request.getHeaderMap(), proxy);
				} else {

					Map<String, Object> jsonParams = new HashMap<String, Object>();
					for (Map.Entry<String, String> paramEntry : appParams.entrySet()) {
						String key = paramEntry.getKey();
						String value = paramEntry.getValue();
						if(value.startsWith("[") && value.endsWith("]")) {
							List<Map<String, Object>> childMap = (List<Map<String, Object>>)TaobaoUtils.jsonToObject(value);
							jsonParams.put(key, childMap);
						} else if(value.startsWith("{") && value.endsWith("}")) {
							Map<String, Object> childMap = (Map<String, Object>)TaobaoUtils.jsonToObject(value);
							jsonParams.put(key, childMap);
						} else {
							jsonParams.put(key, value);
						}
					}

					//构建请求体
					String ctype = "application/json;charset=" + Constants.CHARSET_UTF8;
					byte[] content = new byte[0];
					String body = TaobaoUtils.objectToJson(jsonParams);
					if (body != null) {
						content = body.getBytes(Constants.CHARSET_UTF8);
					}
					data = WebV2Utils.doPost(fullUrl, ctype, content, connectTimeout, readTimeout, null, proxy);
				}
			}
			requestHolder.setResponseBody(data.getBody());
			requestHolder.setResponseHeaders(data.getHeaders());
		} catch (IOException e) {
			TaobaoLogger.logApiError("_dingtalk_", request.getApiMethodName(), serverUrl, requestHolder.getAllParams(), System.currentTimeMillis() - start, e.toString());
			throw new ApiException(e);
		}

		T tRsp = null;
		if (this.needEnableParser) {
			tRsp = parser.parse(requestHolder.getResponseBody(), Constants.RESPONSE_TYPE_DINGTALK_OAPI);
			tRsp.setBody(requestHolder.getResponseBody());
		} else {
			try {
				tRsp = request.getResponseClass().newInstance();
				tRsp.setBody(requestHolder.getResponseBody());
			} catch (Exception e) {
				throw new ApiException(e);
			}
		}

		tRsp.setParams(appParams);
		if (!tRsp.isSuccess()) {
			TaobaoLogger.logApiError("_dingtalk_", request.getApiMethodName(), serverUrl, requestHolder.getAllParams(), System.currentTimeMillis() - start, tRsp.getBody());
		}
		return tRsp;
	}

	public static HttpResponseData doGet(String url, Map<String, String> params, String charset, int connectTimeout,
			int readTimeout, Proxy proxy) throws IOException {
		HttpURLConnection conn = null;
		String rsp = null;
		HttpResponseData data = new HttpResponseData();

		try {
			String ctype = "application/x-www-form-urlencoded;charset=" + charset;
			String query = WebV2Utils.buildQuery(params,charset);
			conn = getConnection(buildGetUrl(url, query), "GET", ctype, (Map) null, (Proxy) proxy);
			conn.setConnectTimeout(connectTimeout);
			conn.setReadTimeout(readTimeout);
			rsp = getResponseAsString(conn);
			data.setBody(rsp);
			data.setHeaders(conn.getHeaderFields());
		} finally {
			if (conn != null) {
				conn.disconnect();
			}

		}

		return data;
	}
	
	private static URL buildGetUrl(String url, String query) throws IOException {
		if (StringUtils.isEmpty(query)) {
			return new URL(url);
		}

		return new URL(WebV2Utils.buildRequestUrl(url, query));
	}
	
	private static HttpURLConnection getConnection(URL url, String method, String ctype, Map<String, String> headerMap, Proxy proxy) throws IOException {
		HttpURLConnection conn = null;
		if(proxy == null) {
			conn = (HttpURLConnection) url.openConnection();
		} else {
			conn = (HttpURLConnection) url.openConnection(proxy);
		}
		if (conn instanceof HttpsURLConnection) {
			HttpsURLConnection connHttps = (HttpsURLConnection) conn;
			if (ignoreSSLCheck) {
				try {
					SSLContext ctx = SSLContext.getInstance("TLS");
					ctx.init(null, new TrustManager[] { new TrustAllTrustManager() }, new SecureRandom());
					connHttps.setSSLSocketFactory(ctx.getSocketFactory());
					connHttps.setHostnameVerifier(new HostnameVerifier() {
						@Override
                        public boolean verify(String hostname, SSLSession session) {
							return true;
						}
					});
				} catch (Exception e) {
					throw new IOException(e.toString());
				}
			} else {
				if (ignoreHostCheck) {
					connHttps.setHostnameVerifier(new HostnameVerifier() {
						@Override
                        public boolean verify(String hostname, SSLSession session) {
							return true;
						}
					});
				}
			}
			conn = connHttps;
		}

		conn.setRequestMethod(method);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		if(headerMap != null && headerMap.get(Constants.TOP_HTTP_DNS_HOST) != null){
		    conn.setRequestProperty("Host", headerMap.get(Constants.TOP_HTTP_DNS_HOST));
		}else{
		    conn.setRequestProperty("Host", url.getHost());
		}
		conn.setRequestProperty("Accept", "text/xml,text/javascript");
		conn.setRequestProperty("User-Agent", "top-sdk-java");
		conn.setRequestProperty("Content-Type", ctype);
		if (headerMap != null) {
			for (Entry<String, String> entry : headerMap.entrySet()) {
			    if(!Constants.TOP_HTTP_DNS_HOST.equals(entry.getKey())){
			        conn.setRequestProperty(entry.getKey(), entry.getValue());
			    }
			}
		}
		return conn;
	}
	
	protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
		String charset = WebV2Utils.getResponseCharset(conn.getContentType());
		if (conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
			String contentEncoding = conn.getContentEncoding();
			if (Constants.CONTENT_ENCODING_GZIP.equalsIgnoreCase(contentEncoding)) {
				return WebV2Utils.getStreamAsString(new GZIPInputStream(conn.getInputStream()), charset);
			} else {
				return WebV2Utils.getStreamAsString(conn.getInputStream(), charset);
			}
		} else {
			// OAuth bad request always return 400 status
			if (conn.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
				InputStream error = conn.getErrorStream();
				if (error != null) {
					return WebV2Utils.getStreamAsString(error, charset);
				}
			}
			// Client Error 4xx and Server Error 5xx
			throw new IOException(conn.getResponseCode() + " " + conn.getResponseMessage());
		}
	}
}
