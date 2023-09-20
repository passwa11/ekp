package com.landray.kmss.hr.ratify.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.web.action.ActionMapping;

public class HrRatifyEntryDRForm extends ExtendForm {

	private String fdEntryId;

	private String fdLoginName;

	private String fdPassword;

	private String fdEntryDeptId;

	private String fdEntryDeptName;

	private String fdEntryPostIds;

	private String fdEntryPostNames;

	private String fdNo;

	private String fdEntryDate;

	private String fdHasWrite;

	public String getFdEntryId() {
		return fdEntryId;
	}

	public void setFdEntryId(String fdEntryId) {
		this.fdEntryId = fdEntryId;
	}

	public String getFdLoginName() {
		return fdLoginName;
	}

	public void setFdLoginName(String fdLoginName) {
		this.fdLoginName = fdLoginName;
	}

	public String getFdPassword() {
		return fdPassword;
	}

	public void setFdPassword(String fdPassword) {
		this.fdPassword = fdPassword;
	}

	public String getFdEntryDeptId() {
		return fdEntryDeptId;
	}

	public void setFdEntryDeptId(String fdEntryDeptId) {
		this.fdEntryDeptId = fdEntryDeptId;
	}

	public String getFdEntryDeptName() {
		return fdEntryDeptName;
	}

	public void setFdEntryDeptName(String fdEntryDeptName) {
		this.fdEntryDeptName = fdEntryDeptName;
	}

	public String getFdEntryPostIds() {
		return fdEntryPostIds;
	}

	public void setFdEntryPostIds(String fdEntryPostIds) {
		this.fdEntryPostIds = fdEntryPostIds;
	}

	public String getFdEntryPostNames() {
		return fdEntryPostNames;
	}

	public void setFdEntryPostNames(String fdEntryPostNames) {
		this.fdEntryPostNames = fdEntryPostNames;
	}

	/**
	 * 编号
	 */
	public String getFdNo() {
		return fdNo;
	}

	/**
	 * 编号
	 */
	public void setFdNo(String fdNo) {
		this.fdNo = fdNo;
	}

	public String getFdEntryDate() {
		return fdEntryDate;
	}

	public void setFdEntryDate(String fdEntryDate) {
		this.fdEntryDate = fdEntryDate;
	}

	public String getFdHasWrite() {
		return fdHasWrite;
	}

	public void setFdHasWrite(String fdHasWrite) {
		this.fdHasWrite = fdHasWrite;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdEntryId = null;
		fdLoginName = null;
		fdPassword = null;
		fdEntryDeptId = null;
		fdEntryDeptName = null;
		fdEntryPostIds = null;
		fdEntryPostNames = null;
		fdNo = null;
		fdEntryDate = null;
		fdHasWrite = null;
		super.reset(mapping, request);
	}

	@Override
	public Class getModelClass() {
		return null;
	}

}
