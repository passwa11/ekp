/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.list;

import net.sf.json.JSONObject;

/**
 * 列模式表格
 * 
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public class ColumnTableTag extends BaseTableTag {

	@Override
	public String getType() {
		if (this.type == null) {
			return "lui/listview/columntable!ColumnTable";
		}
		return this.type;
	}

	@Override
    public String getLayout() {
		if (this.layout == null) {
			this.layout = "sys.ui.listview.columntable";
		}
		return this.layout;
	}

	protected String rowHref;;

	protected String onRowClick;

	protected String sort;
	
	protected String url;

	public String getSort() {
		if (this.sort == null) {
			return "false";
		}
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
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
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	@Override
    protected void postBuildConfigJson(JSONObject config) {
		putConfigValue(config, "rowHref", this.getRowHref());
		putConfigValue(config, "onRowClick", this.getOnRowClick());
		putConfigValue(config, "sort", this.getSort());
		putConfigValue(config, "url", this.getUrl());
		super.postBuildConfigJson(config);
	}

	@Override
	public void release() {
		rowHref = null;
		onRowClick = null;
		sort = null;
		super.release();
	}
}
