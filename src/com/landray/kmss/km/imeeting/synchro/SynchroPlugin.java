package com.landray.kmss.km.imeeting.synchro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.km.imeeting.synchro.interfaces.IMeetingSynchroProvider;

/**
 * 同步拓展点
 */
public class SynchroPlugin {

	private static final String ID = "com.landray.kmss.km.imeeting.synchro";

	private static List<ImeetingSynchroPluginData> extensionList = null;

	// @SuppressWarnings("unchecked")
	// public static List<IExtension> getExtensionList() {
	// if (extensionList == null) {
	// IExtension[] extensions = Plugin.getExtensions(ID, "*");
	// extensionList = ArrayUtil.convertArrayToList(extensions);
	// }
	// return extensionList;
	// }

	public static boolean isExchangeMeetingSynchroEnable() {
		List<ImeetingSynchroPluginData> extensionList = getExtensionList();
		for (ImeetingSynchroPluginData data : extensionList) {
			String key = data.getKey();
			if (!"exchangeMeeting".equals(key)) {
				continue;
			}
			IMeetingSynchroProvider provier = data.getProvider();
			try {
				if (provier.isSynchroEnable()) {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static List<ImeetingSynchroPluginData> getExtensionList() {
		if (extensionList == null) {
			IExtension[] extensions = Plugin.getExtensions(ID, "*");
			extensionList = new ArrayList<ImeetingSynchroPluginData>();
			for (IExtension extension : extensions) {
				ImeetingSynchroPluginData data = new ImeetingSynchroPluginData();
				data.setKey(Plugin.getParamValueString(extension, "key"));
				data.setProvider((IMeetingSynchroProvider) Plugin.getParamValue(
						extension, "provider"));
				data.setOrder(Integer.parseInt(Plugin.getParamValueString(
						extension, "order")));
				data.setName(Plugin.getParamValueString(extension, "name"));
				data.setBindPageUrl(Plugin.getParamValueString(extension,
						"bindPageUrl"));
				extensionList.add(data);
			}
		}
		Collections.sort(extensionList);
		return extensionList;
	}

	public static ImeetingSynchroPluginData getPluginData(String appKey) {
		if (extensionList == null) {
			getExtensionList();
		}
		for (ImeetingSynchroPluginData data : extensionList) {
			if (appKey.equals(data.getKey())) {
				return data;
			}
		}
		return null;
	}

}
