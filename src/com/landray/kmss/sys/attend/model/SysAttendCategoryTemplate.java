package com.landray.kmss.sys.attend.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.attend.forms.SysAttendCategoryTemplateForm;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryAuthTmpModel;

/**
 * 签到组分类
 * 
 * @author admin
 *
 */
public class SysAttendCategoryTemplate extends SysSimpleCategoryAuthTmpModel {

	@Override
    public Class getFormClass() {
		return SysAttendCategoryTemplateForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
		}
		return toFormPropertyMap;
	}

}
