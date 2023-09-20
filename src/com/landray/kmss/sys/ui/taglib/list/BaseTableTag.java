package com.landray.kmss.sys.ui.taglib.list;

import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.ui.taglib.widget.LayoutTag;
import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;

public class BaseTableTag extends WidgetTag {

	protected String isDefault;

	protected String name;

	protected String target;

	protected String layout;

	public String getIsDefault() {
		if (this.isDefault == null) {
			return "false";
		}
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTarget() {
		if (this.target == null) {
			return "_blank";
		}
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	@Override
	public void release() {
		this.name = null;
		this.target = null;
		this.layout = null;
		this.isDefault = null;
		super.release();
	}

	@Override
    protected void postBuildConfigJson(JSONObject config) {
		putConfigValue(config, "name", this.getName());
		putConfigValue(config, "target", this.getTarget());
		putConfigValue(config, "isDefault", this.getIsDefault());
		super.postBuildConfigJson(config);
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

}
