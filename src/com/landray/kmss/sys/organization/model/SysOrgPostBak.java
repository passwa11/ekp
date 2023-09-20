package com.landray.kmss.sys.organization.model;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.organization.forms.SysOrgPostForm;

/**
 * 岗位
 * 
 * @author 叶中奇
 */
public class SysOrgPostBak extends SysOrgElementBak implements IBaseModel {
	public SysOrgPostBak() {
		super();
		setFdOrgType(new Integer(ORG_TYPE_POST));
	}

	@Override
    public Class getFormClass() {
		return SysOrgPostForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdPersons",
					new ModelConvertor_ModelListToString(
							"fdPersonIds:fdPersonNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
	}
}
