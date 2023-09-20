package com.landray.kmss.hr.staff.model;


import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.staff.forms.HrStaffPerformanceSearchForm;
import com.landray.kmss.util.DateUtil;

public class HrStaffPerformanceSearch extends HrStaffBaseModel {

	private static final long serialVersionUID = 1L;
	private String fdStaffId;
	private String fdName;
	private String fdEvaluationIndex;
	private String fdEvaluationDimension;
	private String fdTargetValue;
	private Double fdWeight;
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

	public Date getFdExpiryDate() {
		return fdExpiryDate;
	}

	public void setFdExpiryDate(Date fdExpiryDate) {
		this.fdExpiryDate = fdExpiryDate;
	}



	private String fdFirstLevelDepartment;
	private String fdSecondLevelDepartment;
	private String fdThirdLevelDepartment;
	private Date fdExpiryDate;
	private Date fdBeginDate;
	private String fdJobNature;
	public String getFdJobNature() {
		return fdJobNature;
	}

	public void setFdJobNature(String fdJobNature) {
		this.fdJobNature = fdJobNature;
	}

	public Date getFdBeginDate() {
		return fdBeginDate;
	}

	public void setFdBeginDate(Date fdBeginDate) {
		this.fdBeginDate = fdBeginDate;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	

	private HrStaffEntry docMain;



	public String getFdStaffId() {
		return fdStaffId;
	}

	public void setFdStaffId(String fdStaffId) {
		this.fdStaffId = fdStaffId;
	}


	
	@Override
	public Class getFormClass() {
		// TODO Auto-generated method stub
		return HrStaffPerformanceSearchForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	public HrStaffEntry getDocMain() {
		return docMain;
	}

	public void setDocMain(HrStaffEntry docMain) {
		this.docMain = docMain;
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

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docMain.fdId", "docMainId");
			toFormPropertyMap.put("docMain.fdName", "docMainName");
			toFormPropertyMap.put("fdExpiryDate",
					new ModelConvertor_Common(
							"fdExpiryDate")
									.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdBeginDate",
					new ModelConvertor_Common(
							"fdBeginDate")
									.setDateTimeType(DateUtil.TYPE_DATE));
		}
		return toFormPropertyMap;
	}
}
