package com.landray.kmss.tic.rest.client.config;

import java.io.File;

import com.landray.kmss.tic.rest.client.http.apache.ApacheHttpClientBuilder;
import com.landray.kmss.tic.rest.connector.model.TicRestMain;

import net.sf.json.JSONObject;

public class RestInMemoryConfigStorage implements RestConfigStorage {

	protected volatile String corpId;
	protected volatile String corpSecret;

	protected volatile String token;
	protected volatile String accessToken;
	protected volatile String aesKey;
	protected volatile String agentId;
	protected volatile long expiresTime;

	protected volatile String httpProxyHost;
	protected volatile int httpProxyPort;
	protected volatile String httpProxyUsername;
	protected volatile String httpProxyPassword;

	protected volatile String httpheader;
	protected volatile String accessTokenClazz;

	protected volatile File tmpDirFile;

	private volatile ApacheHttpClientBuilder apacheHttpClientBuilder;
	
	private String returnBody;

	@Override
	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public boolean isAccessTokenExpired() {
		return System.currentTimeMillis() > this.expiresTime;
	}

	@Override
	public void expireAccessToken() {
		this.expiresTime = 0;
	}

	@Override
	public synchronized void updateAccessToken(RestAccessToken accessToken) {
		updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
	}

	@Override
	public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
		this.accessToken = accessToken;
		this.expiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
	}

	@Override
	public String getCorpId() {
		return this.corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	@Override
	public String getCorpSecret() {
		return this.corpSecret;
	}

	public void setCorpSecret(String corpSecret) {
		this.corpSecret = corpSecret;
	}

	@Override
	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public long getExpiresTime() {
		return this.expiresTime;
	}

	public void setExpiresTime(long expiresTime) {
		this.expiresTime = expiresTime;
	}

	@Override
	public String getAesKey() {
		return this.aesKey;
	}

	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}

	@Override
	public String getAgentId() {
		return this.agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	@Override
	public String getHttpProxyHost() {
		return this.httpProxyHost;
	}

	public void setHttpProxyHost(String httpProxyHost) {
		this.httpProxyHost = httpProxyHost;
	}

	@Override
	public int getHttpProxyPort() {
		return this.httpProxyPort;
	}

	public void setHttpProxyPort(int httpProxyPort) {
		this.httpProxyPort = httpProxyPort;
	}

	@Override
	public String getHttpProxyUsername() {
		return this.httpProxyUsername;
	}

	public void setHttpProxyUsername(String httpProxyUsername) {
		this.httpProxyUsername = httpProxyUsername;
	}

	@Override
	public String getHttpProxyPassword() {
		return this.httpProxyPassword;
	}

	public void setHttpProxyPassword(String httpProxyPassword) {
		this.httpProxyPassword = httpProxyPassword;
	}

	@Override
	public String getHttpheader() {
		return this.httpheader;
	}

	public void setHttpheader(String httpheader) {
		this.httpheader = httpheader;
	}
	
	@Override
	public String getAccessTokenClazz() {
		return this.accessTokenClazz;
	}

	public void setAccessTokenClazz(String accessTokenClazz) {
		this.accessTokenClazz = accessTokenClazz;
	}
	
	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString(2);
	}

	@Override
	public File getTmpDirFile() {
		return this.tmpDirFile;
	}

	public void setTmpDirFile(File tmpDirFile) {
		this.tmpDirFile = tmpDirFile;
	}

	@Override
	public ApacheHttpClientBuilder getApacheHttpClientBuilder() {
		return this.apacheHttpClientBuilder;
	}

	public void setApacheHttpClientBuilder(ApacheHttpClientBuilder apacheHttpClientBuilder) {
		this.apacheHttpClientBuilder = apacheHttpClientBuilder;
	}

	protected String appId;

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Override
	public String getAppId() {
		return appId;
	}

	protected String accessTokenURL;

	public void setAccessTokenURL(String accessTokenURL) {
		this.accessTokenURL = accessTokenURL;
	}

	@Override
	public String getAccessTokenURL() {
		return accessTokenURL;
	}	
	
	private int connectionRequestTimeout = 3000;
	private int connectionTimeout = 5000;
	private int soTimeout = 5000;
	private int idleConnTimeout = 60000;
	private int checkWaitTime = 60000;
	private int maxConnPerHost = 10;
	private int maxTotalConn = 50;

	public void setConnectionRequestTimeout(int connectionRequestTimeout) {
		this.connectionRequestTimeout = connectionRequestTimeout;
	}
	@Override
    public int getConnectionRequestTimeout() {
		return connectionRequestTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	@Override
    public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}
	@Override
    public int getSoTimeout() {
		return soTimeout;
	}

	public void setIdleConnTimeout(int idleConnTimeout) {
		this.idleConnTimeout = idleConnTimeout;
	}
	@Override
    public int getIdleConnTimeout() {
		return idleConnTimeout;
	}

	public void setCheckWaitTime(int checkWaitTime) {
		this.checkWaitTime = checkWaitTime;
	}
	@Override
    public int getCheckWaitTime() {
		return checkWaitTime;
	}

	public void setMaxConnPerHost(int maxConnPerHost) {
		this.maxConnPerHost = maxConnPerHost;
	}
	@Override
    public int getMaxConnPerHost() {
		return maxConnPerHost;
	}

	public void setMaxTotalConn(int maxTotalConn) {
		this.maxTotalConn = maxTotalConn;
	}
	@Override
    public int getMaxTotalConn() {
		return maxTotalConn;
	}

	
	private TicRestMain ticRestMain;

	@Override
    public String getReturnBody() {
		return returnBody;
	}

	public void setReturnBody(String returnBody) {
		this.returnBody = returnBody;
	}

	@Override
	public TicRestMain getTicRestMain() {
		return ticRestMain;
	}

	public void setTicRestMain(TicRestMain ticRestMain) {
		this.ticRestMain = ticRestMain;
	}
	
	
}
