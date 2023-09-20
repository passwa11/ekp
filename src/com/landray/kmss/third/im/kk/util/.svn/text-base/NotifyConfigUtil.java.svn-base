package com.landray.kmss.third.im.kk.util;

import com.landray.kmss.third.im.kk.KkConfig;
import com.landray.kmss.util.ResourceUtil;

/**
 * 读取kk配置文件
 * 
 * @author
 * 
 */
public class NotifyConfigUtil {
	private static String bundle = "third-im-kk";

	private static Integer defaultFailureTimes = null;

	// private static Properties configer = null;
	// static {
	// configer = new Properties();
	// InputStream is = NotifyConfigUtil.class
	// .getClassLoader()
	// .getResourceAsStream(
	// "\\com\\landray\\kmss\\sys\\ims\\kk\\notify\\notifyConfigs.properties");
	// try {
	// configer.load(is);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	public static String getNotifyConfig(String key) {
		return ResourceUtil.getString(key, bundle);

	}

	public static String getNotifyUrlPrefix() {
		KkConfig config = new KkConfig();
		/*String urlPrefix = config.getValue("kmss.ims.notify.urlPrefix");
		if (StringUtil.isNotNull(urlPrefix)) {
			urlPrefix = urlPrefix.trim();
			if (urlPrefix.endsWith("/")) {
				urlPrefix = urlPrefix.substring(0, urlPrefix.length() - 1);
			}
			return urlPrefix;
		}*/
		return ResourceUtil.getKmssConfigString("kmss.urlPrefix");
	}

	/*	public static String getMergeNotifyConfig(String key) throws Exception {
			KkConfig config = new KkConfig();
			String value = config.getValue(key);
			if (value != null) {
				value = value.trim();
			}
			if (StringUtil.isNotNull(value)) {
				return value;
			} else {
				value = ResourceUtil.getString(key, bundle);
				if (StringUtil.isNotNull(value)) {
					return value;
				}
			}
			return "";
		}
	
	public static Integer getDefaultFailureTimes() throws NumberFormatException, Exception {
		if (defaultFailureTimes == null) {
			defaultFailureTimes = Integer
					.parseInt(getMergeNotifyConfig(KkConfigConstants.KK_FAILURE_TIMES));
		}
		return defaultFailureTimes;
	}*/

}
