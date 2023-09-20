package com.landray.kmss.sys.portal.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.ui.taglib.template.TargetUrlContentAcquirer;
import com.landray.kmss.sys.ui.taglib.template.AbstractTemplateTag.ParamManager;

public class WidgetTag extends SimpleTagSupport {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PortletTag.class);
	private static final String charEncoding = "UTF-8";
	private ParamManager params;
	protected String file;

	public String getFile() throws JspTagException {
		if (this.params != null) {
			file = params.aggregateParams(file, "UTF-8");
		}
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public void addParam(String name, String value) {
		if (params == null) {
			params = new ParamManager();
		}
		params.addParameter(name, value);
	}

	@Override
    public void doTag() throws JspException {
		PageContext pageContext = (PageContext) getJspContext();
		JspWriter out = pageContext.getOut();
		JspFragment f = getJspBody();
		if (f != null) {
			try {
				f.invoke(out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			String targetUrl = getFile();
			targetUrl = TargetUrlContentAcquirer.coverUrl(targetUrl,
					pageContext);
			String value = new TargetUrlContentAcquirer(targetUrl, pageContext,
					charEncoding).acquireString();
			out.write(value);
		} catch (Exception e) {
			logger.error(e.toString());
			try {
				out.write("引入部件" + getFile() + "时出错");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
