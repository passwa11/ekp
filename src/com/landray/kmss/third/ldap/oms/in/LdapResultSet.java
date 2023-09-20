package com.landray.kmss.third.ldap.oms.in;

import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.oms.in.interfaces.IOMSElementBaseAttribute;
import com.landray.kmss.sys.oms.in.interfaces.IOrgElement;
import com.landray.kmss.third.ldap.LdapContext;
import com.landray.kmss.third.ldap.LdapEntry;
import com.landray.kmss.third.ldap.LdapResult;
import com.landray.kmss.third.ldap.LdapService;
import com.landray.kmss.third.ldap.base.BaseLdapResultSet;
import com.landray.kmss.third.ldap.base.BaseLdapService;
import com.landray.kmss.util.StringUtil;

public class LdapResultSet extends BaseLdapResultSet {

	protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(LdapResultSet.class);

	public LdapResultSet() {
		//init();
	}

	private LdapService service;

	private LdapResult ldapResult;

	public void setService(LdapService service) {
		this.service = service;
		this.context = service.getLdapContext();
		super.init();
	}


	@Override
    public IOMSElementBaseAttribute getElementBaseAttribute() throws Exception {
		SearchResult emel = (SearchResult) ldapResult.getNamingEnumeration()
				.next();
		String dn = emel.getNameInNamespace();
		if (orgTypes[currentType] == 2 && !this.basednisroot
				&& dn.equalsIgnoreCase(this.deptBaseDN)) {
			return null;
		}
		LdapElementBaseAttribute base = new LdapElementBaseAttribute();
		String[] attrs = getAttributes();
		String attr = attrs[0];
		String val = "";
		if ("objectGUID".equalsIgnoreCase(attr)) {
			val = StringUtil.toHexString((byte[]) emel.getAttributes()
					.get(attr).get());
		} else {
			val = emel.getAttributes().get(attr).get().toString();
		}
		base.setElementUUID(val);
		base.setElementType(orgTypes[currentType]);

		base.setElementLdapDN(LdapEntry.formatDN(dn));
		if (StringUtil.isNotNull(attrs[1])
				&& emel.getAttributes().get(attrs[1]) != null
				&& emel.getAttributes().get(attrs[1]).get() != null) {
			base.setElementKeyword(emel.getAttributes().get(attrs[1]).get()
					.toString());
		}
		if (StringUtil.isNotNull(attrs[2])
				&& emel.getAttributes().get(attrs[2]) != null
				&& emel.getAttributes().get(attrs[2]).get() != null) {
			base.setElementNumber(emel.getAttributes().get(attrs[2]).get()
					.toString());
		}
		if (StringUtil.isNotNull(attrs[3])
				&& emel.getAttributes().get(attrs[3]) != null
				&& emel.getAttributes().get(attrs[3]).get() != null) {
			base.setElementName(emel.getAttributes().get(attrs[3]).get()
					.toString());
		}
		base.setElementKeywordNeedSynchro(isPropertyMap(omsTypes[currentType],
				"keyword"));
		base.setElementNumberNeedSynchro(isPropertyMap(omsTypes[currentType],
				"number"));

		return base;
	}

	@Override
    public LdapElementBaseAttribute getMappingAttribute() throws Exception {
		SearchResult emel = (SearchResult) ldapResult.getNamingEnumeration()
				.next();
		String dn = emel.getNameInNamespace();
		logger.debug("DN:" + dn);
		if (orgTypes[currentType] == 2 && !this.basednisroot
				&& dn.equalsIgnoreCase(this.deptBaseDN)) {
			return null;
		}
		LdapElementBaseAttribute base = new LdapElementBaseAttribute();
		String[] attrs = getAttributes();
		if (attrs == null || attrs.length == 0) {
			logger.info("未配置 " + orgTypes[currentType] + " 的映射，不处理");
			return null;
		}
		String attr = attrs[0];
		String val = "";
		if ("objectGUID".equalsIgnoreCase(attr)) {
			val = StringUtil.toHexString((byte[]) emel.getAttributes()
					.get(attr).get());
		} else {
			logger.debug("emel.getAttributes():" + emel.getAttributes().size());
			logger.debug("emel.getAttributes().get(attr):" + attr + "--"
					+ emel.getAttributes().get(attr));
			logger.debug("emel.getAttributes().get(attr).get():"
					+ emel.getAttributes().get(attr).get());
			val = emel.getAttributes().get(attr).get().toString();
		}
		base.setElementUUID(val);
		base.setElementType(orgTypes[currentType]);

		base.setElementLdapDN(LdapEntry.formatDN(dn));
		if ("DN".equals(attrs[1])) {
			String hierName = buildHierName(base.getElementLdapDN());
			if (StringUtil.isNotNull(hierName)) {
				base.setElementMapping(hierName);
			}
		} else if (StringUtil.isNotNull(attrs[1])
				&& emel.getAttributes().get(attrs[1]) != null
				&& emel.getAttributes().get(attrs[1]).get() != null) {
			base.setElementMapping(emel.getAttributes().get(attrs[1]).get()
					.toString());
		}
		return base;
	}


	@Override
    public IOrgElement getElement() throws Exception {
		SearchResult result = (SearchResult) ldapResult.getNamingEnumeration()
				.next();
		String dn = result.getNameInNamespace();
		if (orgTypes[currentType] == 2 && !this.basednisroot
				&& dn.equalsIgnoreCase(this.deptBaseDN)) {
			return null;
		}
		LdapEntry entry = new LdapEntry(context, result.getAttributes(),
				omsTypes[currentType], dn);
		IOrgElement element = null;

		if (orgTypes[currentType] == 2) {
			element = new LdapDept(entry);
		}
		if (orgTypes[currentType] == 8) {
			element = new LdapPerson(entry);
		}
		if (orgTypes[currentType] == 4) {
			element = new LdapPost(entry);
		}
		if (orgTypes[currentType] == 16) {
			element = new LdapGroup(entry);
		}
		return element;
	}

	@Override
    public void open() throws Exception {
		for(String type:omsTypes){
			String baseDN_str = service.getLdapContext().getTypeConfig(type, "baseDN");
			String[] dns = {};
			if(StringUtil.isNotNull(baseDN_str)){
				dns = baseDN_str.split(";");
			}
			dnsOfOrgType.put(type, dns);
		}
			
		ldapResult = service.getLdapResultByDn(getNextBaseDN(),omsTypes[currentType], begin,
				getAttributes(), null, getDeleted);
		
//		ldapResult = service.getLdapResult(omsTypes[currentType], begin,
//				getAttributes(), null);
		context = new LdapContext(null);
		context.connect();
		isOpen = true;
	}

	@Override
    public void openMapping() throws Exception {
		for (String type : omsTypes) {
			String baseDN_str = service.getLdapContext().getTypeConfig(type,
					"baseDN");
			String[] dns = {};
			if (StringUtil.isNotNull(baseDN_str)) {
				dns = baseDN_str.split(";");
			}
			dnsOfOrgType.put(type, dns);
		}
		if (getAttributes().length == 0) {
			ldapResult = null;
		} else {
			ldapResult = service.getLdapResultByDn(getNextBaseDN(),
					omsTypes[currentType], begin,
					getAttributes(), null, getDeleted);
		}

		// ldapResult = service.getLdapResult(omsTypes[currentType], begin,
		// getAttributes(), null);
		context = new LdapContext(null);
		context.connect();
		isOpen = true;
	}

	@Override
    public void close() throws Exception {
		ldapResult = null;
		service.close();
		context.close();
		isOpen = false;
	}
	
	private boolean nextDn() throws Exception {
		currentDn++;
		if(currentDn < dnsOfOrgType.get(omsTypes[currentType]).length){
			
			ldapResult = service.getLdapResultByDn(getNextBaseDN(),omsTypes[currentType], begin,
					getAttributes(), null, getDeleted);
			return next();
		}else{
			currentDn = 0;
			return nextType();
		}
		
	}

	private boolean nextType() throws Exception {
		currentType++;
		currentDn = 0;
		if (currentType < omsTypes.length) {
//			ldapResult = service.getLdapResult(omsTypes[currentType], begin,
//					getAttributes(), null);
			
			ldapResult = service.getLdapResultByDn(getNextBaseDN(),omsTypes[currentType], begin,
					getAttributes(), null, getDeleted);
			return next();
		} else {
			return false;
		}
	}

	@Override
    public boolean next() throws Exception {
		if (isOpen == false) {
			throw new Exception("LdapResultSet 没有调用open方法");
		}
		if (ldapResult == null) {
			return nextDn();
		}
		if (currentType < omsTypes.length) {
			if (ldapResult.getNamingEnumeration().hasMore()) {
				return true;
			} else {
				Control[] control = ldapResult.getControls();
				if (control != null) {
					ldapResult = service.getLdapResultByDn(dnsOfOrgType.get(omsTypes[currentType])[currentDn],omsTypes[currentType], begin,
							getAttributes(), control, getDeleted);
//					ldapResult = service.getLdapResult(omsTypes[currentType],
//							begin, getAttributes(), control);
					return next();
				} else {
					return nextDn();
				}
			}
		}
		return false;
	}
	

	@Override
	public void setService(BaseLdapService service) {
		this.service = (LdapService) service;
		this.context = service.getLdapContext();
		init();
	}

}
