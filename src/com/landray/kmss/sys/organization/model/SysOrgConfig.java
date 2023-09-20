package com.landray.kmss.sys.organization.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.util.ResourceUtil;

public class SysOrgConfig extends BaseAppConfig {
	private int fdType;

	public int getFdType() {
		return fdType;
	}

	public void setFdType(int fdType) {
		this.fdType = fdType;
	}

	public SysOrgConfig() throws Exception {
		super();
	}

	// 启用地址本数据在用户场景下隔离
	public String getOrgAeraEnable() {
		if (!ISysAuthConstant.IS_AREA_ENABLED) {
			return "false";
		}
		return getValue("isOrgAeraEnable");
	}

	public void setOrgAeraEnable(String isOrgAeraEnable) {
		setValue("isOrgAeraEnable", isOrgAeraEnable);
	}
	
	public String getOrgVisibleEnable() {
		return getValue("isOrgVisibleEnable");
	}

	public void setOrgVisibleEnable(String isOrgVisibleEnable) {
		setValue("isOrgVisibleEnable", isOrgVisibleEnable);
	}

	public String getDefaultVisibleLevel() {
		return getValue("defaultVisibleLevel");
	}

	public void setDefaultVisibleLevel(String defaultVisibleLevel) {
		setValue("defaultVisibleLevel", defaultVisibleLevel);
	}

	private String orgStaffingLevelFilter;

	private String orgStaffingLevelFilterSub;

	@Override
	public String getJSPUrl() {
		return null;
	}

	public void setOrgStaffingLevelFilterEnable(String orgStaffingLevelFilter) {
		setValue("isOrgStaffingLevelFilterEnable", orgStaffingLevelFilter);
	}

	public String getOrgStaffingLevelFilterEnable() {
		return getValue("isOrgStaffingLevelFilterEnable");
		
	}

	public void setOrgStaffingLevelFilterSub(String orgStaffingLevelFilterSub) {
		setValue("orgStaffingLevelFilterSub", orgStaffingLevelFilterSub);
	}

	public String getOrgStaffingLevelFilterSub() {
		return getValue("orgStaffingLevelFilterSub");
	}

	public void setOrgStaffingLevelFilterDirection(
			String orgStaffingLevelFilterDirection) {
		setValue("orgStaffingLevelFilterDirection",
				orgStaffingLevelFilterDirection);
	}

	public String getOrgStaffingLevelFilterDirection() {
		return getValue("orgStaffingLevelFilterDirection");
	}
	
	@Override
	public String getModelDesc() {
		String desc = null;
		switch (fdType) {
		case 1: // 组织可见性配置
			desc = ResourceUtil
					.getString("sys-organization:sysOrganizationVisible.config");
			break;
		case 2: // 职级过滤配置
			desc = ResourceUtil
					.getString("sys-organization:sysOrganizationStaffingLevelFilter.setting");
			break;
		default:
			desc = ResourceUtil
					.getString("sys-organization:org.address.isolation");
			break;
		}
		return desc;
	}
	
}
