package com.landray.kmss.sys.ui.taglib.widget.container;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;

public class ContainerTag extends WidgetTag {
	protected String lazyDraw;

	@Override
    public String getType() {
		if (this.type != null) {

		} else {
			this.type = "lui/base!Container";
		}
		return this.type;
	}

	public String getLazyDraw() {
		return lazyDraw;
	}

	public void setLazyDraw(String lazyDraw) {
		this.lazyDraw = lazyDraw;
	}

	@Override
    public void release() {
		lazyDraw = null;
		super.release();
	}

	@Override
    protected void postBuildConfigJson(JSONObject config) {
		putConfigValue(config, "lazyDraw", lazyDraw);
		super.postBuildConfigJson(config);
	}
}
