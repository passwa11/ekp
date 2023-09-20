package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.staff.model.HrStaffAccumulationFund;
import com.landray.kmss.hr.staff.model.HrStaffPerformanceContractImport;
import com.landray.kmss.hr.staff.model.HrStaffSecurity;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.web.action.ActionMapping;

public class HrStaffPerformanceContractImportForm extends HrStaffBaseForm {
	private static final long serialVersionUID = 1L;

	private String docMainId;
	private String docMainName;
	
	private String fdStaffId;
	private String fdName;
	private String fdEvaluationIndex;
	private String fdEvaluationDimension;
	private String fdTargetValue;
	private Double fdWeight;
	private String fdExpiryDate;
	private String fdJobNature;
	public String getFdJobNature() {
		return fdJobNature;
	}

	public void setFdJobNature(String fdJobNature) {
		this.fdJobNature = fdJobNature;
	}

	public String getFdExpiryDate() {
		return fdExpiryDate;
	}

	public void setFdExpiryDate(String fdExpiryDate) {
		this.fdExpiryDate = fdExpiryDate;
	}

	private String fdFirstLevelDepartment;
	private String fdBeginDate;
	public String getFdBeginDate() {
		return fdBeginDate;
	}

	public void setFdBeginDate(String fdBeginDate) {
		this.fdBeginDate = fdBeginDate;
	}

	private String fdSecondLevelDepartment;

	private String fdThirdLevelDepartment;
	public String getDocMainId() {
		return docMainId;
	}

	public void setDocMainId(String docMainId) {
		this.docMainId = docMainId;
	}

	public String getDocMainName() {
		return docMainName;
	}

	public void setDocMainName(String docMainName) {
		this.docMainName = docMainName;
	}

	public String getFdStaffId() {
		return fdStaffId;
	}

	public String getFdEvaluationIndex() {
		return fdEvaluationIndex;
	}

	public void setFdEvaluationIndex(String fdEvaluationIndex) {
		this.fdEvaluationIndex = fdEvaluationIndex;
	}

	public String getFdEvaluationDimension() {
		return fdEvaluationDimension;
	}

	public void setFdEvaluationDimension(String fdEvaluationDimension) {
		this.fdEvaluationDimension = fdEvaluationDimension;
	}

	public String getFdTargetValue() {
		return fdTargetValue;
	}

	public void setFdTargetValue(String fdTargetValue) {
		this.fdTargetValue = fdTargetValue;
	}

	public Double getFdWeight() {
		return fdWeight;
	}

	public void setFdWeight(Double fdWeight) {
		this.fdWeight = fdWeight;
	}

	
	public void setFdStaffId(String fdStaffId) {
		this.fdStaffId = fdStaffId;
	}

	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);

	}

	public String getFdFirstLevelDepartment() {
		return fdFirstLevelDepartment;
	}

	public void setFdFirstLevelDepartment(String fdFirstLevelDepartment) {
		this.fdFirstLevelDepartment = fdFirstLevelDepartment;
	}

	public String getFdSecondLevelDepartment() {
		return fdSecondLevelDepartment;
	}

	public void setFdSecondLevelDepartment(String fdSecondLevelDepartment) {
		this.fdSecondLevelDepartment = fdSecondLevelDepartment;
	}

	public String getFdThirdLevelDepartment() {
		return fdThirdLevelDepartment;
	}

	public void setFdThirdLevelDepartment(String fdThirdLevelDepartment) {
		this.fdThirdLevelDepartment = fdThirdLevelDepartment;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}




	
	@Override
	public Class getModelClass() {
		// TODO Auto-generated method stub
		return HrStaffPerformanceContractImport.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;
	@Override
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docMainId", new FormConvertor_IDToModel(
					"docMain", SysOrgElement.class));

		}
		return toModelPropertyMap;
	}
}
