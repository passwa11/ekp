package com.landray.kmss.third.ldap.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.oms.in.interfaces.IOMSElementBaseAttribute;
import com.landray.kmss.sys.oms.in.interfaces.IOMSResultSet;
import com.landray.kmss.sys.oms.in.interfaces.IOrgElement;
import com.landray.kmss.third.ldap.oms.in.LdapElementBaseAttribute;
import com.landray.kmss.util.StringUtil;

public abstract class BaseLdapResultSet implements IOMSResultSet {

	protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(BaseLdapResultSet.class);

	public BaseLdapResultSet() {
		
		
	}

	public abstract void setService(BaseLdapService service);

	protected boolean basednisroot = false;
	protected String deptBaseDN = "";
	protected BaseLdapContext context;
	protected Date begin;
	private String[] attributes;
	private String[][] ldapAttributesName;
	// 
	protected String[] omsTypes = new String[] { "dept", "person", "post",
			"group" };
	//  
	protected int[] orgTypes = new int[] { 2, 8, 4, 16 };
	protected boolean isOpen = false;
	protected int currentType = 0;
	
	protected int currentDn = 0;

	protected boolean getDeleted = false;

	protected Map<String, String[]> dnsOfOrgType = new HashMap<String, String[]>();
	
	public Map<String, String[]> getDnsOfOrgType() {
		return dnsOfOrgType;
	}

	public void setDnsOfOrgType(Map<String, String[]> dnsOfOrgType) {
		this.dnsOfOrgType = dnsOfOrgType;
	}


	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public void setAttributes(String[] attributes) {
		this.attributes = attributes;
		ldapAttributesName = new String[omsTypes.length][];
		for (int j = 0; j < omsTypes.length; j++) {
			String[] atts = new String[this.attributes.length];
			for (int i = 0; i < atts.length; i++) {
				atts[i] = context.getTypeConfig(omsTypes[j],
						this.attributes[i]);
			}
			ldapAttributesName[j] = atts;
		}
	}

	public void setAttributes(String[] attributes, int orgTypeOrder) {
		this.attributes = attributes;
		if (ldapAttributesName == null) {
			ldapAttributesName = new String[omsTypes.length][];
		}
		ldapAttributesName[orgTypeOrder] = attributes;
	}

	public String[] getAttributes() {
		if (this.attributes == null) {
			return null;
		}
		return ldapAttributesName[currentType];
	}

	public void init() {
		this.deptBaseDN = context.getConfig(
				"kmss.ldap.type.dept.baseDN");
		String bbb = context.getConfig(
				"kmss.ldap.type.dept.basednisroot");
		if (StringUtil.isNotNull(bbb)) {
			try {
				this.basednisroot = Boolean.valueOf(bbb);
			} catch (Exception e) {
			}
		}
	}

	public int getElementOrgType() {
		return orgTypes[currentType];
	}

	@Override
    public abstract IOMSElementBaseAttribute getElementBaseAttribute()
			throws Exception;

	public abstract LdapElementBaseAttribute getMappingAttribute()
			throws Exception;

	@Override
    public abstract IOrgElement getElement() throws Exception;

	@Override
    public abstract void open() throws Exception;

	public abstract void openMapping() throws Exception;

	@Override
    public abstract void close() throws Exception;

	@Override
    public abstract boolean next() throws Exception;
	
	
	protected String buildHierName(String ldapDn) {
		String hierName = ldapDn;
		String mappingDn = null;
		String[] dns = dnsOfOrgType.get("dept");
		String basednisroot = context.getTypeConfig("dept",
				"basednisroot");
		for (String baseDn : dns) {
			if (ldapDn.equals(baseDn)) {
				if ("true".equals(basednisroot)) {
					String deptName = ldapDn.substring(0, ldapDn.indexOf(","));
					deptName = deptName.substring(deptName.indexOf("=") + 1)
							.trim();
					return deptName;
				} else {
					return null;
				}
			}
			if (ldapDn.endsWith(baseDn)) {
				hierName = ldapDn.substring(0,
						ldapDn.length() - baseDn.length() - 1);
				mappingDn = baseDn;
				break;
			}
		}
		if (StringUtil.isNull(mappingDn)) {
			return null;
		}
		List<String> deptValues = new ArrayList<String>();
		String[] ss = hierName.split(",");
		for (String s : ss) {
			s = s.trim();
			String[] vs = s.split("=");
			String value = vs[1].trim();
			deptValues.add(value);
		}
		if ("true".equals(basednisroot)) {
			String deptName = mappingDn.substring(0, mappingDn.indexOf(","));
			deptName = deptName.substring(deptName.indexOf("=") + 1).trim();
			deptValues.add(deptName);
		}
		hierName = "";
		for (int i = deptValues.size() - 1; i >= 0; i--) {
			hierName += "#" + deptValues.get(i);
		}
		hierName = hierName.substring(1);
		return hierName;

	}

	public boolean isGetDeleted() {
		return getDeleted;
	}

	public void setGetDeleted(boolean getDeleted) {
		this.getDeleted = getDeleted;
	}

	public boolean isPropertyMap(String orgType, String name)
			throws NamingException {
		return context.getLdapAttribute(orgType, name) != null;
	}

	protected String getNextBaseDN() {
		String baseDn = null;
		String[] dns = dnsOfOrgType.get(omsTypes[currentType]);
		if (currentDn < dns.length) {
			baseDn = dns[currentDn];
		}
		return baseDn;
	}

}
