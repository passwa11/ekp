package com.landray.kmss.third.welink.oms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;


public class WelinkOmsConfig extends BaseAppConfig {

	public WelinkOmsConfig() throws Exception {
		super();
	}

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WelinkOmsConfig.class);

	@Override
	public String getJSPUrl() {
		return null;
	}


	public String getLastSynchroTime() {
		return getValue("lastSynchroTime");
	}

	public void setLastSynchroTime(String lastSynchroTime) {
		setValue("lastSynchroTime", lastSynchroTime);
	}

	
}
