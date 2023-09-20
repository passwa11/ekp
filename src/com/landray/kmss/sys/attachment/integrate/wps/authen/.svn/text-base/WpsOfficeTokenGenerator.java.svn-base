package com.landray.kmss.sys.attachment.integrate.wps.authen;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.attachment.integrate.wps.model.ThirdWpsOfficeTokenInfo;


/**
 * Token生成器
 * 
 * @author 叶中奇
 */
public abstract class WpsOfficeTokenGenerator {
	public static final String COOKIE_DOMAIN = "cookie.domain";
	public static final String COOKIE_MAXAGE = "cookie.maxAge";
	public static final String COOKIE_NAME = "cookie.name";
	public static final String INSTANCE_CLASS = "instance.class";

	@SuppressWarnings("unused")
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(WpsOfficeTokenGenerator.class);
	
	private static WpsOfficeTokenGenerator generator;

	/**
	 * 获取一个实例
	 * 
	 * @return
	 * @throws IllegalAccessError
	 *             未初始化时就调用，抛出错误
	 */
	public static WpsOfficeTokenGenerator getInstance() throws IllegalAccessError {
		if (generator == null) {
			generator = new WpsOfficeLRTokenGenerator();
		}
		
		
		return generator;
	}

	/**
	 * 判断生成器是否已经初始化
	 * 
	 * @return
	 */
	public static boolean isInitialized() {
		return generator != null;
	}

	/**
	 * 从一个密钥文件中加载配置信息
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static WpsOfficeTokenGenerator loadTokenInfo(String json)
			throws Exception {
		ThirdWpsOfficeTokenInfo info = JSONObject.parseObject(json, ThirdWpsOfficeTokenInfo.class);
		generator.init(info.getSecurityKeyPublic(), info.getSecurityKeyPrivate());
		return generator;
	}

	/**
	 * 初始化实例其它参数
	 * 
	 * @param prop
	 * @throws Exception
	 */
	protected abstract void  init(String publicKey, String privateKey) throws Exception;

	/**
	 * cookie中存储名称
	 */
	private String cookieName;

	public String getCookieName() {
		return cookieName;
	}

	public void setCookieName(String cookieName) {
		this.cookieName = cookieName;
	}

	/**
	 * cookie存储域
	 */
	private String domain;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	private String[] domains;

	/**
	 * 有效期，单位为秒
	 */
	private long maxAge = 12 * 3600;

	public long getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(long maxAge) {
		this.maxAge = maxAge;
	}

	/**
	 * 根据Token字符串生成Token
	 * 
	 * @param tokenString
	 * @return 若解释失败，则返回null
	 */
	public abstract WpsOfficeToken generateTokenByTokenString(String tokenString);

	/**
	 * 根据当前用户名生成Token
	 * 
	 * @param username
	 * @return
	 */
	public abstract WpsOfficeToken generateTokenByUserName(String userName);

	/**
	 * 生成密钥文件
	 * 
	 * @param filename
	 *            密钥文件名
	 * @throws Exception
	 */
	public void storeToKeyFile(String filename) throws Exception {
		String instance = getClass().getName();
		int index = instance.lastIndexOf('.');
		instance = instance.substring(index + 1);
		StringBuffer sb = new StringBuffer();
		sb.append(INSTANCE_CLASS).append("=").append(instance).append("\r\n");
		sb.append(COOKIE_NAME).append("=").append(cookieName).append("\r\n");
		sb.append(COOKIE_MAXAGE).append("=").append(maxAge).append("\r\n");
		sb.append(COOKIE_DOMAIN).append("=").append(domain).append("\r\n");
		sb.append(getKeyFileString());

		File keyFile = new File(filename);
		PrintWriter writer = new PrintWriter(new FileWriter(keyFile));
		writer.print(sb);
		writer.close();
	}

	/**
	 * 获取实例中其它需要写入key中的信息
	 * 
	 * @return
	 */
	protected abstract String getKeyFileString();

	
	
	public String getSuitedDomain(String appurl){
		if(domains==null){
			return null;
		}
		for(String domain:domains){
			if(appurl.endsWith(domain.trim())){
				return domain.trim();
			}
		}
		return null;
	}

	public void setDomains(String[] domains) {
		this.domains = domains;
	}

	public String[] getDomains() {
		return domains;
	}
}
