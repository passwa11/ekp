package com.landray.kmss.sys.ui.taglib.api;

import java.util.HashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import com.landray.kmss.sys.ui.taglib.widget.BaseTag;

/**
 * 该类代表输出一个数据对象子项的一个字段下的拥有复杂值的属性名如： render, validators, enum, optionValue
 * 
 *
 */
@SuppressWarnings("serial")
public class PropertyMapAttributeTag extends BaseTag
		implements MapParentTagElement {

	private String key = null;

	protected HashMap<String, Object> valuesMap = null;

	@Override
	public void release() {
		key = null;
		valuesMap = null;

		super.release();
	}

	@Override
	public int doStartTag() throws JspException {
		valuesMap = new HashMap<String, Object>();
		return EVAL_BODY_INCLUDE;
	}



	@Override
	public int doEndTag() throws JspException {
		try {
			Tag parent = getParent();
			if (parent instanceof PropertyTag
					&& key != null) {
				PropertyTag propertyTag = (PropertyTag) parent;

				buildValuesMap();
				propertyTag.addKeyValue(key, valuesMap);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new JspTagException(e);
		}
		registerToParent();
		release();
		return EVAL_PAGE;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	protected void buildValuesMap() {

	}

	@Override
	public void addKeyValue(String key, Object value) {
		if (key != null && value != null && valuesMap != null) {
			valuesMap.put(key, value);
		}
	}

	@Override
	public void removeKeyValue(String key) {
		if (valuesMap != null && key != null) {
			valuesMap.remove(key);
		}
	}
}
