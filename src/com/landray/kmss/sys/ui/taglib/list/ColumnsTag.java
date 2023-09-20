package com.landray.kmss.sys.ui.taglib.list;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;

public class ColumnsTag extends WidgetTag {

	protected String props;
	
	public String getProps() {
		return props;
	}

	public void setProps(String props) {
		this.props = props;
	}

	@Override
	public void release() {
		props = null;
		super.release();
	}

	@Override
    protected void postBuildConfigJson(JSONObject config) {
		putConfigValue(config, "properties", this.getProps());
		super.postBuildConfigJson(config);
	}

	@Override
    public String getType() {
		return this.type = "lui/listview/columntable!Columns";
	}
}
