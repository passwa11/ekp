package com.landray.kmss.sys.praise.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.praise.forms.SysPraiseInfoCategoryForm;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryAuthTmpModel;

public class SysPraiseInfoCategory extends SysSimpleCategoryAuthTmpModel{

	@Override
    public Class<SysPraiseInfoCategoryForm> getFormClass() {
		return SysPraiseInfoCategoryForm.class;
		
	}
	
	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdParent.fdId", "fdParentId");
			toFormPropertyMap.put("fdParent.fdName", "fdParentName");
		}
		return toFormPropertyMap;
	}
}
