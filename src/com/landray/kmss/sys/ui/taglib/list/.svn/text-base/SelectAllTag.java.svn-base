package com.landray.kmss.sys.ui.taglib.list;

import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;

import net.sf.json.JSONObject;

public class SelectAllTag extends WidgetTag {

	protected String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
    public String getType() {
		return this.type = "lui/listview/listview!SelectAll";
	}

	@Override
    protected String acquireString(String body) throws Exception {
		StringBuilder sb = new StringBuilder();
		return super.acquireString(sb.toString());
	}

	@Override
    public void release() {
		text = null;
		super.release();
	}

	@Override
    protected void postBuildConfigJson(JSONObject config) {
		putConfigValue(config, "text", this.getText());
		super.postBuildConfigJson(config);
	}
}
