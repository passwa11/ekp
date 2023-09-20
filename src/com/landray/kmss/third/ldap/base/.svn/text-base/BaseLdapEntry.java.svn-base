package com.landray.kmss.third.ldap.base;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.util.StringUtil;


@SuppressWarnings("unchecked")
public abstract class BaseLdapEntry {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(BaseLdapEntry.class);

	protected BaseLdapContext context = null;

	protected String type = null;

	protected String dn = null;

	public BaseLdapEntry(BaseLdapContext context,
			String type,
			String dn) {
		this.context = context;
		this.type = type;
		this.dn = dn;
	}

	/**
	 * 格式化DN，去除中间的空格，忽略大小写
	 * 
	 * @param orgDN
	 * @return
	 */
	public static String formatDN(String orgDN) {
		if (orgDN == null) {
            return null;
        }
		String[] dns = orgDN.split(",");
		StringBuffer rtnVal = new StringBuffer();
		for (int i = 0; i < dns.length; i++) {
			rtnVal.append(',');
			dns[i] = dns[i].trim();
			int index = dns[i].indexOf('=');
			if (index > -1) {
				// 去掉转为小写 huangwq 2012-7-26
				// rtnVal.append(dns[i].substring(0,
				// index).toLowerCase()).append(
				// dns[i].substring(index));
				rtnVal.append(dns[i].substring(0, index)).append(
						dns[i].substring(index));
			} else {
				rtnVal.append(dns[i]);
			}
		}
		return rtnVal.substring(1);
	}

	/**
	 * 获取entry的类型
	 * 
	 * @return
	 * @throws NamingException
	 */
	public String getType() throws Exception {
		return type;
	}

	/**
	 * 判断某个属性是否在配置中有映射
	 * 
	 * @param name
	 * @return
	 * @throws NamingException
	 */
	public boolean isPropertyMap(String name) throws Exception {
		return context.getLdapAttribute(getType(), name) != null;
	}

	/**
	 * 获取字符串属性
	 * 
	 * @param name
	 * @return
	 * @throws NamingException
	 */
	public String getStringProperty(String name) throws Exception {
		String attName = context.getLdapAttribute(getType(), name);
		if ("objectGUID".equalsIgnoreCase(attName)) {
			byte[] guid = (byte[]) getObjectProperty(name);
			return getGUID(guid);
		}
		return (String) getObjectProperty(name);
	}

	private String getGUID(byte[] guid) {
		if (guid == null) {
			return null;
		}
		return StringUtil.toHexString(guid);
	}

	/**
	 * 获取对象属性
	 * 
	 * @param name
	 * @return
	 * @throws NamingException
	 */
	public abstract Object getObjectProperty(String name) throws Exception;

	/**
	 * 获取时间属性
	 * 
	 * @param name
	 * @return
	 * @throws NamingException
	 */
	public Date getDateProperty(String name) throws Exception {
		return context.getTimeByString(getStringProperty(name));
	}

	public String getImportInfoValue(String name, String searchValue)
			throws Exception {
		if (StringUtil.isNull(searchValue)) {
			searchValue = getStringProperty(name);
		}
		String byParentDN = context.getTypeConfig(getType(), "prop." + name
				+ ".byParentDN");
		String returnValue = "";
		if ("true".equals(byParentDN)) {
			returnValue = searchValue.substring(formatDN(searchValue).indexOf(
					",") + 1);
		} else {
			returnValue = searchValue;
		}
		return returnValue;

	}

	public abstract List getImportInfoValues(String name) throws Exception;

	public String searchImportInfo(String searchType, String searchKey,
			String searchValue) throws Exception {
		String attr = context.getTypeConfig(searchType, "prop.unid");
		String val = searchValue;
		return val;
	}


	public String getSexMale() {
		return context.getSexMale();
	}

	public String getSexFemale() {
		return context.getSexFemale();
	}

	private static boolean checkDN(String dn, String baseDN) {
		if (baseDN == null) {
            return false;
        }
		return dn.toLowerCase().endsWith(baseDN.toLowerCase());
	}

	public boolean isLangSupport() {
		if (context == null) {
			return false;
		}
		return context.getIsLangSupport().booleanValue();
	}
}
