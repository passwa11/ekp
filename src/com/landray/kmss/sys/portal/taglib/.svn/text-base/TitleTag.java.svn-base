package com.landray.kmss.sys.portal.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.landray.kmss.sys.config.util.LicenseUtil;
import com.landray.kmss.sys.portal.util.PortalUtil;
import com.landray.kmss.util.StringUtil;

public class TitleTag extends SimpleTagSupport {
	@Override
	public void doTag() throws JspException {
		try {
			PageContext pageContext = (PageContext) getJspContext();
			if (pageContext.getRequest().getParameter("designertime") != null
					&& "yes"
							.equals(pageContext.getRequest().getParameter("designertime"))) {

			} else {
				String title = PortalUtil.getPortalInfo(
						(HttpServletRequest) pageContext.getRequest())
						.getPortalPageName();
				String licen = LicenseUtil.get("license-title");
				if (StringUtil.isNotNull(licen)) {
					title = title + licen;
				}
				pageContext.getOut().print(title);
			}
		} catch (Exception e) {
			throw new JspException(e);
		}
	}
}
