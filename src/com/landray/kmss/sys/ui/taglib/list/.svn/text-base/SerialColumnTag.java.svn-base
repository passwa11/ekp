package com.landray.kmss.sys.ui.taglib.list;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;

public class SerialColumnTag extends WidgetTag {

	protected String title;

	protected String headerStyle;

	public String getHeaderStyle() {
		return headerStyle;
	}

	public void setHeaderStyle(String headerStyle) {
		this.headerStyle = headerStyle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void release() {
		title = null;
		headerStyle = null;
		super.release();
	}

	@Override
    protected void postBuildConfigJson(JSONObject config) {
		putConfigValue(config, "title", this.getTitle());
		putConfigValue(config, "headerStyle", this.getHeaderStyle());
		super.postBuildConfigJson(config);
	}

	@Override
    public String getType() {
		return this.type = "lui/listview/columntable!SerialColumn";
	}

}
