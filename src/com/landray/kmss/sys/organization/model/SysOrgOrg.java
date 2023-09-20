package com.landray.kmss.sys.organization.model;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.organization.forms.SysOrgOrgForm;
import com.landray.kmss.sys.organization.interfaces.ISysOrgOrg;

/**
 * 机构
 * 
 * @author 叶中奇
 */
public class SysOrgOrg extends SysOrgElement implements ISysOrgOrg {
	public SysOrgOrg() {
		super();
		setFdOrgType(new Integer(ORG_TYPE_ORG));
	}

	@Override
    public Class getFormClass() {
		return SysOrgOrgForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("hbmSuperLeader.fdId", "fdSuperLeaderId");
			toFormPropertyMap.put("hbmSuperLeader.fdName", "fdSuperLeaderName");
			toFormPropertyMap.put("authElementAdmins",
					new ModelConvertor_ModelListToString(
							"authElementAdminIds:authElementAdminNames",
							"fdId:fdName"));
		}
		return toFormPropertyMap;
	}

	/*
	 * 岗位名称关联机构名称
	 */
	private Boolean fdIsRelation;

	public Boolean getFdIsRelation() {
		return fdIsRelation;
	}

	public void setFdIsRelation(Boolean fdIsRelation) {
		this.fdIsRelation = fdIsRelation;
	}
}
