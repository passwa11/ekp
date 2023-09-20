package com.landray.kmss.sys.ui.taglib.widget.container;

import javax.servlet.jsp.tagext.BodyTagSupport;

import com.landray.kmss.sys.ui.taglib.widget.LayoutTag;
import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

public abstract class AbstractPanel extends WidgetTag {
	private String width;
	private String height;
	private String scroll;
	private String layout;
	private Boolean expand;
	private String toggle;

	public Boolean getExpand() {
		if (this.expand == null) {
			this.expand = true;
		}
		return expand;
	}

	public void setExpand(Boolean expand) {
		this.expand = expand;
	}
	

	public String getToggle() {
		if (StringUtil.isNull(toggle) || "default".equals(toggle)) {
			return "true";
		}
		if ("none".equals(toggle)) {
			return "false";
		}
		return toggle;
	}

	public void setToggle(String toggle) {
		this.toggle = toggle;
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

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getScroll() {
		return scroll;
	}

	public void setScroll(String scroll) {
		this.scroll = scroll;
	}

	@Override
    public String getType() {
		if (StringUtil.isNotNull(this.type)) {
			if (this.type.indexOf("!") > 0) {

			} else {
				this.type = "lui/panel/panel!" + this.type;
			}
		} else {
			this.type = getDefaultType();
		}
		return this.type;
	}

	public abstract String getDefaultType();

	public abstract String getDefaultLayoutId();

	@Override
    protected void postBuildConfigJson(JSONObject config) {
		putConfigValue(config, "width", this.getWidth());
		putConfigValue(config, "height", this.getHeight());
		putConfigValue(config, "scroll", this.getScroll());
		putConfigValue(config, "expand", this.getExpand());
		putConfigValue(config, "toggle", this.getToggle());
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

	@Override
    public void release() {
		this.width = null;
		this.height = null;
		this.expand = null;
		this.toggle = null;
		this.scroll = null;
		super.release();
	}
}
