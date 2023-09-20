package com.landray.kmss.third.weixin.util;

import com.landray.kmss.third.weixin.api.WxApiService;
import com.landray.kmss.third.weixin.api.WxApiServiceImpl;

public class WxUtils {
	private static WxApiService wxApiService;

	static {
		wxApiService = new WxApiServiceImpl();
	}

	public static WxApiService getWxApiService() {
		return wxApiService;
	}
}
