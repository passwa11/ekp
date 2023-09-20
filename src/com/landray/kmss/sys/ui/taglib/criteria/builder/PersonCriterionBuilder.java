/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.criteria.builder;

import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModelProperty;

/**
 * @author 傅游翔
 * 
 */
public class PersonCriterionBuilder extends ImportRefCriterionBuilder {

	@Override
    protected String getRefId() {
		return "criterion.sys.person";
	}

	@Override
    public boolean support(SysDictCommonProperty property) {
		if (property instanceof SysDictModelProperty) {
			String type = property.getType();
			if ("com.landray.kmss.sys.organization.model.SysOrgPerson"
					.equals(type)) {
				return true;
			}
			String djs = property.getDialogJS();
			if (djs == null || djs.length() == 0) {
				return false;
			}
			int index = djs.indexOf("ORG_TYPE_");
			if (index > -1 && djs.indexOf("ORG_TYPE_", index + 1) < 0
					&& djs.indexOf("ORG_TYPE_PERSON") > -1) {
				return true;
			}
		}
		return false;
	}

}
