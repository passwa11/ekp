package com.landray.kmss.tic.rest.client.http.apache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.slf4j.Logger;
import org.apache.http.Consts;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;

import com.landray.kmss.tic.rest.client.api.impl.LtpaTokenCookieProvider;
import com.landray.kmss.tic.rest.client.error.RestError;
import com.landray.kmss.tic.rest.client.error.RestErrorException;
import com.landray.kmss.tic.rest.client.error.RestErrorKeys;
import com.landray.kmss.tic.rest.client.http.RequestHttp;
import com.landray.kmss.tic.rest.client.http.SimplePostRequestExecutor;
import com.landray.kmss.util.StringUtil;


public class ApacheSimplePostRequestExecutor extends SimplePostRequestExecutor<CloseableHttpClient, HttpHost,RestErrorKeys> {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ApacheSimplePostRequestExecutor.class);

	public ApacheSimplePostRequestExecutor(RequestHttp requestHttp) {
		super(requestHttp);
	}

	@Override
	public String execute(String uri, String postEntity) throws RestErrorException, IOException {
		HttpPost httpPost = new HttpPost(uri);
		if (requestHttp.getRequestHttpProxy() != null) {
			RequestConfig config = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
			httpPost.setConfig(config);
		}
		HttpClientContext context = HttpClientContext.create();
		
		if(StringUtil.isNotNull(requestHttp.getErrorKeys().getHeader())){
			JSONArray headers = JSONArray.parseArray(requestHttp.getErrorKeys().getHeader());
			for(int i=0;i<headers.size();i++) {
				String header = headers.getString(i);
				if(StringUtil.isNotNull(header)) {
					String[] kn = header.split(":");
					if (kn.length < 2) {
						logger.error("header格式不正确，" + header);
					}
					httpPost.addHeader(kn[0], kn[1]);
				}
			}
		}
		String key = null;
		CookieStore cookieStore = null;
		if (StringUtil.isNotNull(requestHttp.getErrorKeys().getCookieStr())) {
			JSONArray cookies = JSONArray
					.parseArray(requestHttp.getErrorKeys().getCookieStr());
			cookieStore = new BasicCookieStore();
			for (int i = 0; i < cookies.size(); i++) {
				JSONObject cookie = cookies.getJSONObject(i);
				if (cookie.containsKey("key")) {
					key = cookie.getString("key");
				}
				BasicClientCookie basicClientCookie = new BasicClientCookie(
						cookie.getString("name"), cookie.getString("value"));
				basicClientCookie.setDomain(cookie.getString("domain"));
				if (cookie.containsKey("path")) {
					basicClientCookie.setPath(cookie.getString("path"));
				}
				cookieStore.addCookie(basicClientCookie);
			}
			context.setCookieStore(cookieStore);
		}
		
		if (postEntity != null) {
			try{
				JSONObject o = JSONObject.parseObject(postEntity, Feature.OrderedField);
				if(o.containsKey("body")){
					postEntity = o.getJSONObject("body").toString();
				}
			}catch(Exception e){
				logger.error(e.getMessage(),e);
			}
			String contentType = null;
			if (httpPost.getFirstHeader("Content-Type") != null) {
				contentType = httpPost.getFirstHeader("Content-Type")
					.getValue();
			}
			if (contentType != null && contentType
					.startsWith("application/x-www-form-urlencoded")) {
				httpPost.setEntity(new UrlEncodedFormEntity(
						buildNameValuePairs(postEntity), "UTF-8"));
			} else {
				StringEntity entity = new StringEntity(postEntity,
						Consts.UTF_8);
				httpPost.setEntity(entity);
			}
		}

		try (CloseableHttpResponse response = requestHttp
				.getRequestHttpClient()
				.execute(httpPost, context)) {
			String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
			// System.out.println("54321:" + responseContent);
			if (responseContent.isEmpty()) {
				throw new RestErrorException(RestError.fromMsg("无响应内容"));
			}
			if (StringUtil.isNotNull(key)) {
				LtpaTokenCookieProvider.updateCookieCache(key,
						cookieStore);
			}
			if (responseContent.startsWith("<xml>")) {
				// xml格式输出直接返回
				return responseContent;
			}

			try {
				RestError error = RestError.fromJson(responseContent,
						requestHttp.getErrorKeys());
				if (error.hasError()) {
					throw new RestErrorException(error);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
			return responseContent;
		} finally {
			httpPost.releaseConnection();
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
