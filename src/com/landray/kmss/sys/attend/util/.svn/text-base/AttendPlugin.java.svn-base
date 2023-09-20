package com.landray.kmss.sys.attend.util;

import java.util.HashMap;
import java.util.Map;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;

public class AttendPlugin {

	private static final String ATTEND_POINT = "com.landray.kmss.sys.attend";

	private static final String ATTEND_ITEM = "extendItem";

	private static IExtension[] extensions = null;

	private static Map<String, IExtension> extensionMap = null;

	private static Map<String, IExtension> extensionKeyMap = null;

	public static IExtension[] getExtensions() {
		if (extensions == null) {
			extensions = Plugin.getExtensions(ATTEND_POINT, "*", ATTEND_ITEM);
		}
		return extensions;
	}

	/**
	 * modelName -> extension
	 * 
	 * eg. "com.landray.kmss.km.imeeting.model.KmImeetingMain" -> extension
	 * 
	 * @return
	 */
	public static Map<String, IExtension> getExtensionMap() {
		if (extensionMap == null) {
			extensionMap = new HashMap<String, IExtension>();
			IExtension[] extensions = getExtensions();
			for (IExtension extension : extensions) {
				String appId = Plugin.getParamValueString(extension, "modelName");
				extensionMap.put(appId, extension);
			}
		}
		return extensionMap;
	}

	/**
	 * modelKey -> extension
	 * 
	 * eg. "kmImeeting" -> extension
	 * 
	 * @return
	 */
	public static Map<String, IExtension> getExtensionKeyMap() {
		if (extensionKeyMap == null) {
			extensionKeyMap = new HashMap<String, IExtension>();
			IExtension[] extensions = getExtensions();
			for (IExtension extension : extensions) {
				String key = Plugin.getParamValueString(extension, "modelKey");
				extensionKeyMap.put(key, extension);
			}
		}
		return extensionKeyMap;
	}

	public static IExtension getExtension(String modelName) {
		extensionMap = getExtensionMap();
		if (extensionMap != null && extensionMap.get(modelName) != null) {
			return extensionMap.get(modelName);
		}
		return null;
	}

	public static IExtension getExtensionByModelKey(String modelKey) {
		extensionKeyMap = getExtensionKeyMap();
		if (extensionKeyMap != null && extensionKeyMap.get(modelKey) != null) {
			return extensionKeyMap.get(modelKey);
		}
		return null;
	}

}
