package com.landray.kmss.sys.ui.taglib.list;

import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.ui.taglib.widget.LayoutTag;
import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;
import com.landray.kmss.util.StringUtil;

public class PagingTag extends WidgetTag {

	protected String layout;

	protected String currentPage;

	protected String pageSize;

	protected String totalSize;

	protected String viewSize;

	public String getViewSize() {
		if (this.viewSize == null) {
			return "2";
		}
		return viewSize;
	}

	public void setViewSize(String viewSize) {
		this.viewSize = viewSize;
	}

	public String getLayout() {
		if (StringUtil.isNull(this.layout)) {
			this.layout = "sys.ui.paging.default";
		}
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getCurrentPage() {
		if (StringUtil.isNull(this.currentPage)) {
			this.currentPage = "1";
		}
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getPageSize() {
		if (StringUtil.isNull(this.pageSize)) {
			this.pageSize = String.valueOf(SysConfigParameters.getRowSize());
		}
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(String totalSize) {
		this.totalSize = totalSize;
	}

	@Override
    protected String acquireString(String body) throws Exception {
		StringBuilder sb = new StringBuilder();
		if (!hasLayout) {
			sb.append(LayoutTag.buildLayoutHtml(this, this.getLayout()));
		}
		sb.append(body == null ? "" : body);
		return super.acquireString(sb.toString());
	}

	protected boolean hasLayout = false;

	@Override
    protected void receiveSubTaglib(BodyTagSupport taglib) {
		if (taglib instanceof LayoutTag) {
			hasLayout = true;
		}
		super.receiveSubTaglib(taglib);
	}

	@Override
    public void release() {
		this.layout = null;
		this.currentPage = null;
		this.pageSize = null;
		this.totalSize = null;
		this.viewSize = null;
		super.release();
	}

	@Override
    protected void postBuildConfigJson(JSONObject config) {
		putConfigValue(config, "currentPage", this.getCurrentPage());
		putConfigValue(config, "pageSize", this.getPageSize());
		putConfigValue(config, "totalSize", this.getTotalSize());
		putConfigValue(config, "viewSize", this.getViewSize());
		super.postBuildConfigJson(config);
	}

	@Override
    public String getType() {
		return this.type = "lui/listview/paging!Paging";
	}
}
