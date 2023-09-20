package com.landray.kmss.sys.attachment.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.attachment.model.SysAttMainHistoryConfig;
import com.landray.sso.client.oracle.StringUtil;

public class HistoryVersionPlugin {
	private static final String ID = "com.landray.kmss.sys.attachment.history";

	// private static List<SoftDeletePluginData> extensionList = null;
	private static Map<String, List<HistoryVersionPluginData>> moduleMap = null;

	private static Map<String, HistoryVersionPluginData> extensionMap = null;

	private static Map<String, HistoryVersionPluginData> enabledModels = null;

	private static synchronized void buildExtensionMap() {
		IExtension[] extensions = Plugin.getExtensions(ID, "*",
				"historyVersion");
		extensionMap = new HashMap<String, HistoryVersionPluginData>();
		for (IExtension extension : extensions) {
			HistoryVersionPluginData data = new HistoryVersionPluginData();

			data.setModelClassName(
					Plugin.getParamValueString(extension, "modelClassName"));
			data.setModelName(
					Plugin.getParamValueString(extension, "modelName"));
			data.setModuleName(
					Plugin.getParamValueString(extension, "moduleName"));

			extensionMap.put(data.getModelClassName(), data);
		}
	}

	private static synchronized void buildModuleMap() {
		moduleMap = new HashMap<String, List<HistoryVersionPluginData>>();
		Map<String, HistoryVersionPluginData> map = getExtensionMap();
		for (String key : map.keySet()) {
			HistoryVersionPluginData data = map.get(key);
			String moduleName = data.getModuleName();
			if (StringUtil.isNull(moduleName)) {
				moduleName = data.getModelName();
			}
			List<HistoryVersionPluginData> list = moduleMap.get(moduleName);
			if (list == null) {
				list = new ArrayList<HistoryVersionPluginData>();
				moduleMap.put(moduleName, list);
			}
			list.add(data);
		}
	}

	private static synchronized void buildEnabledModelsMap(String modulesStr) {
		enabledModels = new HashMap<String, HistoryVersionPluginData>();
		if (modulesStr == null) {
			SysAttMainHistoryConfig config = null;
			try {
				config = new SysAttMainHistoryConfig();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//SysAttMainHistoryConfig.logger.error(e);
				return;
			}
			modulesStr = config.getAttHistoryConfigEnableModules();
			if (StringUtil.isNull(modulesStr)) {
				return;
			}
		}
		String[] modules = modulesStr.split(";");
		Map<String, List<HistoryVersionPluginData>> moduleMap = getModuleMap();
		for (String module : modules) {
			List<HistoryVersionPluginData> list = moduleMap.get(module);
			if (list != null) {
				for (HistoryVersionPluginData data : list) {
					enabledModels.put(data.getModelClassName(), data);
				}
			}
		}
	}

	public static void resetEnabledModelsMap(String modulesStr) {
		buildEnabledModelsMap(modulesStr);
	}

	public static Map<String, HistoryVersionPluginData> getExtensionMap() {
		if (extensionMap == null) {
			buildExtensionMap();
		}
		return extensionMap;
	}

	public static Map<String, List<HistoryVersionPluginData>> getModuleMap() {
		if (moduleMap == null) {
			buildModuleMap();
		}
		return moduleMap;
	}

	public static Map<String, HistoryVersionPluginData> getEnabledModules() {
		if (enabledModels == null) {
			buildEnabledModelsMap(null);
		}
		return enabledModels;
	}

	public static HistoryVersionPluginData
			getHistoryPluginData(String modelClassName) {
		if (extensionMap == null) {
			getExtensionMap();
		}
		return extensionMap.get(modelClassName);
	}

	public static boolean isEnableHistory(String modelClassName) {
		return getEnabledModules().containsKey(modelClassName);
	}
}
