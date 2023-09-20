package com.landray.kmss.sys.ui.taglib.list;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;

public class ColumnTag extends WidgetTag {

	protected String title;

	protected String property;

	protected String display;

	protected String onHeaderClick;

	protected String headerStyle;
	protected String headerClass;

	public String getHeaderClass() {
		return headerClass;
	}

	public void setHeaderClass(String headerClass) {
		this.headerClass = headerClass;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	protected String styleClass;

	protected String sort;

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

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

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getDisplay() {
		if (this.display == null) {
			return "true";
		}
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getOnHeaderClick() {
		return onHeaderClick;
	}

	public void setOnHeaderClick(String onHeaderClick) {
		this.onHeaderClick = onHeaderClick;
	}

	@Override
	public void release() {
		title = null;
		property = null;
		display = null;
		onHeaderClick = null;
		headerStyle = null;
		headerClass = null;
		styleClass = null;
		sort = null;
		super.release();
	}

	@Override
    protected void postBuildConfigJson(JSONObject config) {
		putConfigValue(config, "title", this.getTitle());
		putConfigValue(config, "property", this.getProperty());
		putConfigValue(config, "display", this.getDisplay());
		putConfigValue(config, "onHeaderClick", this.getOnHeaderClick());
		putConfigValue(config, "headerStyle", this.getHeaderStyle());
		putConfigValue(config, "headerClass", this.getHeaderClass());
		putConfigValue(config, "styleClass", this.getStyleClass());
		putConfigValue(config, "sort", this.getSort());
		super.postBuildConfigJson(config);
	}

	@Override
    public String getType() {
		return this.type = "lui/listview/columntable!Column";
	}
}
