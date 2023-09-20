package com.landray.kmss.sys.restservice.server.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;

/**
 * 日志设置
 * 
 * @author  
 */
public class SysRestServiceBaseInfo extends BaseAppConfig {
	public SysRestServiceBaseInfo() throws Exception {
		super();
	}

	/**
	 * @return 返回 运行记录的保留时间
	 */
	public String getDaysOfBackupLog() {
		return getValue("daysOfBackupLog");
	}

	/**
	 * @param daysOfBackupLog
	 *            要设置的 运行记录的保留时间
	 */
	public void setDaysOfBackupLog(String daysOfBackupLog) {
		setValue("daysOfBackupLog", daysOfBackupLog);
	}

	/**
	 * @return 返回 归档记录的保留时间
	 */
	public String getDaysOfClearLog() {
		return getValue("daysOfClearLog");
	}

	/**
	 * @param daysOfClearLog
	 *            要设置的 归档记录的保留时间
	 */
	public void setDaysOfClearLog(String daysOfClearLog) {
		setValue("daysOfClearLog", daysOfClearLog);
	}

	@Override
	public String getJSPUrl() {
		return "/sys/restservice/server/sys_restservice_server_baseinfo.jsp";
	}

	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("sys-restservice-server:module.sys.restservice.base");
	}
}
