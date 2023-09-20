package com.landray.kmss.sys.ui.taglib.widget.component;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;

public class MenuSourceTag extends WidgetTag {
	protected String href;
	protected String target;
	protected String icon;
	protected Boolean autoFetch;

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public Boolean getAutoFetch() {
		return autoFetch;
	}

	public void setAutoFetch(Boolean autoFetch) {
		this.autoFetch = autoFetch;
	}

	@Override
    public String getType() {
		this.type = "lui/menu!MenuSource";
		return this.type;
	}

	@Override
    protected String acquireString(String body) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(body == null ? "" : body);
		return super.acquireString(sb.toString());
	}

	@Override
    protected void postBuildConfigJson(JSONObject cfg) {
		putConfigValue(cfg, "href", this.getHref());
		putConfigValue(cfg, "target", this.getTarget());
		putConfigValue(cfg, "icon", this.getIcon());
		putConfigValue(cfg, "autoFetch", this.getAutoFetch());
		super.postBuildConfigJson(cfg);
	}

	@Override
    public void release() {
		this.icon = null;
		this.href = null;
		this.target = null;
		this.autoFetch = null;
		super.release();
	}
}
