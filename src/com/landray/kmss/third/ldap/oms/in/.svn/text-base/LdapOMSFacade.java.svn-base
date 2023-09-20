package com.landray.kmss.third.ldap.oms.in;

import java.util.Date;
import java.util.List;

import com.landray.kmss.sys.oms.in.interfaces.IOrgElement;
import com.landray.kmss.third.ldap.LdapEntries;

public class LdapOMSFacade {

	public static void setOMSDept(List omsList, LdapEntries entries)
			throws Exception {
		if (entries == null) {
			return;
		}
		for (int i = 0; i < entries.size(); i++) {
			omsList.add(new LdapDept(entries.get(i)));
		}
	}

	public static void setOMSPerson(List omsList, LdapEntries entries)
			throws Exception {
		if (entries == null) {
			return;
		}
		for (int i = 0; i < entries.size(); i++) {
			omsList.add(new LdapPerson(entries.get(i)));
		}
	}

	public static void setOMSPost(List omsList, LdapEntries entries)
			throws Exception {
		if (entries == null) {
			return;
		}
		for (int i = 0; i < entries.size(); i++) {
			omsList.add(new LdapPost(entries.get(i)));
		}
	}

	public static void setOMSGroup(List omsList, LdapEntries entries)
			throws Exception {
		if (entries == null) {
			return;
		}
		for (int i = 0; i < entries.size(); i++) {
			omsList.add(new LdapGroup(entries.get(i)));
		}
	}

	public static Date getLastUpdateTime(List records) throws Exception {
		Date lastTime = null;
		if (records != null && !records.isEmpty()) {
			long big = 0;
			for (int i = 0; i < records.size(); i++) {
				Date datetime = ((IOrgElement) records.get(i)).getAlterTime();
				if (datetime != null) {
					long time = datetime.getTime();
					if (time > big) {
						big = time;
						lastTime = datetime;
					}
				}
			}
			return lastTime;
		}
		return null;
	}

}