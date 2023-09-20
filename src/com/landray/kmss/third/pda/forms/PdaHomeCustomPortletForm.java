package com.landray.kmss.third.pda.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.pda.model.PdaHomeCustomPortlet;

public class PdaHomeCustomPortletForm extends ExtendForm {

	protected String fdName;

	protected String fdType;

	protected String fdIconUrl;

	protected String fdModuleId;

	protected String fdModuleName;

	protected String fdDataUrl;

	protected String fdTemplateClass;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	public String getFdIconUrl() {
		return fdIconUrl;
	}

	public void setFdIconUrl(String fdIconUrl) {
		this.fdIconUrl = fdIconUrl;
	}

	public String getFdModuleId() {
		return fdModuleId;
	}

	public void setFdModuleId(String fdModuleId) {
		this.fdModuleId = fdModuleId;
	}

	public String getFdModuleName() {
		return fdModuleName;
	}

	public void setFdModuleName(String fdModuleName) {
		this.fdModuleName = fdModuleName;
	}

	public String getFdDataUrl() {
		return fdDataUrl;
	}

	public void setFdDataUrl(String fdDataUrl) {
		this.fdDataUrl = fdDataUrl;
	}

	public String getFdTemplateClass() {
		return fdTemplateClass;
	}

	public void setFdTemplateClass(String fdTemplateClass) {
		this.fdTemplateClass = fdTemplateClass;
	}

	public static void setToModelPropertyMap(
			FormToModelPropertyMap toModelPropertyMap) {
		PdaHomeCustomPortletForm.toModelPropertyMap = toModelPropertyMap;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		fdName = null;
		fdType = "list";
		fdIconUrl = null;
		fdModuleId = null;
		fdModuleName = null;
		fdDataUrl = null;
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
    public Class getModelClass() {
		return PdaHomeCustomPortlet.class;
	}

}
