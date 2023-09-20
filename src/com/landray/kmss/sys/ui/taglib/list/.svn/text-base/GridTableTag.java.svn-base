package com.landray.kmss.sys.ui.taglib.list;

import net.sf.json.JSONObject;

@SuppressWarnings("serial")
public class GridTableTag extends BaseTableTag {

	@Override
	public String getType() {
		if (this.type == null) {
			return "lui/listview/gridtable!GridTable";
		}
		return this.type;
	}

	protected String gridHref;

	protected String onGridClick;

	protected Integer columnNum;

	protected Boolean aline;

	public String getGridHref() {
		return gridHref;
	}

	public void setGridHref(String gridHref) {
		this.gridHref = gridHref;
	}

	public String getOnGridClick() {
		return onGridClick;
	}

	public void setOnGridClick(String onGridClick) {
		this.onGridClick = onGridClick;
	}

	public Integer getColumnNum() {
		if (columnNum == null) {
            return 4;
        }
		return columnNum;
	}

	public void setColumnNum(Integer columnNum) {
		this.columnNum = columnNum;
	}

	public Boolean getAline() {
		return aline;
	}

	public void setAline(Boolean aline) {
		this.aline = aline;
	}

	@Override
	public void setLayout(String layout) {
		this.layout = layout;
	}

	@Override
	public String getLayout() {
		if (this.layout == null) {
			return "sys.ui.listview.gridtable";
		}
		return layout;
	}

	@Override
	public void release() {
		gridHref = null;
		onGridClick = null;
		columnNum = null;
		aline = null;
		super.release();
	}

	@Override
	protected void postBuildConfigJson(JSONObject config) {
		putConfigValue(config, "gridHref", this.getGridHref());
		putConfigValue(config, "onGridClick", this.getOnGridClick());
		putConfigValue(config, "columnNum", this.getColumnNum());
		putConfigValue(config, "aline", this.getAline());
		super.postBuildConfigJson(config);
	}

}
