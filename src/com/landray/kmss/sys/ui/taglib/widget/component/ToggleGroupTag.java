package com.landray.kmss.sys.ui.taglib.widget.component;

import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.ui.taglib.widget.LayoutTag;
import com.landray.kmss.util.StringUtil;

public class ToggleGroupTag extends AbstractButtonTag {

	private String layout;
	
	public String getLayout() {
		if (StringUtil.isNull(this.layout)) {
			this.layout = "sys.ui.toolbar.togglegroup.default";
		}
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	@Override
    public String getType() {
		if (StringUtil.isNotNull(this.type)) {
			if (this.type.indexOf("!") < 0) {
				this.type = "lui/toolbar!" + this.type;
			}
		} else {
			this.type = "lui/toolbar!ToggleGroup";
		}
		return this.type;
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
    protected void postBuildConfigJson(JSONObject cfg) {
		super.postBuildConfigJson(cfg);
	}

	@Override
    public void release() {
		super.release();
	}
}
