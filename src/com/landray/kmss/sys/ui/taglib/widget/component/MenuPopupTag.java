package com.landray.kmss.sys.ui.taglib.widget.component;

import net.sf.json.JSONObject;

public class MenuPopupTag extends MenuItemTag {
	protected Integer borderWidth;
	protected String align;
	protected String triggerEvent;
	protected String triggerObject;

	public Integer getBorderWidth() {
		return borderWidth;
	}

	public void setBorderWidth(Integer borderWidth) {
		this.borderWidth = borderWidth;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getTriggerEvent() {
		return triggerEvent;
	}

	public void setTriggerEvent(String triggerEvent) {
		this.triggerEvent = triggerEvent;
	}
	
	public String getTriggerObject() {
		return triggerObject;
	}

	public void setTriggerObject(String triggerObject) {
		this.triggerObject = triggerObject;
	}

	@Override
    public String getType() {
		this.type = "lui/menu!MenuPopup";
		return this.type;
	}
	
	@Override
    protected String acquireString(String body) throws Exception {
		return super.acquireString("<div>" + body  + "</div>");
	}
	
	@Override
    protected void postBuildConfigJson(JSONObject cfg) {
		putConfigValue(cfg, "align", this.getAlign());
		putConfigValue(cfg, "borderWidth", this.getBorderWidth());
		putConfigValue(cfg, "triggerEvent", this.getTriggerEvent());
		putConfigValue(cfg, "triggerObject", this.getTriggerObject());
		super.postBuildConfigJson(cfg);
	}

}
