package com.landray.kmss.km.calendar.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.calendar.forms.KmCalendarRequestAuthForm;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

public class KmCalendarRequestAuth extends BaseModel
		implements ISysNotifyModel {

	public KmCalendarRequestAuth() {
		super();
	}

	/*
	 * 创建时间
	 */
	protected Date docCreateTime;

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/*
	 * 创建人
	 */
	protected SysOrgPerson docCreator = null;

	public SysOrgPerson getDocCreator() {
		return docCreator;
	}

	public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}

	/**
	 * 期望获得权限的人员
	 */
	protected List<SysOrgPerson> fdRequestPerson;

	public List<SysOrgPerson> getFdRequestPerson() {
		return fdRequestPerson;
	}

	public void setFdRequestPerson(List<SysOrgPerson> fdRequestPerson) {
		this.fdRequestPerson = fdRequestPerson;
	}

	/**
	 * 期望获得的权限
	 */
	protected String fdRequestAuth;

	public String getFdRequestAuth() {
		return fdRequestAuth;
	}

	public void setFdRequestAuth(String fdRequestAuth) {
		this.fdRequestAuth = fdRequestAuth;
	}

	@Override
	public Class getFormClass() {
		return KmCalendarRequestAuthForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdName", "docCreateName");
			toFormPropertyMap.put("docCreator.fdId", "docCreateId");
			toFormPropertyMap.put("fdRequestPerson",
					new ModelConvertor_ModelListToString(
							"fdRequestPersonIds:fdRequestPersonNames",
							"fdId:deptLevelNames"));
		}
		return toFormPropertyMap;
	}

}
