package com.landray.kmss.sys.ui.taglib.list;

import net.sf.json.JSONObject;

public class RowTableTag extends BaseTableTag {

	@Override
	public String getType() {
		if (this.type == null) {
			return "lui/listview/rowtable!RowTable";
		}
		return this.type;
	}

	protected String rowHref;

	protected String onRowClick;

	@Override
    public void setLayout(String layout) {
		this.layout = layout;
	}

	@Override
    public String getLayout() {
		if (this.layout == null) {
			return "sys.ui.listview.rowtable";
		}
		return layout;
	}

	public String getRowHref() {
		return rowHref;
	}

	public void setRowHref(String rowHref) {
		this.rowHref = rowHref;
	}

	public String getOnRowClick() {
		return onRowClick;
	}

	public void setOnRowClick(String onRowClick) {
		this.onRowClick = onRowClick;
	}

	@Override
	public void release() {
		this.rowHref = null;
		this.onRowClick = null;
		super.release();
	}

	@Override
    protected void postBuildConfigJson(JSONObject config) {
		putConfigValue(config, "rowHref", this.getRowHref());
		putConfigValue(config, "onRowClick", this.getOnRowClick());
		super.postBuildConfigJson(config);
	}

}
