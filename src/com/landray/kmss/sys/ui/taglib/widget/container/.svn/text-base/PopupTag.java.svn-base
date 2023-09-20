package com.landray.kmss.sys.ui.taglib.widget.container;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;
import com.landray.kmss.util.StringUtil;

public class PopupTag extends WidgetTag {
	protected Integer borderWidth;
	protected String borderColor;
	
	protected String align;
	protected String positionObject;
	protected String triggerObject;
	protected String triggerEvent;

	public String getPositionObject() {
		return positionObject;
	}

	public void setPositionObject(String positionObject) {
		this.positionObject = positionObject;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getTriggerObject() {
		return triggerObject;
	}

	public void setTriggerObject(String triggerObject) {
		this.triggerObject = triggerObject;
	}

	public String getTriggerEvent() {
		return triggerEvent;
	}

	public void setTriggerEvent(String triggerEvent) {
		this.triggerEvent = triggerEvent;
	}

	public Integer getBorderWidth() {
		return borderWidth;
	}

	public void setBorderWidth(Integer borderWidth) {
		this.borderWidth = borderWidth;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	@Override
    public String getChannel() {
		return channel;
	}

	@Override
    public void setChannel(String channel) {
		this.channel = channel;
	}

	@Override
    public String getType() {
		if (StringUtil.isNotNull(this.type)) {
			if (this.type.indexOf("!") > 0) {

			} else {
				this.type = "lui/popup!" + this.type;
			}
		} else {
			this.type = "lui/popup!Popup";
		}
		return this.type;
	}

	@Override
    protected void postBuildConfigJson(JSONObject config) {
		putConfigValue(config, "borderWidth", this.getBorderWidth());
		putConfigValue(config, "borderColor", this.getBorderColor());
		putConfigValue(config, "channel", this.getChannel());
		putConfigValue(config, "align", this.getAlign());
		putConfigValue(config, "positionObject", this.getPositionObject());
		putConfigValue(config, "triggerObject", this.getTriggerObject());
		putConfigValue(config, "triggerEvent", this.getTriggerEvent());

		super.postBuildConfigJson(config);
	}

}
