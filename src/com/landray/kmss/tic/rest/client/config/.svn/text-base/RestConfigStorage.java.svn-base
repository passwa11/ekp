package com.landray.kmss.tic.rest.client.config;

import java.io.File;

import com.landray.kmss.tic.rest.client.http.apache.ApacheHttpClientBuilder;
import com.landray.kmss.tic.rest.connector.model.TicRestMain;

/**
 * 客户端配置存储
 *
 */
public interface RestConfigStorage {
	String getAppId();
	
	String getAccessTokenURL();

	String getAccessToken();

	boolean isAccessTokenExpired();

	/**
	 * 强制将access token过期掉
	 */
	void expireAccessToken();

	void updateAccessToken(RestAccessToken accessToken);

	void updateAccessToken(String accessToken, int expiresIn);

	String getCorpId();

	String getCorpSecret();

	String getAgentId();

	String getToken();

	String getAesKey();

	long getExpiresTime();

	String getHttpProxyHost();

	int getHttpProxyPort();

	String getHttpProxyUsername();

	String getHttpProxyPassword();

	String getHttpheader();
	
	String getAccessTokenClazz();
	
	TicRestMain getTicRestMain();

	File getTmpDirFile();

	/**
	 * http client builder
	 *
	 * @return ApacheHttpClientBuilder
	 */
	ApacheHttpClientBuilder getApacheHttpClientBuilder();

	public int getConnectionRequestTimeout();

	public int getConnectionTimeout() ;

	public int getSoTimeout();
	
	public int getIdleConnTimeout() ;

	public int getCheckWaitTime();

	public int getMaxConnPerHost() ;

	public int getMaxTotalConn() ;
	
	public String getReturnBody();
}
