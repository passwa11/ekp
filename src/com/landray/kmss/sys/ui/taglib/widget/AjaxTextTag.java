package com.landray.kmss.sys.ui.taglib.widget;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sf.json.JSONObject;

import com.landray.kmss.util.StringUtil;

public class AjaxTextTag extends BodyTagSupport {
	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_BUFFERED;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			String body = "";
			if (getBodyContent() != null) {
				body = getBodyContent().getString().trim();				
			}
			try {
				HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
				String jsonpcallback = request.getParameter("jsonpcallback");
				String _data = request.getParameter("_data");
				if (StringUtil.isNotNull(jsonpcallback)) {
					JSONObject json = new JSONObject();
					json.put("text", body);
					pageContext.getOut().append(jsonpcallback + "(" + json.toString() + ")");
				} else if (StringUtil.isNotNull(_data)) {
					JSONObject json = new JSONObject();
					json.put("text", body);
					pageContext.getOut().append(json.toString());
				} else {
					pageContext.getOut().append(body);
				}
			} catch (IOException e) {
				throw new JspTagException("输出内容出错:", e);
			}
		} finally {
			release();
		}
		return super.doEndTag();
	}
}
