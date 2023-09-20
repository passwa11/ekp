package com.landray.kmss.sys.ui.taglib.widget.ref;

import javax.servlet.jsp.JspException;

import com.landray.kmss.sys.ui.taglib.widget.BaseTag;
import com.landray.kmss.util.StringUtil;

public class VarParamTag extends BaseTag {
	private String name;
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = StringUtil.XMLEscape(value.toString());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int doEndTag() throws JspException {
		RefBaseTag base = (RefBaseTag) findAncestorWithClass(this,
				RefBaseTag.class);
		String _value = this.getValue();
		if (StringUtil.isNull(_value)) {
			_value = this.bodyContent.getString();
		}
		base.addParam(this.getName(), _value);
		return super.doEndTag();
	}

	@Override
	public void release() {
		this.name = null;
		this.value = null;
		super.release();
	}
}
