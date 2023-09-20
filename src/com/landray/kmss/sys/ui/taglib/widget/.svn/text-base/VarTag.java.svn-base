package com.landray.kmss.sys.ui.taglib.widget;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

public class VarTag extends BodyTagSupport implements TryCatchFinally {
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
    public int doStartTag() throws JspException {
		return super.doStartTag();
	}

	@Override
    public int doEndTag() throws JspException {
		WidgetTag parent = ((WidgetTag) findAncestorWithClass(this,
				WidgetTag.class));
		parent.getVars().put(this.getName(), this.getValue());
		return super.doEndTag();
	}

	@Override
	public void release() {
		name = null;
		value = null;
		super.release();
	}

	@Override
    public void doCatch(Throwable throwable) throws Throwable {
		throw throwable;
	}

	@Override
    public void doFinally() {
		release();
	}
}
