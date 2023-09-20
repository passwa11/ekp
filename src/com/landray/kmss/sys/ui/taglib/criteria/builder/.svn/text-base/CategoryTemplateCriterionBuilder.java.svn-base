/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.criteria.builder;

import java.util.HashMap;
import java.util.Map;

import com.landray.kmss.common.model.IBaseTemplateModel;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModelProperty;

/**
 * @author 傅游翔
 * 
 */
public class CategoryTemplateCriterionBuilder extends ImportRefCriterionBuilder
		implements CriterionBuilder {

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
			if (IBaseTemplateModel.class.isAssignableFrom(clz)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected String getRefId() {
		return "criterion.sys.category";
	}

}
