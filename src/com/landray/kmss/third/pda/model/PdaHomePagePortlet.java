package com.landray.kmss.third.pda.model;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.pda.forms.PdaHomePagePortletForm;

public class PdaHomePagePortlet extends BaseModel {

	protected String fdName;

	protected Integer fdOrder;

	protected String fdType;

	protected String fdIconUrl;

	protected String fdFullRowFlag;

	protected String fdLabelId;

	protected String fdModuleId;

	protected String fdModuleName;

	protected String fdContent;
	
	protected String fdDataUrl;
	
	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
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
    public Class getFormClass() {
		return PdaHomePagePortletForm.class;
	}

}
