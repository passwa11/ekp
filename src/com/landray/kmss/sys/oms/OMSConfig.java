package com.landray.kmss.sys.oms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;


public class OMSConfig extends BaseAppConfig {
	
	public OMSConfig() throws Exception {
		super();
	}

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(OMSConfig.class);

	@Override
	public String getJSPUrl() {
		// TODO 自动生成的方法存根
		return "/sys/oms/omsConfig_edit.jsp";
	}

	@Override
    public String getValue(String name) {
		return (String) getDataMap().get(name);
	}

	@Override
    public void setValue(String name, String value) {
		getDataMap().put(name, value);
	}

	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("sys-oms:oms.setting");
	}

}
