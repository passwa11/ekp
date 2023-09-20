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
public interface CriterionBuilder {

	public boolean support(SysDictCommonProperty property);

	public String build(SysDictModel model, SysDictCommonProperty property,
			PageContext pageContext, Map<String, Object> attrs)
			throws Exception;
}
