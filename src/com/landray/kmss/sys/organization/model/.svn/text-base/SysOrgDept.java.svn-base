package com.landray.kmss.sys.organization.model;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.organization.forms.SysOrgDeptForm;
import com.landray.kmss.sys.organization.interfaces.ISysOrgDept;

/**
 * 部门
 * 
 * @author 叶中奇
 */
public class SysOrgDept extends SysOrgElement implements ISysOrgDept {
	public SysOrgDept() {
		super();
		setFdOrgType(new Integer(ORG_TYPE_DEPT));
	}

	@Override
    public Class getFormClass() {
		return SysOrgDeptForm.class;
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
