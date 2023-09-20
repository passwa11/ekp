package com.landray.kmss.sys.praise.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.praise.model.SysPraiseInfoCategory;
import com.landray.kmss.sys.simplecategory.forms.SysSimpleCategoryAuthTmpForm;

public class SysPraiseInfoCategoryForm extends SysSimpleCategoryAuthTmpForm {
	
	@Override
    public Class<SysPraiseInfoCategory> getModelClass() {
		return SysPraiseInfoCategory.class;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdParentId", new FormConvertor_IDToModel("fdParent", SysPraiseInfoCategory.class));
		}
		return toModelPropertyMap;
	}

}
