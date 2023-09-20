package com.landray.kmss.third.ldap.apache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.directory.api.ldap.codec.controls.search.pagedSearch.PagedResultsDecorator;
import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.cursor.SearchCursor;
import org.apache.directory.api.ldap.model.entry.DefaultEntry;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.message.Response;
import org.apache.directory.api.ldap.model.message.ResultCodeEnum;
import org.apache.directory.api.ldap.model.message.SearchRequest;
import org.apache.directory.api.ldap.model.message.SearchRequestImpl;
import org.apache.directory.api.ldap.model.message.SearchResultDone;
import org.apache.directory.api.ldap.model.message.SearchResultEntry;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.api.ldap.model.message.controls.PagedResults;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.api.util.Strings;
import org.apache.directory.ldap.client.api.DefaultLdapConnectionFactory;
import org.apache.directory.ldap.client.api.DefaultPoolableLdapConnectionFactory;
import org.apache.directory.ldap.client.api.EntryCursorImpl;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapConnectionConfig;
import org.apache.directory.ldap.client.api.LdapConnectionPool;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.apache.directory.ldap.client.api.NoVerificationTrustManager;
import org.slf4j.Logger;

import com.landray.kmss.third.ldap.LdapConstant;
import com.landray.kmss.third.ldap.base.BaseLdapContext;

/**
 * Ldap服务代码的上下文对象，不提供对外接口
 * 
 * @author 叶中奇
 * 
 */
@SuppressWarnings("unchecked")
public class ApacheLdapContext extends BaseLdapContext {

	protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(ApacheLdapContext.class);

	private LdapConnectionPool pool = null;

	private int fetchSize = 0;

	private Long connectTimeout = 2000L;

	private Long readTimeout = 2000L;

	/**
	 * 构造函数
	 * 
	 * @param configFile
	 */
	public ApacheLdapContext(Map<String, String> dataMap) {
		super(dataMap);

		try {

			String url = getConfig("kmss.ldap.config.url")
					.split(";")[0];
			String host = null;
			int port = 636;
			if (url.startsWith("ldap://")) {
				host = url.substring(7);
			} else if (url.startsWith("ldaps://")) {
				host = url.substring(8);
			}
			if (host.contains(":")) {
				port = Integer.parseInt(host.substring(host.indexOf(":") + 1));
				host = host.substring(0, host.indexOf(":"));
			}

			LdapConnectionConfig config = new LdapConnectionConfig();
			config.setLdapHost(host);
			config.setLdapPort(port);
			config.setName(getConfig("kmss.ldap.config.managerDN"));
			config.setCredentials(getConfig("kmss.ldap.config.password"));
			if (url.startsWith("ldaps://")) {
				config.setTrustManagers(new NoVerificationTrustManager());
				config.setUseSsl(true);
			}

			DefaultLdapConnectionFactory factory = new DefaultLdapConnectionFactory(
					config);
			factory.setTimeOut(3000);

			// optional, values below are defaults
			GenericObjectPool.Config poolConfig = new GenericObjectPool.Config();
			// poolConfig.lifo = true;
			poolConfig.maxActive = 500;
			poolConfig.maxIdle = 200;
			poolConfig.maxWait = 2000L;
			poolConfig.minEvictableIdleTimeMillis = 1000L * 60L * 30L;
			poolConfig.minIdle = 20;
			poolConfig.numTestsPerEvictionRun = 3;
			// poolConfig.softMinEvictableIdleTimeMillis = -1L;
			poolConfig.testOnBorrow = false;
			poolConfig.testOnReturn = false;
			poolConfig.testWhileIdle = false;
			poolConfig.timeBetweenEvictionRunsMillis = -1L;
			poolConfig.whenExhaustedAction = GenericObjectPool.WHEN_EXHAUSTED_BLOCK;

			pool = new LdapConnectionPool(
					new DefaultPoolableLdapConnectionFactory(factory),
					poolConfig);

			String fetchSizeString = getConfig("kmss.ldap.config.fetchsize");
			if (fetchSizeString != null) {
				fetchSize = Integer.parseInt(fetchSizeString);
			}
			String ldaptype = getConfig("kmss.ldap.config.ldap.type");
			if ("Active Directory".equals(ldaptype)) {
				// env.put("java.naming.ldap.attributes.binary", "objectGUID");
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

	public LdapConnection getConnection() throws Exception {
		if (pool == null) {
			throw new Exception("pool is null");
		}
		logger.debug("ldap连接池信息, getNumActive:" + pool.getNumActive()
				+ ",getNumIdle:" + pool.getNumIdle());
		LdapConnection connection = pool.getConnection();
		if (readTimeout != null) {
			connection.setTimeOut(readTimeout);
		}
		return connection;
	}

	public void closeConnection(LdapConnection conn) {
		if (conn == null) {
			return;
		}
		// try {
		// conn.close();
		// } catch (IOException e) {
		// logger.error(e.getMessage(), e);
		// }
		if (pool != null) {
			try {
				pool.releaseConnection(conn);
			} catch (LdapException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 关闭LDAP连接
	 * 
	 * @throws NamingException
	 */
	@Override
    public synchronized void close() throws Exception {
		if (pool != null) {
			pool.clear();
		}
	}

	/**
	 * 打开LDAP连接
	 * 
	 * @throws NamingException
	 */
	@Override
    public synchronized void connect() throws Exception {

	}

	/**
	 * 判断是否已经连接
	 * 
	 * @return
	 */
	protected synchronized boolean isConnected() {
		return pool != null;
	}

	private List<Entry> searchByPage(String baseDN,
			String filter, String[] attributes, int count) throws Exception {
		LdapConnection connection = null;
		try {
			connection = getConnection();
			PagedResults pagedSearchControl = new PagedResultsDecorator(
					connection.getCodecService());
			pagedSearchControl.setSize(fetchSize);
			List<Entry> rtn = new ArrayList<Entry>();
			int index = 0;
			while (true) {
				EntryCursor cursor = null;
				try {
					SearchRequest searchRequest = new SearchRequestImpl();
					searchRequest.setScope(SearchScope.SUBTREE);
					searchRequest.addAttributes(attributes);
					searchRequest.setTimeLimit(0);
					searchRequest.setBase(new Dn(baseDN));
					searchRequest.setFilter(filter);
					searchRequest.setSizeLimit(fetchSize);
					searchRequest.addControl(pagedSearchControl);
					cursor = new EntryCursorImpl(connection.search(
							searchRequest));
					while (cursor.next()) {
						// Entry result = cursor.get();
						Entry response = cursor.get();
						// process the SearchResultEntry
						if (response instanceof DefaultEntry) {
							// Entry resultEntry = ((SearchResultEntry)
							// response)
							// .getEntry();
							rtn.add(response);
							index++;
							if (count > 0 && index >= count) {
								break;
							}
						}
					}
					if (count > 0 && index >= count) {
						break;
					}
					SearchResultDone result = cursor.getSearchResultDone();
					pagedSearchControl = (PagedResults) result.getControl(
							PagedResults.OID);
					if (result.getLdapResult()
							.getResultCode() == ResultCodeEnum.UNWILLING_TO_PERFORM
							|| result.getLdapResult()
									.getResultCode() == ResultCodeEnum.SIZE_LIMIT_EXCEEDED) {
						break;
					}
				} finally {
					if (cursor != null) {
						cursor.close();
					}
				}
				// check if this is over
				byte[] cookie = pagedSearchControl.getCookie();
				if (Strings.isEmpty(cookie)) {
					break;
				}
				// Prepare the next iteration, sending a bad cookie
				pagedSearchControl.setCookie(cookie);
				pagedSearchControl.setSize(fetchSize);
			}

			return rtn;
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection(connection);
		}
	}

	protected List<Entry> searchByBaseDN(String baseDN, String type,
			String searchFilter, String[] attributes, int count)
			throws Exception {
		if (fetchSize <= 0) {
			fetchSize = 900;
		}
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
		return searchByPage(baseDN, filter, attributes, count);
	}

	protected List<Entry> searchByDn(String baseDN, String type,
			String searchFilter, String[] attributes)
			throws Exception {
		return searchByBaseDN(baseDN, type, searchFilter, attributes, 0);
	}

	protected List<Entry> searchDeletedByDn(String baseDN, String type,
			String searchFilter, String[] attributes)
			throws Exception {
		String filter = getTypeConfig(type, "filterDel");
		if (filter == null) {
			return null;
		} else {
			if (searchFilter != null) {
				filter = "(&" + filter + searchFilter + ")";
			}
		}
		return searchByPage(baseDN, filter, attributes, 0);

	}

	/**
	 * 根据DN搜索Ldap
	 * 
	 * @param dn
	 * @param attributes
	 * @return
	 * @throws Exception
	 * @throws LdapException
	 * @throws NamingException
	 */
	protected Entry searchByDN(String dn, String[] attributes)
			throws LdapException, Exception {
		LdapConnection connection = null;
		try {
			SearchRequest req = new SearchRequestImpl();
			req.setScope(SearchScope.OBJECT);
			req.addAttributes(attributes);
			req.setTimeLimit(0);
			req.setBase(new Dn(dn));
			connection = getConnection();
			SearchCursor searchCursor = connection.search(req);

			List rtn = new ArrayList();
			while (searchCursor.next()) {
				Response response = searchCursor.get();
				// process the SearchResultEntry
				if (response instanceof SearchResultEntry) {
					Entry resultEntry = ((SearchResultEntry) response)
							.getEntry();
					return resultEntry;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			closeConnection(connection);
		}
		return null;
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

		String baseDN_str = getTypeConfig("auth", "baseDN");
		String[] baseDNs = baseDN_str.split(";");

		for (int i = 0; i < baseDNs.length; i++) {
			List<Entry> entrys = searchByBaseDN(baseDNs[i], "auth",
					"(" + loginNameProp + "=" + username + ")",
					getLdapAttributes("auth"), 1);
			if (entrys != null && !entrys.isEmpty()) {
				if (entrys.size() > 1) {
					throw new Exception("根据筛选条件找到多个用户，baseDN：" + baseDNs[i]
							+ "，filter：" + "(" + loginNameProp + "=" + username
							+ ")");
				} else {
					String userDN = entrys.get(0).getDn().getName();
					return validateUserByDn(userDN, password);
				}
			}
		}

		if (logger.isDebugEnabled()) {
            logger.debug("找不到对应的用户名：" + username + "，验证失败");
        }
		return false;
	}

	public boolean validateUserByDn(String DN, String password)
			throws Exception {
		LdapConnection connection = null;
		try {
			LdapConnectionConfig sslConfig = new LdapConnectionConfig();
			String url = getConfig("kmss.ldap.config.url")
					.split(";")[0];
			int port = 636;
			String host = "";
			if (url.startsWith("ldap://")) {
				host = url.substring(7);
			} else if (url.startsWith("ldaps://")) {
				host = url.substring(8);
				sslConfig.setUseSsl(true);
			}
			if (host.contains(":")) {
				port = Integer.parseInt(host.substring(host.indexOf(":") + 1));
				host = host.substring(0, host.indexOf(":"));
			}

			sslConfig.setLdapHost(host);
			sslConfig.setLdapPort(port);
			sslConfig.setTrustManagers(new NoVerificationTrustManager());
			connection = new LdapNetworkConnection(sslConfig);
			connection.bind(
					DN,
					password);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		return false;
	}

	public String findDNByLoginName(String loginName) throws Exception {
		String loginNameProp = getTypeConfig("auth", "prop.login");

		String baseDN_str = getTypeConfig("auth", "baseDN");
		String[] baseDNs = baseDN_str.split(";");

		for (int i = 0; i < baseDNs.length; i++) {
			List<Entry> entrys = searchByBaseDN(baseDNs[i], "auth",
					"(" + loginNameProp + "=" + loginName + ")",
					getLdapAttributes("auth"), 1);
			if (entrys != null && !entrys.isEmpty()) {
				if (entrys.size() > 1) {
					throw new Exception("根据筛选条件找到多个用户，baseDN：" + baseDNs[i]
							+ "，filter：" + "(" + loginNameProp + "=" + loginName
							+ ")");
				} else {
					String userDN = entrys.get(0).getDn().getName();
					return userDN;
				}
			}
		}
		return null;
	}

	@Override
	public void setConnectTimeout(long timeout) throws Exception {
		this.connectTimeout = timeout;
	}

	@Override
	public void setReadTimeout(long timeout) throws Exception {
		this.readTimeout = timeout;
	}

}
