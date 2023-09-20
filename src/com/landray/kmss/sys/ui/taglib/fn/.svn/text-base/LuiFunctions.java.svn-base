package com.landray.kmss.sys.ui.taglib.fn;

import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LuiFunctions {
	
	public static String message(String key) {

		return ResourceUtil.getString(key);
	}

	public static String msg(String key) {
		String v = ResourceUtil.getMessage(key);
		if (StringUtil.isNull(v)) {
			return key;
		} else {
			return v;
		}
	}

	public static String concat(String str1, String str2) {
		return str1 + str2;
	}

	public static String langMessage(String key, String lang) {
		Locale locale = ResourceUtil.getLocale(lang);
		return ResourceUtil.getStringValue(key, null, locale);
	}

	public static String loginLangMsg(String key) {
		String value = "";
		try {
			HttpServletRequest request = Plugin.currentRequest();
			Object obj = request.getAttribute("config");
			if (obj == null) {
				return value;
			}
			JSONObject config = (JSONObject) obj;
			String country = ResourceUtil.getLocaleByUser().getCountry();
			String officialLang = SysLangUtil.getOfficialLang();
			if (StringUtil.isNull(officialLang)) {
				officialLang = "CN";
			}
			value = config.get(key + "_" + country) == null
					? config.getString(key + "_" + officialLang)
					: config.getString(key + "_" + country);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return value;
	}

	public static String loginArrayLangMsg(String key, Integer index,
			String pkey) {
		String value = "";
		try {
			HttpServletRequest request = Plugin.currentRequest();
			Object obj = request.getAttribute("config");
			if (obj == null) {
				return value;
			}
			JSONObject config = (JSONObject) obj;
			JSONObject item = config.getJSONArray(key).getJSONObject(index);
			String country = ResourceUtil.getLocaleByUser().getCountry();
			String officialLang = SysLangUtil.getOfficialLang();
			if (StringUtil.isNull(officialLang)) {
				officialLang = "CN";
			}
			value = item.get(pkey + "_" + country) == null
					? item.getString(pkey + "_" + officialLang)
					: item.getString(pkey + "_" + country);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static String escapeHtml(String str) {
		if(StringUtil.isNotNull(str)) {
            return StringEscapeUtils.escapeHtml(str).replace("'", "&apos;");
        } else {
            return "";
        }
	}

	public static String ceil(Double a) {
		int d = Math.abs((int) Math.ceil(a));
		return String.valueOf(d);
	}

	public static String decodeUnicode(String unicode) {
		List<String> list = new ArrayList<String>();
		String reg = "\\\\u[0-9,a-f,A-F]{4}";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(unicode);
		while (m.find()) {
			list.add(m.group());
		}
		for (int i = 0, j = 2; i < list.size(); i++) {
			String code = list.get(i).substring(j, j + 4);
			char ch = (char) Integer.parseInt(code, 16);
			unicode = unicode.replace(list.get(i), String.valueOf(ch));
		}
		return unicode;
	}

	public static Boolean jsCompressEnabled(String executorId, String fileKey) {
		return true;
	}
}
