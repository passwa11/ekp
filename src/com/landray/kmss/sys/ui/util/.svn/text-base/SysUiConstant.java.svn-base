package com.landray.kmss.sys.ui.util;

import com.landray.kmss.sys.cluster.interfaces.ClusterDiscover;
import com.landray.kmss.sys.cluster.model.SysClusterGroup;

public class SysUiConstant {
	public static final String SEPARATOR = "://";

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
}
