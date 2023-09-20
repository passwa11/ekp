/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.criteria.builder;

/**
 * @author 傅游翔
 * 
 */
public class PersonCriterionPopupBuilder extends PersonCriterionBuilder
		implements CriterionPopupBuilder {

	@Override
    protected String getRefId() {
		return "criterion.sys.person.popup";
	}
}
