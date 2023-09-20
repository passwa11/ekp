package com.landray.kmss.sys.organization.webservice.in;

public class SysSynchroSetOrgContext {
	/**
	 * 设置组织架构来源 说明:允许为空. 该字段信息会存于系统组织架构表sys_org_element字段fd_import_info中 格式:
	 * 系统来源 + "_" + 传入的组织架构唯一标识. 为空 则默认格式为:
	 * "com.landray.kmss.sys.organization.webservice.in_" + 传入的组织架构唯一标识.
	 */
	private String appName;

	/**
	 * 组织架构基本信息或组织架构信息json数组.
	 */
	private String orgJsonData;

	/**
	 * 组织架构同步配置信息
	 */
	private String orgSyncConfig;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getOrgJsonData() {
		return orgJsonData;
	}

	public void setOrgJsonData(String orgJsonData) {
		this.orgJsonData = orgJsonData;
	}

	public String getOrgSyncConfig() {
		return orgSyncConfig;
	}

	public void setOrgSyncConfig(String orgSyncConfig) {
		this.orgSyncConfig = orgSyncConfig;
	}

	public Boolean getDeleteNoInOrgJsonData() {
		return deleteNoInOrgJsonData;
	}

	public void setDeleteNoInOrgJsonData(Boolean deleteNoInOrgJsonData) {
		if(deleteNoInOrgJsonData!=null){
			this.deleteNoInOrgJsonData = deleteNoInOrgJsonData;
		}
	}

	private Boolean deleteNoInOrgJsonData = true;

}
