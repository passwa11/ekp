package com.landray.kmss.sys.attend.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attend.forms.SysAttendCategoryBusinessForm;

public class SysAttendCategoryBusiness extends BaseModel {

	private String fdBusName;

	private String fdBusType;

	private String fdTemplateId;

	private String fdTemplateName;

	protected SysAttendCategory fdCategory;

	public String getFdBusName() {
		return fdBusName;
	}

	public void setFdBusName(String fdBusName) {
		this.fdBusName = fdBusName;
	}

	public String getFdBusType() {
		return fdBusType;
	}

	public void setFdBusType(String fdBusType) {
		this.fdBusType = fdBusType;
	}

	public String getFdTemplateId() {
		return fdTemplateId;
	}

	public void setFdTemplateId(String fdTemplateId) {
		this.fdTemplateId = fdTemplateId;
	}

	public String getFdTemplateName() {
		return fdTemplateName;
	}

	public void setFdTemplateName(String fdTemplateName) {
		this.fdTemplateName = fdTemplateName;
	}

	public SysAttendCategory getFdCategory() {
		return fdCategory;
	}

	public void setFdCategory(SysAttendCategory fdCategory) {
		this.fdCategory = fdCategory;
	}

	@Override
	public Class getFormClass() {
		return SysAttendCategoryBusinessForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdCategory.fdId", "fdCategoryId");
		}
		return toFormPropertyMap;
	}


}
