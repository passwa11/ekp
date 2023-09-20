package com.landray.kmss.sys.mportal.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class SysMportalNavConfig extends BaseAppConfig {

	public SysMportalNavConfig() throws Exception {
		super();
	}

	@Override
	public String getJSPUrl() {
		return "/sys/mportal/sys_mportal_nav_config/sysMportalNavConfig_edit.jsp";
	}

	public String getFdType() {
		String type = getValue("fdType");
		if (StringUtil.isNull(type)) {
			type = "top";
		}
		return type;
	}

	public void setFdType(String fdType) {
		setValue("fdType", fdType);
	}

	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("sys-mportal:sysMportal.profile.nav.config");
	}
}
