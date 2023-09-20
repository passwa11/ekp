package com.landray.kmss.km.imeeting.util;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.util.StringUtil;

/**
 * 获取默认通知方式
 */
public class DefaultNotify {
	public static String defaultValue = null;
	static {
		IExtension[] extensions = Plugin.getExtensions(
				"com.landray.kmss.sys.notify", "*", "notifyType");
		StringBuffer tempDefaultValue = new StringBuffer();
		for (int i = 0; i < extensions.length; i++) {
			Boolean isDefault = (Boolean) Plugin.getParamValue(extensions[i],
					"isDefault");
			if (isDefault.booleanValue()) {
				String key = (String) Plugin
						.getParamValue(extensions[i], "key");
				tempDefaultValue.append(key).append(";");
			}
		}
		if (StringUtil.isNotNull(tempDefaultValue.toString())) {
            defaultValue = tempDefaultValue.substring(0, tempDefaultValue
                    .length() - 1);
        }
	}
}
