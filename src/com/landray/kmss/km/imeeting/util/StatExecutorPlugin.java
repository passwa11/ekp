package com.landray.kmss.km.imeeting.util;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.km.imeeting.service.stat.KmImeetingStatExecutor;

/**
 * 查询执行器扩展公共函数
 */
public class StatExecutorPlugin {

	private static final String STAT_POINT = "com.landray.kmss.km.imeeting.stat";

	private static final String STAT_ITEM = "stat";

	/**
	 * @param Key
	 *            查询器类型
	 * @return 指定类型查询器
	 */
	public static IExtension getExtensionForStat(String key) {
		IExtension[] extensions = getStatExtensions();
		return Plugin.getExtension(extensions, "type", key);
	}

	/**
	 * 获取系统中实现的查询器列表
	 * 
	 * @return
	 */
	public static IExtension[] getStatExtensions() {
		return Plugin.getExtensions(STAT_POINT, "*", STAT_ITEM);
	}

	/**
	 * @param extension
	 * @return 某个查询分析器
	 */
	public static KmImeetingStatExecutor getStat(IExtension extension) {
		return (KmImeetingStatExecutor) Plugin.getParamValue(extension,
				"executor");
	}

	/**
	 * 图标类型
	 * 
	 * @param extension
	 * @return
	 */
	public static String getChartType(IExtension extension) {
		return Plugin.getParamValue(extension, "chartType");
	}

	/**
	 * 条件JSP
	 * 
	 * @param extension
	 * @return
	 */
	public static String getConditionJsp(IExtension extension) {
		return Plugin.getParamValue(extension, "conditionJsp");
	}

	/**
	 * 移动端条件JSP
	 * 
	 * @param extension
	 * @return
	 */
	public static String getConditionJsp4m(IExtension extension) {
		return Plugin.getParamValue(extension, "conditionJsp4m");
	}


	/**
	 * 拓展JS
	 * 
	 * @param extension
	 * @return
	 */
	public static String getExtJs(IExtension extension) {
		return Plugin.getParamValue(extension, "extJs");
	}

}
