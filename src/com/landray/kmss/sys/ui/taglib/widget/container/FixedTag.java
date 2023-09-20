package com.landray.kmss.sys.ui.taglib.widget.container;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;

@SuppressWarnings("serial")
public class FixedTag extends WidgetTag {

	protected String elem;

	public String getElem() {
		return elem;
	}

	public void setElem(String elem) {
		this.elem = elem;
	}

	protected String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
    public String getType() {
		if (this.type != null) {
		} else {
			this.type = "lui/fixed!Fixed";
		}
		return this.type;
	}

	@Override
    public void release() {
		elem = null;
		content = null;
		super.release();
	}

	@Override
    protected void postBuildConfigJson(JSONObject config) {
		putConfigValue(config, "elem", this.getElem());
		putConfigValue(config, "content", this.getContent());
		super.postBuildConfigJson(config);
	}
}
