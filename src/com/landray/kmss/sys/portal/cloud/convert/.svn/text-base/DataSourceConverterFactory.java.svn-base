package com.landray.kmss.sys.portal.cloud.convert;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.landray.kmss.util.ClassUtils;

import com.landray.kmss.util.StringUtil;

public class DataSourceConverterFactory {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DataSourceConverterFactory.class);

	private static final String CONVERTER_FILE_PATH = "com/landray/kmss/sys/portal/cloud/resource/datasource-converter.properties";

	private static Properties p;

	static {
		try (InputStream input = DataSourceConverterFactory.class
				.getClassLoader().getResourceAsStream(CONVERTER_FILE_PATH)) {
			p = new Properties();
			p.load(input);
		} catch (Exception e) {
			logger.error("加载配置文件:'" + CONVERTER_FILE_PATH + "'加载出错！", e);
		}
	}

	private static Map<String, IDataSourceConverter> converters = new HashMap<>();

	public static IDataSourceConverter getConverter(String format) {
		if (converters.containsKey(format)) {
			return converters.get(format);
		}
		String className = p.getProperty(format);
		if (StringUtil.isNull(className)) {
			return null;
		}
		IDataSourceConverter converter = null;
		try {
			Class clazz = com.landray.kmss.util.ClassUtils.forName(className);
			converter = (IDataSourceConverter) clazz.newInstance();
		} catch (ClassNotFoundException e) {
			logger.error("未找到转换器：" + className);
			converter = null;
		} catch (Exception e) {
			logger.error("获取转换器出错!", e);
			converter = null;
		}
		converters.put(format, converter);
		return converter;
	}
}
