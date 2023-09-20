package com.landray.kmss.sys.restservice.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.KmssMediaTypes;

public class RestRequestConfigImpl implements RestRequestConfig{

	private int connectionRequestTimeout = 500;
	private int connectTimeout = 5000;
	private int socketTimeout = 5000;
	private String siteUri = "http://sso.landray.com.cn:8888";
	private String userAgent = "test user agent";
	private String defaultUrl = "http://localhost:80";
	private String proxyUri;
	private String defaultHeaders;
	private CommonAuthInfo authInfo = new CommonAuthInfo("testing","pai",CommonAuthInfo.AUTH_TYPE_BASIC);
	private CommonAuthInfo proxyAuthInfo;
	private int retry = 1;
	private String localAddress;
	private int waitForContinue = 3000;
	private Map<String,String> headers = new HashMap<>();
	@Override
    public String getLocalAddress() {
		return localAddress;
	}
	@Override
    public void setLocalAddress(String localAddress) {
		this.localAddress = localAddress;
	}

	@Override
    public int getRetry() {
		return retry;
	}

	@Override
    public void setRetry(int retry) {
		this.retry = retry;
	}

	@Override
    public CommonAuthInfo getProxyAuthInfo() {
		return proxyAuthInfo;
	}

	@Override
    public void setProxyAuthInfo(CommonAuthInfo proxyAuthInfo) {
		this.proxyAuthInfo = proxyAuthInfo;
	}

	@Override
    public int getConnectionRequestTimeout() {
		return connectionRequestTimeout;
	}

	@Override
    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
		this.connectionRequestTimeout = connectionRequestTimeout;
	}

	@Override
    public int getConnectTimeout() {
		return connectTimeout;
	}

	@Override
    public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	@Override
    public int getSocketTimeout() {
		return socketTimeout;
	}

	@Override
    public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	@Override
    public String getSiteUri() {
		return siteUri;
	}

	@Override
    public void setSiteUri(String siteUri) {
		while(StringUtil.isNotNull(siteUri) && siteUri.endsWith("/")) {
			siteUri = siteUri.substring(0,siteUri.length()-1);
		}
		this.siteUri = siteUri;
	}

	@Override
    public String getUserAgent() {
		return userAgent;
	}

	@Override
    public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	@Override
    public String getDefaultUrl() {
		return defaultUrl;
	}

	@Override
    public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}

	@Override
    public String getProxyUri() {
		return proxyUri;
	}

	@Override
    public void setProxyUri(String proxyUri) {
		this.proxyUri = proxyUri;
	}

	@Override
    public CommonAuthInfo getAuthInfo() {
		return authInfo;
	}

	@Override
    public void setAuthInfo(CommonAuthInfo authInfo) {
		this.authInfo = authInfo;
	}

	
	@Override
    public int getWaitForContinue() {
        return waitForContinue;
    }
    @Override
    public void setWaitForContinue(int waitForContinue) {
        this.waitForContinue = waitForContinue;
    }

	//{"Content-Type":"application/xml","Accept":"application/json"}
	public String getDefaultHeaders() {
		if(StringUtil.isNotNull(defaultHeaders)) {
			return defaultHeaders;
		}
		HttpHeaders headers = new HttpHeaders();
		List<MediaType> mts = new ArrayList<>();
		mts.add(KmssMediaTypes.APPLICATION_JSON);
		mts.add(KmssMediaTypes.APPLICATION_XML);
		headers.setAccept(mts);
		headers.setContentType(KmssMediaTypes.APPLICATION_JSON);
		headers.setDate(System.currentTimeMillis());
		String jsonString = toJSONString(headers);
		return jsonString;
	}
	
	private static String toJSONString(HttpHeaders headers) {
		Set<Entry<String, List<String>>> entrySet = headers.entrySet();
		JSONObject jo = new JSONObject();
		for(Entry<String, List<String>> entry:entrySet) {
			String key = entry.getKey();
			List<String> value = entry.getValue();
			String join = StringUtils.join(value, ',');
			jo.put(key, join);
		}
		String jsonString = jo.toJSONString();
		return jsonString;
	}
	
    @Override
    public void setDefaultHeader(String headerName, String value) {
        headers.put(headerName, value);
    }
    
    @Override
    public Map<String, String> getDefaultHeader() {
        return headers;
    }
    @Override
    public void setDefaultHeader(Map<String, String> defaultHeader) {
        headers.clear();
        if(defaultHeader!=null&&!defaultHeader.isEmpty()){
            headers.putAll(defaultHeader);
        }
    }

}
