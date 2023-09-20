package com.landray.kmss.sys.ui.taglib.widget;

import net.sf.json.JSONObject;

/**
 * ui:iframe组件
 */
public class IframeTag extends WidgetTag {

	private static final long serialVersionUID = 1L;

	private String src;

	public String getSrc() {
		return this.src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	@Override
    public String getType() {
		if (this.type == null) {
			this.type = "lui/iframe!Iframe";
		}
		return this.type;
	}

	@Override
    protected void postBuildConfigJson(JSONObject cfg) {
		putConfigValue(cfg, "src", this.getSrc());
		super.postBuildConfigJson(cfg);
	}

}
