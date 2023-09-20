package com.landray.kmss.sys.ui.taglib.list;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;

public class RadioColumnTag extends WidgetTag {

	protected String name;

	protected String headerStyle;

	public String getHeaderStyle() {
		return headerStyle;
	}

	public void setHeaderStyle(String headerStyle) {
		this.headerStyle = headerStyle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void release() {
		name = null;
		super.release();
	}

	@Override
    protected void postBuildConfigJson(JSONObject config) {
		putConfigValue(config, "name", this.getName());
		putConfigValue(config, "headerStyle", this.getHeaderStyle());
		super.postBuildConfigJson(config);
	}

	@Override
    public String getType() {
		return this.type = "lui/listview/columntable!RadioColumn";
	}
}
