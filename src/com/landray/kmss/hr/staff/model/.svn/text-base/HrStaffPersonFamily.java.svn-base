package com.landray.kmss.hr.staff.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.staff.forms.HrStaffPersonFamilyForm;

/**
 * 家庭信息
 */
public class HrStaffPersonFamily extends HrStaffBaseModel {
	private static final long serialVersionUID = 1L;
	private static ModelToFormPropertyMap toFormPropertyMap;
	// 关系
	private String fdRelated;
	// 名字
	private String fdName;
	// 任职单位
	private String fdCompany;
	// 职业
	private String fdOccupation;
	// 联系方式
	private String fdConnect;
	// 备注
	private String fdMemo;
	// 与入职表关联
	private HrStaffEntry docMain;

	private Integer docIndex;

	// 相关流程
	private String fdRelatedProcess;

	public String getFdRelated() {
		return fdRelated;
	}

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docMain.fdName", "docMainName");
			toFormPropertyMap.put("docMain.fdId", "docMainId");
		}
		return toFormPropertyMap;
	}

	public void setFdRelated(String fdRelated) {
		this.fdRelated = fdRelated;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdCompany() {
		return fdCompany;
	}

	public void setFdCompany(String fdCompany) {
		this.fdCompany = fdCompany;
	}

	public String getFdOccupation() {
		return fdOccupation;
	}

	public void setFdOccupation(String fdOccupation) {
		this.fdOccupation = fdOccupation;
	}

	public String getFdConnect() {
		return fdConnect;
	}

	public void setFdConnect(String fdConnect) {
		this.fdConnect = fdConnect;
	}

	public String getFdMemo() {
		return fdMemo;
	}

	public void setFdMemo(String fdMemo) {
		this.fdMemo = fdMemo;
	}
	@Override
	public Class getFormClass() {
		return HrStaffPersonFamilyForm.class;
	}

	public HrStaffEntry getDocMain() {
		return this.docMain;
	}

	public void setDocMain(HrStaffEntry docMain) {
		this.docMain = docMain;
	}

	public Integer getDocIndex() {
		return this.docIndex;
	}

	public void setDocIndex(Integer docIndex) {
		this.docIndex = docIndex;
	}

	public String getFdRelatedProcess() {
		return fdRelatedProcess;
	}

	public void setFdRelatedProcess(String fdRelatedProcess) {
		this.fdRelatedProcess = fdRelatedProcess;
	}
}
