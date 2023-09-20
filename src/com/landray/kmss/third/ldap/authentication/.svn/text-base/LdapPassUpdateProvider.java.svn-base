package com.landray.kmss.third.ldap.authentication;

import java.util.Map;

import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.directory.api.ldap.model.entry.DefaultModification;
import org.apache.directory.api.ldap.model.entry.Modification;
import org.apache.directory.api.ldap.model.entry.ModificationOperation;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapConnectionConfig;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.apache.directory.ldap.client.api.NoVerificationTrustManager;

import com.landray.kmss.sys.organization.interfaces.ISysOrgPassUpdate;
import com.landray.kmss.third.ldap.LdapConfig;
import com.landray.kmss.third.ldap.LdapContext;
import com.landray.kmss.third.ldap.LdapDetailConfig;
import com.landray.kmss.third.ldap.LdapService;
import com.landray.sso.client.oracle.StringUtil;

public class LdapPassUpdateProvider implements ISysOrgPassUpdate{
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(LdapPassUpdateProvider.class);
	
	@Override
    public void changePassword(String loginName, String newPassword) throws Exception {
		LdapContext context = new LdapContext(null);
		context.connect();
		String DN = context.findDNByLoginName(loginName);
		context.close();
		
		if(StringUtil.isNull(DN)){
			logger.debug("LDAP中找不到用户："+loginName);
			return;
		}
		//
		String trustStore = context.getConfig("kmss.ldap.type.auth.trustStore");
		if (StringUtil.isNull(trustStore)) {
			updateAdPassApacheAPI(DN, newPassword);
		} else {
			updateAdPassJNDI(context, DN, newPassword);
		}
		
	}
	
	
	@Override
    public boolean validatePassword(HttpServletRequest request,
                                    String username, String password)  {
		LdapService ldapService = new LdapService();
		try {
			return ldapService.validateUser(username, password);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			logger.error("", e);
		}
		return false;
	}

	@Override
	public boolean isPassUpdateEnable() throws Exception {
		LdapConfig config = null;
		try {
			config = new LdapConfig();
			if (!"true".equals(config
					.getValue("kmss.authentication.ldap.enabled"))) {
				return false;
			}
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			logger.error("获取配置信息出错", e);
			return false;
		}

		Map map = new LdapDetailConfig().getDataMap();
		String authCheck = (String)map.get("kmss.ldap.config.auth.check");
		String updatePass = (String)map.get("kmss.ldap.config.auth.updatePass");
		if("true".equals(authCheck) && "true".equals(updatePass)){
			return true;
		}
		return false;
	}
	
	public void updateAdPassApacheAPI(String DN, String newPassword)
			throws Exception {
		LdapConnection connection = null;
		try {
			Map<String, String> map = new LdapDetailConfig().getDataMap();
			String url = map.get("kmss.ldap.config.url")
					.split(";")[0];
			if (url.startsWith("ldap://")) {
				url = url.substring(7);
			}
			if (url.contains(":")) {
				url = url.substring(0, url.indexOf(":"));
			}
			LdapConnectionConfig sslConfig = new LdapConnectionConfig();
			sslConfig.setLdapHost(url);
			sslConfig.setUseSsl(true);
			sslConfig.setLdapPort(636);
			sslConfig.setTrustManagers(new NoVerificationTrustManager());
			connection = new LdapNetworkConnection(sslConfig);
			connection.bind(
					map.get("kmss.ldap.config.managerDN"),
					map.get("kmss.ldap.config.password"));

			byte[] newUnicodePassword = ("\"" + newPassword + "\"")
					.getBytes("UTF-16LE");
			Modification replaceGn = new DefaultModification(
					ModificationOperation.REPLACE_ATTRIBUTE, "unicodePwd",
					newUnicodePassword);

			connection.modify(
					DN,
					replaceGn);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	public void updateAdPassJNDI(LdapContext context, String DN,
			String newPassword) throws Exception {
		try {
		ModificationItem[] mods = new ModificationItem[1];
		String newQuotedPassword = "\"" + newPassword + "\"";
		byte[] newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
		mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
				new BasicAttribute("unicodePwd", newUnicodePassword));

		InitialDirContext sslContext = context.getSslContext();
		if (sslContext == null) {
			sslContext = context.getDummySslContext();
		}

		sslContext.modifyAttributes(DN, mods);
		sslContext.close();
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		}
	}

}
