package com.landray.kmss.hr.staff.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.staff.forms.HrStaffPersonExperienceProjectForm;

/**
 * 项目经历
 * 
 * @author 朱湖强 2017-01-09
 * 
 */
public class HrStaffPersonExperienceProject extends HrStaffPersonExperienceBase {
	private static final long serialVersionUID = 1L;

	// 项目名称
	private String fdName;
	// 所属角色
	private String fdRole;
	
	

	@Override
	public Class getFormClass() {
		return HrStaffPersonExperienceProjectForm.class;
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

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdRole() {
		return fdRole;
	}

	public void setFdRole(String fdRole) {
		this.fdRole = fdRole;
	}
}
