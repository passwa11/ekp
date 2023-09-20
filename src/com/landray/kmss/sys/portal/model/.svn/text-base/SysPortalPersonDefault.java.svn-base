package com.landray.kmss.sys.portal.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.portal.forms.SysPortalPersonDefaultForm;

/**
 * 个人默认门户
 *
 */
public class SysPortalPersonDefault extends BaseModel {

	private String fdPortalId;

	private String fdPortalName;

	private SysOrgPerson fdPerson;

	private Date docCreateTime;

	private boolean fdIsDefault;



	public String getFdPortalId() {
		return fdPortalId;
	}

	public void setFdPortalId(String fdPortalId) {
		this.fdPortalId = fdPortalId;
	}

	public String getFdPortalName() {
		return fdPortalName;
	}

	public void setFdPortalName(String fdPortalName) {
		this.fdPortalName = fdPortalName;
	}

	public SysOrgPerson getFdPerson() {
		return fdPerson;
	}

	public void setFdPerson(SysOrgPerson fdPerson) {
		this.fdPerson = fdPerson;
	}

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public boolean isFdIsDefault() {
		return fdIsDefault;
	}

	public void setFdIsDefault(boolean fdIsDefault) {
		this.fdIsDefault = fdIsDefault;
	}

	@Override
	public Class getFormClass() {
		return SysPortalPersonDefaultForm.class;
	}

	private static ModelToFormPropertyMap modelToFormPropertyMap = null;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (modelToFormPropertyMap == null) {
			modelToFormPropertyMap = new ModelToFormPropertyMap();
			modelToFormPropertyMap.putAll(super.getToFormPropertyMap());
			// 创建者
			modelToFormPropertyMap.put("fdPerson.fdId", "fdPersonId");
			modelToFormPropertyMap.put("fdPerson.fdName", "fdPersonName");
		}
		return modelToFormPropertyMap;
	}
}
