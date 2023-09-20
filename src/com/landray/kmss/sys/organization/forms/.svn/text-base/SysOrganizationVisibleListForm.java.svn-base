package com.landray.kmss.sys.organization.forms;

import java.io.Serializable;
import java.util.List;

import com.landray.kmss.web.action.ActionForm;

import com.landray.kmss.util.AutoArrayList;

public class SysOrganizationVisibleListForm extends ActionForm implements Serializable {
	
	protected List sysOrganizationVisibleFormList = new AutoArrayList(
			SysOrganizationVisibleForm.class);
	
	protected String isOrgVisibleEnable = "false";
	
	protected String defaultVisibleLevel = "0";
	
	public String getDefaultVisibleLevel() {
		return defaultVisibleLevel;
	}

	public void setDefaultVisibleLevel(String defaultVisibleLevel) {
		this.defaultVisibleLevel = defaultVisibleLevel;
	}

	public String getIsOrgVisibleEnable() {
		return isOrgVisibleEnable;
	}

	public void setIsOrgVisibleEnable(String isOrgVisibleEnable) {
		this.isOrgVisibleEnable = isOrgVisibleEnable;
	}
	
	// 启用地址本数据在用户场景下隔离
	protected String isOrgAeraEnable = "false";

	public String getIsOrgAeraEnable() {
		return isOrgAeraEnable;
	}

	public void setIsOrgAeraEnable(String isOrgAeraEnable) {
		this.isOrgAeraEnable = isOrgAeraEnable;
	}
	
	public void setSysOrganizationVisibleFormList(
			List sysOrganizationVisibleFormList) {
		this.sysOrganizationVisibleFormList = sysOrganizationVisibleFormList;
	}

	public List getSysOrganizationVisibleFormList() {
		return sysOrganizationVisibleFormList;
	}

	

	
}
