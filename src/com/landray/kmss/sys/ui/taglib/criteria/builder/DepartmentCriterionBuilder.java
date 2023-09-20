/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.criteria.builder;

import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModelProperty;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
 * @author 傅游翔
 * 
 */
public class DepartmentCriterionBuilder extends ImportRefCriterionBuilder
		implements CriterionBuilder {

	@Override
    public boolean support(SysDictCommonProperty property) {
		if (property instanceof SysDictModelProperty) {
			String type = property.getType();
			Class<?> clz = getTypeClass(type);
			if (!"com.landray.kmss.sys.organization.model.SysOrgPerson"
					.equals(type) && SysOrgElement.class.isAssignableFrom(clz)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected String getRefId() {
		return "criterion.sys.dept";
	}

}
