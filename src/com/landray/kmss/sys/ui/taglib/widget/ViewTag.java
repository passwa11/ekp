package com.landray.kmss.sys.ui.taglib.widget;

import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

public class ViewTag extends WidgetTag {

	private static final long serialVersionUID = 1L;

	private String name; // 视图名称，当name为空时默认为根视图（根视图只能存在一个）

	private String mode; // 模式，default or quick

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMode() {
		if (StringUtil.isNull(this.mode)) {
			this.mode = "default";
		}
		return this.mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	@Override
    public String getType() {
		if (this.type == null) {
			this.type = "sys/ui/js/framework/view/view!View";
		}
		return this.type;
	}

	@Override
    protected void postBuildConfigJson(JSONObject cfg) {
		putConfigValue(cfg, "name", this.getName());
		putConfigValue(cfg, "mode", this.getMode());
		super.postBuildConfigJson(cfg);
	}

}
