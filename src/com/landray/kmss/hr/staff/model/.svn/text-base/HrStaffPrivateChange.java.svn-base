package com.landray.kmss.hr.staff.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.hr.staff.forms.HrStaffPrivateChangeForm;

/**
 * 个人简介
 * 
 * @author 朱湖强 2017-01-09
 * 
 */
public class HrStaffPrivateChange extends BaseModel {
	private static final long serialVersionUID = 1L;

	// 个人信息Id
	private String fdPersonId;
	private String fdPersonName;
	
	private Boolean isBriefPrivate;
	private Boolean isProjectPrivate;
	private Boolean isWorkPrivate;
	private Boolean isEducationPrivate;
	private Boolean isTrainingPrivate;
	private Boolean isBonusPrivate;

	@Override
	public Class getFormClass() {
		return HrStaffPrivateChangeForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
		}
		return toFormPropertyMap;
	}


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


	public Boolean getIsBriefPrivate() {
		return isBriefPrivate;
	}

	public void setIsBriefPrivate(Boolean isBriefPrivate) {
		this.isBriefPrivate = isBriefPrivate;
	}

	public Boolean getIsProjectPrivate() {
		return isProjectPrivate;
	}

	public void setIsProjectPrivate(Boolean isProjectPrivate) {
		this.isProjectPrivate = isProjectPrivate;
	}

	public Boolean getIsWorkPrivate() {
		return isWorkPrivate;
	}

	public void setIsWorkPrivate(Boolean isWorkPrivate) {
		this.isWorkPrivate = isWorkPrivate;
	}

	public Boolean getIsEducationPrivate() {
		return isEducationPrivate;
	}

	public void setIsEducationPrivate(Boolean isEducationPrivate) {
		this.isEducationPrivate = isEducationPrivate;
	}

	public Boolean getIsTrainingPrivate() {
		return isTrainingPrivate;
	}

	public void setIsTrainingPrivate(Boolean isTrainingPrivate) {
		this.isTrainingPrivate = isTrainingPrivate;
	}


	public Boolean getIsBonusPrivate() {
		return isBonusPrivate;
	}


	public void setIsBonusPrivate(Boolean isBonusPrivate) {
		this.isBonusPrivate = isBonusPrivate;
	}
}
