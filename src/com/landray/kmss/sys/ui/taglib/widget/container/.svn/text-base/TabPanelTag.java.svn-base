package com.landray.kmss.sys.ui.taglib.widget.container;

import net.sf.json.JSONObject;

public class TabPanelTag extends AbstractPanel {
	private Integer selectedIndex;

	private Boolean suckTop;

	@Override
    public String getDefaultType() {
		return "lui/panel!TabPanel";
	}

	@Override
    public String getDefaultLayoutId() {
		return "sys.ui.tabpanel.default";
	}

	public Integer getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(Integer selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	public Boolean getSuckTop() {
		if (this.suckTop == null) {
			this.suckTop = false;
		}
		return suckTop;
	}

	public void setSuckTop(Boolean suckTop) {
		this.suckTop = suckTop;
	}

	@Override
    protected void postBuildConfigJson(JSONObject config) {
		if (this.getSelectedIndex() != null) {
			config.put("selectedIndex", this.getSelectedIndex());
		}
		config.put("suckTop", this.getSuckTop());
		super.postBuildConfigJson(config);
	}

	@Override
    public void release() {
		this.selectedIndex = null;
		this.suckTop = null;
		super.release();
	}

}
