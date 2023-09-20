package com.landray.kmss.third.im.kk.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.plugin.core.config.IExtensionPoint;
import com.landray.kmss.framework.service.plugin.Plugin;

/**
 * <P>扩展点util</P>
 * @author 孙佳
 */
public class PluginUtil {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(PluginUtil.class);

	/**
	 * 扩展点
	 */
	private static final String KK_EXTENSION_POINT_ID = "com.landray.kmss.third.im.kk.transfer";

	private static String CONFIG = "config";

	public static String SERVICE = "service";

	public static String METHOD = "method";

	public static String MODELNAME = "modelName";

	public static String KEY = "key";

	private static Map<String, Map<String, String>> thirdkkMap = new HashMap<String, Map<String, String>>();


	public static Map<String, Map<String, String>> getTransferMap() {
		if (thirdkkMap.isEmpty()) {
			getThirdkkMap();
		}
		return thirdkkMap;
	}

	/**
	 * 获取扩展点配置的参数
	 * 
	 * @return
	 */
	private static Map<String, Map<String, String>> getThirdkkMap() {

		if (thirdkkMap.isEmpty()) {
			synchronized (thirdkkMap) {
				if (thirdkkMap.isEmpty()) {
					Map<String, String> map = null;
					IExtensionPoint point = Plugin.getExtensionPoint(KK_EXTENSION_POINT_ID);
					IExtension[] extensions = point.getExtensions();
					for (IExtension extension : extensions) {
						if (CONFIG.equals(extension.getAttribute("name"))) {
							map = new HashMap<String, String>();
							map.put(SERVICE, Plugin.getParamValueString(extension, SERVICE));
							map.put(METHOD, Plugin.getParamValueString(extension, METHOD));
							map.put(MODELNAME, Plugin.getParamValueString(extension, MODELNAME));
							thirdkkMap.put(Plugin.getParamValueString(extension, KEY), map);
						}
					}
				}
			}
		}
		logger.info(thirdkkMap.toString());
		return thirdkkMap;
	}

}
