/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.template;

import javax.servlet.jsp.JspException;

/**
 * 覆盖标签，在使用模版时，用来替换模版中区块默认内容。
 * 
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public class ReplaceTag extends NamedTag {

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

		if (getBodyContent() == null) {
			return super.doEndTag();
		}

		TemplateContext tc = getTemplateContext();

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
