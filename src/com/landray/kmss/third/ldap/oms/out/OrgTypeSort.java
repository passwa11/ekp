package com.landray.kmss.third.ldap.oms.out;

import java.util.Comparator;

import com.landray.kmss.sys.organization.model.SysOrgElement;

public class OrgTypeSort implements Comparator<SysOrgElement> {


	@Override
	public int compare(SysOrgElement o1, SysOrgElement o2) {
		// TODO Auto-generated method stub
		if (o1.getFdOrgType().intValue() == o2.getFdOrgType().intValue()) {
			if (o1.getFdOrgType() == 1) {
				String hierarchyId_o1 = getOrgHierarchyId(o1);
				String hierarchyId_o2 = getOrgHierarchyId(o2);
				return hierarchyId_o1.length() - hierarchyId_o2.length();
			} else {
				return o1.getFdHierarchyId().length()
					- o2.getFdHierarchyId().length();
			}
		}
		return o1.getFdOrgType() - o2.getFdOrgType();
	}

	private String getOrgHierarchyId(SysOrgElement ele) {
		String fdId = ele.getFdId();
		String hierarchyId = fdId;
		while (ele.getFdParent() != null) {
			ele = ele.getFdParent();
			hierarchyId = ele.getFdId() + "X" + hierarchyId;
		}
		return hierarchyId;
	}

}
