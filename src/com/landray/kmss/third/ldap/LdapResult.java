package com.landray.kmss.third.ldap;

import javax.naming.NamingEnumeration;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.PagedResultsControl;
import javax.naming.ldap.PagedResultsResponseControl;

public class LdapResult {
	public LdapResult(int pageSize, InitialLdapContext dirContext,
			NamingEnumeration namingEnumeration) {
		this.pageSize = pageSize;
		this.dirContext = dirContext;
		this.namingEnumeration = namingEnumeration;
	}

	private int pageSize;
	private InitialLdapContext dirContext;
	private NamingEnumeration namingEnumeration;

	public Control[] getControls() throws Exception {
		Control[] controls = dirContext.getResponseControls();
		if (controls != null) {
			byte[] cookie = null;
			for (int i = 0; i < controls.length; i++) {
				if (controls[i] instanceof PagedResultsResponseControl) {
					PagedResultsResponseControl prrc = (PagedResultsResponseControl) controls[i];
					// total = prrc.getResultSize();
					cookie = prrc.getCookie();
				}
			}
			if (cookie != null) {
				// Re-activate paged results
				PagedResultsControl ctl = new PagedResultsControl(pageSize,
						cookie, Control.CRITICAL);
				return new Control[] { ctl };
			}
		}
		return null;
	}

	public InitialLdapContext getDirContext() {
		return dirContext;
	}

	public void setDirContext(InitialLdapContext dirContext) {
		this.dirContext = dirContext;
	}

	public NamingEnumeration getNamingEnumeration() {
		return namingEnumeration;
	}

	public void setNamingEnumeration(NamingEnumeration namingEnumeration) {
		this.namingEnumeration = namingEnumeration;
	}
}
