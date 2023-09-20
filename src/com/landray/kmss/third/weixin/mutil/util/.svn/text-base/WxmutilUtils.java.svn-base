package com.landray.kmss.third.weixin.mutil.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.landray.kmss.third.weixin.mutil.api.WxmutilApiService;
import com.landray.kmss.third.weixin.mutil.api.WxmutilApiServiceImpl;
import com.landray.kmss.third.weixin.mutil.constant.WxmutilConstant;
import com.landray.kmss.third.weixin.mutil.model.WeixinMutilConfig;
import com.landray.kmss.third.weixin.mutil.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.mutil.service.IThirdWeixinWorkService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class WxmutilUtils {
	public static Map<String, WxmutilApiService> wxServiceList;
	public static IThirdWeixinWorkService thirdMutilWeixinWorkService;

	static {
		wxServiceList = new HashMap<String, WxmutilApiService>();
		resetWxworkConfig();
		thirdMutilWeixinWorkService = (IThirdWeixinWorkService) SpringBeanUtil
				.getBean("thirdMutilWeixinWorkService");
	}

	public static Map<String, WxmutilApiService> getWxmutilApiServiceList() {
		return wxServiceList;
	}

	/**
	 * 多企业配置列表
	 */
	public static void resetWxworkConfig() {
		Map<String, Map<String, String>> wxWorkConfig = WeixinMutilConfig
				.getWxConfigDataMap();
		for (Entry<String, Map<String, String>> entry : wxWorkConfig
				.entrySet()) {
			String wxkey = entry.getKey();
			WeixinMutilConfig config = WeixinMutilConfig.newInstance(wxkey);
			WxmutilApiService wxworkApiService = new WxmutilApiServiceImpl(
					config);
			wxServiceList.put(wxkey, wxworkApiService);
		}
	}

	public static String getWxworkApiUrl(String key) {
		String apiUrl = WxmutilConstant.WXWORK_PREFIX;
		String prefix_url = WeixinWorkConfig.newInstance(key).getWxApiUrl();
		if (StringUtil.isNotNull(prefix_url)) {
			// logger.debug("云端企业微信的接口地址取自私有化地址："+prefix_url);
			apiUrl = prefix_url;
		}
		return apiUrl;
	}
}
