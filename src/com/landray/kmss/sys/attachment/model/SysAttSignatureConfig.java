package com.landray.kmss.sys.attachment.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;

/**
 * 签章
 */
public class SysAttSignatureConfig extends BaseAppConfig {

	public SysAttSignatureConfig() throws Exception {
		super();
	}

	@Override
    public String getJSPUrl() {
		return "/sys/attachment/sys_att_signature.jsp";
	}

	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("sys-attachment:attachment.config");
	}

}
