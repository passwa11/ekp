package com.landray.kmss.hr.staff.forms;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.staff.model.HrStaffEntry;
import com.landray.kmss.hr.staff.model.HrStaffPersonFamily;

/**
 * 家庭信息
 * 
 */
public class HrStaffPersonFamilyForm extends HrStaffBaseForm {
	private static final long serialVersionUID = 1L;

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
	// 关联入职表
	private String docMainId;

	private String docMainName;

	private String docIndex;

	public String getFdRelated() {
		return fdRelated;
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
	public Class getModelClass() {
		return HrStaffPersonFamily.class;
	}

	public String getDocMainId() {
		return this.docMainId;
	}

	public void setDocMainId(String docMainId) {
		this.docMainId = docMainId;
	}

	public String getDocMainName() {
		return this.docMainName;
	}

	public void setDocMainName(String docMainName) {
		this.docMainName = docMainName;
	}

	public String getDocIndex() {
		return this.docIndex;
	}

	public void setDocIndex(String docIndex) {
		this.docIndex = docIndex;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docMainId", new FormConvertor_IDToModel(
					"docMain", HrStaffEntry.class));
		}
		return toModelPropertyMap;
	}
}
