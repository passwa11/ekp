package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceQualification;

/**
 * 资格证书
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public class HrStaffPersonExperienceQualificationForm extends
		HrStaffPersonExperienceBaseForm {
	private static final long serialVersionUID = 1L;

	// 证书名称
	private String fdCertificateName;
	// 颁发单位
	private String fdAwardUnit;

	@Override
	public Class getModelClass() {
		return HrStaffPersonExperienceQualification.class;
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
		this.fdCertificateName = null;
		this.fdAwardUnit = null;
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
