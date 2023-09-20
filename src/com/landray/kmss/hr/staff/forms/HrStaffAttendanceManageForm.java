package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.staff.model.HrStaffAttendanceManage;

/**
 * 考勤管理
 * 
 * @author 潘永辉 2017-1-11
 * 
 */
public class HrStaffAttendanceManageForm extends HrStaffBaseForm {
	private static final long serialVersionUID = 1L;

	// 年份
	private String fdYear;
	// 失效日期
	private String fdExpirationDate;
	// 剩余年假天数
	private String fdDaysOfAnnualLeave;
	// 剩余可调休天数
	private String fdDaysOfTakeWorking;
	// 当前剩余带薪病假天数
	private String fdDaysOfSickLeave;

	@Override
	public Class getModelClass() {
		return HrStaffAttendanceManage.class;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.fdYear = null;
		this.fdExpirationDate = null;
		this.fdDaysOfAnnualLeave = null;
		this.fdDaysOfTakeWorking = null;
		this.fdDaysOfSickLeave = null;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
		}
		return toModelPropertyMap;
	}

	public String getFdYear() {
		return fdYear;
	}

	public void setFdYear(String fdYear) {
		this.fdYear = fdYear;
	}

	public String getFdExpirationDate() {
		return fdExpirationDate;
	}

	public void setFdExpirationDate(String fdExpirationDate) {
		this.fdExpirationDate = fdExpirationDate;
	}

	public String getFdDaysOfAnnualLeave() {
		return fdDaysOfAnnualLeave;
	}

	public void setFdDaysOfAnnualLeave(String fdDaysOfAnnualLeave) {
		this.fdDaysOfAnnualLeave = fdDaysOfAnnualLeave;
	}

	public String getFdDaysOfTakeWorking() {
		return fdDaysOfTakeWorking;
	}

	public void setFdDaysOfTakeWorking(String fdDaysOfTakeWorking) {
		this.fdDaysOfTakeWorking = fdDaysOfTakeWorking;
	}

	public String getFdDaysOfSickLeave() {
		return fdDaysOfSickLeave;
	}

	public void setFdDaysOfSickLeave(String fdDaysOfSickLeave) {
		this.fdDaysOfSickLeave = fdDaysOfSickLeave;
	}

}
