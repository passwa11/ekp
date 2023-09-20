package com.landray.kmss.sys.restservice.client.util;

import com.landray.kmss.web.util.RequestUtils;

public class PersistentUtil {
	/**
	 * 设计元素类型
	 * 
	 * @author 叶中奇
	 */
	public enum ElementType {
		/** 应用 */
		MetaApplication,
		/** 模块 */
		MetaModule,
		/** 表 */
		MetaEntity,
		/** 扩展表 */
		ExtEntity,
		/** 扩展点 */
		ExtensionPoint,
		/** 扩展 */
		Extension,
		/** 远程API */
		RemoteApi;

		ElementType() {
		}
	}

	// cloud注册中心保存设计信息的地址
	public static final String SAVE_DESIGN_ELEMENT_URL = "/api/framework-discovery/designElement/saveAll";
	/** 路径分隔符 */
	public static final String PATH_SPLIT = ":";

	/** ID的长度 */
	public static final int ID_LEN = 320;

	/** 空的JSON */
	public static final String JSON_EMPTY = "{}";
	/** id */
	public static final String PROP_ID = "id";
	/** label */
	public static final String PROP_LABEL = "label";
	/** md5 */
	public static final String PROP_MD5 = "md5";
	/** messageKey */
	public static final String PROP_MESSAGEKEY = "messageKey";
	/** module */
	public static final String PROP_MODULE = "module";

	/** 应用配置KEY的前缀 */
	public static final String CONFIG_PREFIX = "Profile:";

	/** 扩展点更新订阅主题 */
	public static final String EXTENSIONPOINT_CHANGE_TOPIC = "ExtensionPoint:Changed:Topic";
	public static final String EXTENSIONPOINT_CHANGE_LOCK = "ExtensionPoint:Changed:Lock:";

	public static final String DOT = ".";
	/**
	 * ekp基本包前缀
	 */
	public static final String EKP_BASE_PACKAGE_PREFIX = "com.landray.kmss.";
	/**
	 * ekp基本包路径
	 */
	public static final String EKP_BASE_PACKAGE = "com.landray.kmss";
	/**
	 * cloud基本包前缀
	 */
	public static final String BASE_PACKAGE_PREFIX = "com.landray.";
	/**
	 * cloud基本包路径
	 */
	public static final String BASE_PACKAGE = "com.landray";

	public static final String EKP_PACKAGE_KEY = ".kmss.";

	/**
	 * 构造ID
	 * 
	 * @param type
	 * @param paths
	 * @return
	 * @throws Exception
	 */
	public static String toId(ElementType type, String... paths) {
		StringBuilder sb = new StringBuilder(type.name());
		for (String path : paths) {
			sb.append(PATH_SPLIT).append(shortName(path));
		}
		if (sb.length() > ID_LEN) {
			// 配置ID长度不能超过320个字符
			// throw new KmssRuntimeException(new KmssMessage(""));
		}
		return sb.toString();
	}

	public static String shortName(String name) {
		String appName = RequestUtils.getAppName();
		if (name.contains(EKP_PACKAGE_KEY) && !name.endsWith(appName)) {
			name += DOT + appName;
		}
		if (name.startsWith(EKP_BASE_PACKAGE_PREFIX)) {
			name = name.substring(EKP_BASE_PACKAGE.length());
		} else if (name.startsWith(BASE_PACKAGE_PREFIX)) {
			name = name.substring(BASE_PACKAGE.length());
		}
		return name;
	}
}
