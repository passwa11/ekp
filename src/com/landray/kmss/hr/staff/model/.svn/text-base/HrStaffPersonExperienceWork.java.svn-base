package com.landray.kmss.hr.staff.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.staff.forms.HrStaffPersonExperienceWorkForm;

/**
 * 工作经历
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public class HrStaffPersonExperienceWork extends HrStaffPersonExperienceBase {
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
	public Class getFormClass() {
		return HrStaffPersonExperienceWorkForm.class;
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
