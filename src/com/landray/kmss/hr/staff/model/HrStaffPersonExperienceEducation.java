package com.landray.kmss.hr.staff.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.staff.forms.HrStaffPersonExperienceEducationForm;

/**
 * 教育记录
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public class HrStaffPersonExperienceEducation extends
		HrStaffPersonExperienceBase {
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
	public Class getFormClass() {
		return HrStaffPersonExperienceEducationForm.class;
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
