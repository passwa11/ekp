package com.landray.kmss.sys.ui.taglib.list;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;

public class SortTag extends WidgetTag {

	protected String text;

	protected String property;

	protected String group;

	protected String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	@Override
    public String getType() {
		return this.type = "lui/listview/sort!Sort";
	}

	@Override
    protected String acquireString(String body) throws Exception {
		StringBuilder sb = new StringBuilder();
		return super.acquireString(sb.toString());
	}

	@Override
    public void release() {
		text = null;
		property = null;
		group = null;
		value = null;
		super.release();
	}

	@Override
    protected void postBuildConfigJson(JSONObject config) {
		putConfigValue(config, "text", this.getText());
		putConfigValue(config, "property", this.getProperty());
		putConfigValue(config, "group", this.getGroup());
		putConfigValue(config, "value", this.getValue());
		super.postBuildConfigJson(config);
	}
}
