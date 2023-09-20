package com.landray.kmss.sys.ui.taglib.api;

import javax.servlet.jsp.JspException;

/**
 * 该类代表输出一个数据对象子项的一个字段下的拥有enum的属性
 * 
 *
 */
@SuppressWarnings("serial")
public class PropertyEnumTag extends PropertyMapAttributeTag {
	private static final String KEY_NAME_ENUM = "enum";

	
	@Override
	public int doStartTag() throws JspException {
		setKey(KEY_NAME_ENUM);
		return super.doStartTag();
	}
}
