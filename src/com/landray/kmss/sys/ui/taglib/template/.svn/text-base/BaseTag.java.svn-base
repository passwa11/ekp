/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.template;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 模版标签基础类
 * 
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public abstract class BaseTag extends BodyTagSupport implements TryCatchFinally {

	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	protected TemplateContext getTemplateContext() {
		return TemplateContext.get(pageContext);
	}

	@Override
    public void doCatch(Throwable t) throws Throwable {
		logger.error("标签(" + getClass().getSimpleName() + ")执行出现错误：", t);
		if (!(t instanceof JspException)) {
			throw new JspException(t);
		}
		throw t;
	}

	@Override
    public void doFinally() {
		try {
			release();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
    public void release() {
		super.release();
		clearResource();
	}

	protected void clearResource() {

	}

	protected void printString(String str) throws JspException {
		try {
			pageContext.getOut().print(str);
		} catch (IOException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("输出JSP内容出错", e);
			}
			//throw new JspTagException("输出JSP内容出错", e);
		}
	}

}
