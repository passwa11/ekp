package com.landray.kmss.hr.staff.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.staff.forms.HrStaffPersonExperienceTrainingForm;

/**
 * 培训记录
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public class HrStaffPersonExperienceTraining extends
		HrStaffPersonExperienceBase {
	private static final long serialVersionUID = 1L;

	// 培训名称
	private String fdTrainingName;
	// 培训单位
	private String fdTrainingUnit;

	//培训证书
	private String fdCertificate;
	public String getFdCertificate() {
		return fdCertificate;
	}

	public void setFdCertificate(String fdCertificate) {
		this.fdCertificate = fdCertificate;
	}

	@Override
	public Class getFormClass() {
		return HrStaffPersonExperienceTrainingForm.class;
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

	public String getFdTrainingName() {
		return fdTrainingName;
	}

	public void setFdTrainingName(String fdTrainingName) {
		this.fdTrainingName = fdTrainingName;
	}

	public String getFdTrainingUnit() {
		return fdTrainingUnit;
	}

	public void setFdTrainingUnit(String fdTrainingUnit) {
		this.fdTrainingUnit = fdTrainingUnit;
	}

}
