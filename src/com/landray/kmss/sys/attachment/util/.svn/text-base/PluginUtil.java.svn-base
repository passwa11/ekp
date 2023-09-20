package com.landray.kmss.sys.attachment.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 扩展点工具
 * 
 * @author 潘永辉 2019年10月30日
 *
 */
public class PluginUtil {
	private final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	private static final String EXTENSION_POINT = "com.landray.kmss.sys.attachment.jg.online";
	private static final String VIEW_ITEM = "view";
	private static final String EDIT_ITEM = "edit";
	private static final String PARAM_JSP = "jsp";
	private static final String PARAM_SCRIPT = "script";
	private static final String PARAM_ORDER = "order";

	private static PluginUtil INSTANCE = new PluginUtil();

	public static PluginUtil getInstance() {
		return INSTANCE;
	}

	public void setEditOnline(HttpServletRequest request) {
		String model = (String) request.getAttribute("fdModelName");
		request.setAttribute("editOnlineMap", getEditOnline(model));
	}

	public void setViewOnline(HttpServletRequest request) {
		String model = (String) request.getAttribute("fdModelName");
		request.setAttribute("viewOnlineMap", getViewOnline(model));
	}

	/**
	 * 获取金格控件页面扩展
	 * 
	 * @param item
	 * @return
	 */
	private List<IExtension> getExtension(String model, String item) {
		if (StringUtil.isNull(model)) {
			return null;
		}
		IExtension[] extensions = Plugin.getExtensions(EXTENSION_POINT, model, item);
		if (extensions == null) {
			return null;
		}
		// 临时列表，保存未排序的扩展点
		List<IExtension> tempExtensions = Arrays.asList(extensions);
		/* 根据order参数排序，小的排前面 */
		Collections.sort(tempExtensions, new Comparator<IExtension>() {
			@Override
            public int compare(IExtension o1, IExtension o2) {
				int order1, order2;
				Object obj1 = Plugin.getParamValue(o1, PARAM_ORDER);
				Object obj2 = Plugin.getParamValue(o2, PARAM_ORDER);
				if (obj1 instanceof Integer) {
					order1 = (Integer) obj1;
					order2 = (Integer) obj2;
				} else {
					order1 = Integer.valueOf((String) obj1);
					order2 = Integer.valueOf((String) obj2);
				}
				return order1 - order2;
			}
		});
		return tempExtensions;
	}

	/**
	 * 获取编辑页面扩展信息
	 * 
	 * @return
	 */
	private Map<String, List<String>> getEditOnline(String model) {
		List<IExtension> extensions = getExtension(model, EDIT_ITEM);
		if (CollectionUtils.isNotEmpty(extensions)) {
			return build(extensions);
		} else {
			return Collections.EMPTY_MAP;
		}
	}
	
	/**
	 * 获取查看页面扩展信息
	 * 
	 * @return
	 */
	private Map<String, List<String>> getViewOnline(String model) {
		List<IExtension> extensions = getExtension(model, VIEW_ITEM);
		if (CollectionUtils.isNotEmpty(extensions)) {
			return build(extensions);
		} else {
			return Collections.EMPTY_MAP;
		}
	}
	
	/**
	 * 根据扩展点构建信息
	 * 
	 * @param extensions
	 * @return
	 */
	private Map<String, List<String>> build(List<IExtension> extensions) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		if (extensions != null && !extensions.isEmpty()) {
			for (IExtension extension : extensions) {
				addFile(map, extension, PARAM_JSP);
				addFile(map, extension, PARAM_SCRIPT);
			}
		}
		return map;
	}

	private void addFile(Map<String, List<String>> map, IExtension extension, String param) {
		String webContentPath = ConfigLocationsUtil.getWebContentPath();
		String path = Plugin.getParamValueString(extension, param);
		if (StringUtil.isNull(path)) {
			return;
		}
		// 判断文件是否存在
		File file = new File(webContentPath + path);
		if (file.exists()) {
			if (map.get(param) != null && !map.get(param).isEmpty()) {
				List<String> jspList = map.get(param);
				jspList.add(path);
			} else {
				List<String> jspList = new ArrayList<String>();
				jspList.add(path);
				map.put(param, jspList);
			}
		} else {
			logger.error("扩展文件：'" + webContentPath + path + "'不存在，请检查文件，当前忽略该配置项");
		}
	}

}
