package com.landray.kmss.third.ldap.apache;

import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.directory.api.ldap.model.entry.Entry;

import com.landray.kmss.sys.oms.in.interfaces.IOMSElementBaseAttribute;
import com.landray.kmss.sys.oms.in.interfaces.IOrgElement;
import com.landray.kmss.third.ldap.base.BaseLdapResultSet;
import com.landray.kmss.third.ldap.base.BaseLdapService;
import com.landray.kmss.third.ldap.oms.in.LdapDept;
import com.landray.kmss.third.ldap.oms.in.LdapElementBaseAttribute;
import com.landray.kmss.third.ldap.oms.in.LdapGroup;
import com.landray.kmss.third.ldap.oms.in.LdapPerson;
import com.landray.kmss.third.ldap.oms.in.LdapPost;
import com.landray.kmss.third.ldap.oms.in.LdapResultSet;
import com.landray.kmss.util.StringUtil;

public class ApacheLdapResultSet extends BaseLdapResultSet {

	protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(LdapResultSet.class);

	private ApacheLdapService service;

	public ApacheLdapResultSet() {
		//init();
	}


	private ApacheLdapResultEnum<Entry> ldapResult;



	@Override
    public IOMSElementBaseAttribute getElementBaseAttribute() throws Exception {
		Entry emel = (Entry) ldapResult.next();
		String dn = emel.getDn().getName();
		if (orgTypes[currentType] == 2 && !this.basednisroot
				&& dn.equalsIgnoreCase(this.deptBaseDN)) {
			return null;
		}
		LdapElementBaseAttribute base = new LdapElementBaseAttribute();
		String[] attrs = getAttributes();
		String attr = attrs[0];
		String val = "";
		if ("objectGUID".equalsIgnoreCase(attr)) {
			val = StringUtil.toHexString(emel.get(attr).getBytes());
		} else {
			val = emel.get(attr).get().toString();
		}
		base.setElementUUID(val);
		base.setElementType(orgTypes[currentType]);

		base.setElementLdapDN(ApacheLdapEntry.formatDN(dn));
		if (StringUtil.isNotNull(attrs[1])
				&& emel.get(attrs[1]) != null
				&& emel.get(attrs[1]).get() != null) {
			base.setElementKeyword(emel.get(attrs[1]).get()
					.toString());
		}
		if (StringUtil.isNotNull(attrs[2])
				&& emel.get(attrs[2]) != null
				&& emel.get(attrs[2]).get() != null) {
			base.setElementNumber(emel.get(attrs[2]).get()
					.toString());
		}
		if (StringUtil.isNotNull(attrs[3])
				&& emel.get(attrs[3]) != null
				&& emel.get(attrs[3]).get() != null) {
			base.setElementName(emel.get(attrs[3]).get()
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
		Entry emel = (Entry) ldapResult.next();
		String dn = emel.getDn().getName();
		logger.debug("DN:" + dn);
		if (orgTypes[currentType] == 2 && !this.basednisroot
				&& dn.equalsIgnoreCase(this.deptBaseDN)) {
			return null;
		}
		LdapElementBaseAttribute base = new LdapElementBaseAttribute();
		String[] attrs = getAttributes();
		String attr = attrs[0];
		String val = "";
		if ("objectGUID".equalsIgnoreCase(attr)) {
			val = StringUtil.toHexString(emel.get(attr).getBytes());
		} else {
			logger.debug("emel.getAttributes():" + emel.getAttributes().size());
			logger.debug("emel.getAttributes().get(attr):" + attr + "--"
					+ emel.get(attr));
			logger.debug("emel.getAttributes().get(attr).get():"
					+ emel.get(attr).get());
			val = emel.get(attr).get().toString();
		}
		base.setElementUUID(val);
		base.setElementType(orgTypes[currentType]);

		base.setElementLdapDN(ApacheLdapEntry.formatDN(dn));
		if ("DN".equals(attrs[1])) {
			String hierName = buildHierName(base.getElementLdapDN());
			if (StringUtil.isNotNull(hierName)) {
				base.setElementMapping(hierName);
			}
		} else if (StringUtil.isNotNull(attrs[1])
				&& emel.get(attrs[1]) != null
				&& emel.get(attrs[1]).get() != null) {
			base.setElementMapping(emel.get(attrs[1]).get()
					.toString());
		}
		return base;
	}


	@Override
    public IOrgElement getElement() throws Exception {
		Entry result = (Entry) ldapResult
				.next();
		String dn = result.getDn().getName();
		if (orgTypes[currentType] == 2 && !this.basednisroot
				&& dn.equalsIgnoreCase(this.deptBaseDN)) {
			return null;
		}
		ApacheLdapEntry entry = new ApacheLdapEntry(context,
				IteratorUtils.toList(result.getAttributes().iterator()),
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
			
		List<Entry> entrys = service.getLdapResultByDn(getNextBaseDN(),
				omsTypes[currentType], begin,
				getAttributes(), getDeleted);
		ldapResult = new ApacheLdapResultEnum(entrys);
		
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
			List<Entry> entrys = service.getLdapResultByDn(getNextBaseDN(),
					omsTypes[currentType], begin,
					getAttributes(), getDeleted);
			ldapResult = new ApacheLdapResultEnum(entrys);
		}

		isOpen = true;
	}

	@Override
    public void close() throws Exception {
		ldapResult = null;
		service.close();
		isOpen = false;
	}
	
	private boolean nextDn() throws Exception {
		currentDn++;
		if(currentDn < dnsOfOrgType.get(omsTypes[currentType]).length){
			List<Entry> entrys = service.getLdapResultByDn(getNextBaseDN(),
					omsTypes[currentType], begin,
					getAttributes(), getDeleted);
			ldapResult = new ApacheLdapResultEnum(entrys);
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
			List<Entry> entrys = service.getLdapResultByDn(getNextBaseDN(),
					omsTypes[currentType], begin,
					getAttributes(), getDeleted);
			ldapResult = new ApacheLdapResultEnum(entrys);
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
			if (ldapResult.hasMore()) {
				return true;
			} else {
				return nextDn();
			}
		}
		return false;
	}

	@Override
	public void setService(BaseLdapService service) {
		// TODO Auto-generated method stub
		this.service = (ApacheLdapService) service;
		this.context = service.getLdapContext();
		init();
	}
	


}
