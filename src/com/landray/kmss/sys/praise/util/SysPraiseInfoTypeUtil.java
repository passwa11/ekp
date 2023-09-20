package com.landray.kmss.sys.praise.util;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.plugin.core.config.IExtensionPoint;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.util.StringUtil;

public class SysPraiseInfoTypeUtil {

	public static final List<String> extendList = new ArrayList<String>();

	private static final String BASE_EXTENSION_POINT_ID = "com.landray.kmss.sys.praiseInfo";

	private static final Object TYPE = "jspConfig";

	private static final String JSP = "jsp";

	public static boolean newInit = true;

	public static List<String> getExtendList() {
		synchronized (extendList) {
			if (newInit) {
				init();
			}
		}
		return extendList;
	}

	private static void init() {
		extendList.clear();
		IExtensionPoint point = Plugin.getExtensionPoint(BASE_EXTENSION_POINT_ID);
		IExtension[] extensions = point.getExtensions();
		for (IExtension extension : extensions) {
			if (TYPE.equals(extension.getAttribute("name"))
					&& StringUtil.isNotNull(Plugin.getParamValueString(extension, JSP))) {
				extendList.add(Plugin.getParamValueString(extension, JSP));
			}
		}
		newInit = false;
	}
}
