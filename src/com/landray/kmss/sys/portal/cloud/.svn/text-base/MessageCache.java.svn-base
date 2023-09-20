package com.landray.kmss.sys.portal.cloud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.portal.cloud.util.CloudPortalUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class MessageCache {
	/**
	 * "zh-CN" => "xxx.msg" => "信息"
	 */
	private static ThreadLocal<Map<String, Map<String, String>>> messageMapHolder = new ThreadLocal<>();

	public static final String OPERATION = "operation";

	private static String TEMP = new String();

	private static Map<String, Map<String, String>>
			initMessageMap() {
		Map<String, Map<String, String>> messageMap = new HashMap<>(32);
		if (SysLangUtil.isLangEnabled()) {
			ArrayList<String> langs = SysLangUtil.getSupportedLangList();
			for (String lang : langs) {
				messageMap.put(lang,
						new HashMap<String, String>());
			}
		} else {
			messageMap.put(CloudPortalUtil.getOfficialLang(),
					new HashMap<String, String>());
		}
		// 操作operation单独处理
		messageMap.put(OPERATION, new HashMap<String, String>());
		messageMapHolder.set(messageMap);
		return messageMap;
	}

	/**
	 * 
	 * @param messageKey
	 *            sys-portal:msg
	 */
	public static void saveToLocal(String messageKey, boolean isOperation) {
		Map<String, Map<String, String>> messageMap = messageMapHolder
				.get();
		if (messageMap == null) {
			messageMap = initMessageMap();
		}
		// 去掉“:”号前面的
		String newMessageKey = messageKey;
		if (messageKey.contains(":")) {
			newMessageKey = messageKey.substring(messageKey.indexOf(":") + 1);
		}
		// 开启了多语言
		if (SysLangUtil.isLangEnabled()) {
			ArrayList<String> langs = SysLangUtil.getSupportedLangList();
			for (String lang : langs) {
				Locale locale = ResourceUtil.getLocale(lang);
				String msgValue = ResourceUtil.getStringValue(messageKey, null,
						locale);
				// 取到值了才放进去
				if (StringUtil.isNotNull(msgValue)) {
					messageMap.get(lang).put(newMessageKey,
							ResourceUtil.getStringValue(messageKey, null,
									locale));
				}
			}
		} else {
			// 官方语言
			messageMap.get(CloudPortalUtil.getOfficialLang()).put(
					newMessageKey,
					ResourceUtil.getOfficialString(messageKey));
		}
		if (isOperation) {
			messageMap.get(OPERATION).put(newMessageKey, TEMP);
		}
	}

	public static void clear() {
		messageMapHolder.remove();
	}

	public static Map<String, Map<String, String>> get() {
		return messageMapHolder.get();
	}
}
