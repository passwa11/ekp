package com.landray.kmss.tic.rest.client.http;

import com.landray.kmss.tic.rest.client.http.apache.DefaultApacheHttpClientBuilder;

public interface RequestHttp<H, P, R> {

	/**
	 * 返回httpClient
	 *
	 */
	H getRequestHttpClient();

	/**
	 * 返回httpProxy
	 *
	 */
	P getRequestHttpProxy();

	R getErrorKeys();
	
	H getNewRequestHttpClient(DefaultApacheHttpClientBuilder defaultHttp);

}
