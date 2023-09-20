package com.landray.kmss.hr.config.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.StringUtil;

public class WhiteListConfig extends BaseAppConfig {

	public WhiteListConfig() throws Exception {
		super();
	}

	@Override
	public String getJSPUrl() {
		return "/hr/config/configWhiteList.jsp";
	}

	public String getFdOvertimeWhiteName() {
		return getValue("fdOvertimeWhiteName");
	}
	
	public String getFdOvertimeWhiteId() {
		return getValue("fdOvertimeWhiteId");
	}
}
