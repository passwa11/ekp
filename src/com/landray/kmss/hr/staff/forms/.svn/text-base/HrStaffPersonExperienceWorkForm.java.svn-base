package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceWork;

/**
 * 工作经历
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public class HrStaffPersonExperienceWorkForm extends
		HrStaffPersonExperienceBaseForm {
	private static final long serialVersionUID = 1L;

	// 公司
	private String fdCompany;
	// 职位
	private String fdPosition;
	// 工作描述
	private String fdDescription;
	// 离职原因
	private String fdReasons;

	@Override
	public Class getModelClass() {
		return HrStaffPersonExperienceWork.class;
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
		this.fdCompany = null;
		this.fdPosition = null;
		this.fdDescription = null;
		this.fdReasons = null;
	}

	public String getFdCompany() {
		return fdCompany;
	}

	public void setFdCompany(String fdCompany) {
		this.fdCompany = fdCompany;
	}

	public String getFdPosition() {
		return fdPosition;
	}

	public void setFdPosition(String fdPosition) {
		this.fdPosition = fdPosition;
	}

	public String getFdDescription() {
		return fdDescription;
	}

	public void setFdDescription(String fdDescription) {
		this.fdDescription = fdDescription;
	}

	public String getFdReasons() {
		return fdReasons;
	}

	public void setFdReasons(String fdReasons) {
		this.fdReasons = fdReasons;
	}

}
