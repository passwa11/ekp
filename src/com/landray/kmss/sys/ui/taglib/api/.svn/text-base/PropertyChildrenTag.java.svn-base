package com.landray.kmss.sys.ui.taglib.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import com.alibaba.fastjson.JSONArray;
import com.landray.kmss.sys.ui.taglib.widget.BaseTag;

/**
 * 该类代表输出一个数据对象子项的一个字段下的拥有children的属性
 * 
 *
 */
@SuppressWarnings("serial")
public class PropertyChildrenTag extends BaseTag
		implements ListParentTagElement {



	private JSONArray children = null;

	private Map<String, Object> value = null;

	@Override
	public void release() {
		children = null;
		value = null;

		super.release();
	}

	@Override
	public int doStartTag() throws JspException {
		children = new JSONArray();
		value = new HashMap<String, Object>();

		return EVAL_BODY_INCLUDE;
	}


	@Override
	public int doEndTag() throws JspException {
		try {
			Tag parent = getParent();
			if (parent instanceof PropertyTag) {
				PropertyTag propertyTag = (PropertyTag) parent;

				propertyTag.addKeyValue(ResponseConstant.KEY_NAME_CHILDREN, children);
				if (value != null && !value.isEmpty()) {
					propertyTag.addKeyValue(ResponseConstant.KEY_NAME_VALUE,
							value);
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

	@Override
	public void addItem(Map<String, Object> child) {
		if (child != null && children != null) {
			// 如果包含 value值 提取出来放入 value哈希表里
			if (child.containsKey(ResponseConstant.KEY_NAME_VALUE)) {
				Object keyValue = child.remove(ResponseConstant.KEY_NAME_VALUE);
				Object key = child.get(ResponseConstant.KEY_NAME_KEY);
				if (keyValue != null && key != null) {
					value.put(key.toString(), keyValue);
				}
			}
			children.add(child);
		}
	}
}
