package com.landray.kmss.tic.rest.client.api;

public interface IOAuthAccessToken {
	/**
	 * REST请求时需要开放授权获取访问令牌的方法
	 * @return JSON串，格式中的属性必须是令牌access_tokent和过期时间expires_in为数字型,如{access_token:"",expires_in:720}，
	 * 如果接口不是这种属性名，请在实现中转换
	 */
	public String getAccessToken();
}
