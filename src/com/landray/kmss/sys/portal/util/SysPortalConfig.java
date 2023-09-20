package com.landray.kmss.sys.portal.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.cluster.interfaces.ClusterDiscover;
import com.landray.kmss.sys.cluster.model.SysClusterGroup;
import com.landray.kmss.sys.ui.util.SysUiConstant;
import com.landray.kmss.sys.ui.xml.model.SysUiFormat;
import com.landray.kmss.sys.ui.xml.model.SysUiOperation;
import com.landray.kmss.sys.ui.xml.model.SysUiPortlet;
import com.landray.kmss.sys.ui.xml.model.SysUiRender;
import com.landray.kmss.sys.ui.xml.model.SysUiSource;
import com.landray.kmss.sys.ui.xml.model.SysUiVar;
import com.landray.kmss.util.ResourceUtil;

public class SysPortalConfig {
	public static boolean BEHAVIOR_ENABLE = "true".equals(ResourceUtil
			.getKmssConfigString("kmss.behavior.enabled"));

	public static SysUiPortlet newPortletByJson(String server, JSONObject json) {
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("fdVars", SysUiVar.class);
		classMap.put("fdOperations", SysUiOperation.class);
		SysUiPortlet portlet = (SysUiPortlet) JSONObject.toBean(json,
				SysUiPortlet.class, classMap);
		portlet.setFdModule(server + SysUiConstant.SEPARATOR
				+ portlet.getFdModule());
		portlet.setFdSource(server + SysUiConstant.SEPARATOR
				+ portlet.getFdSource());
		portlet.setFdId(server + SysUiConstant.SEPARATOR + portlet.getFdId());
		return portlet;
	}

	public static SysUiSource newSourceByJson(String server, JSONObject json) {
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("fdVars", SysUiVar.class);
		SysUiSource source = (SysUiSource) JSONObject.toBean(json,
				SysUiSource.class, classMap);
		source.setFdId(server + SysUiConstant.SEPARATOR + source.getFdId());
		return source;
	}

	public static SysUiRender newRenderByJson(String server, JSONObject json) {
		SysUiRender render = (SysUiRender) JSONObject.toBean(json,
				SysUiRender.class);
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("fdVars", SysUiVar.class);
		render.setFdId(server + SysUiConstant.SEPARATOR + render.getFdId());
		return render;
	}

	public static SysUiFormat newFormatByJson(String server, JSONObject json) {
		SysUiFormat format = (SysUiFormat) JSONObject.toBean(json,
				SysUiFormat.class);
		format.setFdId(server + SysUiConstant.SEPARATOR + format.getFdId());
		return format;
	}

	public static String getServerUrl(String code) {
		SysClusterGroup group = ClusterDiscover.getInstance().getGroup(code);
		if (group != null) {
			String url = group.getFdUrl();
			if (url != null) {
				if (url.endsWith("/")) {
                    return url.substring(0, url.length() - 1);
                }
			}
			return url;
		}
		return null;
	}

	public static Map<String, String> getServerName() {
		Map<String, String> names = new HashMap<String, String>();
		List<SysClusterGroup> groups = ClusterDiscover.getInstance()
				.getAllGroup();
		for (int i = 0; i < groups.size(); i++) {
			SysClusterGroup group = groups.get(i);
			names.put(group.getFdKey(), group.getFdName());
		}
		return names;
	}

	public static String[] getCurrentGroupInfo() {
		String[] info = new String[3];
		List<SysClusterGroup> groups = ClusterDiscover.getInstance()
				.getAllGroup();
		for (int i = 0; i < groups.size(); i++) {
			SysClusterGroup group = groups.get(i);
			if (group.getFdLocal()) {
				info[0] = group.getFdKey();
				info[1] = group.getFdName();
				info[2] = group.getFdUrl();
				return info;
			}
		}
		return null;
	}

	public static String getCurrentServerName() {
		List<SysClusterGroup> groups = ClusterDiscover.getInstance()
				.getAllGroup();
		for (int i = 0; i < groups.size(); i++) {
			SysClusterGroup group = groups.get(i);
			if (group.getFdLocal()) {
                return group.getFdName();
            }
		}
		return null;
	}

	public static String getServerName(String code) {
		SysClusterGroup group = ClusterDiscover.getInstance().getGroup(code);
		if (group != null) {
			return group.getFdName();
		}
		return null;
	}

}
