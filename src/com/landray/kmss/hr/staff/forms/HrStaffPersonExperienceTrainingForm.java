package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceTraining;

/**
 * 培训记录
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public class HrStaffPersonExperienceTrainingForm extends
		HrStaffPersonExperienceBaseForm {
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
	public Class getModelClass() {
		return HrStaffPersonExperienceTraining.class;
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
		this.fdTrainingName = null;
		this.fdTrainingUnit = null;
		this.fdCertificate = null;
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
