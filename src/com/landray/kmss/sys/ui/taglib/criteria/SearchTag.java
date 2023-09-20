package com.landray.kmss.sys.ui.taglib.criteria;

import net.sf.json.JSONObject;

@SuppressWarnings("serial")
public class SearchTag extends CriteriaBaseTag {

	private String placeholder;
	
	private String width;
	
	private String height;
	
	private String backgroundColor;

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

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	@Override
	public String getType() {
		if (this.type == null) {
			return "lui/search_box!SearchBox";
		}
		return this.type;
	}

	@Override
	public void release() {
		super.release();
		placeholder = null;
	}

	@Override
	protected void postBuildConfigJson(JSONObject cfg) {
		putConfigValue(cfg, "placeholder", placeholder);
		putConfigValue(cfg, "width", width);
		putConfigValue(cfg, "height", height);
		putConfigValue(cfg, "backgroundColor", backgroundColor);
		super.postBuildConfigJson(cfg);
	}
}
