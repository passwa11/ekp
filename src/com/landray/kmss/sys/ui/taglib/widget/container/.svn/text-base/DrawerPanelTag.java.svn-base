package com.landray.kmss.sys.ui.taglib.widget.container;

import net.sf.json.JSONObject;

@SuppressWarnings("serial")
public class DrawerPanelTag extends AbstractPanel {
	private String dir;

	public void setDir(String dir) {
		this.dir = dir;
	}


	public String getDir() {
		return dir;
	}
	

	@Override
    public String getDefaultType() {
		return "lui/panel!DrawerPanel";
	}

	@Override
    public String getDefaultLayoutId() {
		return "sys.ui.drawerpanel.default";
	}


	@Override
    protected void postBuildConfigJson(JSONObject config) {
		if (this.getDir() != null) {
			config.put("dir", this.getDir());
		}
		super.postBuildConfigJson(config);
	}

	@Override
    public void release() {
		this.dir = null;
		super.release();
	}

}
