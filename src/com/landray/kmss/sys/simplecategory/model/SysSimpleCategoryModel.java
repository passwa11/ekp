package com.landray.kmss.sys.simplecategory.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.category.model.SysCategoryBaseModel;
import com.landray.kmss.sys.simplecategory.forms.SysSimpleCategoryForm;

/**
 * 简单分类默认实现类
 * 
 * @author wubin
 * 
 */
public class SysSimpleCategoryModel extends SysCategoryBaseModel implements
		ISysSimpleCategoryModel {
	/**
	 * 是否继承父类别可维护者。该设置项已经废除，强制返回true
	 */
	@Override
    @Deprecated
	public Boolean getFdIsinheritMaintainer() {
		return Boolean.TRUE;
	}

	@Override
    @Deprecated
	public void setFdIsinheritMaintainer(Boolean fdIsinheritMaintainer) {
	}

	/**
	 * 是否继承父类别可使用者，该设置项已经废除，强制返回false
	 */
	@Override
    @Deprecated
	public Boolean getFdIsinheritUser() {
		return Boolean.FALSE;
	}

	@Override
    @Deprecated
	public void setFdIsinheritUser(Boolean fdIsinheritUser) {
	}

	@Override
    public Class getFormClass() {
		return SysSimpleCategoryForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(setDefaultPropertyMap());
		}
		return toFormPropertyMap;
	}
}
