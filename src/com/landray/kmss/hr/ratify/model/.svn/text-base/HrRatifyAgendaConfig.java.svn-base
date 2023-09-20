package com.landray.kmss.hr.ratify.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.StringUtil;

public class HrRatifyAgendaConfig extends BaseAppConfig {

	public HrRatifyAgendaConfig() throws Exception {
		super();
		String fdHrStaffCont = getValue("fdHrStaffCont");
		if (StringUtil.isNull(fdHrStaffCont)) {
            setValue("fdHrStaffCont", "true");
        }
		String fdHrStaffStatus = getValue("fdHrStaffStatus");
		if (StringUtil.isNull(fdHrStaffStatus)) {
            setValue("fdHrStaffStatus", "true");
        }
		String fdHrStaffSalary = getValue("fdHrStaffSalary");
		if (StringUtil.isNull(fdHrStaffSalary)) {
            setValue("fdHrStaffSalary", "true");
        }
		String fdUpdateSysOrg = getValue("fdUpdateSysOrg");
		if (StringUtil.isNull(fdUpdateSysOrg)) {
            setValue("fdUpdateSysOrg", "true");
        }
		String fdFalseSysOrg = getValue("fdFalseSysOrg");
		if (StringUtil.isNull(fdFalseSysOrg)) {
            setValue("fdFalseSysOrg", "true");
        }
		String fdFalseHrStaff = getValue("fdFalseHrStaff");
		if (StringUtil.isNull(fdFalseHrStaff)) {
            setValue("fdFalseHrStaff", "true");
        }
		String fdTrueSysOrg = getValue("fdTrueSysOrg");
		if (StringUtil.isNull(fdTrueSysOrg)) {
            setValue("fdTrueSysOrg", "true");
        }
	}

	@Override
	public String getJSPUrl() {
		return "/hr/ratify/hr_ratify_config/hrRatifyAgendaConfig_edit.jsp";
	}

	/**
	 * 合同签订/变更/解除
	 */
	public String getFdHrStaffCont() {
		return getValue("fdHrStaffCont");
	}

	/**
	 * 转正申请
	 */
	public String getFdHrStaffStatus() {
		return getValue("fdHrStaffStatus");
	}

	/**
	 * 调薪申请
	 */
	public String getFdHrStaffSalary() {
		return getValue("fdHrStaffSalary");
	}

	/**
	 * 调岗申请
	 */
	public String getFdUpdateSysOrg() {
		return getValue("fdUpdateSysOrg");
	}

	/**
	 * 离职/解聘/退休申请 系统账号置为无效
	 */
	public String getFdFalseSysOrg() {
		return getValue("fdFalseSysOrg");
	}

	/**
	 * 离职确认
	 * 
	 * @return
	 */
	public String getFdLeaveManage() {
		return getValue("fdLeaveManage");
	}

	/**
	 * 离职/解聘/退休申请 人事档案信息员工状态置为离职/解聘/退休
	 */
	public String getFdFalseHrStaff() {
		return getValue("fdFalseHrStaff");
	}

	/**
	 * 返聘申请 系统账号置为有效并将人事档案信息员工状态置为正式
	 */
	public String getFdTrueSysOrg() {
		return getValue("fdTrueSysOrg");
	}

}
