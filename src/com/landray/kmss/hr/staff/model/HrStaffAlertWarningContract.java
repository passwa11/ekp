/**
 * 
 */
package com.landray.kmss.hr.staff.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 合同到期提醒
 * @author 陈经纬
 * @creation 2017-1-1 
 */
public class HrStaffAlertWarningContract extends BaseAppConfig{
	protected String staffReminder; //合同到期提醒是否开启
	protected String cycleReminder;//合同到期提醒周期
	//提醒人员
	protected String personReminderId;//提醒人员id
	protected String personReminderName;//提醒人员名字
	/**
	 * 校验档案授权
	 */
	protected String cerifyAuthorization;

	public String getCerifyAuthorization() {
		return super.getValue("cerifyAuthorization");
	}
	public String getStaffReminder() {
		return super.getValue("staffReminder");
	}

	public String getCycleReminder() {
		return super.getValue("cycleReminder");
	}
	public HrStaffAlertWarningContract() throws Exception {
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

		String cycleReminder=super.getValue("cycleReminder");
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


	/* （非 Javadoc）
	 * @see com.landray.kmss.sys.appconfig.model.BaseAppConfig#getJSPUrl()
	 */
	@Override
	public String getJSPUrl() {
		return "/hr/staff/hr_staff_alert_warning_setting/contract_expiration/index.jsp";
	}
	
	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("hr-staff:hr.staff.tree.contract.expiration.reminder");
	}
}
