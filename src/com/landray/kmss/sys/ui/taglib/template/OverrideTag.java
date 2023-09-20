/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.template;

import javax.servlet.jsp.JspException;

/**
 * 重写区块内容，用在继承模版中，用来重新定义区块默认内容
 * 
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public class OverrideTag extends NamedTag {

	@Override
    public int doStartTag() throws JspException {
		TemplateContext tc = getTemplateContext();

		if (tc.hasReplaceBlock(name)) {
			if (tc.isIncludeSuper(name)) {
				return EVAL_BODY_BUFFERED;
			}

			return SKIP_BODY;
		}

		return EVAL_BODY_BUFFERED;
	}

	@Override
    public int doEndTag() throws JspException {
		TemplateContext tc = getTemplateContext();

		if (getBodyContent() == null) {
			return super.doEndTag();
		}

		String body = getBodyContent().getString();
		if (!tc.hasReplaceBlock(name)) {
			tc.putReplaceBlock(name, body);
		} else {
			if (tc.isIncludeSuper(name)) {
				JspBlock block = tc.getReplaceBlock(name);
				block.setSuperContent(body);
			}
		}

		return super.doEndTag();
	}
}
