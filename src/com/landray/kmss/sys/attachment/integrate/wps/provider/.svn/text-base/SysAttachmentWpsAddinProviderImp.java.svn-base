package com.landray.kmss.sys.attachment.integrate.wps.provider;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.attachment.integrate.wps.authen.WpsOfficeToken;
import com.landray.kmss.sys.attachment.integrate.wps.authen.WpsOfficeTokenGenerator;
import com.landray.kmss.sys.attachment.integrate.wps.cache.WpsOfficeTokenCache;
import com.landray.kmss.sys.attachment.integrate.wps.interfaces.ISysAttachmentWpsAddinProvider;
import com.landray.kmss.sys.attachment.integrate.wps.model.ThirdWpsOfficeToken;
import com.landray.kmss.sys.attachment.integrate.wps.model.ThirdWpsOfficeTokenInfo;
import com.landray.kmss.sys.attachment.integrate.wps.service.IThirdWpsOfficeTokenService;
import com.landray.kmss.sys.authentication.token.LRTokenGenerator;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.sys.config.action.SysConfigAdminUtil;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

import java.util.Properties;

/**
 * 
 * Token服务组件
 *
 */
public class SysAttachmentWpsAddinProviderImp implements ISysAttachmentWpsAddinProvider {
	private final static Log logger = LogFactory.getLog(SysAttachmentWpsAddinProviderImp.class);
	private final static String WPS_OFFICE_TOKEN_NAME = "WpsOfficeToken";
	private final static String WPS_OFFICE_TOKEN_FILE_NAME = "/LRToken";
	private final static String WPS_OFFICE_TOKEN_COOKIE_ADMIN = "cookie.domain";
	private final static String WPS_OFFICE_TOKEN_COOKIE_MAX_AGE = "43200";
	private final static String WPS_OFFICE_SECURITY_KEY_PUBLIC = "security.key.public";
	private final static String WPS_OFFICE_SECUTIRY_KEY_PRIVATE = "security.key.private";
	private IThirdWpsOfficeTokenService thirdWpsOfficeTokenService = null;
	private IThirdWpsOfficeTokenService getThirdWpsOfficeTokenService() {
		if(thirdWpsOfficeTokenService == null) {
			thirdWpsOfficeTokenService = (IThirdWpsOfficeTokenService) SpringBeanUtil.getBean("thirdWpsOfficeTokenokenService");
		}
		
		return thirdWpsOfficeTokenService;
	}
	
	/**
	 * 添加Token
	 */
	@Override
	public void addinAuthentication(String url) {
		try {
			LRTokenGenerator generator = new LRTokenGenerator();
			generator.generateKeys();
	
			ThirdWpsOfficeTokenInfo thirdWpsOfficeTokenInfo = new ThirdWpsOfficeTokenInfo()
					.setCookieDomain(url)
					.setCookieMaxAge(WPS_OFFICE_TOKEN_COOKIE_MAX_AGE)
					.setCookieMaxAgeRedis("")
					.setCookieName(WPS_OFFICE_TOKEN_NAME)
					.setInstanceClass(WPS_OFFICE_TOKEN_NAME)
					.setSecurityKeyPublic(generator.getPublicKey())
					.setSecurityKeyPrivate(generator.getPrivateKey());
			
			String content = JSONObject.toJSONString(thirdWpsOfficeTokenInfo);

			if(logger.isDebugEnabled()) {
				logger.debug("创建加载项使用的token信息：" + content);
			}
			
			HQLInfo hqlInfo = new HQLInfo();
			String whereBlock = "fdTokenName =:tokenName";
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setParameter("tokenName", WPS_OFFICE_TOKEN_NAME);
			Object obj = getThirdWpsOfficeTokenService().findFirstOne(hqlInfo);
			// 删除缓存token
			WpsOfficeTokenCache.getInstance().delete(WPS_OFFICE_TOKEN_NAME);
			if(obj==null) {
				ThirdWpsOfficeToken thirdWpsOfficeToken = new ThirdWpsOfficeToken();
				thirdWpsOfficeToken.setFdTokenName(WPS_OFFICE_TOKEN_NAME);
				thirdWpsOfficeToken.setFdToken(content);
				getThirdWpsOfficeTokenService().add(thirdWpsOfficeToken);
			} else {
				ThirdWpsOfficeToken thirdWpsOfficeToken = (ThirdWpsOfficeToken)obj;
				thirdWpsOfficeToken.setFdToken(content);
				getThirdWpsOfficeTokenService().update(thirdWpsOfficeToken);
			}
			
			// 添加token到缓存(若果出现事务未提交，则会有问题)
			//WpsOfficeTokenCache.getInstance().set(WPS_OFFICE_TOKEN_NAME, content);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("wps加载项添加token出错:" + e);
		}
	}
	/**
	 * 获取Token
	 * @return
	 */
	@SuppressWarnings("static-access")
	@Override
	public String getToken() {
		try {
			WpsOfficeTokenGenerator.getInstance().loadTokenInfo(getAuthentication());
			WpsOfficeToken token = WpsOfficeTokenGenerator.getInstance()
					.generateTokenByUserName(UserUtil.getUser().getFdLoginName());
			if(logger.isDebugEnabled()) {
				logger.debug("加载项项需要生成Token的信息,用户信息：" + UserUtil.getUser().getFdLoginName()
						+",Token:" + token.getTokenString());
			}

			if(StringUtil.isNotNull(token.getTokenString())) {
				return token.getTokenString();
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.error("加载项获取Token为空......");
		
		return "";

	}
	/**
	 * 获取授权信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getAuthentication() {
		// 缓存中存在token,直接从缓存中获取
		String token = WpsOfficeTokenCache.getInstance().get(WPS_OFFICE_TOKEN_NAME);
		if(token != null && StringUtil.isNotNull(token)) {	
			return token;
		}
		
		try {
			// 查询数据库中的token信息
			HQLInfo hqlInfo = new HQLInfo();
			String whereBlock = "fdTokenName =:tokenName";
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setParameter("tokenName", WPS_OFFICE_TOKEN_NAME);
			Object obj = getThirdWpsOfficeTokenService().findFirstOne(hqlInfo);
			
			if(obj!=null) {
				ThirdWpsOfficeToken thirdWpsOfficeToken = (ThirdWpsOfficeToken) obj;
				String tokenContent = thirdWpsOfficeToken.getFdToken();
				// 查询token信息放入到缓存中
				 WpsOfficeTokenCache.getInstance().set(WPS_OFFICE_TOKEN_NAME, tokenContent);
				 
				 return tokenContent;
			}
			
			
			String zdWebContentPath = ConfigLocationsUtil
					.getKmssConfigPath();
			if(StringUtil.isNull(zdWebContentPath)) {
				throw new IllegalAccessError(
						"不存在LRToken,请在【附件机制】【WPS加载项配置】中开启加载项配置");
			}
			
			logger.warn("表third_wps_office_token和缓存中不存在token,"
					+ "将从LRToken文件读取，若报错，请在【附件机制】【WPS加载项配置】中开启加载项配置");
			// 兼容历史功能，读取LRToken文件中的信息
			zdWebContentPath = zdWebContentPath + WPS_OFFICE_TOKEN_FILE_NAME;
			Properties prop = new Properties();
			SysConfigAdminUtil.loadProperties(prop, SysConfigAdminUtil.loadTokenKeyFileProperties(zdWebContentPath,SysConfigAdminUtil.isEncryptEnabled()), null);
			ThirdWpsOfficeTokenInfo thirdWpsOfficeTokenInfo = new ThirdWpsOfficeTokenInfo()
					.setCookieDomain(prop.getProperty(WPS_OFFICE_TOKEN_COOKIE_ADMIN))
					.setCookieMaxAge(WPS_OFFICE_TOKEN_COOKIE_MAX_AGE)
					.setCookieMaxAgeRedis("")
					.setCookieName(WPS_OFFICE_TOKEN_NAME)
					.setInstanceClass(WPS_OFFICE_TOKEN_NAME)
					.setSecurityKeyPublic(prop.getProperty(WPS_OFFICE_SECURITY_KEY_PUBLIC))
					.setSecurityKeyPrivate(prop.getProperty(WPS_OFFICE_SECUTIRY_KEY_PRIVATE));
			
			String content = JSONObject.toJSONString(thirdWpsOfficeTokenInfo);
			ThirdWpsOfficeToken thirdWpsOfficeToken = new ThirdWpsOfficeToken();
			thirdWpsOfficeToken.setFdTokenName(WPS_OFFICE_TOKEN_NAME);
			thirdWpsOfficeToken.setFdToken(content);
			getThirdWpsOfficeTokenService().add(thirdWpsOfficeToken);
			WpsOfficeTokenCache.getInstance().set(WPS_OFFICE_TOKEN_NAME, content);
			return content;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		logger.error("WPS加载项获取Token问题:缓存和数据库中都不存在token");
		return "";
	}

	/**
	 * 删除Token
	 */
	@Override
	public void deleteToken() {
		try {
			WpsOfficeTokenCache.getInstance().delete(WPS_OFFICE_TOKEN_NAME);
			String hql = "delete from ThirdWpsOfficeToken where fdTokenName =:tokenName";
			Query query = getThirdWpsOfficeTokenService().getBaseDao()
					.getHibernateSession().createQuery(hql);
			query.setParameter("tokenName", WPS_OFFICE_TOKEN_NAME);
			query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除出错:" + e);
		}
		
		
	}

}
