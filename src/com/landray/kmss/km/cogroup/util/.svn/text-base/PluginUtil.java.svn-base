package com.landray.kmss.km.cogroup.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.plugin.core.config.IExtensionPoint;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.km.cogroup.interfaces.ICogroupConfigService;

/**
 * <P>扩展点util</P>
 * @author 孙佳
 */
public class PluginUtil {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(PluginUtil.class);

	/**
	 * 扩展点
	 */
	private static final String PERSON_EXTENSION_POINT_ID = "com.landray.kmss.km.cogroup";

	private static String CONFIG = "config";

	private static String SERVICE = "service";

	private static Map<String, IExtension> cogroupMap = new HashMap<String, IExtension>();

	private static IExtension getCogroupMap(String modelName) {
		return getCogroupMap().get(modelName);
	}

	public static ICogroupConfigService getConfig(String modelName) {
		return Plugin.getParamValue(getCogroupMap(modelName), SERVICE);
	}

	/**
	 * 获取扩展点模块分类设置
	 * 
	 * @return
	 */
	private static Map<String, IExtension> getCogroupMap() {

		if (cogroupMap.isEmpty()) {
			synchronized (cogroupMap) {
				if (cogroupMap.isEmpty()) {
					IExtensionPoint point = Plugin.getExtensionPoint(PERSON_EXTENSION_POINT_ID);
					IExtension[] extensions = point.getExtensions();
					for (IExtension extension : extensions) {
						if (CONFIG.equals(extension.getAttribute("name"))) {
							cogroupMap.put(extension.getModel(), extension);
						}
					}
				}
			}
		}
		logger.info("",cogroupMap);
		return cogroupMap;
	}

}
