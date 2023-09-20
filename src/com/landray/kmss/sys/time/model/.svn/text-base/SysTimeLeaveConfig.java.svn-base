package com.landray.kmss.sys.time.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-12-17
 */
public class SysTimeLeaveConfig extends BaseAppConfig {

	public SysTimeLeaveConfig() throws Exception {
		super();
		if (StringUtil.isNull(getDayConvertTime())) {
			setDayConvertTime("8");
		}
	}

	public String getDayConvertTime() {
		return getValue("dayConvertTime");
	}

	public void setDayConvertTime(String dayConvertTime) {
		setValue("dayConvertTime", dayConvertTime);
	}

	@Override
	public String getJSPUrl() {
		return "/sys/time/sys_time_leave_config/sysTimeLeaveConfig.jsp";
	}

	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("sys-time:sysTimeLeaveConfig.appconfig");
	}

}
