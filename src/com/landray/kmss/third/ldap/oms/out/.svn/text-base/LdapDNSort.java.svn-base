package com.landray.kmss.third.ldap.oms.out;

import java.util.Comparator;

import com.landray.kmss.sys.organization.model.SysOrgElement;

public class LdapDNSort implements Comparator<SysOrgElement> {

	@Override
	public int compare(SysOrgElement o1, SysOrgElement o2) {
		String dn1 = o1.getFdLdapDN();
		String dn2 = o2.getFdLdapDN();
		int length1 = dn1.split(",").length;
		int length2 = dn2.split(",").length;
		return length1 - length2;
	}

}
