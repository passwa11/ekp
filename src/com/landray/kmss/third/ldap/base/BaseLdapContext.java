package com.landray.kmss.third.ldap.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.ldap.LdapDetailConfig;
import com.landray.kmss.third.ldap.LdapUtil;

public abstract class BaseLdapContext {

	protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(BaseLdapContext.class);

	private Map attributeMap = null;

	private Map<String, String> config = null;

	private String timePattern = null;

	private String timeZone = null;

	private String sexMale = null;

	private String sexFemale = null;

	protected String[] allURLs;

	public BaseLdapContext(Map<String, String> dataMap) {
		try {
			if (dataMap == null || dataMap.isEmpty()) {
				config = new LdapDetailConfig().getDataMap();
			} else {
				config = dataMap;
			}
			if (config == null || config.isEmpty()) {
				config = LdapUtil.getDefaultConfig();
			}
			timePattern = getConfig("kmss.ldap.config.timePattern");
			timeZone = getConfig("kmss.ldap.config.timeZone");
			allURLs = getConfig("kmss.ldap.config.url").split(";");

			sexMale = getConfig("kmss.ldap.config.sex.m");
			sexFemale = getConfig("kmss.ldap.config.sex.f");
		} catch (Exception e) {

		}
	}

	/**
	 * 判断某种类型的对象是否映射
	 * 
	 * @param type
	 * @return
	 */
	public boolean isTypeMap(String type) {
		return getAttributeMap().containsKey(type);
	}

	/**
	 * 初始化属性映射表
	 * 
	 * @return
	 */
	private Map getAttributeMap() {
		return attributeMap;
	}

	protected void initAttributeMap() throws Exception {
		attributeMap = new HashMap();
		List types = new ArrayList();
		// config = new LdapDetailConfig().getDataMap();
		for (String key : config.keySet()) {
			if (!key.startsWith("kmss.ldap.type.")) {
                continue;
            }
			String[] p = key.split("\\.");
			if (p.length != 6) {
                continue;
            }
			if (!"prop".equals(p[4])) {
                continue;
            }
			if (!attributeMap.containsKey(p[3])) {
				attributeMap.put(p[3], new HashMap());
				types.add(p[3]);
			}
			String value = config.get(key);
			((Map) attributeMap.get(p[3])).put(p[5], value);
		}
		if (types.contains("common")) {
			Map cMap = (Map) attributeMap.get("common");
			for (int i = 0; i < types.size(); i++) {
				if ("common".equals(types.get(i))) {
                    continue;
                }
				String type = (String) types.get(i);
				Map map = new HashMap();
				map.putAll(cMap);
				map.putAll((Map) attributeMap.get(type));
				attributeMap.put(type, map);
			}
		}
		String[] propTypes = { "dept", "person", "post", "group", "auth" };
		for (String type : propTypes) {
			if (!"true".equals(config
					.get("kmss.ldap.config." + type + ".check"))) {
				attributeMap.remove(type);
			}
		}
		if (logger.isDebugEnabled()) {
            logger.debug("属性映射表=" + attributeMap);
        }
	}

	/**
	 * 获取type类型的name属性在Ldap中的名称
	 * 
	 * @param type
	 * @param name
	 * @return
	 */
	public String getLdapAttribute(String type, String name) {
		try {
			return (String) ((Map) getAttributeMap().get(type)).get(name);
		} catch (Exception e) {
			logger.error(type + "---" + name);
			throw e;
		}
	}

	/**
	 * 根据type类型，获取有映射的name列表
	 * 
	 * @param type
	 * @return
	 */
	public String[] getLdapAttributes(String type) {
		return (String[]) ((Map) getAttributeMap().get(type)).values().toArray(
				new String[] {});
	}

	/**
	 * 将字符串转成Date类型
	 * 
	 * @param d
	 * @return
	 */
	public Date getTimeByString(String d) {
		if (d == null) {
            return null;
        }
		if (d.endsWith("Z")) {
            d = d.substring(0, d.length() - 1);
        }
		SimpleDateFormat format = new SimpleDateFormat(timePattern);
		format.setTimeZone(TimeZone.getTimeZone(timeZone));
		try {
			return format.parse(d);
		} catch (ParseException e) {
			logger.error("转换字符" + d + "到日期发生错误", e);
			return null;
		}
	}

	/**
	 * 将Date转成字符串类型
	 * 
	 * @param d
	 * @return
	 */
	public String getTimeToString(Date d) {
		if (d == null) {
            return null;
        }
		SimpleDateFormat format = new SimpleDateFormat(timePattern);
		format.setTimeZone(TimeZone.getTimeZone(timeZone));
		String result = format.format(d);
		return result.concat("Z");
	}

	/**
	 * 获取“kmss.ldap.type.[type/common].key”的配置参数值
	 * 
	 * @param type
	 * @param key
	 * @return
	 */
	public String getTypeConfig(String type, String key) {
		String retval = null;

		try {
			// config = new LdapDetailConfig().getDataMap();
			retval = config.get("kmss.ldap.type." + type + "." + key);
		} catch (Exception e) {
			logger.error("", e);
		}
		if (retval == null) {
			try {
				retval = config.get("kmss.ldap.type.common." + key);
			} catch (MissingResourceException e) {
			}
		}

		// if (retval == null && logger.isDebugEnabled())
		// logger.debug("kmss.ldap.type." + type + "." + key + "未配置");
		return retval;
	}

	/**
	 * 获取配置参数值
	 * 
	 * @param key
	 * @return
	 */
	public String getConfig(String key) {
		try {
			String value = config.get(key);
			if ("kmss.ldap.config.password".equals(key)) {
				value = LdapUtil.desDecrypt(value);
			}
			return value;
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
                logger.debug(key + "未配置");
            }
		}
		return null;
	}

	public String getSexMale() {
		return sexMale;
	}

	public String getSexFemale() {
		return sexFemale;
	}

	public Boolean getIsLangSupport() {
		if (isLangSupport == null) {
			boolean support = SysLangUtil.isLangEnabled()
					&& SysLangUtil.isPropertyLangSupport(
							SysOrgElement.class.getName(), "fdName");
			isLangSupport = Boolean.valueOf(support);
		}
		return isLangSupport;
	}

	public void setIsLangSupport(Boolean isLangSupport) {
		this.isLangSupport = isLangSupport;
	}

	private Boolean isLangSupport = null;

	public abstract void connect() throws Exception;

	public abstract void close() throws Exception;

	public abstract boolean validateUser(String username, String password)
			throws Exception;

	public abstract void setConnectTimeout(long timeout) throws Exception;

	public abstract void setReadTimeout(long timeout) throws Exception;

}
