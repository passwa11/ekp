package com.landray.kmss.third.pda.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.lic.SystemParameter;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class LicenseUtil {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(LicenseUtil.class);

	// 无限制
	public static final int LICENSE_UNLIMIT = -1;

	// 无此功能
	public static final int LICENSE_NOFUNCTION = 0;

	// ekp.license文件中对应key
	private static final String LICENSE_CONFIG_IPHONE_KEY = "license-pda-iphone";

	private static final String LICENSE_CONFIG_PHONE_KEY = "license-pda-phone";

	private static final String KMSSCACHE_PDA_LICENSE_MAP_KEY = "KMSSCACHE_PDA_LICENSE_MAP_KEY";

	// 授权失败放行的jsp路径
	private static final String[] LICENSE_ERROR_PAGE = {
			"/third/pda/resource/jsp/errorInfo.jsp", "/resource/error.do",
			"/logout.jsp", "/admin.do", "/vcode", "/resource/js/" };

	private static final String[] LICENSE_LOGIN_PAGE = { "/login.jsp",
			"/j_acegi_security_check", "/third/pda/login.jsp",
			"/sys/authentication/validate" };

	// 个人登录超时，超过在线限制等
	private static final String[] LICENSE_USER_ERROR_PAGE = { "overcount",
			"exception", "overtime" };

	private static final String[] LICENSE_USER_ERROR_MSG = {
			"userexception.overcount.info", "userexception.exception.info",
			"userexception.overtime.info" };

	private static String accessConfig = "";

	// 授权用户Map
	private static KmssCache pdaLicenseMap = new KmssCache(LicenseUtil.class);

	/*
	 * 获取license授权 -100未初始化 0:无此次功能 -1:功能使用无限制 其他正数:限制数
	 */

	private static int LICENSE_OF_PHONE = -100;

	// 获取当前系统的license信息
	public static int getPhoneLicence() {
		if (LICENSE_OF_PHONE == -100) {
			String param1 = SystemParameter.get(LICENSE_CONFIG_PHONE_KEY);
			String param2 = SystemParameter.get(LICENSE_CONFIG_IPHONE_KEY);
			int config1 = 0;
			int config2 = 0;
			logger.debug("获取PhoneLicense值为:" + param1 + "," + param2);
			if (StringUtil.isNull(param1)) {
                config1 = 0;
            } else {
                config1 = Integer.valueOf(param1);
            }
			if (StringUtil.isNull(param2)) {
                config2 = 0;
            } else {
                config2 = Integer.valueOf(param2);
            }

			if (config1 == 0 && config2 == 0) {
				LICENSE_OF_PHONE = LICENSE_NOFUNCTION;
			} else if (config1 < 0 || config2 < 0) {
				LICENSE_OF_PHONE = LICENSE_UNLIMIT;
			} else if (config1 > 0 || config2 > 0) {
				int count = 0;
				if (config1 > 0) {
                    count += config1;
                }
				if (config2 > 0) {
                    count += config2;
                }
				LICENSE_OF_PHONE = count;
			} else {
                LICENSE_OF_PHONE = LICENSE_NOFUNCTION;
            }
		}
		return LICENSE_OF_PHONE;
	}

	// 设置允许访问人员
	public static void setPersonInfoMap(Map<String, String> personInfo) {
		pdaLicenseMap.put(KMSSCACHE_PDA_LICENSE_MAP_KEY, personInfo);
	}

	// 清理集群缓存
	public static void clearKmssCachePdaLicense() {
		if (logger.isDebugEnabled()) {
            logger.debug("remove pda license cache .......");
        }
		pdaLicenseMap.remove(KMSSCACHE_PDA_LICENSE_MAP_KEY);
	}

	// 获取访问人员
	public static Map<String, String> getPersonInfoMap() {
		Map<String, String> targetMap = (Map<String, String>) pdaLicenseMap
				.get(KMSSCACHE_PDA_LICENSE_MAP_KEY);
		if (targetMap != null && !targetMap.isEmpty()) {
            return targetMap;
        }
		return null;
	}

	// 判断是否为错误页面
	public static boolean chkPathIsError(String path) {
		if (StringUtil.isNull(path)) {
            return false;
        }
		for (int i = 0; i < LICENSE_ERROR_PAGE.length; i++) {
			if (path.indexOf(LICENSE_ERROR_PAGE[i]) > -1) {
                return true;
            }
		}
		return false;
	}
	//判断路径是否为登录相关
	public static boolean chkPathIsLogin(String path) {
		if (StringUtil.isNull(path)) {
            return false;
        }
		for (int i = 0; i < LICENSE_LOGIN_PAGE.length; i++) {
			if (path.indexOf(LICENSE_LOGIN_PAGE[i]) > -1) {
                return true;
            }
		}
		return false;
	}

	// 判断是否为用户受限错误（超时，超过在线数等）
	public static String getUserErrorMsg(String path) {
		if (StringUtil.isNull(path)) {
            return null;
        }
		for (int i = 0; i < LICENSE_USER_ERROR_PAGE.length; i++) {
			if (path.toLowerCase().indexOf(LICENSE_USER_ERROR_PAGE[i]) > -1) {
                return LICENSE_USER_ERROR_MSG[i];
            }
		}
		return null;
	}

	// 获取kmssconfig配置，仅允许移动端访问
	public static boolean isOnlyPdaAccess() {
		if (StringUtil.isNull(accessConfig)) {
            accessConfig = ResourceUtil
                    .getKmssConfigString("third.phone.summary.access.enabled");
        }
		if (StringUtil.isNull(accessConfig)) {
            return false;
        } else if ("true".equalsIgnoreCase(accessConfig)) {
            return true;
        }
		return false;
	}

	// 是否不允许登录访问
	public static boolean isNotAllowLogin() {
		String loginAble = ResourceUtil
				.getKmssConfigString("third.phone.login.enabled");
		if (StringUtil.isNull(loginAble)) {
            return false;
        } else if ("true".equalsIgnoreCase(loginAble)) {
            return true;
        }
		return false;
	}

	public static String getErrorPath(int i) {
		return LICENSE_ERROR_PAGE[i];
	}
}
