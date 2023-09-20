package com.landray.kmss.sys.ui.taglib.api;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import com.landray.kmss.sys.ui.taglib.widget.BaseTag;

/**
 * 该类代表输出一个数据对象子项的一个字段下的一个属性名及值
 * 
 *
 */
@SuppressWarnings("serial")
public class PropertyAttributeTag extends BaseTag {

	private String name = null;

	private Object value = null;

	@Override
	public void release() {
		name = null;
		value = null;

		super.release();
	}

	@Override
	public int doStartTag() throws JspException {
		if (name != null) {
			return EVAL_BODY_INCLUDE;
		} else {
			return SKIP_BODY;
		}
	}


	@Override
	public int doEndTag() throws JspException {
		try {
			Tag parent = getParent();
			if (parent instanceof MapParentTagElement
					&& name != null) {
				MapParentTagElement mapParentTagable = (MapParentTagElement) parent;

				mapParentTagable.addKeyValue(name, value);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new JspTagException(e);
		}
		registerToParent();
		release();
		return EVAL_PAGE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
