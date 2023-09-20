package com.landray.kmss.sys.ui.taglib.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import com.landray.kmss.sys.ui.taglib.widget.BaseTag;

/**
 * 该类代表输出一个数据对象子项的一个字段下的拥有children属性下的一个child
 * 
 *
 */
@SuppressWarnings("serial")
public class PropertyChildTag extends BaseTag {

	private String key = null;

	private String dataType = null;

	private Object value = null;

	@Override
	public void release() {
		key = null;
		value = null;
		dataType = null;

		super.release();
	}

	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_INCLUDE;
	}


	@Override
	public int doEndTag() throws JspException {
		try {
			Tag parent = getParent();
			if (parent instanceof ListParentTagElement) {
				ListParentTagElement listParentTagElement = (ListParentTagElement) parent;

				if (key != null && dataType != null) {
					Map<String, Object> valuesMap = new HashMap<String, Object>(
							3);
					valuesMap.put(ResponseConstant.KEY_NAME_KEY, key);
					valuesMap.put(ResponseConstant.KEY_NAME_DATA_TYPE,
							dataType);
					if (value != null) {
						valuesMap.put(ResponseConstant.KEY_NAME_VALUE, value);
					}
					listParentTagElement.addItem(valuesMap);
				}
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

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
