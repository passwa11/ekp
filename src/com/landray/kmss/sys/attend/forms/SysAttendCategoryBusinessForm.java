package com.landray.kmss.sys.attend.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryBusiness;

public class SysAttendCategoryBusinessForm extends ExtendForm {

	private String fdBusName;

	private String fdBusType;

	private String fdTemplateId;

	private String fdTemplateName;

	protected String fdCategoryId;

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

	public String getFdCategoryId() {
		return fdCategoryId;
	}

	public void setFdCategoryId(String fdCategoryId) {
		this.fdCategoryId = fdCategoryId;
	}

	@Override
	public Class getModelClass() {
		return SysAttendCategoryBusiness.class;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdBusName = null;
		fdBusType = null;
		fdTemplateId = null;
		fdCategoryId = null;
		fdTemplateName = null;
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdCategoryId",
					new FormConvertor_IDToModel("fdCategory",
							SysAttendCategory.class));
		}
		return toModelPropertyMap;
	}

}
