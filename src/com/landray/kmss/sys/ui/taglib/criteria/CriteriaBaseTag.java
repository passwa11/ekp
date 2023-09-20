/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.criteria;

import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.ui.taglib.widget.BuildUtils;
import com.landray.kmss.sys.ui.taglib.widget.LayoutTag;
import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;

/**
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public abstract class CriteriaBaseTag extends WidgetTag {

	protected Boolean expand;

	protected Boolean multi;

	protected String layout;

	protected boolean hasLayout = false;

	public Boolean getExpand() {
		return expand;
	}

	public void setExpand(Boolean expand) {
		this.expand = expand;
	}

	public Boolean getMulti() {
		return multi;
	}

	public void setMulti(Boolean multi) {
		this.multi = multi;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	@Override
	protected void receiveSubTaglib(BodyTagSupport taglib) {
		if (taglib instanceof LayoutTag) {
			hasLayout = true;
		}
		super.receiveSubTaglib(taglib);
	}

	@Override
	public void release() {
		super.release();
		expand = null;
		multi = null;
		layout = null;
		hasLayout = false;
	}

	protected String buildLayoutHtml() {
		String id = null;
		String type = layout;
		String attr = null;
		JSONObject config = null;
		String body = null;
		return BuildUtils.buildLUIHtml(id, type, attr, config, body);
	}

	@Override
	protected String acquireString(String body) throws Exception {
		if (layout != null && !hasLayout) {
			body = buildLayoutHtml() + "\r\n" + body;
		}
		return super.acquireString(body);
	}

	@Override
	protected void postBuildConfigJson(JSONObject cfg) {
		putConfigValue(cfg, "expand", expand);
		putConfigValue(cfg, "canMulti", multi);
		putConfigValue(cfg, "channel", getChannel());
		super.postBuildConfigJson(cfg);
	}
}
