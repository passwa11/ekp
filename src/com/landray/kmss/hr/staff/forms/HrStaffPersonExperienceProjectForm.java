package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceProject;

/**
 * 项目经历
 * 
 * @author 朱湖强 2017-01-09
 * 
 */
public class HrStaffPersonExperienceProjectForm extends
		HrStaffPersonExperienceBaseForm {
	private static final long serialVersionUID = 1L;

	// 公司
	private String fdName;
	// 职位
	private String fdRole;

	@Override
	public Class getModelClass() {
		return HrStaffPersonExperienceProject.class;
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
		this.fdName = null;
		this.fdRole = null;
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
