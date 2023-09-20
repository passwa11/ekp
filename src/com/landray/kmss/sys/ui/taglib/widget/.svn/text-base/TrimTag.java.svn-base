/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.widget;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * 修减前后字符串内容
 * 
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public class TrimTag extends BodyTagSupport {

	private String sign = ",";

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_BUFFERED;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			if (getBodyContent() != null) {
				String body = getBodyContent().getString();
				body = body.trim();
				while (body.startsWith(sign) || body.endsWith(sign)) {
					if (body.startsWith(sign)) {
						body = body.substring(1);
					}
					if (body.endsWith(sign)) {
						body = body.substring(0, body.length() - 1);
					}
				}
				try {
					pageContext.getOut().append(body);
				} catch (IOException e) {
					throw new JspTagException("输出内容出错:", e);
				}
			}
		} finally {
			release();
		}
		return super.doEndTag();
	}

	@Override
	public void release() {
		super.release();
		sign = ",";
	}

}
