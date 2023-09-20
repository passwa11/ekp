package com.landray.kmss.sys.organization.model;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.organization.forms.SysOrgDeptForm;

/**
 * 部门
 * 
 * @author 叶中奇
 */
public class SysOrgDeptBak extends SysOrgElementBak implements IBaseModel {
	public SysOrgDeptBak() {
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
}
