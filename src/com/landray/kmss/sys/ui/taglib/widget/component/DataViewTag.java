package com.landray.kmss.sys.ui.taglib.widget.component;

import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.taglib.widget.RenderTag;
import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;
import com.landray.kmss.util.StringUtil;

public class DataViewTag extends WidgetTag {
	protected String format;

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@Override
    public String getType() {
		if (StringUtil.isNull(this.type)) { 
			this.type = "lui/base!DataView";
		}
		return this.type;
	}

	@Override
    protected void postBuildConfigJson(JSONObject cfg) {
		if (StringUtil.isNotNull(this.getFormat())) {
			cfg.put("format", this.getFormat());
		}
		super.postBuildConfigJson(cfg);
	}

	@Override
    protected String acquireString(String body) throws Exception {
		StringBuilder sb = new StringBuilder();
		if (!hasRender) {
			String renderId = SysUiPluginUtil.getFormatById(this.getFormat())
					.getFdDefaultRender();
			sb.append(RenderTag.buildRenderHtml(this, renderId));
		}
		sb.append(body == null ? "" : body);
		return super.acquireString(sb.toString());
	}

	@Override
    public void release() {
		this.format = null;
		this.hasRender = false;
		super.release();
	}

	protected boolean hasRender = false;

	@Override
    protected void receiveSubTaglib(BodyTagSupport taglib) {
		if (taglib instanceof RenderTag) {
			hasRender = true;
		}
		super.receiveSubTaglib(taglib);
	}
}
