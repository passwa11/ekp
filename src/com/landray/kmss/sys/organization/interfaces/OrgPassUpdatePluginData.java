package com.landray.kmss.sys.organization.interfaces;

public class OrgPassUpdatePluginData {
	
	private String key;
	
	private ISysOrgPassUpdate bean;

	public void setBean(ISysOrgPassUpdate bean) {
		this.bean = bean;
	}

	public ISysOrgPassUpdate getBean() {
		return bean;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
