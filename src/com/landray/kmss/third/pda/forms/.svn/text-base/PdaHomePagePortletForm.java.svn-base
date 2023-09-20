package com.landray.kmss.third.pda.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.pda.model.PdaHomePagePortlet;

public class PdaHomePagePortletForm extends ExtendForm {

	protected String fdName;

	protected String fdOrder;

	protected String fdType;

	protected String fdIconUrl;

	protected String fdFullRowFlag;

	protected String fdLabelId;

	protected String fdModuleId;

	protected String fdModuleName;

	protected String fdDataUrl;

	protected String fdContent;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
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

	public String getFdFullRowFlag() {
		return fdFullRowFlag;
	}

	public void setFdFullRowFlag(String fdFullRowFlag) {
		this.fdFullRowFlag = fdFullRowFlag;
	}

	public String getFdLabelId() {
		return fdLabelId;
	}

	public void setFdLabelId(String fdLabelId) {
		this.fdLabelId = fdLabelId;
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

	public String getFdContent() {
		return fdContent;
	}

	public void setFdContent(String fdContent) {
		this.fdContent = fdContent;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		fdName = null;
		fdOrder = null;
		fdType = "list";
		fdIconUrl = null;
		fdFullRowFlag = null;
		fdLabelId = null;
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
		return PdaHomePagePortlet.class;
	}

}
