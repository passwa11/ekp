package com.landray.kmss.sys.simplecategory.forms;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.category.forms.SysCategoryBaseForm;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryModel;

/**
 * 简单分类默认FORM实现
 * 
 * @author wubin
 * 
 */
public class SysSimpleCategoryForm extends SysCategoryBaseForm implements
		ISysSimpleCategoryForm {

	@Override
    public Class getModelClass() {
		return SysSimpleCategoryModel.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.setDefaultPropertyMap());
		}
		return toModelPropertyMap;
	}

}
