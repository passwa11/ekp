package com.landray.kmss.third.ldap.apache;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.model.SysOrgDefaultConfig;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgGroup;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.util.PasswordUtil;
import com.landray.kmss.third.ldap.LdapConfig;
import com.landray.kmss.third.ldap.base.BaseLdapService;
import com.landray.kmss.third.ldap.oms.in.LdapElementBaseAttribute;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.opensymphony.util.BeanUtils;
import org.apache.directory.api.ldap.model.entry.*;
import org.apache.directory.api.ldap.model.exception.LdapEntryAlreadyExistsException;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.exception.LdapNoSuchAttributeException;
import org.apache.directory.api.ldap.model.exception.LdapNoSuchObjectException;
import org.apache.directory.api.ldap.model.message.ModifyRequest;
import org.apache.directory.api.ldap.model.schema.AttributeType;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.slf4j.Logger;

import javax.naming.NamingException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@SuppressWarnings("unchecked")
public class ApacheLdapService extends BaseLdapService {

	protected static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(ApacheLdapService.class);

	private static String[] dept_ldap_attr = { "name", "number", "order",
			"keyword", "memo" };
	private static String[] dept_ekp_attr = { "fdName", "fdNo", "fdOrder",
			"fdKeyword", "fdMemo" };

	private static String[] person_ldap_attr = { "name", "number", "order",
			"mail", "mobileNo", "workPhone", "loginName", "password", "lang",
			"keyword", "rtx", "scard", "memo", "sex", "wechat", "shortNo" };
	private static String[] person_ekp_attr = { "fdName", "fdNo", "fdOrder",
			"fdEmail", "fdMobileNo", "fdWorkPhone", "fdLoginName",
			"fdPassword", "fdDefaultLang", "fdKeyword", "fdRtxNo", "fdCardNo",
			"fdMemo", "fdSex", "fdWechatNo", "fdShortNo" };

	private static String[] post_ldap_attr = { "name", "number", "order",
			"keyword", "memo" };
	private static String[] post_ekp_attr = { "fdName", "fdNo", "fdOrder",
			"fdKeyword", "fdMemo" };

	private static String[] group_ldap_attr = { "name", "number", "order",
			"keyword", "memo" };
	private static String[] group_ekp_attr = { "fdName", "fdNo", "fdOrder",
			"fdKeyword", "fdMemo" };

	/**
	 * 构造函数，以ldapconfig.properties文件作为配置文件
	 */
	public ApacheLdapService() {
		context = new ApacheLdapContext(null);
	}

	public ApacheLdapService(boolean authentication) {
		if (authentication) {
			try {
				LdapConfig config = new LdapConfig();
				if ("true".equals(config
						.getValue("kmss.authentication.ldap.enabled"))) {
					context = new ApacheLdapContext(null);
				}

			} catch (Exception e) {
				logger.error("", e);
			}
		} else {
			context = new ApacheLdapContext(null);
		}

	}

	public ApacheLdapService(Map<String, String> config) {
		context = new ApacheLdapContext(config);
	}

	/**
	 * 链接LDAP服务器
	 * 
	 * @throws NamingException
	 */
	@Override
    public void connect() throws Exception {
		context.connect();
	}

	/**
	 * 关闭链接
	 * 
	 * @throws NamingException
	 */
	@Override
    public void close() throws Exception {
		context.close();
	}

	/**
	 * 获取某个类型的列表
	 * 
	 * @param type
	 * @param time
	 * @return
	 * @throws NamingException
	 */
	public List<Entry> getApacheEntries(String type, Date time)
			throws Exception {
		return getApacheEntries(type, time, 0);
	}

	public List<Entry> getApacheEntries(String type, Date time, int count)
			throws Exception {
		if (!context.isTypeMap(type)) {
			return null;
		}
		String filter = null;
		if (time != null) {
			filter = "(" + context.getTypeConfig(type, "prop.modifytimestamp")
					+ ">=" + context.getTimeToString(time) + ")";
		}
		String baseDN_str = context.getTypeConfig(type, "baseDN");
		String[] baseDNs = baseDN_str.split(";");
		if (baseDNs == null || baseDNs.length < 1) {
			return null;
		}

		List<Entry> entrys = new ArrayList<Entry>();

		for (int i = 0; i < baseDNs.length; i++) {
			List<Entry> entrys_this = ((ApacheLdapContext) context)
					.searchByBaseDN(
							baseDNs[i], type,
							filter, context
									.getLdapAttributes(type),
							count);
			if (entrys_this != null) {
				entrys.addAll(entrys_this);
			}
		}
		return entrys;
	}

	public List<Entry> getLdapResultByDn(String dn, String type, Date time,
			String[] attributes, boolean getDeleted)
			throws Exception {
		if (StringUtil.isNull(dn)) {
			return null;
		}
		String filter = null;
		if (time != null) {
			filter = "(" + context.getTypeConfig(type, "prop.modifytimestamp")
					+ ">=" + context.getTimeToString(time) + ")";
		}
		if (attributes == null) {
			attributes = context.getLdapAttributes(type);
		}
		if (getDeleted) {
			return ((ApacheLdapContext) context).searchDeletedByDn(dn, type,
					filter,
					attributes);
		} else {
			return ((ApacheLdapContext) context).searchByDn(dn, type, filter,
					attributes);
		}
	}

	/**
	 * 取得系统的时间戳格式
	 * 
	 * @return
	 * @throws NamingException
	 */
	public String getTimeStamp() throws Exception {
		String managerDN = context.getConfig("kmss.ldap.config.managerDN");
		String ts = context
				.getConfig("kmss.ldap.type.common.prop.modifytimestamp");
		String time = "";
		Entry entry = ((ApacheLdapContext) context).searchByDN(managerDN,
				new String[] { ts });
		if (entry != null) {
			Attribute attribute = entry.get(new AttributeType(ts));
			time = attribute.getString();
		}
		return time;
	}

	@Override
    public String doMapping() throws Exception {
		ApacheLdapResultSet resultSet = new ApacheLdapResultSet();
		resultSet.setBegin(null);
		connect();
		resultSet.setService(this);

		String deptLdapAttr = getLdapContext().getTypeConfig("mapping",
				"prop.dept.ldapAttr");
		String personLdapAttr = getLdapContext().getTypeConfig("mapping",
				"prop.person.ldapAttr");
		String postLdapAttr = getLdapContext().getTypeConfig("mapping",
				"prop.post.ldapAttr");
		String groupLdapAttr = getLdapContext().getTypeConfig("mapping",
				"prop.group.ldapAttr");

		// String unidProp = getLdapContext().getTypeConfig("dept",
		// "prop.unid");
		if (StringUtil.isNotNull(deptLdapAttr)) {
			resultSet.setAttributes(
					new String[] { getLdapContext().getTypeConfig("dept",
							"prop.unid"), deptLdapAttr },
					0);
		} else {
			resultSet.setAttributes(
					new String[] {},
					0);
		}
		if (StringUtil.isNotNull(personLdapAttr)) {
			resultSet.setAttributes(
					new String[] { getLdapContext().getTypeConfig("person",
							"prop.unid"), personLdapAttr },
					1);
		} else {
			resultSet.setAttributes(
					new String[] {},
					1);
		}
		if (StringUtil.isNotNull(postLdapAttr)) {
			resultSet.setAttributes(
					new String[] { getLdapContext().getTypeConfig("post",
							"prop.unid"), postLdapAttr },
					2);
		} else {
			resultSet.setAttributes(
					new String[] {},
					2);
		}
		if (StringUtil.isNotNull(groupLdapAttr)) {
			resultSet.setAttributes(
					new String[] { getLdapContext().getTypeConfig("group",
							"prop.unid"), groupLdapAttr },
					3);
		} else {
			resultSet.setAttributes(
					new String[] {},
					3);
		}
		resultSet.openMapping();

		List<LdapElementBaseAttribute> depts = new ArrayList<LdapElementBaseAttribute>();
		List<LdapElementBaseAttribute> persons = new ArrayList<LdapElementBaseAttribute>();
		List<LdapElementBaseAttribute> posts = new ArrayList<LdapElementBaseAttribute>();
		List<LdapElementBaseAttribute> groups = new ArrayList<LdapElementBaseAttribute>();

		while (resultSet.next()) {
			LdapElementBaseAttribute baseAtts = resultSet.getMappingAttribute();
			if (baseAtts == null) {
				continue;
			}
			int orgType = baseAtts.getElementType();
			switch (orgType) {
			case 2:
				depts.add(baseAtts);
				break;
			case 8:
				persons.add(baseAtts);
				break;
			case 4:
				posts.add(baseAtts);
				break;
			case 16:
				groups.add(baseAtts);
				break;
			}
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("-----------开始处理 " + getOrgTypeName("dept") + " 映射，总数："
				+ depts.size() + "<br>");
		logger.warn(
				"-----------开始处理 " + getOrgTypeName("dept") + " 映射，总数："
						+ depts.size());
		mapping(depts, "dept", buffer);
		buffer.append(
				"-----------" + getOrgTypeName("dept") + "映射 处理结束<br><br><br>");
		logger.warn("-----------" + getOrgTypeName("dept") + "映射 处理结束");

		buffer.append("-----------开始处理 " + getOrgTypeName("person") + " 映射，总数："
				+ persons.size() + "<br>");
		logger.warn("-----------开始处理 " + getOrgTypeName("person") + " 映射，总数："
				+ persons.size());
		mapping(persons, "person", buffer);
		buffer.append("-----------" + getOrgTypeName("person")
				+ "映射 处理结束<br><br><br>");
		logger.warn("-----------" + getOrgTypeName("person") + "映射 处理结束");

		buffer.append("-----------开始处理 " + getOrgTypeName("post") + " 映射，总数："
				+ posts.size() + "<br>");
		logger.warn(
				"-----------开始处理 " + getOrgTypeName("post") + " 映射，总数："
						+ posts.size());
		mapping(posts, "post", buffer);
		buffer.append(
				"-----------" + getOrgTypeName("post") + "映射 处理结束<br><br><br>");
		logger.warn("-----------" + getOrgTypeName("post") + "映射 处理结束");

		buffer.append("-----------开始处理 " + getOrgTypeName("group") + " 映射，总数："
				+ groups.size() + "<br>");
		logger.warn(
				"-----------开始处理 " + getOrgTypeName("group") + " 映射，总数："
						+ groups.size());
		mapping(groups, "group", buffer);
		buffer.append("-----------" + getOrgTypeName("group")
				+ "映射 处理结束<br><br><br>");
		logger.warn("-----------" + getOrgTypeName("group") + "映射 处理结束");

		return buffer.toString();
	}

	@Override
	public void setConnectTimeout(long timeout) throws Exception {
		context.setConnectTimeout(timeout);
	}

	@Override
	public void setReadTimeout(long timeout) throws Exception {
		context.setReadTimeout(timeout);
	}

	public String getFdParentsName(SysOrgElement e, String lang) {
		String fdParentsName = "";
		List list = new ArrayList();
		SysOrgElement fdParent = e.getFdParent();
		if (fdParent != null) {
			try {
				SysOrgElement parent = fdParent;
				while (parent != null) {
					list.add(parent);
					parent = parent.getFdParent();
				}
			} catch (Exception ex) {
			}
		}
		for (int i = 0; i < list.size(); i++) {
			fdParentsName += ",OU="
					+ ((SysOrgElement) list.get(i)).getFdName(lang);
		}
		if (fdParentsName.length() > 0) {
			fdParentsName = fdParentsName.substring(1);
		}
		return fdParentsName;
	}

	private String buildDN(SysOrgElement e, String type) {
		String parentType = null;
		if ("post".equals(type) || "person".equals(type)) {
			parentType = context.getTypeConfig(type,
					"prop.dept.byParentDN");
		} else {
			parentType = context.getTypeConfig(type,
					"prop.parent.byParentDN");
		}
		String DN_parent = context.getTypeConfig(type, "baseDN");
		if ("true".equals(parentType)) {
			String parentDn = getFdParentsName(e,
					SysLangUtil.getOfficialLang());
			if (StringUtil.isNotNull(parentDn)) {
				DN_parent = parentDn + ","
						+ DN_parent;
			}
		}
		String DN = null;
		if ("dept".equals(type)) {
			DN = "OU=" + e.getFdName() + "," + DN_parent;
		} else {
			DN = "CN=" + e.getFdName() + "," + DN_parent;
		}
		return DN;
	}

	private byte[] getValueByte(Object bean, String prop)
			throws UnsupportedEncodingException {
		String valueStr = "";
		if (bean != null) {
			Object value = BeanUtils.getValue(bean, prop);
			if (value != null) {
				valueStr = value.toString();
			}
		}
		return valueStr.getBytes("UTF-8");
	}

	private String getRelationPropName(String prop) {
		if ("number".equals(prop)) {
			return "fdNo";
		}
		if ("keyword".equals(prop)) {
			return "fdKeyword";
		}
		if ("dn".equals(prop)) {
			return "fdLdapDN";
		}
		return null;
	}

	private void addParentAttr(DefaultEntry entry, SysOrgElement e, String type)
			throws UnsupportedEncodingException, LdapException {
		String byParentDN = context.getTypeConfig(type,
				"prop.parent.byParentDN");

		if ("false".equals(byParentDN)) {
			String parent = context.getTypeConfig(type, "prop.parent");
			String objKey = context.getTypeConfig(type, "prop.parent.objKey");
			if (StringUtil.isNotNull(parent) && StringUtil.isNotNull(objKey)) {
				byte[] bytes = getValueByte(e.getFdParent(),
						getRelationPropName(objKey));
				if (bytes.length > 0) {
					entry.add(parent, bytes);
				}
			}
		}
	}

	/**
	 * 设置其他属性
	 * 
	 * @param entry
	 * @param type
	 * @throws UnsupportedEncodingException
	 * @throws LdapException
	 */
	private void addOtherAttr(DefaultEntry entry, String type)
			throws UnsupportedEncodingException, LdapException {
		String otherAll = context.getLdapAttribute(type,
				"other");
		Map<String, List<byte[]>> othersMap = new HashMap<String, List<byte[]>>();
		if (StringUtil.isNotNull(otherAll)) {
			String[] others = otherAll.split(";");
			for (int i = 0; i < others.length; i++) {
				String other = others[i].trim();
				String[] propPair = other.split("=");
				String attr = propPair[0].trim();
				String value = propPair[1].trim();
				List<byte[]> valuesList = null;
				if (othersMap.containsKey(attr)) {
					valuesList = othersMap.get(attr);
				} else {
					valuesList = new ArrayList<byte[]>();
					othersMap.put(attr, valuesList);
				}
				valuesList.add(value.getBytes("UTF-8"));
			}
			for (String attr : othersMap.keySet()) {
				List<byte[]> values = othersMap.get(attr);
				byte[][] valuesArray = new byte[values.size()][];
				for (int i = 0; i < values.size(); i++) {
					valuesArray[i] = values.get(i);
				}
				entry.add(attr, valuesArray);
			}
		}

	}

	/**
	 * 设置上级部门
	 * 
	 * @param req
	 * @param e
	 * @param type
	 * @throws UnsupportedEncodingException
	 */
	private void replaceParentAttr(List<Modification> modList, SysOrgElement e,
			String type) throws UnsupportedEncodingException {
		String byParentDN = context.getTypeConfig(type,
				"prop.parent.byParentDN");
		if ("false".equals(byParentDN)) {
			String parent = context.getTypeConfig(type, "prop.parent");
			String objKey = context.getTypeConfig(type,
					"prop.parent.objKey");
			if (StringUtil.isNotNull(parent) && StringUtil.isNotNull(objKey)) {
				byte[] bytes = getValueByte(e.getFdParent(),
						getRelationPropName(objKey));
				if (bytes.length > 0) {
					modList.add(new DefaultModification(
							ModificationOperation.REPLACE_ATTRIBUTE, parent,
							bytes));
				} else {
					modList.add(new DefaultModification(
							ModificationOperation.REMOVE_ATTRIBUTE, parent));
				}
			}
		}
	}

	/**
	 * 同步部门
	 * 
	 * @param dept
	 * @throws Exception
	 */
	public void syncDept(SysOrgElement dept) throws Exception {
		String ldap_dn = dept.getFdLdapDN();
		if (StringUtil.isNull(ldap_dn)) {
			String DN = addDept(dept);
			if (StringUtil.isNotNull(DN)) {
				dept.setFdLdapDN(DN);
				getSysOrgElementService().update(dept);
			}
		} else {
			updateDept(dept);
		}
	}

	private void addDeptAttrs(SysOrgElement e, DefaultEntry entry)
			throws LdapException, UnsupportedEncodingException {
		for (int i = 0; i < dept_ldap_attr.length; i++) {
			String attr = context.getLdapAttribute("dept", dept_ldap_attr[i]);
			if (StringUtil.isNotNull(attr)) {
				byte[] bytes = getValueByte(e, dept_ekp_attr[i]);
				if (bytes.length > 0) {
					entry.add(attr, bytes);
				}
			}
		}
		addParentAttr(entry, e, "dept");
		addOtherAttr(entry, "dept");
	}

	/**
	 * 新增部门
	 * 
	 * @param dept
	 * @return
	 * @throws Exception
	 */
	public String addDept(SysOrgElement dept) throws Exception {
		String DN = buildDN(dept, "dept");
		logger.error(DN);
		DefaultEntry entry = new DefaultEntry(DN);
		addDeptAttrs(dept, entry);
		try {
			addEntry(entry);
			return DN;
		} catch (Exception e) {
			throw e;
		}
	}

	// private void updateChildrenDN(String ldapDN_old,String ldapDN_new) throws
	// Exception{

	// }

	/**
	 * 更新部门
	 * 
	 * @param dept
	 * @throws Exception
	 */
	public void updateDept(SysOrgElement dept) throws Exception {
		String ldapDN_old = dept.getFdLdapDN();
		String ldapDN_new = buildDN(dept, "dept");
		if (!ldapDN_old.equals(ldapDN_new)) {
			logger.debug("移动部门，fdId:{},fdName:{},DN:{}", dept.getFdId(),
					dept.getFdName(), ldapDN_new);
			move(ldapDN_old, ldapDN_new);
			dept.setFdLdapDN(ldapDN_new);
			getSysOrgElementService().update(dept);
			HQLInfo info = new HQLInfo();
			info.setWhereBlock(
					"(fdOrgType=1 or fdOrgType=2 or fdOrgType=4 or fdOrgType=8) and fdIsAvailable=1 and fdLdapDN like '%"
							+ ldapDN_old + "'");
			List<SysOrgElement> list = getSysOrgElementService().findList(info);
			if (list != null && !list.isEmpty()) {
				for (SysOrgElement e : list) {
					String ldapDN = e.getFdLdapDN();
					ldapDN = ldapDN.replace(ldapDN_old, ldapDN_new);
					e.setFdLdapDN(ldapDN);
					getSysOrgElementService().update(e);
				}
			}

		}

		List<Modification> modList = new ArrayList<Modification>();
		for (int i = 0; i < dept_ldap_attr.length; i++) {
			String attr = context.getLdapAttribute("dept", dept_ldap_attr[i]);
			if (StringUtil.isNotNull(attr) && !"name".equals(attr)) {
				byte[] bytes = getValueByte(dept, dept_ekp_attr[i]);
				if (bytes.length > 0) {
					modList.add(new DefaultModification(
							ModificationOperation.REPLACE_ATTRIBUTE, attr,
							bytes));
				} else {
					modList.add(new DefaultModification(
							ModificationOperation.REPLACE_ATTRIBUTE,
							attr, new String[0]));
				}
			}
		}
		replaceParentAttr(modList, dept, "dept");
		logger.debug("更新部门，fdId:{},fdName:{},DN:{}", dept.getFdId(),
				dept.getFdName(), ldapDN_new);
		if (modList.isEmpty()) {
			return;
		}
		try {
			modify(ldapDN_new, modList);
		} catch (Exception e) {
			if (e instanceof LdapNoSuchAttributeException
					&& e.getMessage().contains("NO_ATTRIBUTE_OR_VAL")) {
				logger.info(e.getMessage(), e);
			} else {
				throw e;
			}
		}
	}

	/**
	 * 更新部门领导及上级领导
	 * 
	 * @param dept
	 * @throws Exception
	 */
	public void updateDeptRelation(SysOrgElement dept) throws Exception {
		String ldapDN_old = dept.getFdLdapDN();
		List<Modification> modList = new ArrayList<Modification>();
		String thisleaderAttr = context.getLdapAttribute("dept", "thisleader");
		String superleaderAttr = context.getLdapAttribute("dept",
				"superleader");
		if (StringUtil.isNotNull(thisleaderAttr)) {
			String thisleaderObjKeyAttr = context.getTypeConfig("dept",
					"prop.thisleader.objKey");

			if (StringUtil.isNotNull(thisleaderObjKeyAttr)) {
				byte[] bytes = getValueByte(dept.getHbmThisLeader(),
						getRelationPropName(thisleaderObjKeyAttr));
				if (bytes.length > 0) {
					modList.add(new DefaultModification(
							ModificationOperation.REPLACE_ATTRIBUTE,
							thisleaderAttr, bytes));
				} else {
					modList.add(new DefaultModification(
							ModificationOperation.REPLACE_ATTRIBUTE,
							thisleaderAttr, new String[0]));
				}
			}
		}
		if (StringUtil.isNotNull(superleaderAttr)) {
			String superleaderObjKeyAttr = context.getTypeConfig("dept",
					"prop.superleader.objKey");
			if (StringUtil.isNotNull(superleaderObjKeyAttr)) {
				byte[] bytes = getValueByte(dept.getHbmSuperLeader(),
						getRelationPropName(superleaderObjKeyAttr));
				if (bytes.length > 0) {
					modList.add(new DefaultModification(
							ModificationOperation.REPLACE_ATTRIBUTE,
							superleaderAttr, bytes));
				} else {
					modList.add(new DefaultModification(
							ModificationOperation.REMOVE_ATTRIBUTE,
							superleaderAttr));
				}
			}
		}
		logger.debug("更新部门领导，fdId:{},fdName:{},DN:{}", dept.getFdId(),
				dept.getFdName(), ldapDN_old);
		modify(ldapDN_old, modList);
	}

	/**
	 * 同步人员
	 * 
	 * @param person
	 * @throws Exception
	 */
	public void syncPerson(SysOrgPerson person) throws Exception {
		String ldap_dn = person.getFdLdapDN();
		if (StringUtil.isNull(ldap_dn)) {
			String DN = addPerson(person);
			if (StringUtil.isNotNull(DN)) {
				person.setFdLdapDN(DN);
				getSysOrgElementService().update(person);
			}
		} else {
			updatePerson(person);
		}
	}

	/**
	 * 获取性别的值
	 * 
	 * @param person
	 * @return
	 */
	private String getSexValue(SysOrgPerson person) {
		String fdSex = person.getFdSex();
		if (StringUtil.isNotNull(fdSex)) {
			String m = context.getConfig("kmss.ldap.config.sex.m");
			String f = context.getConfig("kmss.ldap.config.sex.f");
			if ("M".equals(fdSex)) {
				return m;
			} else if ("F".equals(fdSex)) {
				return f;
			}
		} else {
			return "";
		}
		return "";
	}

	private String getPassword(SysOrgPerson person) {
		String password = null;
		String initPassword = person.getFdInitPassword();
		if (StringUtil.isNotNull(initPassword)) {
			password = PasswordUtil.desDecrypt(initPassword);
		}
		if (StringUtil.isNull(password)) {
			try {
				SysOrgDefaultConfig sysOrgDefaultConfig = new SysOrgDefaultConfig();
				password = sysOrgDefaultConfig.getOrgDefaultPassword();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return password;
	}

	/**
	 * 新增人员
	 * 
	 * @param person
	 * @return
	 * @throws Exception
	 */
	public String addPerson(SysOrgPerson person) throws Exception {
		String DN = buildDN(person, "person");
		DefaultEntry entry = new DefaultEntry(DN);
		for (int i = 0; i < person_ldap_attr.length; i++) {
			String attr = context.getLdapAttribute("person",
					person_ldap_attr[i]);
			if (StringUtil.isNotNull(attr)) {
				if ("sex".equals(person_ldap_attr[i])) {
					entry.add(attr, getSexValue(person).getBytes("UTF-8"));
				} else if ("password".equals(person_ldap_attr[i])) {
					String password = getPassword(person);
					if ("unicodePwd".equals(attr)) {
						byte[] newUnicodePassword = ("\"" + password + "\"")
								.getBytes("UTF-16LE");
						entry.add("unicodePwd", newUnicodePassword);
					} else {
						entry.add(attr, password);
					}
				} else {
					byte[] bytes = getValueByte(person, person_ekp_attr[i]);
					if (bytes.length > 0) {
						entry.add(attr, bytes);
					}
				}
			}
		}
		addParentAttr(entry, person, "person");
		addOtherAttr(entry, "person");
		try {
			logger.debug("新增人员，fdId:{},fdName:{},DN:{}", person.getFdId(),
					person.getFdName(), DN);
			addEntry(entry);
			return DN;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 更新人员信息
	 * 
	 * @param person
	 * @throws Exception
	 */
	public void updatePerson(SysOrgPerson person) throws Exception {
		String ldapDN_old = person.getFdLdapDN();
		String ldapDN_new = buildDN(person, "person");
		if (!ldapDN_old.equals(ldapDN_new)) {
			logger.debug("移动人员，fdId:{},fdName:{},DN:{}", person.getFdId(),
					person.getFdName(), ldapDN_new);
			move(ldapDN_old, ldapDN_new);
			person.setFdLdapDN(ldapDN_new);
			getSysOrgElementService().update(person);
		}
		List<Modification> modList = new ArrayList<Modification>();
		for (int i = 0; i < person_ldap_attr.length; i++) {
			String attr = context.getLdapAttribute("person",
					person_ldap_attr[i]);
			if ("name".equals(attr)) {
				continue;
			}
			if (StringUtil.isNotNull(attr)) {
				byte[] bytes = null;
				if ("sex".equals(person_ldap_attr[i])) {
					bytes = getSexValue(person).getBytes("UTF-8");
				} else if ("password".equals(person_ldap_attr[i])) {
					String password = getPassword(person);
					if ("unicodePwd".equals(attr)) {
						bytes = ("\"" + password + "\"")
								.getBytes("UTF-16LE");
					} else {
						bytes = getValueByte(person, person_ekp_attr[i]);
					}
				} else {
					bytes = getValueByte(person, person_ekp_attr[i]);
				}
				if (bytes.length > 0) {
					modList.add(new DefaultModification(
							ModificationOperation.REPLACE_ATTRIBUTE,
							attr, bytes));
				} else {
					modList.add(new DefaultModification(
								ModificationOperation.REPLACE_ATTRIBUTE,
							attr, new String[0]));
				}
			}
		}
		replaceParentAttr(modList, person, "person");
		logger.debug("更新人员，fdId:{},fdName:{},DN:{}", person.getFdId(),
				person.getFdName(), ldapDN_new);
		if (modList.isEmpty()) {
			return;
		}
		try {
			modify(ldapDN_new, modList);
		} catch (Exception e) {
			if (e instanceof LdapNoSuchAttributeException
					&& e.getMessage().contains("NO_ATTRIBUTE_OR_VAL")) {
				logger.info(e.getMessage(), e);
			} else {
				throw e;
			}
		}
	}

	/**
	 * 获取人员岗位信息
	 * 
	 * @param person
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private byte[][] getPersonPostValue(SysOrgPerson person)
			throws UnsupportedEncodingException {
		String postObjKeyAttr = context.getTypeConfig("person",
				"prop.post.objKey");
		if (StringUtil.isNotNull(postObjKeyAttr)) {
			List<SysOrgElement> eles = person.getHbmPosts();
			List<String> values = new ArrayList<String>();
			String propName = getRelationPropName(postObjKeyAttr);
			for (SysOrgElement e : eles) {
				String value = (String) BeanUtils.getValue(e, propName);
				if (StringUtil.isNotNull(value)) {
					values.add(value);
				}
			}
			byte[][] valuesArray = new byte[values.size()][];
			for (int i = 0; i < values.size(); i++) {
				valuesArray[i] = values.get(i).getBytes("UTF-8");
			}
			return valuesArray;
		}
		return null;
	}

	/**
	 * 更新人员岗位关系
	 * 
	 * @param person
	 * @throws Exception
	 */
	public void updatePersonRelation(SysOrgPerson person) throws Exception {
		String ldapDN_old = person.getFdLdapDN();
		List<Modification> modList = new ArrayList<Modification>();
		String postAttr = context.getLdapAttribute("person", "post");
		if (StringUtil.isNotNull(postAttr)) {
			byte[][] valuesArray = getPersonPostValue(person);
			if (valuesArray != null) {
				modList.add(new DefaultModification(
						ModificationOperation.REPLACE_ATTRIBUTE,
						postAttr, valuesArray));
			} else {
				modList.add(new DefaultModification(
						ModificationOperation.REPLACE_ATTRIBUTE,
						postAttr, new String[0]));
			}
		}
		logger.debug("更新人员岗位，fdId:{},fdName:{},DN:{}", person.getFdId(),
				person.getFdName(), ldapDN_old);
		modify(ldapDN_old, modList);
	}

	/**
	 * 同步岗位
	 * 
	 * @param post
	 * @throws Exception
	 */
	public void syncPost(SysOrgElement post) throws Exception {
		String ldap_dn = post.getFdLdapDN();
		if (StringUtil.isNull(ldap_dn)) {
			String DN = addPost(post);
			if (StringUtil.isNotNull(DN)) {
				post.setFdLdapDN(DN);
				getSysOrgElementService().update(post);
			}
		} else {
			updatePost(post);
		}
	}

	/**
	 * 获取岗位成员信息
	 * 
	 * @param post
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private byte[][] getPostMemberValue(SysOrgElement post)
			throws UnsupportedEncodingException {
		String memberObjKeyAttr = context.getTypeConfig("post",
				"prop.member.objKey");
		if (StringUtil.isNotNull(memberObjKeyAttr)) {
			List<SysOrgElement> eles = post.getHbmPersons();
			List<String> values = new ArrayList<String>();
			String propName = getRelationPropName(memberObjKeyAttr);
			for (SysOrgElement e : eles) {
				String value = (String) BeanUtils.getValue(e, propName);
				if (StringUtil.isNotNull(value)) {
					values.add(value);
				}
			}

			byte[][] valuesArray = new byte[values.size()][];
			for (int i = 0; i < values.size(); i++) {
				valuesArray[i] = values.get(i).getBytes("utf-8");
			}
			return valuesArray;

		}
		return null;
	}


	/**
	 * 新增岗位
	 * 
	 * @param post
	 * @return
	 * @throws Exception
	 */
	public String addPost(SysOrgElement post) throws Exception {
		String DN = buildDN(post, "post");
		DefaultEntry entry = new DefaultEntry(DN);
		for (int i = 0; i < post_ldap_attr.length; i++) {
			String attr = context.getLdapAttribute("post", post_ldap_attr[i]);
			if (StringUtil.isNotNull(attr)) {
				byte[] bytes = getValueByte(post, post_ekp_attr[i]);
				if (bytes.length > 0) {
					entry.add(attr, bytes);
				}
			}
		}
		// 设置岗位成员
		String memberAttr = context.getLdapAttribute("post", "member");
		if (StringUtil.isNotNull(memberAttr)) {
			byte[][] valuesArray = getPostMemberValue(post);
			if (valuesArray != null && valuesArray.length > 0) {
				entry.add(memberAttr, valuesArray);
			}
		}
		// 设置岗位领导
		String thisleaderAttr = context.getLdapAttribute("post", "thisleader");
		if (StringUtil.isNotNull(thisleaderAttr)) {
			String thisleaderObjKeyAttr = context.getTypeConfig("post",
					"prop.thisleader.objKey");
			if (StringUtil.isNotNull(thisleaderObjKeyAttr)) {
				byte[] bytes = getValueByte(post.getHbmThisLeader(),
						getRelationPropName(thisleaderObjKeyAttr));
				if (bytes.length > 0) {
					entry.add(thisleaderAttr, bytes);
				}
			}
		}
		addParentAttr(entry, post, "post");
		addOtherAttr(entry, "post");
		try {
			logger.debug("新增岗位，fdId:{},fdName:{},DN:{}", post.getFdId(),
					post.getFdName(), DN);
			addEntry(entry);
			return DN;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 更新岗位
	 * 
	 * @param post
	 * @throws Exception
	 */
	public void updatePost(SysOrgElement post) throws Exception {
		String ldapDN_old = post.getFdLdapDN();
		String ldapDN_new = buildDN(post, "post");
		if (!ldapDN_old.equals(ldapDN_new)) {
			logger.debug("移动岗位，fdId:{},fdName:{},DN:{}", post.getFdId(),
					post.getFdName(), ldapDN_new);
			move(ldapDN_old, ldapDN_new);
			post.setFdLdapDN(ldapDN_new);
			getSysOrgElementService().update(post);
		}
		// List<Modification> modList = new ArrayList<Modification>();
		List<Modification> modList = new ArrayList<Modification>();
		// req.setName(new Dn(ldapDN_new));
		for (int i = 0; i < post_ldap_attr.length; i++) {
			String attr = context.getLdapAttribute("post", post_ldap_attr[i]);
			if ("name".equals(attr)) {
				continue;
			}
			if (StringUtil.isNotNull(attr)) {
				byte[] bytes = getValueByte(post, post_ekp_attr[i]);
				if (bytes.length > 0) {
					// req.replace(attr, bytes);
					modList.add(new DefaultModification(
							ModificationOperation.REPLACE_ATTRIBUTE, attr,
							bytes));
				} else {
					// req.remove(attr);
					modList.add(new DefaultModification(
							ModificationOperation.REPLACE_ATTRIBUTE,
							attr, new String[0]));
				}
			}
		}
		// 设置岗位成员
		String memberAttr = context.getLdapAttribute("post", "member");
		if (StringUtil.isNotNull(memberAttr)) {
			byte[][] valuesArray = getPostMemberValue(post);
			if (valuesArray != null) {
				if (valuesArray.length == 0) {
					// req.remove(memberAttr);
					modList.add(new DefaultModification(
							ModificationOperation.REMOVE_ATTRIBUTE,
							memberAttr));
				} else {
					// req.replace(memberAttr,
					// valuesArray);
					modList.add(new DefaultModification(
							ModificationOperation.REPLACE_ATTRIBUTE, memberAttr,
							valuesArray));
				}
			}
		}
		// 设置岗位领导
		String thisleaderAttr = context.getLdapAttribute("post", "thisleader");
		if (StringUtil.isNotNull(thisleaderAttr)) {
			String thisleaderObjKeyAttr = context.getTypeConfig("post",
					"prop.thisleader.objKey");
			if (StringUtil.isNotNull(thisleaderObjKeyAttr)) {
				byte[] bytes = getValueByte(post.getHbmThisLeader(),
						getRelationPropName(thisleaderObjKeyAttr));
				if (bytes.length > 0) {
					// req.replace(thisleaderAttr, bytes);
					modList.add(new DefaultModification(
							ModificationOperation.REPLACE_ATTRIBUTE,
							thisleaderAttr,
							bytes));
				} else {
					// req.remove(thisleaderAttr);
					modList.add(new DefaultModification(
							ModificationOperation.REMOVE_ATTRIBUTE,
							thisleaderAttr));
				}
			}
		}
		logger.debug("更新岗位，fdId:{},fdName:{},DN:{}", post.getFdId(),
				post.getFdName(), ldapDN_new);
		// replaceParentAttr(req, post, "post");
		// modify(req);
		if (modList.isEmpty()) {
			return;
		}
		try {
			modify(ldapDN_new, modList);
		} catch (Exception e) {
			if (e instanceof LdapNoSuchAttributeException
					&& e.getMessage().contains("NO_ATTRIBUTE_OR_VAL")) {
				logger.info(e.getMessage(), e);
			} else {
				throw e;
			}
		}
	}

	/**
	 * 同步群组
	 * 
	 * @param group
	 * @throws Exception
	 */
	public void syncGroup(SysOrgElement group) throws Exception {
		String ldap_dn = group.getFdLdapDN();
		if (StringUtil.isNull(ldap_dn)) {
			String DN = addGroup(group);
			if (StringUtil.isNotNull(DN)) {
				if (findGroupByDn(DN) != null) {
					throw new Exception("已存在同名的群组，DN：" + DN);
				} else {
					group.setFdLdapDN(DN);
					getSysOrgElementService().update(group);
				}
			}
		} else {
			updateGroup(group);
		}
	}

	/**
	 * 新增群组
	 * 
	 * @param group
	 * @return
	 * @throws Exception
	 */
	public String addGroup(SysOrgElement group) throws Exception {
		String DN = buildDN(group, "group");
		DefaultEntry entry = new DefaultEntry(DN);
		for (int i = 0; i < group_ldap_attr.length; i++) {
			String attr = context.getLdapAttribute("group", group_ldap_attr[i]);
			if (StringUtil.isNotNull(attr)) {
				byte[] bytes = getValueByte(group, group_ekp_attr[i]);
				if (bytes.length > 0) {
					entry.add(attr, bytes);
				}
			}
		}
		addOtherAttr(entry, "group");
		try {
			logger.debug("新增群组，fdId:{},fdName:{},DN:{}", group.getFdId(),
					group.getFdName(), DN);
			addEntry(entry);
			return DN;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 更新群组
	 * 
	 * @param group
	 * @throws Exception
	 */
	public void updateGroup(SysOrgElement group) throws Exception {
		String ldapDN_old = group.getFdLdapDN();
		String ldapDN_new = buildDN(group, "group");
		if (!ldapDN_old.equals(ldapDN_new)) {
			logger.debug("移动群组，fdId:{},fdName:{},DN:{}", group.getFdId(),
					group.getFdName(), ldapDN_new);
			move(ldapDN_old, ldapDN_new);
			group.setFdLdapDN(ldapDN_new);
			getSysOrgElementService().update(group);
		}
		List<Modification> modList = new ArrayList<Modification>();
		for (int i = 0; i < group_ldap_attr.length; i++) {
			String attr = context.getLdapAttribute("group", group_ldap_attr[i]);
			if ("name".equals(attr)) {
				continue;
			}
			if (StringUtil.isNotNull(attr)) {
				byte[] bytes = getValueByte(group, group_ekp_attr[i]);
				if (bytes.length > 0) {
					modList.add(new DefaultModification(
							ModificationOperation.REPLACE_ATTRIBUTE,
							attr, bytes));
				} else {
					modList.add(new DefaultModification(
							ModificationOperation.REPLACE_ATTRIBUTE,
							attr, new String[0]));
				}
			}
		}
		logger.debug("更新群组，fdId:{},fdName:{},DN:{}", group.getFdId(),
				group.getFdName(), ldapDN_new);
		if (modList.isEmpty()) {
			return;
		}
		try {
			modify(ldapDN_new, modList);
		} catch (Exception e) {
			if (e instanceof LdapNoSuchAttributeException
					&& e.getMessage().contains("NO_ATTRIBUTE_OR_VAL")) {
				logger.info(e.getMessage(), e);
			} else {
				throw e;
			}
		}
	}

	/**
	 * 获取岗位成员信息
	 * 
	 * @param group
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private byte[][] getGroupMemberValue(SysOrgGroup group)
			throws UnsupportedEncodingException {
		String memberObjKeyAttr = context.getTypeConfig("group",
				"prop.member.objKey");
		if (StringUtil.isNotNull(memberObjKeyAttr)) {
			List<SysOrgElement> eles = group.getHbmMembers();
			List<String> values = new ArrayList<String>();
			String propName = getRelationPropName(memberObjKeyAttr);
			for (SysOrgElement e : eles) {
				String value = (String) BeanUtils.getValue(e, propName);
				if (StringUtil.isNotNull(value)) {
					values.add(value);
				}
			}
			byte[][] valuesArray = new byte[values.size()][];
			for (int i = 0; i < values.size(); i++) {
				valuesArray[i] = values.get(i).getBytes("UTF-8");
			}
			return valuesArray;
		}
		return null;
	}

	/**
	 * 更新群组成员
	 * 
	 * @param group
	 * @throws Exception
	 */
	public void updateGroupRelation(SysOrgGroup group) throws Exception {
		String ldapDN_old = group.getFdLdapDN();
		List<Modification> modList = new ArrayList<Modification>();
		String memberAttr = context.getLdapAttribute("group", "member");
		if (StringUtil.isNotNull(memberAttr)) {
			byte[][] valuesArray = getGroupMemberValue(group);
			if (valuesArray != null) {
				logger.debug("更新群组成员，fdId:{},fdName:{},DN:{}", group.getFdId(),
						group.getFdName(), ldapDN_old);
				if (valuesArray.length > 0) {
					modList.add(new DefaultModification(
							ModificationOperation.REPLACE_ATTRIBUTE,
							memberAttr, valuesArray));
				} else {
					modList.add(new DefaultModification(
							ModificationOperation.REPLACE_ATTRIBUTE,
							memberAttr, new String[0]));
				}
				if (modList.isEmpty()) {
					return;
				}
				modify(ldapDN_old, modList);
			}
		}
	}

	private void addEntry(DefaultEntry entry) throws Exception {
		LdapConnection conn = getConnection();
		try {
			conn.add(entry);
		} catch (LdapException e) {
			logger.error(entry.toString());
			logger.error(e.getMessage(), e);
			if (!(e instanceof LdapEntryAlreadyExistsException)) {
				throw e;
			}
		} finally {
			releaseConnection(conn);
		}
	}

	private void modify(ModifyRequest req) throws Exception {
		LdapConnection conn = getConnection();
		try {
			conn.modify(req);
		} catch (LdapException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			releaseConnection(conn);
		}
	}

	private void modify(String dn, List<Modification> modList)
			throws Exception {
		LdapConnection conn = getConnection();
		try {
			conn.modify(dn,
					modList.toArray(new Modification[modList.size()]));
		} catch (LdapException e) {
			// logger.error(e.getMessage(), e);
			throw e;
		} finally {
			releaseConnection(conn);
		}
	}

	public void move(String old_dn, String new_dn)
			throws Exception {
		LdapConnection conn = getConnection();
		try {
			logger.debug("移动记录，从{}  移动到  {}", old_dn, new_dn);
			conn.moveAndRename(old_dn, new_dn, false);
		} catch (LdapException e) {
			logger.error("移动记录，从{}  移动到  {} 失败", old_dn, new_dn);
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			releaseConnection(conn);
		}
	}

	public void delete(SysOrgElement e)
			throws Exception {
		String ldapDN_old = e.getFdLdapDN();
		if (StringUtil.isNull(ldapDN_old)) {
			return;
		}
		LdapConnection conn = getConnection();
		try {
			logger.debug("删除记录，fdId:{}，DN:{}", e.getFdId(), ldapDN_old);
			conn.delete(ldapDN_old);
			e.setFdLdapDN(null);
			getSysOrgElementService().update(e);
		} catch (LdapException e1) {
			logger.error(e1.getMessage(), e1);
			if (e1 instanceof LdapNoSuchObjectException) {
				e.setFdLdapDN(null);
				getSysOrgElementService().update(e);
			} else {
				throw e1;
			}
		} finally {
			releaseConnection(conn);
		}
	}

	private LdapConnection getConnection() throws Exception {
		return ((ApacheLdapContext) context).getConnection();
	}

	private void releaseConnection(LdapConnection conn) throws Exception {
		((ApacheLdapContext) context).closeConnection(conn);
	}

	private ISysOrgElementService sysOrgElementService;

	@Override
    public ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) SpringBeanUtil
					.getBean("sysOrgElementService");
		}
		return sysOrgElementService;
	}

	private String findGroupByDn(String dn) {
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("fdId");
			hqlInfo.setWhereBlock("fdOrgType=16 and fdLdapDN='" + dn + "'");
			return (String) getSysOrgElementService().findFirstOne(hqlInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
}
