/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.criteria.builder;

import java.util.HashMap;
import java.util.Map;

import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModelProperty;
import com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel;

/**
 * @author 傅游翔
 * 
 */
public class SimpleCategoryCriterionBuilder extends ImportRefCriterionBuilder {

	@Override
	protected Map<String, Object> getParamMap(SysDictCommonProperty property) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("modelName", property.getType());
		return params;
	}

	@Override
    public boolean support(SysDictCommonProperty property) {
		if (property instanceof SysDictModelProperty) {
			String type = property.getType();
			Class<?> clz = getTypeClass(type);
			if (ISysSimpleCategoryModel.class.isAssignableFrom(clz)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected String getRefId() {
		return "criterion.sys.simpleCategory";
	}

}
