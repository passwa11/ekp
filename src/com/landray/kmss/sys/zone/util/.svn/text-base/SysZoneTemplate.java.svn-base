package com.landray.kmss.sys.zone.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.plugin.core.config.IExtensionPoint;
import com.landray.kmss.framework.service.plugin.Plugin;

public class SysZoneTemplate {
	private static final List<Map<String, String>> pcTemplates = new ArrayList<Map<String, String>>();
	private static final List<Map<String, String>> mobileTemplates = new ArrayList<Map<String, String>>();

	private static final String FORWARD_PATH = "forwardPath";
	private static final String SHOWTYPE = "showType";
	private static final String ICON_PATH = "iconPath";
	private static final String PAGE_TEMPLATE_POINT_ID = "com.landray.kmss.sys.zone.layout";
	private static final Map<String, String> pageTemplateJsp = new HashMap<String, String>();
	private static boolean isInitTemplate = false;

	/**
	 * 获取页面布局的扩展信息
	 * 
	 * @param type
	 * @return
	 */
	public static List<Map<String, String>> getTemplatesByType(String type) {
		List<Map<String, String>> list = null;
		if (SysZoneConfigUtil.TYPE_PC_KEY.equals(type)) {
			list = pcTemplates;
		} else {
			list = mobileTemplates;
		}
		if (!isInitTemplate) {
			initPageTemplatesMap();
		}
		return list;
	}

	/**
	 * 初始加载页面布局的扩展信息
	 */
	private static synchronized void initPageTemplatesMap() {
		if (isInitTemplate) {
			return;
		}
		IExtensionPoint point = Plugin.getExtensionPoint(PAGE_TEMPLATE_POINT_ID);
		IExtension[] extensions = point.getExtensions();
		for (IExtension extension : extensions) {
			String showType = Plugin.getParamValueString(extension, SHOWTYPE);
			Map<String, String> params = new HashMap<String, String>();
			params.put(FORWARD_PATH,
					Plugin.getParamValueString(extension, FORWARD_PATH));
			params.put(ICON_PATH,
					Plugin.getParamValueString(extension, ICON_PATH));
			if (SysZoneConfigUtil.TYPE_PC_KEY.equals(showType)) {
				pcTemplates.add(params);
			} else {
				mobileTemplates.add(params);
			}
		}
		isInitTemplate = true;
	}

	/**
	 * 获取后台配置选中的页面布局模板
	 * 
	 * @param showType
	 * @return
	 */
	public static String getPageTemplateJsp(String showType) {
		return pageTemplateJsp.get(showType);
	}

	public static void setPageTemplateJsp(String showType, String path) {
		pageTemplateJsp.put(showType, path);
	}

	/**
	 * 获取默认的页面布局模板
	 * 
	 * @param showType
	 * @return
	 */
	public static String getDefaultPageTemplateJsp(String showType) {
		if (!isInitTemplate) {
			initPageTemplatesMap();
		}
		if (SysZoneConfigUtil.TYPE_PC_KEY.equals(showType)) {
			return pcTemplates.get(0).get(FORWARD_PATH);
		}
		return mobileTemplates.get(0).get(FORWARD_PATH);
	}
}
