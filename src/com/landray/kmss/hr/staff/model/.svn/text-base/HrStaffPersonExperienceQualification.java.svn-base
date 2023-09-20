package com.landray.kmss.hr.staff.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.staff.forms.HrStaffPersonExperienceQualificationForm;

/**
 * 资格证书
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public class HrStaffPersonExperienceQualification extends
		HrStaffPersonExperienceBase {
	private static final long serialVersionUID = 1L;

	// 证书名称
	private String fdCertificateName;
	// 颁发单位
	private String fdAwardUnit;

	@Override
	public Class getFormClass() {
		return HrStaffPersonExperienceQualificationForm.class;
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

	public String getFdCertificateName() {
		return fdCertificateName;
	}

	public void setFdCertificateName(String fdCertificateName) {
		this.fdCertificateName = fdCertificateName;
	}

	public String getFdAwardUnit() {
		return fdAwardUnit;
	}

	public void setFdAwardUnit(String fdAwardUnit) {
		this.fdAwardUnit = fdAwardUnit;
	}

}
