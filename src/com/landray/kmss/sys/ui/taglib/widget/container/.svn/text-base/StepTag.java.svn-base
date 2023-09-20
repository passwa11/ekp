package com.landray.kmss.sys.ui.taglib.widget.container;

import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.ui.taglib.widget.LayoutTag;
import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;
import com.landray.kmss.util.StringUtil;

public class StepTag extends WidgetTag {
	private String layout;

	private String onSubmit;

	public String getOnSubmit() {
		return onSubmit;
	}

	public void setOnSubmit(String onSubmit) {
		this.onSubmit = onSubmit;
	}

	public String getLayout() {
		if (StringUtil.isNull(this.layout)) {
			this.layout = getDefaultLayoutId();
		}
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getDefaultLayoutId() {
		return "sys.ui.step.default";
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
    public String getType() {
		return this.type = "lui/step!Step";
	}

	@Override
    protected void postBuildConfigJson(JSONObject cfg) {
		if (StringUtil.isNotNull(this.getOnSubmit())) {
			cfg.element("onSubmit", this.getOnSubmit());
		}
		super.postBuildConfigJson(cfg);
	}
}
