package com.landray.kmss.sys.attachment.plugin.customPage.service;

import javax.servlet.http.HttpServletRequest;

public interface ISysAttCustomPageService {

	/**
	 * 获取需要自定义页面跳转的url
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getCustomPageUrl(HttpServletRequest request) throws Exception;

}
