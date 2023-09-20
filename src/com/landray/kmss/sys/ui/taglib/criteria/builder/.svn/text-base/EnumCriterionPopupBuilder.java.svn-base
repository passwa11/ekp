/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.criteria.builder;

import java.util.Map;

import javax.servlet.jsp.PageContext;

import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;

/**
 * @author 傅游翔
 * 
 */
public class EnumCriterionPopupBuilder extends EnumCriterionBuilder implements
		CriterionPopupBuilder {

	@Override
	public String build(SysDictModel model, SysDictCommonProperty property,
			PageContext pageContext, Map<String, Object> attrs)
			throws Exception {
		String selectHtml = buildSelectHtml(property);

		String title = getTitle(property);
		return buildCriterionPopup(title, property.getName(), selectHtml);
	}
}
