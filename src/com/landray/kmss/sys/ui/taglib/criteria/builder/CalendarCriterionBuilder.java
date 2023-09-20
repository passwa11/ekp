/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.criteria.builder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.sys.config.dict.SysDictCommonProperty;

/**
 * @author 傅游翔
 * 
 */
public class CalendarCriterionBuilder extends ImportRefCriterionBuilder
		implements CriterionBuilder {

	@Override
    protected String getRefId() {
		return "criterion.sys.calendar";
	}

	protected final static List<String> calendarTypes = Collections
			.unmodifiableList(Arrays.asList("DateTime".toLowerCase(),
					"Date".toLowerCase(), "Time".toLowerCase()));

	@Override
    public boolean support(SysDictCommonProperty property) {
		return calendarTypes.contains(property.getType().toLowerCase());
	}

	@Override
	protected Map<String, Object> getParamMap(SysDictCommonProperty property) {
		Map<String, Object> params = new HashMap<String, Object>();
		String type = property.getType();
		if ("DateTime".equals(type)) {
			type = "Date";
		}
		params.put("type", "Criterion" + type + "Datas");
		return params;
	}

}
