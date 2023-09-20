package com.landray.kmss.hr.organization.model;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.organization.forms.HrOrganizationOrgForm;
import com.landray.kmss.hr.organization.interfaces.IHrOrgOrg;

/**
  * 机构信息
  */
public class HrOrganizationOrg extends HrOrganizationElement implements IHrOrgOrg {

	public HrOrganizationOrg() {
		super();
		setFdOrgType(new Integer(HR_TYPE_ORG));
	}

	@Override
	public Class getFormClass() {
		return HrOrganizationOrgForm.class;
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
