package com.landray.kmss.third.ldap.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.third.ldap.LdapMapping;
import com.landray.kmss.third.ldap.oms.in.LdapElementBaseAttribute;
import com.landray.kmss.third.ldap.oms.in.LdapSynchroInProviderImp;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

@SuppressWarnings("unchecked")
public abstract class BaseLdapService {

	protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(BaseLdapService.class);

	protected BaseLdapContext context = null;

	public BaseLdapContext getLdapContext() {
		return context;
	}

	/**
	 * 链接LDAP服务器
	 * 
	 * @throws NamingException
	 */
	public abstract void connect() throws Exception;

	/**
	 * 关闭链接
	 * 
	 * @throws NamingException
	 */
	public abstract void close() throws Exception;

	/**
	 * 判断某种类型的对象是否映射
	 * 
	 * @param type
	 * @return
	 */
	public boolean isTypeMap(String type) {
		return context.isTypeMap(type);
	}
	
	public boolean validateUser(String username, String password)
			throws Exception {
		return context.validateUser(username, password);
	}


	protected void mapping(List<LdapElementBaseAttribute> elems, String orgType,
			StringBuffer buffer) throws Exception {
		String orgTypeName = getOrgTypeName(orgType);
		String ldapAttr = getLdapContext().getTypeConfig("mapping",
				"prop." + orgType + ".ldapAttr");
		String ekpAttr = getLdapContext().getTypeConfig("mapping",
				"prop." + orgType + ".ekpAttr");
		if (StringUtil.isNull(ldapAttr) || StringUtil.isNull(ekpAttr)) {
			buffer.append(orgTypeName + "没配置映射关系，不进行处理<br>");
			logger.warn(orgTypeName + "没配置映射关系，不进行处理");
			return;
		}
		if (elems == null || elems.isEmpty()) {
			buffer.append("从LDAP获取到的" + orgTypeName + "为空，不进行映射。<br>");
			logger.warn("从LDAP获取到的" + orgTypeName + "为空，不进行映射。");
			return;
		}
		Map<String, LdapMapping> orgs = null;
		if ("dept".equals(orgType)) {
			orgs = findDepts();
		} else if ("person".equals(orgType)) {
			orgs = findPersons();
		} else if ("post".equals(orgType)) {
			orgs = findPosts();
		} else if ("group".equals(orgType)) {
			orgs = findGroups();
		}
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		PreparedStatement psupdate = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			psupdate = conn
					.prepareStatement(
							"update sys_org_element set fd_ldap_dn=?,fd_import_info=? where fd_id=?");
			for (LdapElementBaseAttribute attr : elems) {
				String mappingValue = attr.getElementMapping();
				if (StringUtil.isNull(mappingValue)) {
					buffer.append(
							"LDAP映射字段的值为空，不进行映射:" + attr.getElementLdapDN()
									+ "<br>");
					logger.warn(
							"LDAP映射字段的值为空，不进行映射:" + attr.getElementLdapDN());
					continue;
				}
				LdapMapping mapping = orgs.get(mappingValue);
				if (mapping != null) {
					buffer.append("建立映射关系，" + attr.getElementLdapDN()
							+ "=" + mapping.getId() + "---" + mapping.getName()
							+ "<br>");
					logger.warn("建立映射关系，" + attr.getElementLdapDN()
							+ "=" + mapping.getId() + "---"
							+ mapping.getName());
					psupdate
							.setString(1, attr.getElementLdapDN());
					psupdate.setString(2,
							LdapSynchroInProviderImp.class.getName()
									+ attr.getElementUUID());
					psupdate.setString(3, mapping.getId());
					psupdate.addBatch();
				} else {
					buffer.append("找不到映射关系，" + attr.getElementLdapDN() + "，"
							+ mappingValue+"。"
							+ "<br>");
					logger.warn("找不到映射关系，" + attr.getElementLdapDN() + "，"
							+ mappingValue+"。");
				}
			}
			psupdate.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("", e);
		} finally {
			JdbcUtils.closeStatement(psupdate);
			JdbcUtils.closeConnection(conn);
		}


	}

	private Map<String, LdapMapping> findDepts() throws Exception {
		String ekpAttr = getLdapContext().getTypeConfig("mapping",
				"prop.dept.ekpAttr");
		HQLInfo info = new HQLInfo();
		info.setWhereBlock(
				"sysOrgElement.fdOrgType < 4 and sysOrgElement.fdIsAvailable = 1");
		List<SysOrgElement> eles = getSysOrgElementService().findList(info);
		return buildOrgs(eles, ekpAttr);
	}

	private Map<String, LdapMapping> findPosts() throws Exception {
		String ekpAttr = getLdapContext().getTypeConfig("mapping",
				"prop.post.ekpAttr");
		HQLInfo info = new HQLInfo();
		info.setWhereBlock(
				"sysOrgElement.fdOrgType = 4 and sysOrgElement.fdIsAvailable = 1");
		List<SysOrgElement> eles = getSysOrgElementService().findList(info);
		return buildOrgs(eles, ekpAttr);
	}

	private Map<String, LdapMapping> findGroups() throws Exception {
		String ekpAttr = getLdapContext().getTypeConfig("mapping",
				"prop.group.ekpAttr");
		HQLInfo info = new HQLInfo();
		info.setWhereBlock(
				"sysOrgElement.fdOrgType = 16 and sysOrgElement.fdIsAvailable = 1");
		List<SysOrgElement> eles = getSysOrgElementService().findList(info);
		return buildOrgs(eles, ekpAttr);
	}

	private Map<String, LdapMapping> findPersons() throws Exception {
		String ekpAttr = getLdapContext().getTypeConfig("mapping",
				"prop.person.ekpAttr");

		HQLInfo info = new HQLInfo();
		info.setSelectBlock(
				"sysOrgPerson.fdId,sysOrgPerson.fdName,sysOrgPerson.fdLoginName,sysOrgPerson.fdNo,sysOrgPerson.fdKeyword");
		info.setWhereBlock(
				"sysOrgPerson.fdIsAvailable = 1");
		List<Object[]> rtnList = getSysOrgPersonService().findValue(info);
		String key = null;
		Map<String, LdapMapping> orgs = new HashMap<String, LdapMapping>();
		for (Object[] obj : rtnList) {
			LdapMapping user = new LdapMapping();
			user.setId(String.valueOf(obj[0]));
			user.setName(String.valueOf(obj[1]));
			user.setLoginName(String.valueOf(obj[2]));
			user.setNo(String.valueOf(obj[3]));
			user.setKeyword(String.valueOf(obj[4]));
			if ("name".equals(ekpAttr)) {
				key = user.getName();
			} else if ("no".equals(ekpAttr)) {
				key = user.getNo();
			} else if ("keyword".equals(ekpAttr)) {
				key = user.getKeyword();
			} else if ("loginName".equals(ekpAttr)) {
				key = user.getLoginName();
			}
			if (StringUtil.isNull(key)) {
				logger.warn("EKP映射字段的值为空，不进行映射:" + user.getName() + ","
						+ user.getId());
				continue;
			}
			orgs.put(key, user);
		}
		return orgs;
	}

	private Map<String, LdapMapping> buildOrgs(List<SysOrgElement> eles,
			String ekpAttr) {
		Map<String, LdapMapping> orgs = new HashMap<String, LdapMapping>();
		String key = null;
		for (SysOrgElement e : eles) {
			LdapMapping org = new LdapMapping();
			if ("hierName".equals(ekpAttr)) {
				String hName = e.getFdParentsName("#",
						SysLangUtil.getOfficialLang());
				if (StringUtil.isNull(hName)) {
					hName = e.getFdNameOri();
				} else {
					hName = hName + "#" + e.getFdNameOri();
				}
				org.sethName(hName);
				key = hName;
			} else if ("name".equals(ekpAttr)) {
				key = e.getFdNameOri();
			} else if ("no".equals(ekpAttr)) {
				key = e.getFdNo();
			} else if ("keyword".equals(ekpAttr)) {
				key = e.getFdKeyword();
			}
			if (StringUtil.isNull(key)) {
				logger.warn("EKP映射字段的值为空，不进行映射:" + e.getFdNameOri() + ","
						+ e.getFdId());
				continue;
			}
			org.setNo(e.getFdNo());
			org.setKeyword(e.getFdKeyword());
			org.setName(e.getFdNameOri());
			org.setId(e.getFdId());
			orgs.put(key, org);
		}
		return orgs;
	}

	protected String getOrgTypeName(String orgType) {
		if ("dept".equals(orgType)) {
			return "部门";
		}
		if ("person".equals(orgType)) {
			return "人员";
		}
		if ("post".equals(orgType)) {
			return "岗位";
		}
		if ("group".equals(orgType)) {
			return "群组";
		}
		return null;
	}

	public ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) SpringBeanUtil
					.getBean("sysOrgElementService");
		}
		return sysOrgElementService;
	}

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	private ISysOrgElementService sysOrgElementService;

	private ISysOrgPersonService sysOrgPersonService;

	public ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
					.getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}

	public abstract void setConnectTimeout(long timeout) throws Exception;

	public abstract void setReadTimeout(long timeout) throws Exception;

	public abstract String doMapping() throws Exception;
}
