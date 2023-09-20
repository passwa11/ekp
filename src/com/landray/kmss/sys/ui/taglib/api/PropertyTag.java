package com.landray.kmss.sys.ui.taglib.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import com.landray.kmss.sys.ui.taglib.widget.BaseTag;

/**
 * 该类代表输出一个数据对象子项的一个字段
 * 
 *
 */
@SuppressWarnings("serial")
public class PropertyTag extends BaseTag
		implements MapParentTagElement {

	private String name = null;

	private Map<String, Object> valuesMap = new HashMap<String, Object>();

	@Override
	public void release() {
		name = null;
		valuesMap = null;

		super.release();
	}

	@Override
	public int doStartTag() throws JspException {
		if (name != null) {
			if (valuesMap == null) {
				valuesMap = new HashMap<String, Object>();
			}
			return EVAL_BODY_INCLUDE;
		} else {
			return SKIP_BODY;
		}
	}


	@Override
	public int doEndTag() throws JspException {
		try {
			Tag parent = getParent();
			if (parent instanceof DataObjectTag
					&& !valuesMap.isEmpty()) {
				DataObjectTag responseDataObjectTag = (DataObjectTag) parent;

				responseDataObjectTag.addOrUpdateDataObjectProperty(name,
						valuesMap);
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

	public Map<String, Object> getValuesMap() {
		return valuesMap;
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
