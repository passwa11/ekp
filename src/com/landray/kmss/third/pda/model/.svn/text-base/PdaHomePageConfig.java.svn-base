package com.landray.kmss.third.pda.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.pda.forms.PdaHomePageConfigForm;

public class PdaHomePageConfig extends BaseModel {

	protected String fdName;

	protected Integer fdOrder;

	protected Date fdCreateTime;

	protected Date docAlterTime;

	protected String fdIconUrl;

	protected SysOrgElement docCreator;

	protected SysOrgElement docAlteror;

	private String fdIsDefault;
	
	private Integer fdRowsize;

	protected List<PdaHomePagePortlet> fdPortlets = new ArrayList<PdaHomePagePortlet>();

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

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	public Date getDocAlterTime() {
		return docAlterTime;
	}

	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	public String getFdIconUrl() {
		return fdIconUrl;
	}

	public void setFdIconUrl(String fdIconUrl) {
		this.fdIconUrl = fdIconUrl;
	}

	public SysOrgElement getDocCreator() {
		return docCreator;
	}

	public void setDocCreator(SysOrgElement docCreator) {
		this.docCreator = docCreator;
	}

	public SysOrgElement getDocAlteror() {
		return docAlteror;
	}

	public void setDocAlteror(SysOrgElement docAlteror) {
		this.docAlteror = docAlteror;
	}

	public String getFdIsDefault() {
		return fdIsDefault;
	}

	public void setFdIsDefault(String fdIsDefault) {
		this.fdIsDefault = fdIsDefault;
	}
	
	public Integer getFdRowsize() {
		return fdRowsize;
	}

	public void setFdRowsize(Integer fdRowsize) {
		this.fdRowsize = fdRowsize;
	}

	public List<PdaHomePagePortlet> getFdPortlets() {
		return fdPortlets;
	}

	public void setFdPortlets(List<PdaHomePagePortlet> fdPortlets) {
		this.fdPortlets = fdPortlets;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
			toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
			toFormPropertyMap.put("fdPortlets",
					new ModelConvertor_ModelListToFormList("fdPortlets"));
		}
		return toFormPropertyMap;
	}

	@Override
    public Class getFormClass() {
		return PdaHomePageConfigForm.class;
	}

}
