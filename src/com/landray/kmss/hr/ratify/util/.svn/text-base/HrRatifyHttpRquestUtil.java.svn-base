package com.landray.kmss.hr.ratify.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.landray.kmss.framework.service.plugin.Plugin;

public class HrRatifyHttpRquestUtil {

	public static HttpServletRequest getRequest() {
		HttpServletRequest request = Plugin.currentRequest();
		return request;

	}

	public static HttpSession getSession() {
		HttpSession session = getRequest().getSession();
		return session;
	}

}
