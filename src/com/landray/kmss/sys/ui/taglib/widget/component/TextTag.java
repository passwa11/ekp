package com.landray.kmss.sys.ui.taglib.widget.component;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;
import com.landray.kmss.util.StringUtil;

public class TextTag extends WidgetTag {
	private String text;
	private String title;
	private String icon;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
    public String getType() {
		if (StringUtil.isNotNull(this.type)) {
			if (this.type.indexOf("!") < 0) {
				this.type = "lui/element!" + this.type;
			}
		} else {
			this.type = "lui/element!Text";
		}
		return this.type;
	}

	@Override
    protected void postBuildConfigJson(JSONObject cfg) {
		putConfigValue(cfg, "text", this.getText());
		putConfigValue(cfg, "title", this.getTitle());
		putConfigValue(cfg, "icon", this.getIcon());
		super.postBuildConfigJson(cfg);
	}

	@Override
    public void release() {
		this.text = null;
		this.title = null;
		this.icon = null;
		super.release();
	}
}
