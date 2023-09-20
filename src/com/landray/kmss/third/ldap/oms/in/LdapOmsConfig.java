package com.landray.kmss.third.ldap.oms.in;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.TimeZone;

import org.slf4j.Logger;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.third.ldap.LdapContext;
import com.landray.kmss.third.ldap.LdapUtil;
import com.landray.kmss.util.StringUtil;

public class LdapOmsConfig extends BaseAppConfig {
	protected static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(LdapContext.class);

	private Properties config = null;

	private String timePattern = null;

	private String timeZone = null;

	public LdapOmsConfig() throws Exception {
		super();
		config = LdapUtil.getLdapConfig();
		timePattern = getConfig("kmss.ldap.config.timePattern");
		timeZone = getConfig("kmss.ldap.config.timeZone");
	}

	@Override
    public String getJSPUrl() {
		return "";
	}

	public Date getLastUpdateTime() {
		String str = getValue("lastUpdateTime");
		if (StringUtil.isNull(str)) {
			return null;
		}
		return getTimeByString(str);
	}

	/**
	 * 将字符串转成Date类型
	 * 
	 * @param d
	 * @return
	 */
	public Date getTimeByString(String d) {
		return getTimeByString(d, null);
	}

	public Date getTimeByString(String d, String timePattern) {
		if (d == null) {
            return null;
        }
		if (StringUtil.isNull(timePattern)) {
			timePattern = this.timePattern;
		}
		if (d.endsWith("Z")) {
            d = d.substring(0, d.length() - 1);
        }
		SimpleDateFormat format = new SimpleDateFormat(timePattern);
		if (StringUtil.isNotNull(timeZone)) {
			format.setTimeZone(TimeZone.getTimeZone(timeZone));
		}
		try {
			return format.parse(d);
		} catch (ParseException e) {
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
		return getTimeToString(d, null);
	}

	public String getTimeToString(Date d, String timePattern) {
		if (d == null) {
            return null;
        }
		if (StringUtil.isNull(timePattern)) {
			timePattern = this.timePattern;
		}
		SimpleDateFormat format = new SimpleDateFormat(timePattern);
		if (StringUtil.isNotNull(timeZone)) {
			format.setTimeZone(TimeZone.getTimeZone(timeZone));
		}
		String result = format.format(d);
		return result.concat("Z");
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		setValue("lastUpdateTime", getTimeToString(lastUpdateTime));
	}

	public Date getLastDeleteTime() {
		String str = getValue("lastDeleteTime");
		if (StringUtil.isNull(str)) {
            return null;
        }
		return getTimeByString(str);
	}

	public void setLastDeleteTime(Date lastDeleteTime) {
		setValue("lastDeleteTime", getTimeToString(lastDeleteTime));
	}

	/**
	 * 获取配置参数值
	 * 
	 * @param key
	 * @return
	 */
	protected String getConfig(String key) {
		try {
			return config.getProperty(key);
		} catch (MissingResourceException e) {
			if (logger.isDebugEnabled()) {
                logger.debug(key + "未配置");
            }
		}
		return null;
	}

	public Date getLastSynchroOutTime() {
		String str = getValue("lastSynchroOutTime");
		if (StringUtil.isNull(str)) {
            return null;
        }
		return getTimeByString(str, "yyyy-MM-dd HH:mm:ss.sss");
	}

	public void setLastSynchroOutTime(Date lastSynchroOutTime) {
		setValue("lastSynchroOutTime",
				getTimeToString(lastSynchroOutTime, "yyyy-MM-dd HH:mm:ss.sss"));
	}

}
