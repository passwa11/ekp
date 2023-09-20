package com.landray.kmss.third.ldap.oms.in;

import java.util.Date;
import java.util.Map;

import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.sys.oms.in.interfaces.IOMSResultSet;
import com.landray.kmss.sys.oms.in.interfaces.OMSBaseSynchroInIteratorProvider;
import com.landray.kmss.sys.oms.in.interfaces.ValueMapTo;
import com.landray.kmss.sys.oms.in.interfaces.ValueMapType;
import com.landray.kmss.third.ldap.LdapConfig;
import com.landray.kmss.third.ldap.LdapContext;
import com.landray.kmss.third.ldap.LdapDetailConfig;
import com.landray.kmss.third.ldap.LdapService;
import com.landray.kmss.third.ldap.apache.ApacheLdapContext;
import com.landray.kmss.third.ldap.apache.ApacheLdapResultSet;
import com.landray.kmss.third.ldap.apache.ApacheLdapService;
import com.landray.kmss.third.ldap.base.BaseLdapContext;
import com.landray.kmss.third.ldap.base.BaseLdapResultSet;
import com.landray.kmss.third.ldap.base.BaseLdapService;

public class LdapSynchroInProviderImp extends OMSBaseSynchroInIteratorProvider {

	private Date lastUpdateTime = null;

	private Date lastCheckDeleteTime = null;

	private LdapOmsConfig ldapOmsConfig = null;

	private BaseLdapContext getContext() throws Exception {
		Map<String, String> config = new LdapDetailConfig().getDataMap();
		String url = config.get("kmss.ldap.config.url");
		if (StringUtil.isNull(url)) {
			return new LdapContext(null);
		}
		if (url.startsWith("ldaps")) {
			return new ApacheLdapContext(null);
		}
		return new LdapContext(null);
	}

	private BaseLdapResultSet getLdapResultSet(Long connectTimeout,
			Long readTimeout) throws Exception {
		Map<String, String> config = new LdapDetailConfig().getDataMap();
		String url = config.get("kmss.ldap.config.url");
		if (StringUtil.isNotNull(url) && url.startsWith("ldaps")) {
			ApacheLdapResultSet resultSet = new ApacheLdapResultSet();
			BaseLdapService service = new ApacheLdapService();
			service.setConnectTimeout(connectTimeout);
			service.setReadTimeout(readTimeout);
			service.connect();
			resultSet.setService(service);
			return resultSet;
		}
		LdapResultSet resultSet = new LdapResultSet();
		BaseLdapService service = new LdapService();
		service.setConnectTimeout(connectTimeout);
		service.setReadTimeout(readTimeout);
		service.connect();
		resultSet.setService(service);
		return resultSet;

	}

	@Override
	public void init() throws Exception {
		ldapOmsConfig = new LdapOmsConfig();
		lastCheckDeleteTime = ldapOmsConfig.getLastDeleteTime();
		context = getContext();
	}

	@Override
	public String getKey() {
		return LdapSynchroInProviderImp.class.getName();
	}

	private String getTypeName(int type) {
		switch (type) {
		case SysOrgConstant.ORG_TYPE_ORG:
			return "org";
		case SysOrgConstant.ORG_TYPE_DEPT:
			return "dept";
		case SysOrgConstant.ORG_TYPE_PERSON:
			return "person";
		case SysOrgConstant.ORG_TYPE_POST:
			return "post";
		case SysOrgConstant.ORG_TYPE_GROUP:
			return "group";
		}
		return "";
	}

	@Override
	public void setLastUpdateTime(Date date) {
		ldapOmsConfig.setLastUpdateTime(date);
	}

	@Override
	public void terminate() throws Exception {
		ldapOmsConfig.setLastDeleteTime(lastCheckDeleteTime);
		ldapOmsConfig.save();
		context.close();
	}

	@Override
	public IOMSResultSet getSynchroRecords() throws Exception {
		lastUpdateTime = ldapOmsConfig.getLastUpdateTime();
		Date date = null;
		if (lastUpdateTime != null) {
			date = new Date(lastUpdateTime.getTime() - 86400000L);
		}
		BaseLdapResultSet resultSet = getLdapResultSet(10000L, 30000L);
		resultSet.setBegin(date);
		resultSet.open();
		return resultSet;
	}

	@Override
	public IOMSResultSet getAllRecordBaseAttributes() throws Exception {
		lastCheckDeleteTime = new Date();
		BaseLdapResultSet resultSet = getLdapResultSet(10000L, 120000L);
		resultSet.setBegin(null);
		resultSet.setAttributes(new String[] { "prop.unid", "prop.keyword",
				"prop.number", "prop.name" });
		resultSet.open();
		return resultSet;
	}

	private BaseLdapContext context;

	public ValueMapTo getMap(String type, String name) {
		String objkey = "";
		String byParentDN = context.getTypeConfig(type, "prop." + name
				+ ".byParentDN");
		if ("true".equals(byParentDN)) {
			objkey = "dn";
		} else {
			objkey = context.getTypeConfig(type, "prop." + name + ".objKey");
		}
		if (objkey != null) {
			if ("keyword".equalsIgnoreCase(objkey)) {
				return ValueMapTo.KEYWORD;
			}
			if ("number".equalsIgnoreCase(objkey)) {
				return ValueMapTo.NUMBER;
			}
			if ("dn".equalsIgnoreCase(objkey)) {
				return ValueMapTo.LDAPDN;
			}
		}
		return null;
	}

	@Override
	public ValueMapTo getDeptLeaderValueMapTo() {
		return getMap("dept", "thisleader");
	}

	@Override
	public ValueMapTo getDeptParentValueMapTo() {
		return getMap("dept", "parent");
	}

	@Override
	public ValueMapTo getDeptSuperLeaderValueMapTo() {
		return getMap("dept", "superleader");
	}

	@Override
	public ValueMapTo getGroupMemberValueMapTo() {
		return getMap("group", "member");
	}

	@Override
	public ValueMapTo getPersonDeptValueMapTo() {
		return getMap("person", "dept");
	}

	@Override
	public ValueMapTo getPersonPostValueMapTo() {
		return getMap("person", "post");
	}

	@Override
	public ValueMapTo getPostDeptValueMapTo() {
		return getMap("post", "dept");
	}

	@Override
	public ValueMapTo getPostLeaderValueMapTo() {
		return getMap("post", "thisleader");
	}

	@Override
	public ValueMapTo getPostPersonValueMapTo() {
		return getMap("post", "member");
	}
	
	@Override
	public ValueMapType[] getGroupMemberValueMapType() {
		return new ValueMapType[] { ValueMapType.ORG, ValueMapType.DEPT,
				ValueMapType.POST, ValueMapType.PERSON, ValueMapType.GROUP };
	}

	@Override
	public boolean isSynchroInEnable() throws Exception {
		// TODO 自动生成的方法存根
		LdapConfig config = new LdapConfig();
		String synchroInEnable = config.getValue("kmss.oms.in.ldap.enabled");
		String strategy = new LdapDetailConfig()
				.getValue("kmss.ldap.type.common.prop.syncStrategy");
		if ("true".equals(synchroInEnable) && !"increment".equals(strategy)) {
			return true;
		}
		return false;
	}

	// public int getPasswordType() {
	// return SysOMSConstant.PASSWORD_TYPE_REQUIRED;
	// }
}
