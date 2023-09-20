package com.landray.kmss.third.ldap;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.util.ResourceUtil;

public class LdapDetailConfig  extends BaseAppConfig{
	
	public LdapDetailConfig() throws Exception {
		super();
	}

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(LdapDetailConfig.class);

	@Override
	public String getJSPUrl() {
		// TODO 自动生成的方法存根
		return null;
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
    public Map<String, String> getDataMap() {
		Map<String, String> dataMap = super.getDataMap();
		if (dataMap != null && dataMap.containsKey("kmss.ldap.config.password")) {
			String value = dataMap.get("kmss.ldap.config.password");
			value = LdapUtil.desDecrypt(value);
			dataMap.put("kmss.ldap.config.password", value);
		}
		if (dataMap != null) {
			String strategy = dataMap
					.get("kmss.ldap.type.common.prop.syncStrategy");
			if (StringUtil.isNull(strategy)) {
				dataMap.put("kmss.ldap.type.common.prop.syncStrategy", "full");
			}
		}
		return dataMap;
	}

	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("third-ldap:ldap.system.setting");
	}

}
