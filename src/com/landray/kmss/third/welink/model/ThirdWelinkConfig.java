package com.landray.kmss.third.welink.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;


public class ThirdWelinkConfig extends BaseAppConfig {

	public ThirdWelinkConfig() throws Exception {
		super();
	}

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdWelinkConfig.class);

	@Override
	public String getJSPUrl() {
		return "/third/welink/welink_config.jsp";
	}

	public static ThirdWelinkConfig newInstance() {
		ThirdWelinkConfig config = null;
		try {
			config = new ThirdWelinkConfig();
		} catch (Exception e) {
			logger.error("", e);
		}
		return config;
	}

	public String welinkEnabled;

	public String getWelinkEnabled() {
		return getValue("welinkEnabled");
	}

	public void setWelinkEnabled(String welinkEnabled) {
		setValue("welinkEnabled", welinkEnabled);
	}


	public String getWelinkClientid() {
		return getValue("welinkClientid");
	}

	public void setWelinkClientid(String welinkClientid) {
		setValue("welinkClientid", welinkClientid);
	}

	public String getWelinkClientsecret() {
		return getValue("welinkClientsecret");
	}

	public void setWelinkClientsecret(String welinkClientsecret) {
		setValue("welinkClientsecret", welinkClientsecret);
	}

	public String getWelinkTodoTaskEnabled() {
		return getValue("welinkTodoTaskEnabled");
	}

	public void setWelinkTodoTaskEnabled(String welinkTodoTaskEnabled) {
		setValue("welinkTodoTaskEnabled", welinkTodoTaskEnabled);
	}


	public String getWelinkSsoEnabled() {
		return getValue("welinkSsoEnabled");
	}

	public void setWelinkSsoEnabled(String welinkSsoEnabled) {
		setValue("welinkSsoEnabled", welinkSsoEnabled);
	}

	public String getWelinkNotifyEnabled() {
		return getValue("welinkNotifyEnabled");
	}

	public void setWelinkNotifyEnabled(String welinkNotifyEnabled) {
		setValue("welinkNotifyEnabled", welinkNotifyEnabled);
	}

	public String getWelinkTodoMsgEnabled() {
		return getValue("welinkTodoMsgEnabled");
	}

	public void setWelinkTodoMsgEnabled(String welinkTodoMsgEnabled) {
		setValue("welinkTodoMsgEnabled", welinkTodoMsgEnabled);
	}

	public String getWelinkToreadMsgEnabled() {
		return getValue("welinkToreadMsgEnabled");
	}

	public void setWelinkToreadMsgEnabled(String welinkToreadMsgEnabled) {
		setValue("welinkToreadMsgEnabled", welinkToreadMsgEnabled);
	}

	public String getWelinkNotifyLogSaveDays() {
		return getValue("welinkNotifyLogSaveDays");
	}

	public void setWelinkNotifyLogSaveDays(String welinkNotifyLogSaveDays) {
		setValue("welinkNotifyLogSaveDays", welinkNotifyLogSaveDays);
	}

	public String getSynchroOrg2Welink() {
		return getValue("synchroOrg2Welink");
	}

	public void setSynchroOrg2Welink(String synchroOrg2Welink) {
		setValue("synchroOrg2Welink", synchroOrg2Welink);
	}

	

	@Override
    protected String getValue(String name) {
		String value = super.getValue(name);
		if (value != null) {
			value = value.trim();
		}
		return value;
	}

	public String getWelinkNotifyTaskApplicantUserId() {
		return getValue("welinkNotifyTaskApplicantUserId");
	}

	public void setWelinkNotifyTaskApplicantUserId(
			String welinkNotifyTaskApplicantUserId) {
		setValue("welinkNotifyTaskApplicantUserId",
				welinkNotifyTaskApplicantUserId);
	}

	public String getWelinkNotifyTaskApplicantUserNameCn() {
		return getValue("welinkNotifyTaskApplicantUserNameCn");
	}

	public void setWelinkNotifyTaskApplicantUserNameCn(
			String welinkNotifyTaskApplicantUserNameCn) {
		setValue("welinkNotifyTaskApplicantUserNameCn",
				welinkNotifyTaskApplicantUserNameCn);
	}

	public String getWelinkNotifyTaskApplicantUserNameEn() {
		return getValue("welinkNotifyTaskApplicantUserNameEn");
	}

	public void setWelinkNotifyTaskApplicantUserNameEn(
			String welinkNotifyTaskApplicantUserNameEn) {
		setValue("welinkNotifyTaskApplicantUserNameEn",
				welinkNotifyTaskApplicantUserNameEn);
	}

	public String getWelinkMsgMobileOnly() {
		return getValue("welinkMsgMobileOnly");
	}

	public void setWelinkMsgMobileOnly(String welinkMsgMobileOnly) {
		setValue("welinkMsgMobileOnly",
				welinkMsgMobileOnly);
	}

}
