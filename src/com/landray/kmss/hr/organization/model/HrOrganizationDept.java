package com.landray.kmss.hr.organization.model;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.organization.forms.HrOrganizationDeptForm;
import com.landray.kmss.hr.organization.interfaces.IHrOrgDept;

/**
  * 人员部门信息
  */
public class HrOrganizationDept extends HrOrganizationElement implements IHrOrgDept {

	public HrOrganizationDept() {
		super();
		setFdOrgType(new Integer(HR_TYPE_DEPT));
	}

	@Override
	public Class getFormClass() {
		return HrOrganizationDeptForm.class;
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
					new ModelConvertor_ModelListToString("authElementAdminIds:authElementAdminNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
	}

	/*
	 * 岗位名称关联部门名称
	 */
	private Boolean fdIsRelation;

	public Boolean getFdIsRelation() {
		return fdIsRelation;
	}

	public void setFdIsRelation(Boolean fdIsRelation) {
		this.fdIsRelation = fdIsRelation;
	}

}
