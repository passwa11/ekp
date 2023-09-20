package com.landray.kmss.sys.portal.taglib;

import com.landray.kmss.sys.portal.util.PortalUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class LogoTag extends SimpleTagSupport {
	@Override
	public void doTag() throws JspException {
		try {
			PageContext ctx = (PageContext) getJspContext();
			HttpServletRequest request = (HttpServletRequest) ctx.getRequest();
			String logo = PortalUtil.getPortalInfo(request).getLogo();
			StringBuilder sb = new StringBuilder();
			sb.append("<div class='lui_portal_header_logo_div' onclick=\"window.open('" + request.getContextPath() + "/','_self')\" title='" + ResourceUtil.getString("home.logoTitle") + "'>");
			sb.append("<img class='lui_portal_header_logo_img' src='");
			if (StringUtil.isNull(logo)) {
				logo = "/resource/images/logo.png";
			}
			sb.append(request.getContextPath() + logo);
			sb.append("'/>");
			sb.append("</div>");
			ctx.getOut().print(sb.toString());
		} catch (Exception e) {
			throw new JspException(e);
		}
	}
}
