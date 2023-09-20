/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.criteria;

import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.ui.taglib.criteria.builder.CriterionPopupBuilder;
import com.landray.kmss.sys.ui.taglib.criteria.builder.CriterionPopupBuilderFactory;

/**
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public class AutoPopupBoxTag extends AutoCriteriaTag {

	@Override
    protected CriterionPopupBuilder lookupCriterion(
			SysDictCommonProperty property) {
		return CriterionPopupBuilderFactory.getBuilder(property);
	}
}
