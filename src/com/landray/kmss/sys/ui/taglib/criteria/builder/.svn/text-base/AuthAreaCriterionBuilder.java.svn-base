/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.criteria.builder;

import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModelProperty;

/**
 * @author zhuhq
 * 
 */
public class AuthAreaCriterionBuilder extends ImportRefCriterionBuilder
		implements CriterionBuilder {

	@Override
    public boolean support(SysDictCommonProperty property) {
		if (property instanceof SysDictModelProperty) {
			String type = property.getType();
			Class<?> clz = getTypeClass(type);
			if ("com.landray.kmss.sys.authorization.model.SysAuthArea"
					.equals(type) && SysAuthArea.class.isAssignableFrom(clz)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected String getRefId() {
		return "criterion.sys.autharea";
	}

}
