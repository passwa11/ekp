package com.landray.kmss.km.cogroup.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class GroupConfig extends BaseAppConfig {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(GroupConfig.class);

	public static GroupConfig newInstance() {
		GroupConfig config = null;
		try {
			config = new GroupConfig();
		} catch (Exception e) {
			logger.error("", e);
		}
		return config;
	}

	public GroupConfig() throws Exception {
		super();
		// ==================以下为默认值===================
		// 是否开启kk群聊
		if (StringUtil.isNull(getCogroupEnabled())) {
			setCogroupEnabled("false");
		}
		if (StringUtil.isNull(getDingCogroupEnable())) {
			setDingCogroupEnable("false");
		}
		if (StringUtil.isNull(getWxWorkCogroupEnable())) {
			setWxWorkCogroupEnable("false");
		}
	}

	@Override
	public String getJSPUrl() {
		return "/km/cogroup/cogroup_config.jsp";
	}

	@Override
    public void save() throws Exception {
		super.save();
	}

	// 是否开启kk群聊
	public String getCogroupEnabled() {
		return getValue("cogroupEnabled");
	}

	public void setCogroupEnabled(String cogroupEnabled) {
		setValue("cogroupEnabled", cogroupEnabled);
	}

	// 是否开启钉钉群聊
	public String getDingCogroupEnable() {
		return getValue("dingCogroupEnable");
	}

	public void setDingCogroupEnable(String dingCogroupEnable) {
		setValue("dingCogroupEnable", dingCogroupEnable);
	}

	// 是否开启企业微信群聊
	public String getWxWorkCogroupEnable() {
		return getValue("wxWorkCogroupEnable");
	}

	public void setWxWorkCogroupEnable(String wxWorkCogroupEnable) {
		setValue("wxWorkCogroupEnable", wxWorkCogroupEnable);
	}

	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("km-cogroup:cogroup.cogroupConfig");
	}

}
