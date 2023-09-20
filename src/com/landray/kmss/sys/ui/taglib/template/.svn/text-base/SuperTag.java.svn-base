/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.template;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

/**
 * 调用模版中预定义内容或者父模块中预定义内容。
 * 
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public class SuperTag extends BaseTag {

	@Override
    public int doStartTag() throws JspException {
		NamedTag block = (NamedTag) findAncestorWithClass(this, NamedTag.class);
		if (block == null) {
			throw new JspTagException("super标签没在区块相关标签内");
		}
		printString(JspBlock.getBlockFlag());

		return SKIP_BODY;
	}
}
