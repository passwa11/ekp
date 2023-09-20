package com.landray.kmss.third.ldap;

import com.landray.kmss.third.ldap.base.BaseLdapContext;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;
import org.springframework.ldap.filter.EqualsFilter;

import javax.naming.*;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.*;
import java.io.IOException;
import java.util.*;

/**
 * Ldap服务代码的上下文对象，不提供对外接口
 * 
 * @author 叶中奇
 * 
 */
@SuppressWarnings("unchecked")
public class LdapContext extends BaseLdapContext {

	protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(LdapContext.class);


	private InitialLdapContext dirContext = null;

	public InitialLdapContext getDirContext() {
		return dirContext;
	}

	private Properties env = new Properties();

	public Properties getEnv() {
		return env;
	}


	private int fetchSize = 0;

	/**
	 * 构造函数
	 * 
	 * @param dataMap
	 */
	public LdapContext(Map<String, String> dataMap) {
		super(dataMap);
		try {
			env.put(Context.INITIAL_CONTEXT_FACTORY,
					"com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.SECURITY_PRINCIPAL,
					getConfig("kmss.ldap.config.managerDN"));
			env.put(Context.SECURITY_CREDENTIALS,
					getConfig("kmss.ldap.config.password"));
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put("java.naming.referral", "follow");
			env.put("com.sun.jndi.ldap.connect.timeout", "10000");
			env.put("com.sun.jndi.ldap.read.timeout", "120000");
			String fetchSizeString = getConfig("kmss.ldap.config.fetchsize");
			if (fetchSizeString != null) {
				fetchSize = Integer.parseInt(fetchSizeString);
			}
			String ldaptype = getConfig("kmss.ldap.config.ldap.type");
			if ("Active Directory".equals(ldaptype)) {
				env.put("java.naming.ldap.attributes.binary", "objectGUID");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("加载配置文件出错：", ex);
		}
		try {
			initAttributeMap();
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
			logger.error("初始化配置信息出错：" + LdapConstant.ROOT_PATH
					+ LdapConstant.FILE_NAME, e);
		}

	}

	/**
	 * 关闭LDAP连接
	 * 
	 * @throws NamingException
	 */
	@Override
    public synchronized void close() throws NamingException {
		if (dirContext != null) {
			try {
				logger.debug("关闭LDAP连接");
				dirContext.close();
			} finally {
				dirContext = null;
			}
		}
	}

	public InitialDirContext getSslContext() {
		String trustStore = getConfig("kmss.ldap.type.auth.trustStore");
		if (StringUtil.isNull(trustStore)) {
			return null;
		}
		try {
			Hashtable env = new Hashtable();
			env.put(Context.INITIAL_CONTEXT_FACTORY,
					"com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, allURLs[0].replace("389", "636"));
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_PRINCIPAL,
					getConfig("kmss.ldap.config.managerDN"));
			env.put(Context.SECURITY_CREDENTIALS,
					getConfig("kmss.ldap.config.password"));
			env.put(Context.SECURITY_PROTOCOL, "ssl");
			env.put("java.naming.ldap.factory.socket",
					"com.landray.kmss.third.ldap.AdSSLSocketFactory");
			env.put("com.sun.jndi.ldap.connect.timeout", "10000");
			env.put("com.sun.jndi.ldap.read.timeout", "120000");

			// 通过参数连接LDAP/AD
			InitialDirContext ctx = new InitialDirContext(env);

			return ctx;
		} catch (NamingException ex) {
			throw new RuntimeException(ex);
		}
	}

	public InitialDirContext getDummySslContext() {
		try {

			Hashtable env = new Hashtable();
			env.put(Context.INITIAL_CONTEXT_FACTORY,
					"com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, allURLs[0].replace("389", "636"));
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_PRINCIPAL,
					getConfig("kmss.ldap.config.managerDN"));
			env.put(Context.SECURITY_CREDENTIALS,
					getConfig("kmss.ldap.config.password"));
			env.put(Context.SECURITY_PROTOCOL, "ssl");
			env.put(Context.REFERRAL, "ignore");
			env.put("java.naming.ldap.factory.socket",
					"com.landray.kmss.third.ldap.ssl.DummySSLSocketFactory");
			env.put("com.sun.jndi.ldap.connect.timeout", "10000");
			env.put("com.sun.jndi.ldap.read.timeout", "120000");
			InitialDirContext ctx = new InitialDirContext(env);

			return ctx;
		} catch (NamingException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 打开LDAP连接
	 * 
	 * @throws NamingException
	 */
	@Override
    public synchronized void connect() throws NamingException {
		close();
		dirContext = buildDirContext(env);
	}

	/**
	 * 判断是否已经连接
	 * 
	 * @return
	 */
	protected synchronized boolean isConnected() {
		return dirContext != null;
	}

	private InitialLdapContext buildDirContext(Properties env)
			throws NamingException {
		NamingException ex = null;
		for (String url : allURLs) {
			env.put(Context.PROVIDER_URL, url);
			try {
				logger.debug(url);
				InitialLdapContext context = new InitialLdapContext(env, null);
				logger.debug("成功连接到LDAP服务器：" + url);
				return context;
			} catch (AuthenticationException e) {
				logger.debug("连接LDAP服务器时验证失败：" + url);
				throw e;
			} catch (CommunicationException e) {
				ex = e;
				logger.debug("连接LDAP服务器时链接失败：" + url + "，正在尝试其它链接");
			}
		}
		if (ex != null) {
            throw ex;
        }
		return null;
	}


	// protected NamingEnumeration searchByKey(String type, String ldapKey,
	// String[] attributes, String obj) throws NamingException {
	// if (!ldapKey.equalsIgnoreCase("objectGUID")) {
	// NamingEnumeration enumeration = search(type, "(" + ldapKey + "="
	// + obj + ")", getLdapAttributes(type), 1);
	// return enumeration;
	// } else {
	//
	// SearchControls s_controls = new SearchControls();
	// s_controls.setReturningAttributes(attributes);
	// s_controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	// String baseDN = getTypeConfig(type, "baseDN");
	// NamingEnumeration names = dirContext.search(baseDN, "(" + ldapKey
	// + "={0})", new Object[] { StringUtil.toBytes(obj) },
	// s_controls);
	// return names;
	// }
	// }

	/**
	 * 搜索Ldap
	 * 
	 * @param type
	 * @param searchFilter
	 * @param attributes
	 * @param count
	 * @return
	 * @throws NamingException
	 * @throws IOException
	 */

	protected NamingEnumeration search_old(String type, String searchFilter,
			String[] attributes, int count) throws NamingException {
		String baseDN = getTypeConfig(type, "baseDN");
		String filter = getTypeConfig(type, "filter");
		if (filter == null) {
			if (searchFilter == null) {
				filter = "(objectclass=*)";
			} else {
				filter = searchFilter;
			}
		} else {
			if (searchFilter != null) {
				filter = "(&" + filter + searchFilter + ")";
			}
		}
		if (fetchSize > 0) {
			List rtn = new ArrayList();
			int pageSize = fetchSize; // 800 entries per page
			byte[] cookie = null;
			try {
				dirContext
						.setRequestControls(new Control[] { new PagedResultsControl(
								pageSize, Control.NONCRITICAL) });

				do {
					SearchControls s_controls = new SearchControls();
					s_controls.setReturningAttributes(attributes);
					s_controls.setSearchScope(SearchControls.SUBTREE_SCOPE);

					NamingEnumeration results = dirContext.search(baseDN,
							filter, s_controls);

					while (results != null && results.hasMore()) {

						SearchResult entry = (SearchResult) results.next();

						rtn.add(entry);

						if (entry instanceof HasControls) {
							// ((HasControls)entry).getControls();
						}
					}
					Control[] controls = dirContext.getResponseControls();
					if (controls != null) {
						for (int i = 0; i < controls.length; i++) {
							if (controls[i] instanceof PagedResultsResponseControl) {
								PagedResultsResponseControl prrc = (PagedResultsResponseControl) controls[i];
								// total = prrc.getResultSize();
								cookie = prrc.getCookie();
							}
						}
					}

					PagedResultsControl ctl = new PagedResultsControl(pageSize,
							cookie, Control.CRITICAL);

					dirContext.setRequestControls(new Control[] { ctl });
				} while (cookie != null);
			} catch (IOException e) {

				e.printStackTrace();
				throw new NamingException(e.getMessage());
			}

			return new ValuesEnumImpl(Collections.enumeration(rtn));
		} else {
			SearchControls controls = new SearchControls();
			// controls.setTimeLimit(10000);
			controls.setReturningAttributes(attributes);
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			if (count > 0) {
                controls.setCountLimit(count);
            }
			if (logger.isDebugEnabled()) {
                logger.debug("搜索Ldap，条件为：" + filter);
            }
			return dirContext.search(baseDN, filter, controls);
		}
	}

	protected NamingEnumeration searchByBaseDN(String baseDN, String type,
			String searchFilter, String[] attributes, int count)
			throws NamingException {
		// String baseDN = getTypeConfig(type, "baseDN");
		String filter = getTypeConfig(type, "filter");
		if (filter == null) {
			if (searchFilter == null) {
				filter = "(objectclass=*)";
			} else {
				filter = searchFilter;
			}
		} else {
			if (searchFilter != null) {
				filter = "(&" + filter + searchFilter + ")";
			}
		}
		if (fetchSize > 0) {
			// 使用翻页 sunzhen
			List rtn = new ArrayList();
			int pageSize = fetchSize; // 800 entries per page
			byte[] cookie = null;
			try {
				dirContext
						.setRequestControls(new Control[] { new PagedResultsControl(
								pageSize, Control.NONCRITICAL) });

				do {
					// Perform the search
					SearchControls s_controls = new SearchControls();
					s_controls.setReturningAttributes(attributes);
					s_controls.setSearchScope(SearchControls.SUBTREE_SCOPE);

					NamingEnumeration results = dirContext.search(baseDN,
							filter, s_controls);

					while (results != null && results.hasMore()) {

						SearchResult entry = (SearchResult) results.next();

						rtn.add(entry);

						if (entry instanceof HasControls) {
							// ((HasControls)entry).getControls();
						}
					}
					// Examine the paged results control response
					Control[] controls = dirContext.getResponseControls();
					if (controls != null) {
						for (int i = 0; i < controls.length; i++) {
							if (controls[i] instanceof PagedResultsResponseControl) {
								PagedResultsResponseControl prrc = (PagedResultsResponseControl) controls[i];
								// total = prrc.getResultSize();
								cookie = prrc.getCookie();
							}
						}
					}
					// Re-activate paged results
					PagedResultsControl ctl = new PagedResultsControl(pageSize,
							cookie, Control.CRITICAL);

					dirContext.setRequestControls(new Control[] { ctl });
				} while (cookie != null);
			} catch (IOException e) {

				e.printStackTrace();
				throw new NamingException(e.getMessage());
			}

			return new ValuesEnumImpl(Collections.enumeration(rtn));
		} else {
			SearchControls controls = new SearchControls();
			// controls.setTimeLimit(10000);
			controls.setReturningAttributes(attributes);
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			if (count > 0) {
                controls.setCountLimit(count);
            }
			if (logger.isDebugEnabled()) {
                logger.debug("搜索Ldap，条件为：" + filter);
            }
			return dirContext.search(baseDN, filter, controls);
		}
	}

	// protected LdapResult search(String type, String searchFilter,
	// String[] attributes, Control[] control) throws Exception {
	// String baseDN = getTypeConfig(type, "baseDN");
	// String filter = getTypeConfig(type, "filter");
	// if (filter == null) {
	// if (searchFilter == null) {
	// filter = "(objectclass=*)";
	// } else {
	// filter = searchFilter;
	// }
	// } else {
	// if (searchFilter != null) {
	// filter = "(&" + filter + searchFilter + ")";
	// }
	// }
	// int pageSize = fetchSize;
	// try {
	// if (control == null) {
	// dirContext
	// .setRequestControls(new Control[] { new PagedResultsControl(
	// pageSize, Control.NONCRITICAL) });
	// } else {
	// dirContext.setRequestControls(control);
	// }
	//
	// SearchControls s_controls = new SearchControls();
	// s_controls.setReturningAttributes(attributes);
	// s_controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	// logger.info("组织架构类型：" + type + "----baseDn:" + baseDN
	// + "----filter:" + filter);
	// NamingEnumeration results = dirContext.search(baseDN, filter,
	// s_controls);
	//
	// return new LdapResult(pageSize, dirContext, results);
	// } catch (IOException e) {
	// e.printStackTrace();
	// throw new NamingException(e.getMessage());
	// }
	// }

	protected LdapResult searchByDn(String baseDN, String type,
			String searchFilter, String[] attributes, Control[] control)
			throws Exception {
		// String baseDN = getTypeConfig(type, "baseDN");
		String filter = getTypeConfig(type, "filter");
		if (filter == null) {
			if (searchFilter == null) {
				filter = "(objectclass=*)";
			} else {
				filter = searchFilter;
			}
		} else {
			if (searchFilter != null) {
				filter = "(&" + filter + searchFilter + ")";
			}
		}
		int pageSize = fetchSize; // 800 entries per page
		try {
			if (control == null) {
				dirContext
						.setRequestControls(new Control[] { new PagedResultsControl(
								pageSize, Control.NONCRITICAL) });
			} else {
				dirContext.setRequestControls(control);
			}

			// Perform the search
			SearchControls s_controls = new SearchControls();
			s_controls.setReturningAttributes(attributes);
			s_controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			logger.info("组织架构类型：" + type + "----baseDn:" + baseDN
					+ "----filter:" + filter);
			NamingEnumeration results = dirContext.search(baseDN, filter,
					s_controls);

			return new LdapResult(pageSize, dirContext, results);
		} catch (IOException e) {
			e.printStackTrace();
			throw new NamingException(e.getMessage());
		}
	}

	protected LdapResult searchDeletedByDn(String baseDN, String type,
			String searchFilter, String[] attributes, Control[] control)
			throws Exception {
		// String baseDN = getTypeConfig(type, "baseDN");
		String filter = getTypeConfig(type, "filterDel");
		if (filter == null) {
			return null;
		} else {
			if (searchFilter != null) {
				filter = "(&" + filter + searchFilter + ")";
			}
		}
		int pageSize = fetchSize; // 800 entries per page
		try {
			if (control == null) {
				dirContext
						.setRequestControls(
								new Control[] { new PagedResultsControl(
										pageSize, Control.NONCRITICAL) });
			} else {
				dirContext.setRequestControls(control);
			}

			// Perform the search
			SearchControls s_controls = new SearchControls();
			s_controls.setReturningAttributes(attributes);
			s_controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			logger.info("组织架构类型：" + type + "----baseDn:" + baseDN
					+ "----filter:" + filter);
			NamingEnumeration results = dirContext.search(baseDN, filter,
					s_controls);

			return new LdapResult(pageSize, dirContext, results);
		} catch (IOException e) {
			e.printStackTrace();
			throw new NamingException(e.getMessage());
		}
	}


	/**
	 * 根据DN搜索Ldap
	 * 
	 * @param dn
	 * @param attributes
	 * @return
	 * @throws NamingException
	 */
	protected NamingEnumeration searchByDN(String dn, String[] attributes)
			throws NamingException {
		SearchControls controls = new SearchControls();
		controls.setReturningAttributes(attributes);
		controls.setSearchScope(SearchControls.OBJECT_SCOPE);
		controls.setCountLimit(1);

		javax.naming.Name name = new LdapName(dn);
		return dirContext.search(name, "(objectclass=*)", controls);
	}

	/**
	 * 验证用户密码
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws NamingException
	 */
	@Override
    public boolean validateUser(String username, String password)
			throws Exception {
		String loginNameProp = getTypeConfig("auth", "prop.login");
		// NamingEnumeration enumeration = search("auth", "(" + loginNameProp
		// + "=" + username + ")", getLdapAttributes("auth"), 1);
		// LdapEntries entries = new LdapEntries(enumeration, this, "auth",
		// null);

		// LdapEntries entries = null;

		String baseDN_str = getTypeConfig("auth", "baseDN");
		String[] baseDNs = baseDN_str.split(";");
		LdapEntries entries = new LdapEntries("auth");

		for (int i = 0; i < baseDNs.length; i++) {
			EqualsFilter loginnameFilter = new EqualsFilter(loginNameProp,
					username);
			NamingEnumeration enumeration = searchByBaseDN(baseDNs[i], "auth",
					loginnameFilter.toString(),
					getLdapAttributes("auth"), 1);
			entries.addLdapEntrys(enumeration, baseDNs[i], this, "auth", null);

		}

		if (entries == null || entries.size() == 0) {
			if (logger.isDebugEnabled()) {
                logger.debug("找不到对应的用户名：" + username + "，验证失败");
            }
			return false;
		}
		String userDN = entries.get(0).getStringProperty("dn");
		if (logger.isDebugEnabled()) {
            logger.debug("用户名：" + username + "对应的DN=" + userDN);
        }
		Properties ep = new Properties();
		ep.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		ep.put(Context.SECURITY_PRINCIPAL, userDN);
		ep.put(Context.SECURITY_CREDENTIALS, password);
		ep.put(Context.SECURITY_AUTHENTICATION, "simple");
		ep.put("java.naming.referral", "follow");
		ep.put("com.sun.jndi.ldap.connect.timeout", "10000");
		ep.put("com.sun.jndi.ldap.read.timeout", "10000");
		try {
			InitialDirContext context = buildDirContext(ep);
			if (context != null) {
				context.close();
				return true;
			}
		} catch (AuthenticationException e) {
			throw e;
		}
		return false;
	}
	
	protected boolean validateUserAD(String username, String password)
			throws NamingException {

		if (StringUtil.isNull(username)) {
			return false;
		}
		String loginName = username;
		String mailDomain = getTypeConfig("auth", "loginSuffix");
		if (StringUtil.isNotNull(mailDomain)) {
			loginName = loginName + mailDomain;
		}
		if(logger.isDebugEnabled()) {
			logger.debug(loginName + "---" + LdapUtil
					.desEncrypt(password) + "---");
		}
		Properties ep = new Properties();
		ep.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		ep.put(Context.SECURITY_PRINCIPAL, loginName);
		ep.put(Context.SECURITY_CREDENTIALS, password);
		ep.put(Context.SECURITY_AUTHENTICATION, "simple");
		ep.put("java.naming.referral", "follow");
		ep.put("com.sun.jndi.ldap.connect.timeout", "3000");
		ep.put("com.sun.jndi.ldap.read.timeout", "3000");
		try {
			InitialDirContext context = buildDirContext(ep);
			// context.addToEnvironment(Context.SECURITY_PRINCIPAL, propVal)
			if (context != null) {
				context.close();
				return true;
			}
		} catch (AuthenticationException e) {
			throw e;
		}
		return false;
	}

	protected boolean validateUserOpenLdap(String username, String password)
			throws NamingException {
		if (StringUtil.isNull(username)) {
			return false;
		}
		String baseDN_str = getTypeConfig("auth", "baseDN");
		String[] baseDNs = baseDN_str.split(";");
		String loginProp = getTypeConfig("auth", "login");
		for (String baseDN : baseDNs) {
			String loginName = loginProp+ "=" + username.trim() + "," + baseDN.trim();
			if(logger.isDebugEnabled()) {
				logger.debug(loginName + "---" + LdapUtil
						.desEncrypt(password) + "---");
			}
			Properties ep = new Properties();
			ep.put("java.naming.factory.initial",
					"com.sun.jndi.ldap.LdapCtxFactory");
			// ep.put("java.naming.security.principal", loginName);
			// ep.put("java.naming.security.credentials", password);
			ep.put(Context.SECURITY_PRINCIPAL, loginName);
			ep.put(Context.SECURITY_CREDENTIALS, password);
			ep.put("java.naming.security.authentication", "simple");
			ep.put("java.naming.referral", "follow");
			ep.put("com.sun.jndi.ldap.connect.timeout", "3000");
			ep.put("com.sun.jndi.ldap.read.timeout", "3000");
			try {
				InitialDirContext context = buildDirContext(ep);
				if (context != null) {
					context.close();
					return true;
				}
			} catch (AuthenticationException e) {
				logger.error("",e);
			}
		}
		return false;
	}

	class ValuesEnumImpl implements NamingEnumeration<Object> {
		Enumeration list;

		ValuesEnumImpl(Enumeration list) {
			this.list = list;
		}

		@Override
        public boolean hasMoreElements() {
			return list.hasMoreElements();
		}

		@Override
        public Object nextElement() {
			return (list.nextElement());
		}

		@Override
        public Object next() throws NamingException {
			return list.nextElement();
		}

		@Override
        public boolean hasMore() throws NamingException {
			return list.hasMoreElements();
		}

		@Override
        public void close() throws NamingException {
			list = null;
		}
	}

	public String findDNByLoginName(String loginName) throws Exception {
		LdapContext context = new LdapContext(null);
		String loginNameProp = context.getTypeConfig("auth", "prop.login");

		String baseDN_str = context.getTypeConfig("auth", "baseDN");
		String[] baseDNs = baseDN_str.split(";");
		LdapEntries entries = new LdapEntries("auth");

		try {

			for (int i = 0; i < baseDNs.length; i++) {
				EqualsFilter loginnameFilter = new EqualsFilter(loginNameProp,
						loginName);
				NamingEnumeration enumeration = searchByBaseDN(baseDNs[i],
						"auth", loginnameFilter.toString(),
						getLdapAttributes("auth"), 1);
				entries.addLdapEntrys(enumeration, baseDNs[i], this, "auth",
						null);

			}

			if (entries == null || entries.size() == 0) {
				if (logger.isDebugEnabled()) {
                    logger.debug("找不到对应的用户名：" + loginName + "，验证失败");
                }
				return null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (context != null) {
                try {
                    context.close();
                } catch (NamingException e) {
                    // TODO 自动生成 catch 块
                    e.printStackTrace();
                }
            }
		}
		return entries.get(0).getStringProperty("dn");
	}

	@Override
	public void setConnectTimeout(long timeout) throws Exception {
		getEnv().put(
				"com.sun.jndi.ldap.connect.timeout", timeout + "");
	}

	@Override
	public void setReadTimeout(long timeout) throws Exception {
		getEnv().put("com.sun.jndi.ldap.read.timeout",
				timeout + "");
	}

}
