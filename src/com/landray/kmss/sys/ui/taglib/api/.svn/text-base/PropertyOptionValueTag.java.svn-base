package com.landray.kmss.sys.ui.taglib.api;

import java.util.List;

import javax.servlet.jsp.JspException;

/**
 * 该类代表输出一个数据对象子项的一个字段下的拥有optionValue的属性
 * 
 *
 */
@SuppressWarnings("serial")
public class PropertyOptionValueTag extends PropertyMapAttributeTag {
	private static final String KEY_NAME_OPTION_VALUE = "optionValue";
	private static final String KEY_NAME_URI = "uri";

	private static String TYPE_STATIC = "static";

	private String type = null;
	private List<Object> value = null;
	private String uri = null;

	@Override
	public int doStartTag() throws JspException {
		setKey(KEY_NAME_OPTION_VALUE);
		return super.doStartTag();
	}
	
	@Override
	public void release() {
		type = null;
		value = null;
		uri = null;

		super.release();
	}

	@Override
    protected void buildValuesMap() {
		if (valuesMap != null && type != null) {
			// 静态类型 value必须有值
			if (TYPE_STATIC.equals(type)) {
				if (value != null) {
					valuesMap.put(ResponseConstant.KEY_NAME_TYPE, type);
					valuesMap.put(ResponseConstant.KEY_NAME_VALUE, value);
				}
			} else {
				if (uri != null) {
					valuesMap.put(ResponseConstant.KEY_NAME_TYPE, type);
					valuesMap.put(KEY_NAME_URI, uri);
				}
			}
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Object> getValue() {
		return value;
	}

	public void setValue(List<Object> value) {
		this.value = value;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
}
