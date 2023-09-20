package com.landray.kmss.sys.ui.taglib.widget.container;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.landray.kmss.sys.portal.util.SysPortalConfig;
import com.landray.kmss.sys.ui.taglib.interfaces.IOperations;
import com.landray.kmss.sys.ui.taglib.widget.LayoutTag;
import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.taglib.editor.IEditorBox;
import com.landray.kmss.web.taglib.editor.TagHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("serial")
public class ContentTag extends WidgetTag implements IOperations, IEditorBox {
	protected String layout;
	protected Boolean toggle;
	protected Boolean expand;

	public String getLayout() {
		if (StringUtil.isNull(this.layout)) {
			this.layout = "sys.ui.content.default";
		}
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public Boolean getToggle() {
		return toggle;
	}

	public void setToggle(Boolean toggle) {
		this.toggle = toggle;
	}

	public Boolean getExpand() {
		return expand;
	}

	public void setExpand(Boolean expand) {
		this.expand = expand;
	}

	protected String title;
	
	protected String countUrl;
	
	protected JSONArray operations;
	protected String titleicon;

	protected String titleimg;
	protected String subtitle;

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getTitleicon() {
		return titleicon;
	}

	public void setTitleicon(String titleicon) {
		this.titleicon = titleicon;
	}
	public String getTitleimg() {
		return titleimg;
	}

	public void setTitleimg(String titleimg) {
		this.titleimg = titleimg;
	}

	@Override
    public JSONArray getOperations() {
		if (this.operations == null) {
			this.operations = new JSONArray();
		}
		return operations;
	}

	public void setOperations(JSONArray operations) {
		this.operations = operations;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	

	public String getCountUrl() {
		return countUrl;
	}

	public void setCountUrl(String countUrl) {
		this.countUrl = countUrl;
	}

	@Override
    public void release() {
		this.subtitle = null;
		this.titleicon = null;
		this.titleimg = null;
		this.title = null;
		this.countUrl=null;
		this.operations = null;
		super.release();
	}

	@Override
    public String getType() {
		if (StringUtil.isNull(this.type)) {
			return "lui/panel!Content";
		} else {
			return this.type;
		}
	}

	@Override
    protected String acquireString(String body) throws Exception {
		StringBuilder sb = new StringBuilder();
		if (!hasLayout) {
			sb.append(LayoutTag.buildLayoutHtml(this, this.getLayout()));
		}
		sb.append(body == null ? "" : body);
		return super.acquireString(sb.toString());
	}

	protected boolean hasLayout = false;

	@Override
    protected void receiveSubTaglib(BodyTagSupport taglib) {
		if (taglib instanceof LayoutTag) {
			hasLayout = true;
		}
		super.receiveSubTaglib(taglib);
	}

	@Override
    protected void postBuildConfigJson(JSONObject cfg) {
		if (this.getTitle() != null) {
			cfg.put("title", this.getTitle());
		}
		if (this.getCountUrl() != null) {
			cfg.put("countUrl", this.getCountUrl());
		}
		if (this.getTitleicon() != null) {
			cfg.put("titleicon", this.getTitleicon());
		}
		if (this.getTitleimg() != null) {
			cfg.put("titleimg", this.getTitleimg());
		}
		if (this.getSubtitle() != null) {
			cfg.put("subtitle", this.getSubtitle());
		}
		putConfigValue(cfg, "toggle", this.getToggle());
		putConfigValue(cfg, "expand", this.getExpand());
		if (this.getOperations().size() > 0) {
			cfg.put("operations", this.getOperations());
		}
		super.postBuildConfigJson(cfg);
	}

	@Override
    public String startEditor(String script) {
		return TagHelper.uiscript(script, "show");
	}

	@Override
    public int doStartTag() throws JspException {
		pageContext.setAttribute("____content____", this, PageContext.REQUEST_SCOPE);
		return super.doStartTag();
	}

	@Override
    public int doEndTag() throws JspException {
		pageContext.setAttribute("____content____", null, PageContext.REQUEST_SCOPE);
		return super.doEndTag();
	}
	
	@Override
    protected void addConfig(String key, String value) {
		if ("cfg-server".equalsIgnoreCase(key)) {
			super.addConfig("cfg-contextPath",SysPortalConfig.getServerUrl(value));
		}else{			
			super.addConfig(key, value);
		}
	}
}
