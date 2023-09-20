/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.template;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TryCatchFinally;

/**
 * 区块标识标签，用来定义可替换区域
 * 
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public class BlockTag extends NamedTag implements TryCatchFinally {

	@Override
	public int doStartTag() throws JspException {
		if (!TemplateContext.hasContext(pageContext)
		// || getTemplateContext().isParse()
		) {
			return EVAL_BODY_INCLUDE;
		}

		TemplateContext tc = getTemplateContext();

		if (tc.hasReplaceBlock(name)) {
			JspBlock block = tc.getReplaceBlock(name);
			if (!block.isIncludeSuper()) {
				printString(block.getBlockContent());
				return SKIP_BODY;
			} else {
				return EVAL_BODY_BUFFERED;
			}
		}

		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		if (!TemplateContext.hasContext(pageContext)
		// || getTemplateContext().isParse()
		) {
			return super.doEndTag();
		}
		TemplateContext tc = getTemplateContext();

		if (!tc.hasReplaceBlock(name)) {
			return super.doEndTag();
		}

		JspBlock block = tc.getReplaceBlock(name);
		if (block.isIncludeSuper()) {
			block.setSuperContent(getBodyContent().getString());
			printString(block.getBlockContent());
		}
		return super.doEndTag();
	}
}
