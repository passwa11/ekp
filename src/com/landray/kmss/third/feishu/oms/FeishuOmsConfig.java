package com.landray.kmss.third.feishu.oms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;


public class FeishuOmsConfig extends BaseAppConfig {

	public FeishuOmsConfig() throws Exception {
		super();
	}

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(FeishuOmsConfig.class);

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

	public String getLastSynchroEkpRootId() {
		return getValue("lastSynchroEkpRootId");
	}

	public void setLastSynchroEkpRootId(String lastSynchroEkpRootId) {
		setValue("lastSynchroEkpRootId", lastSynchroEkpRootId);
	}

}
