package com.landray.kmss.sys.portal.cloud;

import java.util.HashMap;
import java.util.Map;

import com.landray.kmss.sys.ui.xml.model.SysUiPortlet;
import com.landray.kmss.sys.ui.xml.model.SysUiSource;

public class CloudPortalCache {

	private static Map<String, SysUiSource> sources = new HashMap<String, SysUiSource>();

	private static Map<String, SysUiPortlet> portlets = new HashMap<String, SysUiPortlet>();

	public static Map<String, SysUiSource> getSources() {
		return sources;
	}

	public static Map<String, SysUiPortlet> getPortlets() {
		return portlets;
	}

	public static SysUiSource getSourceById(String id) {
		return sources.get(id);
	}

	public static SysUiPortlet getPortletById(String id) {
		return portlets.get(id);
	}
}
