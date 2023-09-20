package com.landray.kmss.km.calendar.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * @author 孟磊
 * @version 创建时间：2013-10-30 上午11:19:13 类说明
 */
public class KmCalendarBaseConfig extends BaseAppConfig {

	private int fdType;

	public int getFdType() {
		return fdType;
	}

	public void setFdType(int fdType) {
		this.fdType = fdType;
	}

	public KmCalendarBaseConfig() throws Exception {
		super();
		if (StringUtil.isNull(getDefaultAuthorityType())) {
			setDefaultAuthorityType("PRIVATE");
		}
	}

	@Override
    public String getJSPUrl() {
		return "/km/calendar/km_calendar_base_config/sysCalendarBaseConfig_edit.jsp";
	}

	/**
	 * @return 获取设置日程的默认开始时间
	 */
	public String getFdStartTime() {
		return getValue("fdStartTime");
	}

	/**
	 * @param fdStartTime
	 *            设置日程的默认开始时间
	 */
	public void setFdStartTime(String fdStartTime) {
		setValue("fdStartTime", fdStartTime);
	}

	/**
	 * @return 获取设置日程的默认结束时间
	 */
	public String getFdEndTime() {
		return getValue("fdEndTime");
	}

	/**
	 * @param fdEndTime
	 *            设置日程的默认结束时间
	 */
	public void setFdEndTime(String fdEndTime) {
		setValue("fdEndTime", fdEndTime);
	}

	/**
	 * @return 获取日程的保留的时间
	 */
	public String getFdKeepDay() {
		return getValue("fdKeepDay");
	}

	/**
	 * @param fdKeepDay
	 *            设置日程的保留的时间
	 */
	public void setFdKeepDay(String fdKeepDay) {
		setValue("fdKeepDay", fdKeepDay);
	}

	public void setDefaultAuthorityType(String defaultAuthorityType) {
		setValue("defaultAuthorityType", defaultAuthorityType);
	}

	public String getDefaultAuthorityType() {
		return getValue("defaultAuthorityType");
	}

	public void setThreadPoolSize(String threadPoolSize) {
		setValue("threadPoolSize", threadPoolSize);
	}

	public String getThreadPoolSize() {
		return getValue("threadPoolSize");
	}

	/**
	 * 个人共享设置默认设置部门为可阅读者
	 */
	public String getDeptCanRead() {
		return getValue("deptCanRead");
	}

	public void setDeptCanRead(String deptCanRead) {
		setValue("deptCanRead", deptCanRead);
	}
	public void setSynchroDirect(String synchroDirect) {
		setValue("synchroDirect", synchroDirect);
	}

	public String getSynchroDirect() {
		return getValue("synchroDirect");
	}

	@Override
	public String getModelDesc() {
		String desc = null;
		switch (fdType) {
		case 1:
			desc = ResourceUtil
					.getString(
							"km-calendar:km.calendar.tree.delete.calendar.set");
			break;
		case 2:
			desc = ResourceUtil.getString(
					"km-calendar:kmCalendarBaseConfig.auth.set");
			break;
		default:
			desc = ResourceUtil.getString(
					"km-calendar:kmCalendarBaseConfig.synchro.setting");
			break;
		}
		return desc;
	}

}
