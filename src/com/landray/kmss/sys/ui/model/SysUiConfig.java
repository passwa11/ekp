package com.landray.kmss.sys.ui.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class SysUiConfig extends BaseAppConfig {

	public SysUiConfig() throws Exception {
		super();
		// 页面宽度限定
		String fdMaxWidth = super.getValue("fdMaxWidth");
		if (StringUtil.isNull(fdMaxWidth)) {
			fdMaxWidth = "1200";
		}
		super.setValue("fdMaxWidth", fdMaxWidth);
		// 页面宽度
		String fdWidth = super.getValue("fdWidth");
		if (StringUtil.isNull(fdWidth)) {
			fdWidth = "980px";
		}
		super.setValue("fdWidth", fdWidth);
		// 个人模板左则列宽度
		String fdPersonLeftSide = super.getValue("fdPersonLeftSide");
		if (StringUtil.isNull(fdPersonLeftSide)) {
			fdPersonLeftSide = "175";
		}
		super.setValue("fdPersonLeftSide", fdPersonLeftSide);
		
		// 用户登录门户设置（用户进行登录时，是否直接登录到用户设置的默认场所下的门户）
		String loginDefaultAreaPortal = super.getValue("loginDefaultAreaPortal");
		if (StringUtil.isNull(loginDefaultAreaPortal)) {
			loginDefaultAreaPortal = "false";
		}
		super.setValue("loginDefaultAreaPortal", loginDefaultAreaPortal);
		
		// 用户漫游到其它场所的同时切换到该场所下的门户
		String roamSwitchPortal = super.getValue("roamSwitchPortal");
		if (StringUtil.isNull(roamSwitchPortal)) {
			roamSwitchPortal = "false";
		}
		super.setValue("roamSwitchPortal", roamSwitchPortal);
		// 设置后台配置logo
		String logoTitle = super.getValue("logoTitle");
		if (StringUtil.isNull(logoTitle)) {
			logoTitle = "/sys/profile/resource/images/logo.png";
		}
		super.setValue("logoTitle", logoTitle);

		// 设置默认的系统主题
		String sysThemeKeys = super.getValue("fdSysThemeKeys");
		if (StringUtil.isNull(sysThemeKeys)) {
			sysThemeKeys = "default,brilliant_blue,fresh_elegant,sky_blue";
		}
		super.setValue("fdSysThemeKeys", sysThemeKeys);
	}

	@Override
	public String getJSPUrl() {
		return "/sys/ui/sys_ui_config/sysUiConfig_edit.jsp";
	}

	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("sys-ui:sys.ui.config");
	}

	public String getCompressSwitch() {
		return getValue("fdIsCompress");
	}

	public String getIsUseCompressResource(){
		return getValue("fdIsUseCompressResource");
	}

	public void setIsUseCompressResource(String fdIsUseCompressResource){
		setValue("fdIsUseCompressResource", fdIsUseCompressResource);
	}

	public String getSysThemeKeys(){
		return getValue("fdSysThemeKeys");
	}
}
