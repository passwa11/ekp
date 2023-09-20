package com.landray.kmss.tic.rest.client.http;

import java.io.IOException;
import java.net.URISyntaxException;

import com.landray.kmss.tic.rest.client.error.RestErrorException;

/**
 * http请求执行器
 *
 * @param <T>
 *            返回值类型
 * @param <E>
 *            请求参数类型
 */
public interface RequestExecutor<T, E> {

	/**
	 * @param uri
	 *            uri
	 * @param data
	 *            数据
	 * @throws URISyntaxException
	 */
	T execute(String uri, E data)
			throws RestErrorException, IOException, URISyntaxException;
}
