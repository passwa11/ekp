package com.landray.kmss.km.signature.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.km.signature.forms.KmSignatureCategoryForm;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryAuthTmpModel;

/**
 * 签章分类
 * 
 * @author weiby
 * @version 1.0 2014-12-13
 */
public class KmSignatureCategory extends SysSimpleCategoryAuthTmpModel {

	@Override
    public Class getFormClass() {
		return KmSignatureCategoryForm.class;
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
