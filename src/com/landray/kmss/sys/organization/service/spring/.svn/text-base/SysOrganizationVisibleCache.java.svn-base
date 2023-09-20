package com.landray.kmss.sys.organization.service.spring;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SysOrganizationVisibleCache {

	private boolean isOrgAeraEnable = false;

	public boolean isOrgAeraEnable() {
		return isOrgAeraEnable;
	}

	public void setOrgAeraEnable(boolean isOrgAeraEnable) {
		this.isOrgAeraEnable = isOrgAeraEnable;
	}
	
	private boolean isOrgVisibleEnable = false;

	public boolean isOrgVisibleEnable() {
		return isOrgVisibleEnable;
	}

	public void setOrgVisibleEnable(boolean isOrgVisibleEnable) {
		this.isOrgVisibleEnable = isOrgVisibleEnable;
	}

	public int getDefaultVisibleLevel() {
		return defaultVisibleLevel;
	}

	public void setDefaultVisibleLevel(int defaultVisibleLevel) {
		this.defaultVisibleLevel = defaultVisibleLevel;
	}

	public Map<String, Set<String>> getOrganizationVisibleMap() {
		return organizationVisibleMap;
	}

	public void setOrganizationVisibleMap(
			Map<String, Set<String>> organizationVisibleMap) {
		this.organizationVisibleMap = organizationVisibleMap;
	}

	private int defaultVisibleLevel;

	private Map<String, Set<String>> organizationVisibleMap = new ConcurrentHashMap<String, Set<String>>();

	public void clearOrganizationVisibleMap() {
		organizationVisibleMap.clear();
		userCurrAreaId.clear();
	}

	public void put(String principalId, Set<String> subordinates) {
		organizationVisibleMap.put(principalId, subordinates);
	}

	public Set<String> get(String principalId) {
		return organizationVisibleMap.get(principalId);
	}

	private Map<String, String> userCurrAreaId = new ConcurrentHashMap<String, String>();

	public Map<String, String> getUserCurrAreaId() {
		return userCurrAreaId;
	}

	public void setUserCurrAreaId(Map<String, String> userCurrAreaId) {
		this.userCurrAreaId = userCurrAreaId;
	}
	
}
