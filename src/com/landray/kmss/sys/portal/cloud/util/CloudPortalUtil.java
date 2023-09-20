package com.landray.kmss.sys.portal.cloud.util;

import com.landray.kmss.sys.portal.cloud.MessageCache;
import com.landray.kmss.sys.portal.cloud.PortalMappingHelper;
import com.landray.kmss.sys.portal.cloud.dto.PortletConfigVO;
import com.landray.kmss.sys.portal.cloud.dto.SysPortalDataModuleVO;
import com.landray.kmss.sys.portal.cloud.dto.SysPortalDataSourceVO;
import com.landray.kmss.sys.ui.xml.model.SysUiCode;
import com.landray.kmss.sys.ui.xml.model.SysUiVar;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.util.RequestUtils;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CloudPortalUtil {

	public static final String MESSAGE_SPLIT = ":";

	public static final String MESSAGE_LEFT = "{";

	public static final String MESSAGE_RIGHT = "}";
	// 中划线
	public static final String STRIKE = "-";
	/**
	 * 匹配URL中的参数名
	 */
	private static Pattern MATCH_PARAM = Pattern
			.compile("[\\?&]([^(&|#)]+)=([^(&|#)]*)");
	/**
	 * 目前正在处理的模块名
	 */
	private static ThreadLocal<String> NOW_MODULE = new ThreadLocal<>();

	private static String officialLang = null;

	public static String getModule() {
		return NOW_MODULE.get();
	}

	public static void setModule(String moduleName) {
		NOW_MODULE.set(moduleName);
	}

	public static void removeModule() {
		NOW_MODULE.remove();
	}

	/**
	 * 获取官方语言简称，如：zh-CN
	 * 
	 * @return
	 */
	public static String getOfficialLang() {
		if (StringUtil.isNotNull(officialLang)) {
			return officialLang;
		}
		String officialLangStr = ResourceUtil
				.getKmssConfigString("kmss.lang.official");
		if (StringUtil.isNull(officialLangStr)) {
			return "zh-CN";
		}
		officialLang = officialLangStr;
		if (officialLang.contains("|")) {
			officialLang = officialLang
					.substring(officialLang.indexOf("\\|") + 1);
		}
		return officialLang;
	}

	/**
	 * {sys-portal:msg} => sys-portal:msg <br>
	 * sys-portal:msg => sys-portal:msg <br>
	 * other => null
	 * 
	 * @param name
	 * @return
	 */
	public static String getMessageKey(String name) {
		if (StringUtil.isNull(name)) {
			return null;
		}
		if (name.startsWith(MESSAGE_LEFT) && name.endsWith(MESSAGE_RIGHT)) {
			name = name.substring(1, name.length() - 1);
		}
		if (ResourceUtil.getString(name) != null) {
			return name;
		}
		return null;
	}

	public static String replaceMessageKey(String messageKey) {
		return replaceMessageKey(messageKey, false);
	}
	/**
	 * 将ekp的多语言{sys-portal:msg}转换为cloud的多语言{origin-ekp-sys-portal:msg}，并将ekp多语言缓存，准备存到数据源VO中
	 * 
	 * @param messageKey
	 * @return
	 */
	public static String replaceMessageKey(String messageKey,
			boolean isOperation) {
		String oldMessageKey = getMessageKey(messageKey);
		if (StringUtil.isNotNull(oldMessageKey)) {
			messageKey = oldMessageKey;
			if (oldMessageKey.contains(":")) {
				// 只需要“:”号后面的
				messageKey = oldMessageKey
						.substring(oldMessageKey.indexOf(":") + 1);
			}
			MessageCache.saveToLocal(oldMessageKey, isOperation);
			// {origin-ekp-sys-portal:aaa}
			return new StringBuffer().append(MESSAGE_LEFT)
					.append(RequestUtils.getAppName())
					.append(STRIKE).append(NOW_MODULE.get())
					.append(MESSAGE_SPLIT).append(messageKey)
					.append(MESSAGE_RIGHT).toString();
		}
		return messageKey;
	}

	/**
	 * 根据source中的code获取url
	 * 
	 * @param code
	 * @return
	 */
	public static String getUrlByCode(SysUiCode code) {
		String url = code.getSrc();
		if (StringUtil.isNull(url)) {
			url = code.getBody().trim();
			try {
				JSONObject json = JSONObject.fromObject(url);
				url = json.getString("url");
			} catch (Exception e) {
			}
		}
		return url;
	}

	/**
	 * ekp的var在cloud叫config，数据格式不一样，这里做个转换
	 * 
	 * @return
	 */
	public static PortletConfigVO varToConfig(SysUiVar var) {
		PortletConfigVO config = new PortletConfigVO();
		config.setLabel(var.getName());
		config.setKey(var.getKey());
		config.setType(PortalMappingHelper.getKindMapping(var.getKind()));
		config.setRequired(var.getRequire());
		config.setDefaultValue(var.getDefault());
		if (StringUtil.isNotNull(var.getBody())) {
			JSONObject content = JSONObject.fromObject(var.getBody());
			config.setContent(content);
		}
		// kind=dialogJs的处理由开发去做，目前只有tree和list
		// if (StringUtil.isNotNull(var.getBody())) {
		// JSONObject content = JSONObject.fromObject(var.getBody());
		// config.setContent(content);
		// // kind=dialogJs的特殊处理
		// if ("dialogJs".equals(var.getKind())
		// && content.get("url") != null) {
		// String url = content.getString("url");
		// if (url.indexOf(PortletConstants.PARENT_PLACE_HOLDER) > -1) {
		// // 包含!{parentId}的是树形结构
		// config.setType("tree");
		// } else {
		// // 不包含!{parentId}的是列表结构
		// config.setType("list");
		// }
		// }
		// }
		return config;
	}

	/**
	 * 首字母大写
	 * 
	 * @param s
	 * @return
	 */
	public static String toUpperCaseFirstChar(String s) {
		if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder())
                    .append(Character.toUpperCase(s.charAt(0)))
                    .append(s.substring(1)).toString();
        }
	}

	/**
	 * 在uri上增加appName
	 * 
	 * @param uri
	 * @return
	 */
	public static String addAppNameInUri(String uri) {
		if (RequestUtils.isDataUri(uri)) {
			// /data/km-review/kmReviewMainPortlet/**
			String prefix = "/data";
			uri = uri.substring(prefix.length());
			String appName = RequestUtils.getAppName();
			if (!uri.startsWith("/" + appName)) {
				return prefix + "/" + appName + uri;
			}
			return uri;
		}
		return uri;
	}

	/**
	 * 替换key
	 * 
	 * @param json
	 * @param oldKey
	 * @param newKey
	 */
	public static void replaceKey(JSONObject json, Object oldKey,
			Object newKey) {
		Object value = json.get(oldKey);
		if (value != null) {
			json.put(newKey, value);
			json.remove(oldKey);
		}
	}

	/**
	 * 获取url中的参数
	 * 
	 * @param url
	 * @return
	 */
	public static Map<String, Object> getUrlParams(String url) {
		Map<String, Object> params = new HashMap<>();
		if (StringUtil.isNotNull(url) && url.indexOf("?") > -1) {
			Matcher matcher = MATCH_PARAM.matcher(url);
			while (matcher.find()) {
				params.put(matcher.group(1), matcher.group(2));
			}
		}
		return params;
	}

	/**
	 * 数据源缓存
	 */
	public static ConcurrentHashMap<String, List<SysPortalDataSourceVO>> dataSourceCache = new ConcurrentHashMap<>(64);

	/**
	 * 模块缓存
	 */
	public static List<SysPortalDataModuleVO> moduleCache = new ArrayList<>();

	/**
	 * 清理门户缓存
	 */
	public static void clearCache() {
		dataSourceCache.clear();
		moduleCache.clear();
	}

}
