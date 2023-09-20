package com.landray.kmss.km.calendar.forms;

import com.landray.kmss.common.forms.BaseForm;

/**
 * @author 孟磊
 * @version 创建时间：2013-10-30 上午11:29:09 类说明
 */
@SuppressWarnings("serial")
public class KmCalendarBaseConfigForm extends BaseForm {
	/**
	 * 设置日程的开始默认时间
	 */
	private String fdStartTime = null;
	/**
	 * 设置日程的结束默认时间
	 */
	private String fdEndTime = null;
	/**
	 * 设置日程的保留时间
	 */
	private String fdKeepDay = null;
	
	// 日历周起始星期
	private String calendarWeekStartDate = null;
	// 日历视图模式
	private String calendarDisplayType = null;
	// 日历时间间隔
	private String calendarMinuteStep = null;
	// 个人权限共享起始日
	private String updateAuthDate = null;

	public String getFdStartTime() {
		return fdStartTime;
	}

	public void setFdStartTime(String fdStartTime) {
		this.fdStartTime = fdStartTime;
	}

	public String getFdEndTime() {
		return fdEndTime;
	}

	public void setFdEndTime(String fdEndTime) {
		this.fdEndTime = fdEndTime;
	}

	public String getFdKeepDay() {
		return fdKeepDay;
	}

	public void setFdKeepDay(String fdKeepDay) {
		this.fdKeepDay = fdKeepDay;
	}

	private String defaultAuthorityType;

	public String getDefaultAuthorityType() {
		return defaultAuthorityType;
	}

	public void setDefaultAuthorityType(String defaultAuthorityType) {
		this.defaultAuthorityType = defaultAuthorityType;
	}

	private String threadPoolSize;

	public String getThreadPoolSize() {
		return threadPoolSize;
	}

	public void setThreadPoolSize(String threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	private String deptCanRead;

	public String getDeptCanRead() {
		return deptCanRead;
	}

	public void setDeptCanRead(String deptCanRead) {
		this.deptCanRead = deptCanRead;
	}

	public String getCalendarWeekStartDate() {
		return calendarWeekStartDate;
	}
	public void setCalendarWeekStartDate(String calendarWeekStartDate) {
		this.calendarWeekStartDate = calendarWeekStartDate;
	}
	
	public String getCalendarDisplayType() {
		return calendarDisplayType;
	}
	public void setCalendarDisplayType(String calendarDisplayType) {
		this.calendarDisplayType = calendarDisplayType;
	}
	
	public String getCalendarMinuteStep() {
		return calendarMinuteStep;
	}
	public void setCalendarMinuteStep(String calendarMinuteStep) {
		this.calendarMinuteStep = calendarMinuteStep;
	}
	
	public String getSynchroDirect() {
		return synchroDirect;
	}

	public void setSynchroDirect(String synchroDirect) {
		this.synchroDirect = synchroDirect;
	}

	public String getUpdateAuthDate() {
		return updateAuthDate;
	}

	public void setUpdateAuthDate(String updateAuthDate) {
		this.updateAuthDate = updateAuthDate;
	}

	private String synchroDirect;
}
