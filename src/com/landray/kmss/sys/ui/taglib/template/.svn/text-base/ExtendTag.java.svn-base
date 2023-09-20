/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.template;

import javax.servlet.jsp.JspException;

/**
 * 模版继承定义标签，用来定义模版的继承
 * 
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public class ExtendTag extends IncludeTempate {

	@Override
    public int doStartTag() throws JspException {
		if (!isByInclude()) {
			(new TemplateContext()).bind(pageContext);

		} else if (hasTemplateContext()) {
			getTemplateContext().asParse();
		}

		return EVAL_BODY_INCLUDE;
	}

	@Override
    public int doEndTag() throws JspException {

		try {
			getTemplateContext().asInclude();
			String value = acquireString();
			printString(value);
		} finally {
			if (!isByInclude()) {
				getTemplateContext().release(pageContext);
			}
		}
		return super.doEndTag();
	}
}
