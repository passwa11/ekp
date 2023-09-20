package com.landray.kmss.sys.portal.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.portal.util.PortalUtil;
import com.landray.kmss.sys.portal.util.SysPortalInfo;
import com.landray.kmss.sys.ui.taglib.template.TargetUrlContentAcquirer;
import com.landray.kmss.sys.ui.taglib.template.AbstractTemplateTag.ParamManager;
import com.landray.kmss.util.StringUtil;

public class FooterTag extends SimpleTagSupport implements DynamicAttributes {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(FooterTag.class);
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
		if (pageContext.getRequest().getParameter("designertime") != null
				&& "yes"
						.equals(pageContext.getRequest().getParameter("designertime"))) {
			try {
				pageContext
						.getOut()
						.print("<div data-lui-mark='template:footer' key='footerDesigner'></div>");
			} catch (IOException e) {
				logger.error(e.toString());
			}
		} else {
			String file = getFile();
			if (StringUtil.isNotNull(file)) {
				try {
					String targetUrl = TargetUrlContentAcquirer.coverUrl(file,
							pageContext);
					String value = new TargetUrlContentAcquirer(targetUrl,
							pageContext, charEncoding).acquireString();
					 
					value = "<div class='lui_portal_footer' style='display:none;'>"+value+"</div>";
					value += "<script>seajs.use(['lui/jquery','theme!portal'],function($){$('.lui_portal_footer').show();});</script>";
					pageContext.getOut().print(value);
				} catch (IOException e) {
					logger.error(e.toString());
				}
			} else {
				// 无页脚信息
			}
		}
		ref = null;
		scene = null;
		params = null;
	}

	protected String getRefFile() throws JspTagException {
		String file = PortalUtil.getPortalFooters().get(this.ref).getFdFile();
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
			String footer = info.getFooterId();
			if (StringUtil.isNotNull(footer)) {
				String file = PortalUtil.getPortalFooters().get(footer)
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
						this.params.addParameter(entry.getKey(),
								entry.getValue());
					}
				}
				if (StringUtil.isNotNull(info.getFooterVars())) {
					JSONObject json = JSONObject.fromObject(info
							.getFooterVars());
					if (json != null && !json.isNullObject()) {
						if (this.params == null) {
							this.params = new ParamManager();
						}
						Iterator<?> keys = json.keys();
						while (keys.hasNext()) {
							String key = keys.next().toString();
							if (!names.contains(key)) {
								this.params.addParameter(key,
										json.getString(key));
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
			e.printStackTrace();
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
}
