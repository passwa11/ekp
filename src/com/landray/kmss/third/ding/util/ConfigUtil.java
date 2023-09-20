package com.landray.kmss.third.ding.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;

/**
 * @author Administrator
 * 
 */
public class ConfigUtil {
	public static final String DING_PROPERTIES_PATH = ConfigLocationsUtil
			.getWebContentPath()
			+ "/WEB-INF/KmssConfig/third/ding/ding-config.properties";

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ConfigUtil.class);

	private static Properties dingConfigBundle = null;

	public static Properties getDingProperties() {
		InputStream inputStream = null;
		Properties p = new Properties();
		try {
			inputStream = new FileInputStream(DING_PROPERTIES_PATH);
			loadProperties(p, inputStream, null);
		} catch (FileNotFoundException e) {
			logger.error("没有找到配置文件：" + DING_PROPERTIES_PATH, e);
		} catch (IOException ex) {
			logger.error("加载配置文件：" + DING_PROPERTIES_PATH + "出错", ex);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
		}
		return p;
	}

	public static void loadProperties(Properties props, InputStream is,
			String fileEncoding) throws IOException {
		PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
		if (fileEncoding != null) {
			propertiesPersister.load(props, new InputStreamReader(is,
					fileEncoding));
		} else {
			propertiesPersister.load(props, is);
		}
	}

	public static String getConfigString(String key) {
		try {
			if(dingConfigBundle==null){
				dingConfigBundle =getDingProperties();
			}
			return dingConfigBundle.getProperty(key);
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}

}
