package com.landray.kmss.sys.ui.taglib.widget.container;

import javax.servlet.jsp.tagext.BodyTagSupport;

import com.landray.kmss.sys.ui.taglib.widget.LayoutTag;
import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;
import com.landray.kmss.util.StringUtil;

public class MenuTag extends WidgetTag {
	private String layout;

	public String getLayout() {
		if (StringUtil.isNull(this.layout)) {
			this.layout = "sys.ui.menu.default";
		}
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	@Override
    public String getType() {
		if (this.type != null) {

		} else {
			this.type = "lui/menu!Menu";
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
    public void release() {
		this.layout = null;
		super.release();
	}
}
