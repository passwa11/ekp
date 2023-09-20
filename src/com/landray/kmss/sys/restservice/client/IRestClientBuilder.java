package com.landray.kmss.sys.restservice.client;

import com.landray.kmss.sys.restservice.client.model.RestRequestConfig;

/**
 * {@link IRestClient}工厂，可能返回null
 * @author 陈进科
 * @since 1.0  2019年1月4日
 *
 */
public interface IRestClientBuilder {

    public static final String DEFAULT_BEAN_ID = "defaultRestClientBuilder";
	/**
	 * 获取不带请求配置的客户端
	 * @return
	 */
	IRestClient buildRestClient();

	/**
	 * 获取适用于某一站点的client
	 * @param siteUri  http(s)://hostname:port/，只到port截止，后面的queryInfo无效
	 * @return
	 */
	IRestClient buildRestClient(String siteUri);
	
	/**
	 * 获取附带请求配置的客户端
	 * @param requestConfig
	 * @return
	 */
	IRestClient buildRestClient(RestRequestConfig requestConfig) ;
}
