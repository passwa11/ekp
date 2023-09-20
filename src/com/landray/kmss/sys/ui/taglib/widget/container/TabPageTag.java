package com.landray.kmss.sys.ui.taglib.widget.container;

import net.sf.json.JSONObject;

public class TabPageTag extends AbstractPanel {

	private Boolean collapsed;

	public Boolean getCollapsed() {
		if (this.collapsed == null) {
			this.collapsed = false;
		}
		return collapsed;

	}

	public void setCollapsed(Boolean collapsed) {
		this.collapsed = collapsed;
	}

	@Override
    public String getDefaultType() {
		return "lui/panel!TabPage";
	}

	@Override
    public String getDefaultLayoutId() {
		return "sys.ui.tabpage.default";
	}

	@Override
    protected void postBuildConfigJson(JSONObject config) {
		putConfigValue(config, "collapsed", this.getCollapsed());
		super.postBuildConfigJson(config);
	}

	@Override
    public void release() {
		this.collapsed = null;
		super.release();
	}

}
