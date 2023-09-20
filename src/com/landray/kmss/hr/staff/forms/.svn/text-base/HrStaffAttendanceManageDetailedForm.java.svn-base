package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.staff.model.HrStaffAttendanceManageDetailed;

/**
 * 个人假期明细
 * 
 * @author 潘永辉 2017-1-11
 * 
 */
public class HrStaffAttendanceManageDetailedForm extends HrStaffBaseForm {
	private static final long serialVersionUID = 1L;

	// 请假（加班）天数
	private String fdLeaveDays;
	// 相关流程
	private String fdRelatedProcess;
	// 开始日期
	private String fdBeginDate;
	// 结束日期
	private String fdEndDate;
	// 请假（加班）类型
	private String fdLeaveType;
	// 操作类型
	private String fdType;

	@Override
	public Class getModelClass() {
		return HrStaffAttendanceManageDetailed.class;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.fdLeaveDays = null;
		this.fdRelatedProcess = null;
		this.fdBeginDate = null;
		this.fdEndDate = null;
		this.fdLeaveType = null;
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

	public String getFdLeaveDays() {
		return fdLeaveDays;
	}

	public void setFdLeaveDays(String fdLeaveDays) {
		this.fdLeaveDays = fdLeaveDays;
	}

	public String getFdRelatedProcess() {
		return fdRelatedProcess;
	}

	public void setFdRelatedProcess(String fdRelatedProcess) {
		this.fdRelatedProcess = fdRelatedProcess;
	}

	public String getFdBeginDate() {
		return fdBeginDate;
	}

	public void setFdBeginDate(String fdBeginDate) {
		this.fdBeginDate = fdBeginDate;
	}

	public String getFdEndDate() {
		return fdEndDate;
	}

	public void setFdEndDate(String fdEndDate) {
		this.fdEndDate = fdEndDate;
	}

	public String getFdLeaveType() {
		return fdLeaveType;
	}

	public void setFdLeaveType(String fdLeaveType) {
		this.fdLeaveType = fdLeaveType;
	}

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

}
