/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.criteria.builder;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.sys.config.dict.SysDictCommonProperty;

/**
 * @author 傅游翔
 * 
 */
public class CriterionPopupBuilderFactory {

	private static final List<CriterionPopupBuilder> cbuilder = new ArrayList<CriterionPopupBuilder>();
	static {
		cbuilder.add(new CalendarCriterionPopupBuilder());
		cbuilder.add(new EnumCriterionPopupBuilder());
		cbuilder.add(new CategoryPropertyCriterionPopupBuilder());
		cbuilder.add(new PersonCriterionPopupBuilder());
		cbuilder.add(new DepartmentCriterionPopupBuilder());
		cbuilder.add(new CategoryTemplateCriterionPopupBuilder());
		cbuilder.add(new SimpleCategoryCriterionPopupBuilder());
		cbuilder.add(new StrNumCriterionPopupBuilder());
	}

	public static CriterionPopupBuilder getBuilder(
			SysDictCommonProperty property) {
		for (CriterionPopupBuilder builder : cbuilder) {
			if (builder.support(property)) {
				return builder;
			}
		}
		return null;
	}
}
