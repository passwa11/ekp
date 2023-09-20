package com.landray.kmss.sys.ui.taglib.list;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;
import com.landray.kmss.util.StringUtil;

public class HtmlColumnTag extends WidgetTag {

	protected String title;

	protected String onHeaderClick;

	protected String headerStyle;
	protected String headerClass;
	protected String styleClass;

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

	public String getOnHeaderClick() {
		return onHeaderClick;
	}

	public void setOnHeaderClick(String onHeaderClick) {
		this.onHeaderClick = onHeaderClick;
	}

	@Override
    public String getType() {
		return this.type = "lui/listview/columntable!HtmlColumn";
	}

	@Override
	public void release() {
		title = null;
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
		putConfigValue(config, "onHeaderClick", this.getOnHeaderClick());
		putConfigValue(config, "headerStyle", this.getHeaderStyle());
		putConfigValue(config, "headerClass", this.getHeaderClass());
		putConfigValue(config, "styleClass", this.getStyleClass());
		putConfigValue(config, "sort", this.getSort());
		super.postBuildConfigJson(config);
	}

	@Override
    protected String acquireString(String body) throws Exception {
		StringBuilder sb = new StringBuilder();
		if (StringUtil.isNotNull(body)) {
			sb.append(buildCodeScriptHtml(body));
		}
		return super.acquireString(sb.toString());
	}

}
