package com.landray.kmss.sys.portal.taglib;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.landray.kmss.util.StringUtil;

public class ParamTag extends SimpleTagSupport {
	private String name;
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
    public void doTag() throws JspException {
		PageContext pageContext = (PageContext) getJspContext();
		StringWriter sw = new StringWriter();
		JspFragment f = getJspBody();
		if (f != null) {
			try {
				f.invoke(sw);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (StringUtil.isNull(this.getValue())) {
			this.value = sw.toString();
		}

		WidgetTag widgetTag = (WidgetTag) findAncestorWithClass(this,
				WidgetTag.class);
		if (widgetTag == null) {

		} else {
			widgetTag.addParam(this.getName(), this.getValue());
		}
	}
}
