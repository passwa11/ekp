package com.landray.kmss.third.ding.util;

import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.oms.DingApiServiceImpl;
import com.landray.kmss.third.ding.oms.DingApiV2Service;
import com.landray.kmss.third.ding.oms.DingApiV2ServiceImpl;


public class DingUtils {

	public static DingApiService dingApiService;

	public static DingApiV2Service dingApiV2Service;

	static {
		dingApiService = new DingApiServiceImpl();
		dingApiV2Service = new DingApiV2ServiceImpl();
	}

	public static DingApiService getDingApiService() {
		return dingApiService;
	}

	public static DingApiV2Service getDingApiV2Service() {
		return dingApiV2Service;
	}
}
