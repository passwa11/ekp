package com.landray.kmss.sys.portal.taglib;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.config.util.LicenseUtil;
import com.landray.kmss.sys.portal.util.PortalUtil;
import com.landray.kmss.sys.portal.util.SysPortalInfo;
import com.landray.kmss.sys.ui.taglib.template.AbstractTemplateTag.ParamManager;
import com.landray.kmss.sys.ui.taglib.template.TargetUrlContentAcquirer;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

public class HeaderTag extends SimpleTagSupport implements DynamicAttributes {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HeaderTag.class);
	private ParamManager params;
	private static final String charEncoding = "UTF-8";

	private String ref;

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	private String scene;

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	@Override
    public void doTag() throws JspException {
		PageContext pageContext = (PageContext) getJspContext();
		licenseNotify(pageContext);
		outputHeaderHtml(pageContext);
		if (pageContext.getRequest().getParameter("designertime") != null
				&& "yes"
						.equals(pageContext.getRequest().getParameter("designertime"))) {
			try {
				pageContext
						.getOut()
						.print(
								"<div data-lui-mark='template:header' key='headerDesigner'></div>");
			} catch (IOException e) {
				logger.error(e.toString());
			}
		} else {
			String file = getFile();
			if (StringUtil.isNotNull(file)) {
				String targetUrl = TargetUrlContentAcquirer.coverUrl(file,
						pageContext);
				String value = new TargetUrlContentAcquirer(targetUrl,
						pageContext, charEncoding).acquireString();
				try {
					value = "<div class='lui_portal_header' style='display:none;'>"
							+ value + "</div>";
					value += "<script>seajs.use(['lui/jquery','theme!portal'],function($){$('.lui_portal_header').show();});</script>";
					pageContext.getOut().print(value);
				} catch (IOException e) {
					logger.error(e.toString());
				}
			} else {
				// 无页眉信息
				/*
				 * 匿名门户无页眉，则不要发送通知
				 * @author 吴进 by 20191203
				 */
				if (!"anonymous".equals(this.scene)) {
					outputPortalNotice(pageContext);
				}
			}
		}
		ref = null;
		scene = null;
		params = null;
	}

	private void licenseNotify(PageContext pageContext) {
		try {
			HttpSession session = pageContext.getSession();
			String notify = (String) session
					.getAttribute("KMSS_LICENSE_NOTIFY");
			if (notify != null) {
				return;
			}
			session.setAttribute("KMSS_LICENSE_NOTIFY", "TRUE");
			String subject = (String) session
					.getAttribute("KMSS_NOTIFY_REGIST_OVERFLOW");
			if (subject != null) {
				subject = URLDecoder.decode(subject, "UTF-8");
				session.removeAttribute("KMSS_NOTIFY_REGIST_OVERFLOW");
			}
			subject = StringUtil.linkString(LicenseUtil
					.getExpireSubject(LicenseUtil.NOTIFY_SYS_BROWSER),
					"\\r\\n", subject);
			if (subject == null) {
				return;
			}
			pageContext.getOut().write(
					"<script>alert('" + StringUtil.replace(subject, "'", "\\'")
							+ "');</script>");
		} catch (IOException e) {
			logger.error(e.toString());
		}
	}

	private void outputHeaderHtml(PageContext pageContext) {
		try {
			String html = (String) pageContext.getRequest().getAttribute(
					"SYS_PORTAL_HEADER_HTML");
			if (StringUtil.isNotNull(html)) {
				pageContext.getOut().write(html);
			}
		} catch (IOException e) {
			logger.error(e.toString());
		}
	}

	protected String getRefFile() throws JspTagException {
		String file = PortalUtil.getPortalHeaders().get(this.ref).getFdFile();
		if (this.params == null) {
			this.params = new ParamManager();
		}
		Iterator<Entry<String, String>> iter = this.getVars().entrySet()
				.iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			this.params.addParameter(entry.getKey(), entry.getValue());
		}
		if (this.params != null) {
			if (StringUtil.isNotNull(this.getScene())) {
				this.params.addParameter("scene", this.getScene());
			}
			file = params.aggregateParams(file, "UTF-8");
		}
		return file;
	}

	protected String getFile() throws JspTagException {
		try {
			if (StringUtil.isNotNull(this.ref)) {
				return getRefFile();
			}
			PageContext ctx = (PageContext) getJspContext();
			SysPortalInfo info = PortalUtil
					.getPortalInfo((HttpServletRequest) ctx.getRequest());
			String header = info.getHeaderId();
			if (StringUtil.isNotNull(header)) {
				String file = PortalUtil.getPortalHeaders().get(header)
						.getFdFile();
				List<String> names = new ArrayList<String>();
				if (this.getVars().size() > 0) {
					if (this.params == null) {
						this.params = new ParamManager();
					}
					Iterator<Entry<String, String>> iter = this.getVars()
							.entrySet().iterator();
					while (iter.hasNext()) {
						Entry<String, String> entry = iter.next();
						names.add(entry.getKey());
						this.params.addParameter(entry.getKey(), entry
								.getValue());
					}
				}
				if (StringUtil.isNotNull(info.getHeaderVars())) {
					JSONObject json = JSONObject.fromObject(info
							.getHeaderVars());
					if (json != null && !json.isNullObject()) {
						if (this.params == null) {
							this.params = new ParamManager();
						}
						Iterator<?> keys = json.keys();
						while (keys.hasNext()) {
							String key = keys.next().toString();
							if (!names.contains(key)) {
								this.params.addParameter(key, json
										.getString(key));
							}
						}
					}
				}
				if (this.params != null) {
					if (StringUtil.isNotNull(this.getScene())) {
						this.params.addParameter("scene", this.getScene());
					}
					file = params.aggregateParams(file, "UTF-8");
				}
				return file;
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return null;
	}

	protected Map<String, String> vars;

	public Map<String, String> getVars() {
		if (this.vars == null) {
			this.vars = new HashMap<String, String>();
		}
		return vars;
	}

	@Override
    public void setDynamicAttribute(String uri, String key, Object value)
			throws JspException {
		if (key.startsWith("var-")) {
			this.getVars().put(key.substring(4), value.toString());
		}
	}

	private void outputPortalNotice(PageContext pageContext) {
		try {
			String noticeUrl = PortalUtil.getPortalNoticeUrl();
			String value = new TargetUrlContentAcquirer(noticeUrl,
					pageContext, charEncoding).acquireString();
			pageContext.getOut().print(value);
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}
}
