package com.landray.kmss.tic.rest.client.api;

import com.alibaba.fastjson.JSONObject;

public interface ICookieProvider {
	/**
	 * 获取cookie
	 * 
	 * @return JSON串
	 *         例如：[{"domain":"java.landray.com.cn","name":"route","path":"/","value":"40217fab86629be630f69fd2ef4aab3c"}.{"domain":"java.landray.com.cn","name":"JSESSIONID","path":"/","value":"15A281143604B2D847A0A28B80DF46E4"}]
	 */
	public String getCookies(String restMainId,
							 JSONObject prefixReqResult, String url)
			throws Exception;
}
