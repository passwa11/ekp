package com.landray.kmss.sys.portal.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.portal.util.PortalUtil;
import com.landray.kmss.sys.portal.util.SysPortalInfo;
import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;
import com.landray.kmss.util.StringUtil;

public class GuideTag extends WidgetTag {
	private static final long serialVersionUID = 1L;

	@Override
	public int doEndTag() throws JspException {
		if (pageContext.getRequest().getParameter("designertime") != null
				&& "yes"
						.equals(pageContext.getRequest().getParameter("designertime"))) {
			try {
				pageContext
						.getOut()
						.print("<div data-lui-mark='template:guide' key='guideDesigner'></div>");
			} catch (IOException e) {
				logger.error(e.toString());
			}
			return EVAL_PAGE;
		} else {
			return super.doEndTag();
		}
	}

	@Override
    protected void postBuildConfigJson(JSONObject json) {
		PageContext ctx = this.pageContext;
		try {
			SysPortalInfo info = PortalUtil
					.getPortalInfo((HttpServletRequest) ctx.getRequest());
			String guideId = info.getGuideId();
			if (StringUtil.isNotNull(guideId)) {
				json.put("guideId", info.getGuideId());
				json.put("guidecfg", info.getGuideCfg());
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
		super.postBuildConfigJson(json);
	}

	@Override
    public String getType() {
		return "sys/portal/sys_portal_guide/component/Guide!Guide";
	}

}
