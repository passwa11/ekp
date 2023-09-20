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
public class CriterionBuilderFactory {

	private static final List<CriterionBuilder> cbuilder = new ArrayList<CriterionBuilder>();
	static {
		cbuilder.add(new CalendarCriterionBuilder());
		cbuilder.add(new EnumCriterionBuilder());
		cbuilder.add(new CategoryPropertyCriterionBuilder());
		cbuilder.add(new PersonCriterionBuilder());
		cbuilder.add(new DepartmentCriterionBuilder());
		cbuilder.add(new AuthAreaCriterionBuilder());
		cbuilder.add(new CategoryTemplateCriterionBuilder());
		cbuilder.add(new SimpleCategoryCriterionBuilder());
		cbuilder.add(new StrNumCriterionBuilder());
	}

	public static CriterionBuilder getBuilder(SysDictCommonProperty property) {
		for (CriterionBuilder builder : cbuilder) {
			if (builder.support(property)) {
				return builder;
			}
		}
		return null;
	}
}
