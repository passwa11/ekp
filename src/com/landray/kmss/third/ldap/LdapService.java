package com.landray.kmss.third.ldap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.naming.CommunicationException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.third.ldap.base.BaseLdapService;
import com.landray.kmss.third.ldap.oms.in.LdapElementBaseAttribute;
import com.landray.kmss.third.ldap.oms.in.LdapResultSet;
import com.landray.kmss.util.StringUtil;

/**
 * 访问LDAP服务
 * 
 * @author 叶中奇
 * 
 */
@SuppressWarnings("unchecked")
public class LdapService extends BaseLdapService {

	protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(LdapService.class);

	/**
	 * 构造函数，以ldapconfig.properties文件作为配置文件
	 */
	public LdapService() {
		context = new LdapContext(null);
	}

	public LdapService(boolean authentication) {
		if (authentication) {
			try {
				LdapConfig config = new LdapConfig();
				if ("true".equals(config
						.getValue("kmss.authentication.ldap.enabled"))) {
					context = new LdapContext(null);
				}

			} catch (Exception e) {
				logger.error("", e);
			}
		} else {
			context = new LdapContext(null);
		}

	}

	public LdapService(Map<String, String> config) {
		context = new LdapContext(config);
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
	public LdapEntries getEntries(String type, Date time)
			throws Exception {
		return getEntries(type, time, 0);
	}

	public LdapEntries getEntries(String type, Date time, int count)
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
		if(baseDNs==null || baseDNs.length<1){
			return null;
		}
//		NamingEnumeration enumeration = context.searchByBaseDN(baseDNs[0],type, filter, context
//				.getLdapAttributes(type), count);
		
		LdapEntries entrys = new LdapEntries(type);
		
		for(int i=0;i<baseDNs.length;i++){
			NamingEnumeration enumeration = ((LdapContext) context)
					.searchByBaseDN(baseDNs[i], type, filter,
							((LdapContext) context)
					.getLdapAttributes(type), count);
			entrys.addLdapEntrys(enumeration, baseDNs[i],
					((LdapContext) context), type, time);
		}
		return entrys;
	}

//	public LdapResult getLdapResult(String type, Date time,
//			String[] attributes, Control[] control) throws Exception {
//		if (!context.isTypeMap(type))
//			return null;
//		String filter = null;
//		if (time != null) {
//			filter = "(" + context.getTypeConfig(type, "prop.modifytimestamp")
//					+ ">=" + context.getTimeToString(time) + ")";
//		}
//		if (attributes == null) {
//			attributes = context.getLdapAttributes(type);
//		}
//		return context.search(type, filter, attributes, control);
//	}
	
	public LdapResult getLdapResultByDn(String dn, String type, Date time,
			String[] attributes, Control[] control, boolean getDeleted)
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
			return ((LdapContext) context).searchDeletedByDn(dn, type, filter,
					attributes,
					control);
		} else {
			return ((LdapContext) context).searchByDn(dn, type, filter,
					attributes, control);
		}
	}

	/**
	 * 判断某个类型的Unid是否存在
	 * 
	 * @param type
	 * @param value
	 * @return
	 * @throws NamingException
	 */
//	public boolean isUnidExist(String type, String value)
//			throws NamingException {
//		if (!context.isTypeMap(type))
//			return false;
//		String unidProp = context.getTypeConfig(type, "prop.unid");
//		
//		return context.searchByKey(type, unidProp, new String[] { unidProp },
//				value).hasMore();
//
//	}

	/**
	 * 判断一个DN值是否存在
	 * 
	 * @param dn
	 * @return
	 * @throws NamingException
	 */
//	public boolean isDNExist(String dn) throws NamingException {
//		try {
//			return context.searchByDN(dn, new String[] { "objectclass" })
//					.hasMore();
//		} catch (NameNotFoundException e) {
//			return false;
//		}
//	}

	/**
	 * 判断某种类型的对象是否映射
	 * 
	 * @param type
	 * @return
	 */
	@Override
    public boolean isTypeMap(String type) {
		return context.isTypeMap(type);
	}
	
	@Override
    public boolean validateUser(String username, String password)
			throws Exception {
		String validateByAD = context
				.getTypeConfig("auth", "prop.validateByAD");
		String validateByOpenLdap = context
				.getTypeConfig("auth", "prop.validateByOpenLdap");
		if ("true".equals(validateByAD)) {
			return validateUserAD(username, password);
		} else if ("true".equals(validateByOpenLdap)) {
			return validateUserOpenLdap(username, password);
		} else {
			return validateUserSimple(username, password);
		}
	}


	/**
	 * 验证用户密码
	 * 
	 * @param baseDN
	 * @param password
	 * @return
	 * @throws NamingException
	 */
	public boolean validateUserSimple(String username, String password)
			throws Exception {
		try {
			boolean connected = false;
			if (!((LdapContext) context).isConnected()) {
				context.connect();
				connected = true;
			}
			try {
				return context.validateUser(username, password);
			} catch (CommunicationException ce) {
				if (!connected) {
					context.connect();
					return context.validateUser(username, password);
				}
				throw ce;
			}
		} catch (NamingException e) {
			if (LdapContext.logger.isDebugEnabled()) {
                LdapContext.logger.debug("验证用户名：" + username + "失败！", e);
            }
			throw e;
		}
	}
	
	public boolean validateUserAD(String username, String password)
			throws NamingException {
		try {
			return ((LdapContext) context).validateUserAD(username, password);
		} catch (NamingException e) {
			if (LdapContext.logger.isDebugEnabled()) {
                LdapContext.logger.debug("验证用户名：" + username + "失败！", e);
            }
			throw e;
		}
	}

	public boolean validateUserOpenLdap(String username, String password)
			throws NamingException {
		try {
			return ((LdapContext) context).validateUserOpenLdap(username,
					password);
		} catch (NamingException e) {
			if (LdapContext.logger.isDebugEnabled()) {
                LdapContext.logger.debug("验证用户名：" + username + "失败！", e);
            }
			throw e;
		}
	}

	/**
	 * 取得系统的时间戳格式
	 * 
	 * @return
	 * @throws NamingException
	 */
	public String getTimeStamp() throws NamingException {
		String managerDN = context.getConfig("kmss.ldap.config.managerDN");
		String ts = context
				.getConfig("kmss.ldap.type.common.prop.modifytimestamp");
		String time = "";
		NamingEnumeration enumeration = ((LdapContext) context).searchByDN(
				managerDN,
				new String[] { ts });
		if (enumeration.hasMore()) {
			SearchResult result = (SearchResult) enumeration.next();
			Attribute attribute = result.getAttributes().get(ts);
			time = (String) attribute.get();
		}
		return time;
	}

	public static void main(String[] args) throws NamingException, Exception {
		final LdapService service = new LdapService();
		for (int i = 0; i < 100; i++) {
			if (i % 2 == 0) {
				new Thread() {
					@Override
                    public void run() {
						long t = System.currentTimeMillis();
						try {
							System.out
									.println(Thread.currentThread().getName()
											+ "\t\t"
											+ service.validateUser("test002",
													"1") + "\t\t"
											+ (System.currentTimeMillis() - t));
						} catch (Exception e) {
							// TODO 自动生成 catch 块
							e.printStackTrace();
						}
					}
				}.start();
			} else {
				new Thread() {
					@Override
                    public void run() {
						long t = System.currentTimeMillis();
						try {
							System.out
									.println(Thread.currentThread().getName()
											+ "\t\t"
											+ service.validateUser("test002",
													"2") + "\t\t"
											+ (System.currentTimeMillis() - t));
						} catch (Exception e) {
							// TODO 自动生成 catch 块
							e.printStackTrace();
						}
					}
				}.start();
			}
		}
	}

	@Override
    public String doMapping() throws Exception {
		LdapResultSet resultSet = new LdapResultSet();
		resultSet.setBegin(null);
		((LdapContext) context).getEnv().put(
				"com.sun.jndi.ldap.connect.timeout", "10000");
		((LdapContext) context).getEnv().put("com.sun.jndi.ldap.read.timeout",
				"120000");
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

}
