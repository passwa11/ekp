package com.landray.kmss.sys.attachment.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;

public class SysAttConfig extends BaseAppConfig {

	public SysAttConfig() throws Exception {
		super();
	}

	@Override
    public String getJSPUrl() {
		return "/sys/attachment/sys_att_config.jsp";
	}

	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("sys-attachment:attachment.config");
	}

}
