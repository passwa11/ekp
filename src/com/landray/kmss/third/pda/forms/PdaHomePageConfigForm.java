package com.landray.kmss.third.pda.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.pda.model.PdaHomePageConfig;
import com.landray.kmss.util.AutoArrayList;

public class PdaHomePageConfigForm extends ExtendForm {

	protected String fdName = null;

	protected String fdOrder = null;

	protected String fdCreateTime = null;

	protected String docAlterTime = null;

	protected String fdIconUrl = null;

	protected String docCreatorId = null;

	protected String docCreatorName = null;

	protected String docAlterorId = null;

	protected String docAlterorName = null;

	private String fdIsDefault = null;

	protected String fdRowsize = null;

	private AutoArrayList fdPortlets = new AutoArrayList(
			PdaHomePagePortletForm.class);

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

	public String getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(String fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	public String getDocAlterTime() {
		return docAlterTime;
	}

	public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	public String getFdIconUrl() {
		return fdIconUrl;
	}

	public void setFdIconUrl(String fdIconUrl) {
		this.fdIconUrl = fdIconUrl;
	}

	public String getDocCreatorId() {
		return docCreatorId;
	}

	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	public String getDocCreatorName() {
		return docCreatorName;
	}

	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	public String getDocAlterorId() {
		return docAlterorId;
	}

	public void setDocAlterorId(String docAlterorId) {
		this.docAlterorId = docAlterorId;
	}

	public String getDocAlterorName() {
		return docAlterorName;
	}

	public void setDocAlterorName(String docAlterorName) {
		this.docAlterorName = docAlterorName;
	}

	public AutoArrayList getFdPortlets() {
		return fdPortlets;
	}

	public void setFdPortlets(AutoArrayList fdPortlets) {
		this.fdPortlets = fdPortlets;
	}

	public String getFdIsDefault() {
		return fdIsDefault;
	}

	public void setFdIsDefault(String fdIsDefault) {
		this.fdIsDefault = fdIsDefault;
	}

	public String getFdRowsize() {
		return fdRowsize;
	}

	public void setFdRowsize(String fdRowsize) {
		this.fdRowsize = fdRowsize;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		fdName = null;
		fdOrder = null;
		fdCreateTime = null;
		docAlterTime = null;
		fdIconUrl = null;
		docCreatorId = null;
		docCreatorName = null;
		docAlterorId = null;
		docAlterorName = null;
		fdRowsize = "6";
		fdIsDefault = "0";
		fdPortlets = new AutoArrayList(PdaHomePagePortletForm.class);
	}

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel(
					"docCreator", SysOrgElement.class));
			toModelPropertyMap.put("docAlterorId", new FormConvertor_IDToModel(
					"docAlteror", SysOrgElement.class));

			toModelPropertyMap.put("fdPortlets",
					new FormConvertor_FormListToModelList("fdPortlets",
							"pdaHomePagePortlet"));
		}
		return toModelPropertyMap;
	}

	@Override
    public Class getModelClass() {
		return PdaHomePageConfig.class;
	}

}
