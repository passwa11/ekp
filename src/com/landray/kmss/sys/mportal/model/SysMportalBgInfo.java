package com.landray.kmss.sys.mportal.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;

public class SysMportalBgInfo extends BaseAppConfig {

	public SysMportalBgInfo() throws Exception {
		super();
	}
	
	public String getBgUrl() {
		return getValue("bgUrl");
	}

	public void setBgUrl(String logoUrl) {
		setValue("bgUrl", logoUrl);
	}
	
	public String getFontColor() {
		return getValue("fontColor");
	}
	public void setFontColor(String fontColor) {
		setValue("fontColor", fontColor);
	}
	
	@Override
	public String getJSPUrl() {
		return "/sys/mportal/sys_mportal_bgInfo/sysMportalBgInfo.do?method=list";
	}

	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("sys-mportal:sysMportal.profile.bg");
	}
	
}
