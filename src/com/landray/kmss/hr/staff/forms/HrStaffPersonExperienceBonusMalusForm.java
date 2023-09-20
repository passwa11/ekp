package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceBonusMalus;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 奖惩信息
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public class HrStaffPersonExperienceBonusMalusForm extends
		HrStaffPersonExperienceBaseForm {
	private static final long serialVersionUID = 1L;

	// 奖惩名称
	private String fdBonusMalusName;
	// 奖惩日期
	private String fdBonusMalusDate;

	@Override
	public Class getModelClass() {
		return HrStaffPersonExperienceBonusMalus.class;
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

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.fdBonusMalusName = null;
		this.fdBonusMalusDate = null;
	}

	public String getFdBonusMalusName() {
		return fdBonusMalusName;
	}

	public void setFdBonusMalusName(String fdBonusMalusName) {
		this.fdBonusMalusName = fdBonusMalusName;
	}

	public String getFdBonusMalusDate() {
		return fdBonusMalusDate;
	}

	public void setFdBonusMalusDate(String fdBonusMalusDate) {
		this.fdBonusMalusDate = fdBonusMalusDate;
	}

	// 奖惩类型
	private String fdBonusMalusType;

	public String getFdBonusMalusType() {
		return fdBonusMalusType;
	}

	public void setFdBonusMalusType(String fdBonusMalusType) {
		this.fdBonusMalusType = fdBonusMalusType;
	}

}
