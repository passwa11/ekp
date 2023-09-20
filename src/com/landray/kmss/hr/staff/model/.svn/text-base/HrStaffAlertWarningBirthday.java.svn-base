package com.landray.kmss.hr.staff.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 
 * 生日提醒
 * 
 * @author 陈经纬 2017-1-6
 * 
 */
public class HrStaffAlertWarningBirthday extends BaseAppConfig {

	protected String staffReminder; // 人事提醒是否开启
	protected String cycleReminder;// 人事提醒提前提醒周期
	// 提醒人员
	protected String personReminderId;// 提醒人员id
	protected String personReminderName;// 提醒人员名字
	/**
	 * 校验档案授权
	 */
	protected String cerifyAuthorization;

	public String getCerifyAuthorization() {
		return super.getValue("cerifyAuthorization");
	}

	public HrStaffAlertWarningBirthday() throws Exception {
		super();
		String staffReminder = super.getValue("staffReminder");
		if (StringUtil.isNull(staffReminder)) {
			staffReminder = "false";
		}
		super.setValue("staffReminder", staffReminder);

		String cerifyAuthorization = super.getValue("cerifyAuthorization");
		if (StringUtil.isNull(staffReminder)) {
			cerifyAuthorization = "false";
		}
		super.setValue("cerifyAuthorization", cerifyAuthorization);


		String cycleReminder = super.getValue("cycleReminder");
		if (StringUtil.isNull(cycleReminder)) {
			cycleReminder = "month";
		}
		super.setValue("cycleReminder", cycleReminder);

		String personReminderId = super.getValue("personReminderId");
		if (StringUtil.isNull(personReminderId)) {
			personReminderId = null;
		}
		super.setValue("personReminderId", personReminderId);

		String personReminderName = super.getValue("personReminderName");
		if (StringUtil.isNull(personReminderName)) {
			personReminderName = null;
		}
		super.setValue("personReminderName", personReminderName);

	}

	/**
	 * 是否开启
	 * @return
	 */
	public String getStaffReminder() {
		return super.getValue("staffReminder");
	}

	/**
	 * 周期
	 * @return
	 */
	public String getCycleReminder() {
		return super.getValue("cycleReminder");
	}

	@Override
	public String getJSPUrl() {
		return "/hr/staff/hr_staff_alert_warning_setting/last_birthday/index.jsp";
	}
	
	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("hr-staff:hr.staff.birthday.staff.reminder");
	}

}
