package com.landray.kmss.sys.ui.taglib.widget.component;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;
import com.landray.kmss.util.StringUtil;

public abstract class AbstractButtonTag extends WidgetTag {

	private String href;

	private String target;

	private Integer order;
	
	private String text;
	
	private String title;
	
	private String icon;

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

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
    public String getType() {
		if (StringUtil.isNotNull(this.type)) {
			if (this.type.indexOf("!") < 0) {
				this.type = "lui/toolbar/toolbar!" + this.type;
			}
		} else {
			this.type = "lui/toolbar/toolbar!Button";
		}
		return this.type;
	}

	@Override
    protected void postBuildConfigJson(JSONObject cfg) {
		putConfigValue(cfg, "href", this.getHref());
		putConfigValue(cfg, "target", this.getTarget());
		putConfigValue(cfg, "order", this.getOrder());
		putConfigValue(cfg, "text", this.getText());
		putConfigValue(cfg, "title", this.getTitle());
		putConfigValue(cfg, "icon", this.getIcon());
		super.postBuildConfigJson(cfg);
	}

	@Override
    public void release() {
		this.href = null;
		this.target = null;
		this.order = null;
		this.text = null;
		this.title = null;
		this.icon = null;
		super.release();
	}
}
