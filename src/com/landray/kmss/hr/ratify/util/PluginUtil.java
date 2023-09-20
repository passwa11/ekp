package com.landray.kmss.hr.ratify.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.plugin.core.config.IExtensionPoint;
import com.landray.kmss.framework.service.plugin.Plugin;

/**
 * 扩展工具类
 * 
 * @author 朱湖强
 * @version 1.0 2013-08-08
 */
public abstract class PluginUtil {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PluginUtil.class);

	public static final String EXTENSION_TEMPLATE_POINT_ID = "com.landray.kmss.hr.ratify.template";

	private static final String ITEM = "template";

	public static final String PARAM_KEY = "fdkey";

	public static final String PARAM_NAME = "name";
	public static final String PARAM_MODELNAME = "modelName";
	private static Map<String, List<Map<String, String>>> configs = new HashMap<String, List<Map<String, String>>>();
	/**
	 * 子表单模板扩展点
	 * 
	 * @return
	 */
	public static Map<String, List<Map<String, String>>> getConfigs(
			String extensionPointId) {
		if (null == configs.get(extensionPointId)
				|| configs.get(extensionPointId).isEmpty()) {
			synchronized (configs) {
				if (null == configs.get(extensionPointId)
						|| configs.get(extensionPointId).isEmpty()) {
					IExtensionPoint point = Plugin
							.getExtensionPoint(extensionPointId);
					IExtension[] extensions = point.getExtensions();
					List<Map<String, String>> pointConfigs = new ArrayList<Map<String, String>>();
					for (IExtension extension : extensions) {
						if (ITEM.equals(extension.getAttribute("name"))) {
							Map<String, String> params = new HashMap<String, String>();
							params.put(PARAM_KEY, Plugin.getParamValueString(
									extension, PARAM_KEY));
							params.put(PARAM_NAME, Plugin.getParamValueString(
									extension, PARAM_NAME));
							params.put(PARAM_MODELNAME, Plugin.getParamValueString(
									extension, PARAM_MODELNAME));
							pointConfigs.add(params);
						}
					}
					configs.put(extensionPointId, pointConfigs);
				}
			}
		}
		return configs;
	}
	/***
	 * 获取扩展点fdkey和name集合
	 * 
	 * @param configs
	 * @param extensionPointId
	 * @return
	 */
	public static List<Map<String, String>> getConfig(String extensionPointId) {
		List<Map<String, String>> configMaps = new ArrayList<Map<String, String>>();
		if (null == configs.get(extensionPointId)
				|| configs.get(extensionPointId).isEmpty()) {
			configs = getConfigs(extensionPointId);
		}
		for (Map<String, String> configMap : configs.get(extensionPointId)) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", configMap.get(PARAM_KEY));
			map.put("text", configMap.get(PARAM_NAME));
			map.put("isAutoFetch", "0");
			configMaps.add(map);
		}
		return configMaps;
	}
	
	/****
	 * 根据扩展属性名、modelName得到对应的属性值
	 * 
	 * @param extensionPointId
	 * @param uuid
	 * @param configName
	 * @return
	 */
	public static String getConfigByModelName(String extensionPointId, String fdKey,
			String configName) {
		if (null == configs.get(extensionPointId)
				|| configs.get(extensionPointId).isEmpty()) {
			configs = getConfigs(extensionPointId);
		}
		for (Map<String, String> configMap : configs.get(extensionPointId)) {
			if (configMap.get(PARAM_KEY).equals(fdKey)) {
				return configMap.get(configName);
			}

		}
		return null;
	}
	
	
}
