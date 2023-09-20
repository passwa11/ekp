package com.landray.kmss.sys.oms.out.interfaces;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgGroup;

/**
 * 群组封装
 * 
 * @author 吴兵
 * @version 1.0 2006-11-29
 */
public class OrgGroupContext extends OrgElementContext {
	private SysOrgGroup orgGroup;

	public SysOrgGroup getOrgGroup() {
		return orgGroup;
	}

	public OrgGroupContext(SysOrgGroup orgGroup) {
		super(orgGroup);
		this.orgGroup = orgGroup;
	}

	public String getGroupCateName() {
		if (orgGroup.getFdGroupCate() == null) {
            return null;
        }
		return orgGroup.getFdGroupCate().getFdNameOri();
	}

	public String getParentGroupCateName() {
		if (orgGroup.getFdGroupCate() == null) {
            return null;
        }
		if (orgGroup.getFdGroupCate().getFdParent() == null) {
            return null;
        }
		return orgGroup.getFdGroupCate().getFdParent().getFdNameOri();
	}

	public List getFdMembers() {
		return getMembers(orgGroup.getFdMembers());
	}

	private List getMembers(List orgMembers) {
		List members = new ArrayList();
		for (int i = 0; i < orgMembers.size(); i++) {
			members
					.add(new OrgElementContext((SysOrgElement) orgMembers
							.get(i)));
		}
		return members;
	}

}
