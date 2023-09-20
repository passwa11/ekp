package com.landray.kmss.sys.attend.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;

public class SysAttendMapConfig extends BaseAppConfig {

	public SysAttendMapConfig() throws Exception {
		super();
	}

	@Override
	public String getJSPUrl() {
		return "/sys/attend/map/sysAttendMap_config.jsp";
	}

	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("sys-attend:sysAttend.tree.map.config.title");
	}

}
