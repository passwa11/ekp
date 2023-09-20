/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.widget.ref;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.taglib.template.AbstractTemplateTag;
import com.landray.kmss.sys.ui.taglib.template.TemplateContext;
import com.landray.kmss.sys.ui.xml.model.SysUiCombin;

/**
 * 
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public abstract class RefBaseTag extends AbstractTemplateTag {

	protected Map<String, Object> varParams = new HashMap<String, Object>();

	protected String ref = null;

	public void setRef(String ref) {
		this.ref = ref;
	}

	public void addAllParams(Map<String, Object> params) {
		varParams.putAll(params);
	}

	public void addParam(String key, Object value) {
		varParams.put(key, value);
	}

	@Override
	protected String getFile() throws JspTagException {
		if (file == null && ref == null) {
			throw new JspTagException("file 同 ref 属性不可以同时为空!");
		}
		if (file == null) {
			// build file from ref
			SysUiCombin combin = SysUiPluginUtil.getCombins().get(ref);
			if (combin == null) {
				throw new JspTagException("ref '" + ref
						+ "' combin is not exist!");
			}
			file = combin.getFdFile();
		}
		return file;
	}

	@Override
    public int doStartTag() throws JspException {
		(new TemplateContext()).bind(pageContext);
		return EVAL_BODY_INCLUDE;
	}

	protected void beforeEndTag() {
		AttributeUtil.set(pageContext, "varParams", varParams);
	}

	protected void afterEndTag() {
		AttributeUtil.release(pageContext, "varParams");
	}

	@Override
    public int doEndTag() throws JspException {

		try {
			beforeEndTag();
			getTemplateContext().asInclude();
			String value = acquireString();
			printString(value);
			afterEndTag();
		} finally {
			getTemplateContext().release(pageContext);
		}

		return super.doEndTag();
	}

	@Override
	public void release() {
		varParams = new HashMap<String, Object>();
		super.release();
	}
}
