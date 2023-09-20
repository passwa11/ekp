package com.landray.kmss.third.ldap.authentication;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.NamingException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.authentication.interfaces.AbstractAuthenticationProcessingFilter;
import com.landray.kmss.third.ldap.LdapConfig;
import com.landray.kmss.third.ldap.LdapDetailConfig;
import com.landray.kmss.third.ldap.LdapService;
import com.landray.kmss.third.ldap.apache.ApacheLdapService;
import com.landray.kmss.third.ldap.base.BaseLdapService;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.Globals;

public class LdapAuthenticationProcessingFilter extends
		AbstractAuthenticationProcessingFilter {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(LdapAuthenticationProcessingFilter.class);

	private LdapService ldapService = new LdapService(true);

	private ApacheLdapService apacheLdapService = new ApacheLdapService(true);

	private static String ldapType = null;

	public void update(Map<String, String> dataMap) {
		ldapService = new LdapService(dataMap);
		apacheLdapService = new ApacheLdapService(dataMap);
	}

	@Override
    public void destroy() {
		try {
			ldapService.close();
		} catch (Exception e) {
		}
	}

	@Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
		LdapConfig config = null;
		boolean getConfigError = false;
		try {
			config = new LdapConfig();
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			getConfigError = true;
			logger.error("获取配置信息出错", e);
			chain.doFilter(req, res);
		}
		if (!getConfigError) {
			if ("true".equals(config
					.getValue("kmss.authentication.ldap.enabled"))) {
				super.doFilter(req, res, chain);
			} else {
				chain.doFilter(req, res);
			}
		}
	}

	private BaseLdapService getService() throws Exception {
		Map<String, String> config = new LdapDetailConfig().getDataMap();
		String url = config.get("kmss.ldap.config.url");
		if (url.startsWith("ldaps")) {
			return apacheLdapService;
		}
		return ldapService;
	}

	@Override
	protected boolean validatePassword(HttpServletRequest request,
			String uname, String pwd) {
		if (StringUtil.isNull(uname) || StringUtil.isNull(pwd)) {
            return false;
        }
		try {
			boolean result = getService().validateUser(uname, pwd);
			if(result){
				request.setAttribute("LDAP_PASSWORD", pwd);
			}
			return result;
		} catch (NamingException e) {
			e.printStackTrace();
			String resultMsg = "";
			if (e instanceof AuthenticationException) {
				AuthenticationException ae = (AuthenticationException) e;
				String msg = ae.getMessage();
				if ("Active Directory".equals(getLdapType())) {
					if (msg.contains("data 52e")) {
						resultMsg = "用户名密码不正确";
					} else if (msg.contains("data 701")) {
						resultMsg = "账户过期";
					} else if (msg.contains("data 773")) {
						resultMsg = "密码过期";
					} else if (msg.contains("data 533")) {
						resultMsg = "账户已被禁用";
					}
				} else if ("SunOne".equals(getLdapType())) {
					if (msg.contains("Invalid Credentials")) {
						resultMsg = "用户名密码不正确";
					} else if (msg.contains("Account locked")) {
						resultMsg = "账户被锁定";
					} else if (msg.contains("password expired")) {
						resultMsg = "密码过期";
					} else if (msg.contains("Account inactivated")) {
						resultMsg = "账户已被禁用";
					}
				}
			} else if (e instanceof CommunicationException) {
				CommunicationException ce = (CommunicationException) e;
				Throwable t = ce.getRootCause();
				if (t instanceof ConnectException) {
					resultMsg = "LDAP服务器连接不上";
				}
			}
			if (StringUtil.isNotNull(resultMsg)) {
				request.setAttribute(
				        Globals.SPRING_SECURITY_AUTHENTICATION_RESULT_MESSAGE,
						resultMsg);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return false;
	}

	@Override
	protected boolean validatePassword(String username, String password) {
		try {
			LdapConfig config = new LdapConfig();
			if (!"true".equals(config
					.getValue("kmss.authentication.ldap.enabled"))) {
				return false;
			}
		} catch (Exception e1) {
			// TODO 自动生成 catch 块
			logger.error("", e1);
			return false;
		}
		// TODO 自动生成的方法存根
		if (StringUtil.isNull(username) || StringUtil.isNull(password)) {
            return false;
        }
		try {
			// return ldapService.validateUser(username, password);
			boolean result = getService().validateUser(username, password);
			return result;
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
			logger.error("LDAP登录校验出错", e);
		}
		return false;
	}

	public String getLdapType() {
		if (StringUtil.isNull(ldapType)) {
			ldapType = ldapService.getLdapContext().getConfig(
					"kmss.ldap.config.ldap.type");
		}
		return ldapType;
	}
}
