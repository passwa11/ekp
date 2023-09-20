package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceEducation;

/**
 * 教育记录
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public class HrStaffPersonExperienceEducationForm extends
		HrStaffPersonExperienceBaseForm {
	private static final long serialVersionUID = 1L;

	// 学校名称
	private String fdSchoolName;
	// 专业
	private String fdMajor;
	// 学位
	private String fdDegree;
	//学历
	private String fdEducation;
	@Override
	public Class getModelClass() {
		return HrStaffPersonExperienceEducation.class;
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
		this.fdSchoolName = null;
		this.fdMajor = null;
		this.fdDegree = null;
		this.fdEducation = null; 
	}

	public String getFdSchoolName() {
		return fdSchoolName;
	}

	public void setFdSchoolName(String fdSchoolName) {
		this.fdSchoolName = fdSchoolName;
	}

	public String getFdMajor() {
		return fdMajor;
	}

	public void setFdMajor(String fdMajor) {
		this.fdMajor = fdMajor;
	}

	public String getFdDegree() {
		return fdDegree;
	}

	public void setFdDegree(String fdDegree) {
		this.fdDegree = fdDegree;
	}

	public String getFdEducation() {
		return fdEducation;
	}

	public void setFdEducation(String fdEducation) {
		this.fdEducation = fdEducation;
	}

}
