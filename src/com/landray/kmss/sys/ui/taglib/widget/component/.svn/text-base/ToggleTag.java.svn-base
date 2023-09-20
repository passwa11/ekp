package com.landray.kmss.sys.ui.taglib.widget.component;

import net.sf.json.JSONObject;

import com.landray.kmss.util.StringUtil;

public class ToggleTag extends AbstractButtonTag {

	@Override
    public String getType() {
		if (StringUtil.isNotNull(this.type)) {
			if (this.type.indexOf("!") < 0) {
				this.type = "lui/toolbar!" + this.type;
			}
		} else {
			this.type = "lui/toolbar!Toggle";
		}
		return this.type;
	}

	private String group;

	private String onclick;
	
	private String value;

	private String selected;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
    protected void postBuildConfigJson(JSONObject cfg) {
		putConfigValue(cfg, "group", this.getGroup());
		putConfigValue(cfg, "click", this.getOnclick());
		putConfigValue(cfg, "value", this.getValue());
		putConfigValue(cfg, "selected", this.getSelected());
		super.postBuildConfigJson(cfg);
	}

	@Override
    public void release() {
		this.group = null;
		this.onclick = null;
		this.value = null;
		this.selected = null;
		super.release();
	}

}
