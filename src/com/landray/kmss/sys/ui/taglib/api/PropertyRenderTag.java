package com.landray.kmss.sys.ui.taglib.api;

import javax.servlet.jsp.JspException;

/**
 * 该类代表输出一个数据对象子项的一个字段下的拥有render的属性
 * 
 *
 */
@SuppressWarnings("serial")
public class PropertyRenderTag extends PropertyMapAttributeTag {
	private static final String KEY_NAME_RENDER = "render";
	private static final String KEY_NAME_LEVEL = "level";

	private String type = null;
	private String level = null;

	@Override
	public int doStartTag() throws JspException {
		setKey(KEY_NAME_RENDER);
		return super.doStartTag();
	}
	
	@Override
	public void release() {
		type = null;
		level = null;

		super.release();
	}

	@Override
    protected void buildValuesMap() {
		if (valuesMap != null && type != null) {
			valuesMap.put(ResponseConstant.KEY_NAME_TYPE, type);
			if (level != null) {
				valuesMap.put(KEY_NAME_LEVEL, level);
			}
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
}
