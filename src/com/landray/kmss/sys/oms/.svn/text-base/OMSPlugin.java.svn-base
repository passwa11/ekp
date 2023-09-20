package com.landray.kmss.sys.oms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.oms.in.interfaces.OMSSynchroInProvider;
import com.landray.kmss.sys.oms.out.interfaces.IOMSSynchroOutProvider;

public class OMSPlugin {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(OMSPlugin.class);
	
	private static final String ID = "com.landray.kmss.sys.oms";

	private static final String IN_ITEM_NAME = "in";

	private static final String OUT_ITEM_NAME = "out";
	
	private static final String ECO_ITEM_NAME = "eco";

	private static Map<String, OMSSynchroInProvider> inExtensionMap = null;

	private static Map<String, String> inExtensionTitleMap = null;

	private static Map<String, IOMSSynchroOutProvider> outExtensionMap = null;
	
	private static OMSSynchroInProvider ecoProvider = null;

	public static Map<String, OMSSynchroInProvider> getInExtensionMap() {
		if (inExtensionMap == null) {
			IExtension[] extensions = Plugin.getExtensions(ID, "*",
					IN_ITEM_NAME);
			inExtensionMap = new HashMap<String, OMSSynchroInProvider>();
			for (IExtension extension : extensions) {
				String key = Plugin.getParamValueString(extension, "name");
				OMSSynchroInProvider value = (OMSSynchroInProvider) Plugin
						.getParamValue(extension, "service");
				inExtensionMap.put(key, value);
			}
		}
		return inExtensionMap;
	}

	public static Map<String, String> getInExtensionTitleMap() {
		if (inExtensionTitleMap == null) {
			IExtension[] extensions = Plugin.getExtensions(ID, "*",
					IN_ITEM_NAME);
			inExtensionTitleMap = new HashMap<String, String>();
			for (IExtension extension : extensions) {
				String key = Plugin.getParamValueString(extension, "name");
				String title = Plugin.getParamValueString(extension, "title");
				inExtensionTitleMap.put(key, title);
			}
		}
		return inExtensionTitleMap;
	}

	public static Map<String, IOMSSynchroOutProvider> getOutExtensionMap() {
		if (outExtensionMap == null) {
			IExtension[] extensions = Plugin.getExtensions(ID, "*",
					OUT_ITEM_NAME);
			outExtensionMap = new HashMap<String, IOMSSynchroOutProvider>();
			for (IExtension extension : extensions) {
				String key = Plugin.getParamValueString(extension, "name");
				IOMSSynchroOutProvider value = (IOMSSynchroOutProvider) Plugin
						.getParamValue(extension, "service");
				outExtensionMap.put(key, value);
			}
		}
		return outExtensionMap;
	}

	public static Set<String> getOutTypes() {
		Set<String> set = new HashSet<String>();
		Map<String, IOMSSynchroOutProvider> map = getOutExtensionMap();
		for(String key:map.keySet()){
			IOMSSynchroOutProvider provider = map.get(key);
			try {
				if(provider.isSynchroOutEnable()){
					set.add(key);
				}
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
				logger.error(e.toString());
			}
		}
		return set;
	}

	/**
	 * 判断是否有其它的应用启用了OMS接入
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static String getOtherOmsInEnabledKey(String name) throws Exception {
		Map<String, OMSSynchroInProvider> map = OMSPlugin.getInExtensionMap();
		for (String key : map.keySet()) {
			OMSSynchroInProvider provider = map.get(key);
			if (provider.isSynchroInEnable()) {
				if(!key.equals(name)){
					return key;
				}
			}
		}
		return null;
	}
	
	public static OMSSynchroInProvider getEcoSynchroExtension() {
		if (ecoProvider == null) { //
			IExtension[] extensions = Plugin.getExtensions(ID, "*",
					ECO_ITEM_NAME);
			if (extensions != null && extensions.length >0) {
				ecoProvider = (OMSSynchroInProvider) Plugin
						.getParamValue(extensions[0], "service");
			}
		}
		return ecoProvider;
	}
}
