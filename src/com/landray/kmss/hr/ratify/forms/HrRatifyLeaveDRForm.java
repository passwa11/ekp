package com.landray.kmss.hr.ratify.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.web.action.ActionMapping;

public class HrRatifyLeaveDRForm extends ExtendForm {

	private String fdUserId;

	private String fdUserName;

	private String fdUserSex; 

	private String fdUserParentName;

	private String fdUserStatus;

	private String fdLeaveApplyDate;

	private String fdLeavePlanDate;

	private String fdLeaveRealDate;

	private String fdLeaveSalaryEndDate;

	private String fdLeaveReason;

	private String fdLeaveRemark;

	private String fdNextCompany;

	private String fdRatifyLeaveId;

	public String getFdUserId() {
		return fdUserId;
	}

	public void setFdUserId(String fdUserId) {
		this.fdUserId = fdUserId;
	}

	public String getFdUserName() {
		return fdUserName;
	}

	public void setFdUserName(String fdUserName) {
		this.fdUserName = fdUserName;
	}

	public String getFdUserSex() {
		return fdUserSex;
	}

	public void setFdUserSex(String fdUserSex) {
		this.fdUserSex = fdUserSex;
	}

	public String getFdUserParentName() {
		return fdUserParentName;
	}

	public void setFdUserParentName(String fdUserParentName) {
		this.fdUserParentName = fdUserParentName;
	}

	public String getFdUserStatus() {
		return fdUserStatus;
	}

	public void setFdUserStatus(String fdUserStatus) {
		this.fdUserStatus = fdUserStatus;
	}

	public String getFdLeaveApplyDate() {
		return fdLeaveApplyDate;
	}

	public void setFdLeaveApplyDate(String fdLeaveApplyDate) {
		this.fdLeaveApplyDate = fdLeaveApplyDate;
	}

	public String getFdLeavePlanDate() {
		return fdLeavePlanDate;
	}

	public void setFdLeavePlanDate(String fdLeavePlanDate) {
		this.fdLeavePlanDate = fdLeavePlanDate;
	}

	public String getFdLeaveRealDate() {
		return fdLeaveRealDate;
	}

	public void setFdLeaveRealDate(String fdLeaveRealDate) {
		this.fdLeaveRealDate = fdLeaveRealDate;
	}

	public String getFdLeaveSalaryEndDate() {
		return fdLeaveSalaryEndDate;
	}

	public void setFdLeaveSalaryEndDate(String fdLeaveSalaryEndDate) {
		this.fdLeaveSalaryEndDate = fdLeaveSalaryEndDate;
	}

	public String getFdLeaveReason() {
		return fdLeaveReason;
	}

	public void setFdLeaveReason(String fdLeaveReason) {
		this.fdLeaveReason = fdLeaveReason;
	}

	public String getFdLeaveRemark() {
		return fdLeaveRemark;
	}

	public void setFdLeaveRemark(String fdLeaveRemark) {
		this.fdLeaveRemark = fdLeaveRemark;
	}

	public String getFdNextCompany() {
		return fdNextCompany;
	}

	public void setFdNextCompany(String fdNextCompany) {
		this.fdNextCompany = fdNextCompany;
	}

	public String getFdRatifyLeaveId() {
		return fdRatifyLeaveId;
	}

	public void setFdRatifyLeaveId(String fdRatifyLeaveId) {
		this.fdRatifyLeaveId = fdRatifyLeaveId;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdUserId = null;
		fdUserName = null;
		fdUserParentName = null;
		fdUserSex = null;
		fdUserStatus = null;
		fdLeaveApplyDate = null;
		fdLeavePlanDate = null;
		fdLeaveRealDate = null;
		fdLeaveSalaryEndDate = null;
		fdLeaveReason = null;
		fdLeaveRemark = null;
		fdNextCompany = null;
		fdRatifyLeaveId = null;
		super.reset(mapping, request);
	}

	@Override
	public Class getModelClass() {
		return null;
	}

}
