package com.landray.kmss.sys.ui.taglib.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import com.landray.kmss.sys.ui.taglib.widget.BaseTag;
import com.landray.kmss.util.ResourceUtil;

/**
 * 该类代表输出一个数据对象子项的一个字段下的拥有enum属性下的一个枚举子项
 * 
 *
 */
@SuppressWarnings("serial")
public class PropertyEnumItemTag extends BaseTag {
	private String value = null;
	private String bundle = null;
	private String labelKey = null;

	@Override
	public void release() {
		value = null;
		bundle = null;
		labelKey = null;

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
			if (parent instanceof MapParentTagElement) {
				MapParentTagElement mapParentTagElement = (MapParentTagElement) parent;

				if (value != null && labelKey != null) {
					String labelValue = null;
					if (bundle != null) {
						labelValue = ResourceUtil.getString(
								(HttpServletRequest) pageContext.getRequest(),
								labelKey,
								bundle);
					} else {
						labelValue = ResourceUtil.getString(pageContext.getSession(), labelKey);
		
					}
					if (labelValue != null) {
						mapParentTagElement.addKeyValue(value, labelValue);
					}
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


	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getBundle() {
		return bundle;
	}

	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	public String getLabelKey() {
		return labelKey;
	}

	public void setLabelKey(String labelKey) {
		this.labelKey = labelKey;
	}
}
