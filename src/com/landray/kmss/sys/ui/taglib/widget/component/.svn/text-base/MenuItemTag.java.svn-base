package com.landray.kmss.sys.ui.taglib.widget.component;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;

public class MenuItemTag  extends WidgetTag {
	protected String href;
	protected String target;
	protected String icon;
	protected String text;
	protected String title;

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

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
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
		putConfigValue(cfg, "text", this.getText());
		putConfigValue(cfg, "title", this.getTitle());
		super.postBuildConfigJson(cfg);
	}

	@Override
    public void release() {
		this.icon = null;
		this.href = null;
		this.target = null;
		this.text = null;
		this.title = null;
		super.release();
	}

	@Override
    public String getType() {
		this.type = "lui/menu!MenuItem";
		return this.type;
	}
}
