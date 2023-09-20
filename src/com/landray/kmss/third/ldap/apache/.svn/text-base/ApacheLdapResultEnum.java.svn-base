package com.landray.kmss.third.ldap.apache;

import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

public class ApacheLdapResultEnum<Entry> implements NamingEnumeration<Entry> {

	private List<Entry> entrys = null;

	private int pos = 0;

	public ApacheLdapResultEnum(List<Entry> entrys) {
		this.entrys = entrys;
	}

	@Override
	public boolean hasMoreElements() {
		if (entrys == null) {
			return false;
		}
		if (pos < entrys.size()) {
			return true;
		}
		return false;
	}

	@Override
	public Entry nextElement() {
		Entry entry = entrys.get(pos);
		pos++;
		return entry;
	}

	@Override
	public Entry next() throws NamingException {
		return nextElement();
	}

	@Override
	public boolean hasMore() throws NamingException {
		return hasMoreElements();
	}

	@Override
	public void close() throws NamingException {
		entrys = null;
		pos = 0;
	}

}
