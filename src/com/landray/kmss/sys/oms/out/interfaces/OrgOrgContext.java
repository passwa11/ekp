package com.landray.kmss.sys.oms.out.interfaces;

import com.landray.kmss.sys.organization.model.SysOrgOrg;

/**
 * 机构封装
 * 
 * @author 吴兵
 * @version 1.0 2006-11-29
 */
public class OrgOrgContext extends OrgElementContext {
	SysOrgOrg orgOrg;

	public SysOrgOrg getOrgOrg() {
		return orgOrg;
	}

	public OrgOrgContext(SysOrgOrg orgOrg) {
		super(orgOrg);
		this.orgOrg = orgOrg;
	}

}
