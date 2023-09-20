package com.landray.kmss.sys.organization.model;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.organization.forms.SysOrgPostForm;
import com.landray.kmss.sys.organization.interfaces.ISysOrgPost;

/**
 * 岗位
 * 
 * @author 叶中奇
 */
public class SysOrgPost extends SysOrgElement implements ISysOrgPost {
	public SysOrgPost() {
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
