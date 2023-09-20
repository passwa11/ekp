package com.landray.kmss.sys.ui.taglib.widget;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 把内容体变成json对象放到一个变量中
 * 
 * @author 叶中奇
 */
public class JsonTag extends BodyTagSupport implements TryCatchFinally {
	private static final long serialVersionUID = -3868265557205635505L;

	private String var = null;

	private String scope = null;

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_BUFFERED;
	}

	@Override
	public int doEndTag() throws JspException {
		BodyContent body = getBodyContent();
		String content = body == null ? null : body.getString();
		if (content != null) {
			Object json = null;
			content = content.trim();
			if (content.startsWith("[")) {
				json = JSONArray.fromObject(content);
			} else if (content.startsWith("{")) {
				json = JSONObject.fromObject(content);
			}
			if (json != null) {
				if ("page".equals(scope)) {
					pageContext.setAttribute(var, json);
				} else if ("session".equals(scope)) {
					pageContext.getSession().setAttribute(var, json);
				} else {
					pageContext.getRequest().setAttribute(var, json);
				}
			}
		}

		return super.doEndTag();
	}

	@Override
	public void release() {
		var = null;
		scope = null;
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
