package com.landray.kmss.sys.ui.taglib.widget.component;

import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

public class ButtonTag extends AbstractButtonTag {

	private String onclick;
	
	private String styleClass;

	private String width;

	private String height;
	
	private String suspend;

	private Boolean isForcedAddClass;

	public Boolean getIsForcedAddClass() {
		if (this.isForcedAddClass == null) {
			this.isForcedAddClass = false;
		}
		return isForcedAddClass;
	}

	public void setIsForcedAddClass(Boolean isForcedAddClass) {
		this.isForcedAddClass = isForcedAddClass;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	
	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
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

	public String getSuspend() {
		return suspend;
	}

	public void setSuspend(String suspend) {
		this.suspend = suspend;
	}

	@Override
    public String getType() {
		if (StringUtil.isNotNull(this.type)) {
			if (this.type.indexOf("!") < 0) {
				this.type = "lui/toolbar!" + this.type;
			}
		} else {
			this.type = "lui/toolbar!Button";
		}
		return this.type;
	}
	
	@Override
    protected void postBuildConfigJson(JSONObject cfg) {
		putConfigValue(cfg, "click", this.getOnclick());
		putConfigValue(cfg, "styleClass", this.getStyleClass());
		putConfigValue(cfg, "width", this.getWidth());
		putConfigValue(cfg, "height", this.getHeight());
		putConfigValue(cfg, "suspend", this.getSuspend());
		putConfigValue(cfg, "isForcedAddClass", this.getIsForcedAddClass());
		super.postBuildConfigJson(cfg);
	}

	@Override
    public void release() {
		this.onclick = null;
		super.release();
	}
}
