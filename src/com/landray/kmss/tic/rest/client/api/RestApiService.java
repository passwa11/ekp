package com.landray.kmss.tic.rest.client.api;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.tic.core.log.model.TicCoreLogMain;
import com.landray.kmss.tic.rest.client.config.RestConfigStorage;
import com.landray.kmss.tic.rest.client.error.RestErrorException;
import com.landray.kmss.tic.rest.client.http.MediaUploadRequestExecutor;
import com.landray.kmss.tic.rest.client.http.RequestExecutor;
import com.landray.kmss.tic.rest.client.http.RequestHttp;
import com.landray.kmss.tic.rest.connector.model.TicRestCookieSetting;
import com.landray.kmss.tic.rest.connector.model.TicRestPrefixReqSetting;


/**
 * API的Service
 */
public interface RestApiService {
	
	/**
	 * 获取access_token, 不强制刷新access_token
	 *
	 * @see #getAccessToken(boolean)
	 */
	String getAccessToken() throws RestErrorException;

	/**
	 * <pre>
	 * 获取access_token，本方法线程安全
	 * 且在多线程同时刷新时只刷新一次，避免超出2000次/日的调用次数上限
	 * 另：本service的所有方法都会在access_token过期是调用此方法
	 * </pre>
	 *
	 * @param forceRefresh
	 *            强制刷新
	 */
	String getAccessToken(boolean forceRefresh) throws RestErrorException;

	/**
	 * <pre>
	 * 设置当Rest提供端系统响应系统繁忙时，要等待多少 retrySleepMillis(ms) * 2^(重试次数 - 1) 再发起重试
	 * 默认：1000ms
	 * </pre>
	 *
	 * @param retrySleepMillis
	 *            重试休息时间
	 */
	void setRetrySleepMillis(int retrySleepMillis);

	/**
	 * <pre>
	 * 设置当Rest提供端系统响应系统繁忙时，最大重试次数
	 * 默认：5次
	 * </pre>
	 *
	 * @param maxRetryTimes
	 *            最大重试次数
	 */
	void setMaxRetryTimes(int maxRetryTimes);

	/**
	 * 当本Service没有实现某个API的时候，可以用这个，针对所有Rest提供端API中的GET请求
	 *
	 * @param url
	 *            接口地址
	 * @param queryParam
	 *            请求参数
	 */
	String get(String url, String queryParam, TicCoreLogMain ticCoreLogMain)
			throws RestErrorException;

	/**
	 * 当本Service没有实现某个API的时候，可以用这个，针对所有Rest提供端API中的POST请求
	 *
	 * @param url
	 *            接口地址
	 * @param postData
	 *            请求body字符串
	 */
	String post(String url, String postData, TicCoreLogMain ticCoreLogMain)
			throws RestErrorException;

	/**
	 * <pre>
	 * Service没有实现某个API的时候，可以用这个，
	 * 比{@link #get}和{@link #post}方法更灵活，可以自己构造RequestExecutor用来处理不同的参数和不同的返回类型。
	 * 可以参考，{@link MediaUploadRequestExecutor}的实现方法
	 * </pre>
	 *
	 * @param executor
	 *            执行器
	 * @param uri
	 *            请求地址
	 * @param data
	 *            参数
	 * @param <T>
	 *            请求值类型
	 * @param <E>
	 *            返回值类型
	 */
	<T, E> T execute(RequestExecutor<T, E> executor, String uri, E data,
			TicCoreLogMain ticCoreLogMain) throws RestErrorException;

	/**
	 * 初始化http请求对象
	 */
	public void initHttp(RestConfigStorage restConfigStorage);

	/**
	 * 获取RestConfigStorage 对象
	 *
	 * @return RestConfigStorage
	 */
	Map<String, RestConfigStorage> getRestConfigStorages();

	RestConfigStorage addRestConfigStorage(RestConfigStorage restConfigStorage);
	
	/**
	 * http请求对象
	 */
	RequestHttp getRequestHttp();

	public String getCookies(JSONObject prefixReqResult, String url)
			throws Exception;

	String getCookiesTest(TicRestCookieSetting cookieSetting) throws Exception;

	String getPrefixReqTest(TicRestPrefixReqSetting prefixReqSetting) throws Exception;

	void close() throws Exception;
}
