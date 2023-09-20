package com.landray.kmss.sys.filestore.location.util;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.plugin.core.config.IExtensionPoint;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.attachment.model.SysAttBase;
import com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationDirectService;
import com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationProxyService;
import com.landray.kmss.sys.filestore.location.model.SysFileLocation;
import com.landray.kmss.sys.filestore.location.service.SysFileLocationDirectService;
import com.landray.kmss.sys.filestore.location.service.SysFileLocationProxyService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.lang3.StringUtils;

public class SysFileLocationUtil {

	private static final String EXTENSION_POINT_ID =
			"com.landray.kmss.sys.filestore.location";

	private static final String ITEM_NAME = "location";

	private static final String TITLE = "title";
	private static final String KEY = "key";
	private static final String DIRECT_SERVICE = "directService";
	private static final String PROXY_SERVICE = "proxyService";
	private static final String CONFIG_JSP_URL = "configJspUrl";
	private static final String ORDER = "order";

	private static List<SysFileLocation> locations = new ArrayList<>();

	/**
	 * 获取所有存储扩展
	 * 
	 * @return
	 */
	public static List<SysFileLocation> getLocations() {

		if (locations.isEmpty()) {
			synchronized (locations) {
				if (locations.isEmpty()) {
					IExtensionPoint point =
							Plugin.getExtensionPoint(EXTENSION_POINT_ID);
					IExtension[] extensions = point.getExtensions();
					SysFileLocation location;
					for (IExtension extension : extensions) {
						if (ITEM_NAME.equals(extension.getAttribute("name"))) {

							location = new SysFileLocation();
							location.setConfigJspUrl(Plugin.getParamValueString(
									extension, CONFIG_JSP_URL));
							location.setTitle(ResourceUtil.getString(Plugin
									.getParamValueString(extension, TITLE)));
							location.setKey(
									Plugin.getParamValueString(extension, KEY));
							location.setOrder(Integer.valueOf(Plugin
									.getParamValueString(extension, ORDER)));

							location.setDirectService(
									(ISysFileLocationDirectService) SpringBeanUtil
											.getBean(Plugin.getParamValueString(
													extension,
													DIRECT_SERVICE)));
							location.setProxyService(
									(ISysFileLocationProxyService) SpringBeanUtil
											.getBean(Plugin.getParamValueString(
													extension, PROXY_SERVICE)));

							locations.add(location);

						}
					}
				}
			}

		}

		return locations;

	}

	/**
	 * 根据key值获取指定的存储扩展
	 * 
	 * @param key
	 * @return
	 */
	private static SysFileLocation getLocationByKey(String key) {
		key = getKey(key);
		List<SysFileLocation> locations = getLocations();

		for (SysFileLocation location : locations) {
			if (location.getKey().equals(key)) {
				return location;
			}
		}

		return null;
	}

	/**
	 * 获取当前启用存储扩展
	 * 
	 * @return
	 */
	public static SysFileLocation getLocation(String key) {
		return getLocationByKey(getKey(key));
	}

	/**
	 * 获取附件存储键值
	 * 
	 * @return
	 */
	public static String getKey(String key) {
		return getKey(key, null);
	}
	
	public static String getKey(String key, String kmssConfig) {
		if (!StringUtil.isNull(key)) {
			return key;
		}
		if (StringUtils.isEmpty(kmssConfig)) {
			kmssConfig = "sys.att.location";
		}
		key = ResourceUtil.getKmssConfigString(kmssConfig);
		if (StringUtil.isNull(key)) {
			key = SysAttBase.ATTACHMENT_LOCATION_SERVER;
		}
		return key;
	}
	
	/**
	 * 获取其它文件存储键值
	 * @return
	 */
	public static String getOtherFileKey(){
		return getKey(null, "sys.other.location");
	}
	
	/**
	 * 获取其它文件存储服务
	 * @return
	 */
	public static ISysFileLocationProxyService getFileService(){
		String key = getOtherFileKey();
		return getProxyService(key);
	}

	/**
	 * 获取直连相关配置，一般用于写文件，读文件可以用getDirectService(location)
	 * 
	 * @return
	 */
	public static ISysFileLocationDirectService getDirectService() {
		return new SysFileLocationDirectService(
				getLocation(null).getDirectService());
	};

	/**
	 * 获取流代理，一般用于写文件，读文件可以用getProxyService(location)
	 * 
	 * @return
	 */
	public static ISysFileLocationProxyService getProxyService() {
		return new SysFileLocationProxyService(
				getLocationByKey(null).getProxyService());
	}

	/**
	 * 根据key(location)获取对应直连相关配置，一般用于读文件
	 * 
	 * @param location
	 * @return
	 */
	public static ISysFileLocationDirectService
			getDirectService(String location) {
		return new SysFileLocationDirectService(
				getLocationByKey(location).getDirectService());
	}

	/**
	 * 根据key(location)获取对应流代理，一般用于读文件
	 * 
	 * @param location
	 * @return
	 */
	public static ISysFileLocationProxyService
			getProxyService(String location) {
		return new SysFileLocationProxyService(
				getLocationByKey(location).getProxyService());
	}

}
