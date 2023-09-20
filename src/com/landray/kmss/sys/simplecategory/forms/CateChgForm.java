package com.landray.kmss.sys.simplecategory.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.forms.BaseForm;

public class CateChgForm extends BaseForm {

	protected String fdIds = null;

	public String getFdIds() {
		return fdIds;
	}

	public void setFdIds(String ids) {
		this.fdIds = ids;
	}

	protected String fdCateId = null;

	public String getFdCateId() {
		return fdCateId;
	}

	public void setFdCateId(String fdCateId) {
		this.fdCateId = fdCateId;
	}

	protected String modelName = null;

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	protected String cateModelName = null;

	public String getCateModelName() {
		return cateModelName;
	}

	public void setCateModelName(String cateModelName) {
		this.cateModelName = cateModelName;
	}

	protected String docFkName = null;

	public String getDocFkName() {
		return docFkName;
	}

	public void setDocFkName(String docFkName) {
		this.docFkName = docFkName;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdIds = null;
		modelName = null;
		fdCateId = null;
		cateModelName = null;
		docFkName = null;
		super.reset(mapping, request);
	}

}
