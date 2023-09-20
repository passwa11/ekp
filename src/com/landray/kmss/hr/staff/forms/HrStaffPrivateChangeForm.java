package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.hr.staff.model.HrStaffPrivateChange;

public class HrStaffPrivateChangeForm extends ExtendForm {
	
	// 个人信息Id
	private String fdPersonId = null;
	private String fdPersonName = null;
	
	private String isBriefPrivate = null;
	private String isProjectPrivate = null;
	private String isWorkPrivate = null;
	private String isEducationPrivate = null;
	private String isTrainingPrivate = null;
	private String isBonusPrivate = null;
	
	


	public String getFdPersonId() {
		return fdPersonId;
	}

	public void setFdPersonId(String fdPersonId) {
		this.fdPersonId = fdPersonId;
	}
	

	public String getFdPersonName() {
		return fdPersonName;
	}

	public void setFdPersonName(String fdPersonName) {
		this.fdPersonName = fdPersonName;
	}

	public String getIsBriefPrivate() {
		return isBriefPrivate;
	}

	public void setIsBriefPrivate(String isBriefPrivate) {
		this.isBriefPrivate = isBriefPrivate;
	}

	public String getIsProjectPrivate() {
		return isProjectPrivate;
	}

	public void setIsProjectPrivate(String isProjectPrivate) {
		this.isProjectPrivate = isProjectPrivate;
	}

	public String getIsWorkPrivate() {
		return isWorkPrivate;
	}

	public void setIsWorkPrivate(String isWorkPrivate) {
		this.isWorkPrivate = isWorkPrivate;
	}

	public String getIsEducationPrivate() {
		return isEducationPrivate;
	}

	public void setIsEducationPrivate(String isEducationPrivate) {
		this.isEducationPrivate = isEducationPrivate;
	}

	public String getIsTrainingPrivate() {
		return isTrainingPrivate;
	}

	public void setIsTrainingPrivate(String isTrainingPrivate) {
		this.isTrainingPrivate = isTrainingPrivate;
	}

	

	public String getIsBonusPrivate() {
		return isBonusPrivate;
	}

	public void setIsBonusPrivate(String isBonusPrivate) {
		this.isBonusPrivate = isBonusPrivate;
	}

	@Override
	public Class getModelClass() {
		return HrStaffPrivateChange.class;
	}
	
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.fdPersonId = null;
		this.isBriefPrivate = null;
		this.isProjectPrivate = null;
		this.isWorkPrivate = null;
		this.isEducationPrivate = null;
		this.isTrainingPrivate = null;
		this.isBonusPrivate = null;
		super.reset(mapping, request);
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
}