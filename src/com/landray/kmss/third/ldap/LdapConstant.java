package com.landray.kmss.third.ldap;

import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;

public class LdapConstant {
	public static String ROOT_PATH = ConfigLocationsUtil.getWebContentPath()
			+ "/WEB-INF/KmssConfig/";

	public static String DEFAULT_FILE_NAME = "ldapconfigDefault.properties";
	public static String FILE_NAME = "ldapconfig.properties";

}
