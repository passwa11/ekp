package com.landray.kmss.km.calendar.cms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.landray.kmss.framework.plugin.core.config.IConfigurationElement;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.framework.service.plugin.util.ConfigUtil;
import com.landray.kmss.km.calendar.cms.interfaces.ICMSProvider;
import com.landray.kmss.km.calendar.cms.interfaces.IOAuthProvider;

public class CMSPlugin {
	private static final String ID = "com.landray.kmss.km.calendar.cms";

	private static List<CMSPluginData> extensionList = null;

	
	public static List<CMSPluginData> getExtensionList() {
		if (extensionList == null) {
			IExtension[] extensions = Plugin.getExtensions(ID, "*","cms");
			extensionList = new ArrayList<CMSPluginData>();
			IConfigurationElement element = null;
			for (IExtension extension : extensions) {
				CMSPluginData data = new CMSPluginData();
				data.setAppKey(Plugin.getParamValueString(extension, "key"));
				data.setCmsProvider((ICMSProvider) Plugin.getParamValue(
						extension, "cmsService"));
				data.setOAuthProvider((IOAuthProvider) Plugin.getParamValue(
						extension, "OAuthService"));
				data.setOrder(Integer.parseInt(Plugin.getParamValueString(
						extension, "order")));
				data.setName(Plugin.getParamValueString(extension, "name"));
				
				element = ConfigUtil.getConfigurationElement(
					      extension, "name");
				data.setOriginName(element.getAttribute("value"));
				data.setBindPageUrl(Plugin.getParamValueString(extension,
						"bindPageUrl"));
				data.setSyncNow((Boolean) Plugin.getParamValue(extension, "syncNow"));
				extensionList.add(data);
			}
		}
		return extensionList;
	}

	public static CMSPluginData getCMSPluginData(String appKey) {
		if (extensionList == null) {
			getExtensionList();
		}
		for (CMSPluginData data : extensionList) {
			if (appKey.equals(data.getAppKey())) {
				return data;
			}
		}
		return null;

	}
}
