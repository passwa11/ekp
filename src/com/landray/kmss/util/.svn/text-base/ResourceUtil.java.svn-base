package com.landray.kmss.util;

import com.alibaba.fastjson.JSONArray;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.config.action.SysConfigAdminUtil;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.metadata.dict.SysDictExtendDynamicProperty;
import com.landray.kmss.sys.profile.util.LoginTemplateUtil;
import com.landray.kmss.sys.profile.util.ResourceBundle;
import com.landray.kmss.web.Globals;
import net.sf.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author Administrator
 *
 */
public class ResourceUtil {
    public final static String APPLICATION_RESOURCE_NAME = "ApplicationResources";
    // 是否安全模式，SetCharacterEncodingFilter过滤器在初始化时赋值
    public static boolean isSafeMode = false;
    private static Properties kmssConfigBundle = new Properties();

    static {
        SysConfigAdminUtil.loadKmssConfigProperties(kmssConfigBundle);
        loadSystemProperties();
    }

    private static final String PARAM_LANDRAY = "Landray.";

    /**
     * 加载启动参数。例如：JAVA_OPTS=-DLandray.serverName1=test1（key:serverName1, value:test1）
     */
    private static void loadSystemProperties() {
        Properties tmp = new Properties();
        Properties props = System.getProperties();
        Iterator<Map.Entry<Object, Object>> iter = props.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Object, Object> entry = iter.next();
            String key = entry.getKey().toString();
            if (key.startsWith(PARAM_LANDRAY)) {
                tmp.put(key.substring(PARAM_LANDRAY.length()), entry.getValue());
            }
        }
        kmssConfigBundle.putAll(tmp);
    }

    public static String KMSS_RESOURCE_PATH = kmssConfigBundle.getProperty("kmss.resource.path");

    private static Locale OFFICIAL_LANG;

    static {
        OFFICIAL_LANG = getLocale(getKmssConfigString("kmss.lang.official"));
        if (OFFICIAL_LANG == null) {
            OFFICIAL_LANG = Locale.CHINA;
        }
        Locale.setDefault(Locale.CHINA);
    }

    /**
     * 获取官方语言
     * @return
     */
    public static Locale getOfficialLang() {
        return OFFICIAL_LANG;
    }

    public static String getMessage(String key) {
        if (StringUtil.isNotNull(key)) {
            String val = "";
            if (key.startsWith("{") && key.endsWith("}")) {
                val = ResourceUtil.getString(key.substring(1, key.length() - 1));
            } else {
                val = key;
            }
            return val;
        } else {
            return key;
        }
    }

    /**
     * 去除excelWorkSheet 名字中不支持的特殊字符
     *
     * @param key            需要获取的key值
     * @param excelWorkSheet 是否需要使用到excelWorkSheet的名字上面 eg: [测试]->测试
     * @return
     */
    public static String getString(String key, boolean excelWorkSheet) {
        String keyString = getString(key);
        if (excelWorkSheet && StringUtil.isNotNull(keyString)) {
            return keyString.replaceAll("\\[|\\]|\\?|\\\\|/|\\*", "");
        }
        return keyString;
    }

    /**
     * 获取默认资源的值
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        return getString(key, null, null);
    }

    /**
     * 获取指定bundle的的资源值
     *
     * @param key
     * @param bundle
     * @return
     */
    public static String getString(String key, String bundle) {
        return getString(key, bundle, null);
    }

    /**
     * 获取用户选择的语言环境的资源值
     *
     * @param key
     * @param locale
     * @return
     */
    public static String getString(String key, Locale locale) {
        return getString(key, null, locale);
    }

    /**
     * 获取指定bundle的的资源值  如果patternNormal=true 则去除value中的[] eg:[测试]->测试
     *
     * @param key
     * @param locale
     * @param patternNormal
     * @return
     */
    public static String getString(String key, Locale locale, boolean patternNormal) {
        String patternString = getString(key, null, locale);
        if (patternNormal && StringUtil.isNotNull(patternString)) {
            return patternString.replaceAll("\\[|\\]", "");
        }
        return patternString;
    }

    /**
     * 获取指定bundle和用户选择的语言环境的资源值
     *
     * @param key
     * @param bundle
     * @param locale 该参数已经废除
     * @return
     */
    public static String getString(String key, String bundle, Locale locale) {
        return getStringValue(key, bundle, getLocaleByUser());
    }

    /**
     * 设置用户语言
     *
     * @param request
     */
    public static void setUserLocal(HttpServletRequest request) {
        HttpSession session = request.getSession();
        KMSSUser user = UserUtil.getKMSSUser((HttpServletRequest) request);
        Locale newLocale = ResourceUtil.getLocale(request.getParameter("j_lang"));
        // #89649 登录页定制页面不需要将j_lang存入session
        boolean isDesign = LoginTemplateUtil.isDesignOrPreview(request);
        if (newLocale != null && !isDesign) {
            user.setLocale(newLocale);
            session.setAttribute(Globals.LOCALE_KEY, newLocale);
            //#139285 16换成springsession后，里面的属性对象无法变更，需回写
            try {
                Object context = session.getAttribute(Globals.SPRING_SECURITY_CONTEXT_KEY);
                if (context != null && context instanceof SecurityContext) {
                    Authentication authentication = ((SecurityContext) context).getAuthentication();
                    KMSSUser us = (KMSSUser) authentication.getPrincipal();
                    us.setLocale(newLocale);
                    session.setAttribute(Globals.SPRING_SECURITY_CONTEXT_KEY, context);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            session.setAttribute(Globals.LOCALE_KEY, user.getLocale());
        }
    }

    // public static void setUserLocal(HttpServletRequest request,
    // HttpServletResponse response) {
    // setUserLocal(request);
    // ResourceUtil.addLangCookie(request, response);
    // }

    public static void addLangCookie(ServletRequest request, ServletResponse response) {
        try {
            if (request != null && response != null && request instanceof HttpServletRequest
                    && response instanceof HttpServletResponse) {
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                HttpServletRequest httpServletRequest = (HttpServletRequest) request;
                Locale locale = (Locale) httpServletRequest.getSession().getAttribute(Globals.LOCALE_KEY);
                if (locale != null) {
                    httpServletResponse.addHeader("Set-Cookie", "j_lang" + "=" + locale.getLanguage() + "-"
                            + locale.getCountry() + "; Version=0" + "; Path=/");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     local cache, to avoid IO frequently.
     BTW, languageconfig.properties is fixed, so base on final consistency,
     no need care about concurrency
     */
    private static volatile Map<String, String> langPropertyConfig = null;

    public static Map<String, String> getLangPropertyConfig() {
        String configFilePath = ConfigLocationsUtil.getWebContentPath()
                + "/WEB-INF/KmssConfig/languageconfig.properties";
        if (!new File(configFilePath).exists()) {
            return null;
        }
        if (langPropertyConfig != null) {
            return langPropertyConfig;
        }
        Map<String, String> map = new HashMap<String, String>();
        InputStream is = null;
        try {
            Properties ps = new Properties();
            is = FileUtil.getInputStream(configFilePath);
            ps.load(is);
            for (Enumeration keys = ps.keys(); keys.hasMoreElements(); ) {
                String keyName = (String) keys.nextElement();
                map.put(keyName, ps.getProperty(keyName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        langPropertyConfig = map;
        return map;
    }

    /**
     * 获取当前用户的locale 字符串
     *
     * @return
     */
    public static String getLocaleStringByUser() {
        return getLocaleByUser().toString().toLowerCase().replace('_', '-');
    }

    /**
     * 获取当前用户的locale 字符串
     *
     * @param request
     * @return
     */
    public static String getLocaleStringByUser(HttpServletRequest request) {
        return getLocaleByUser(request).toString().toLowerCase().replace('_', '-');
    }

    /**
     * 获取当前用户的locale
     *
     * @return
     */
    public static Locale getLocaleByUser() {
        return getLocaleByUser(null);
    }

    /**
     * 获取当前用户的locale
     *
     * @param request
     * @return
     */
    private static Locale getLocaleByUser(HttpServletRequest request) {
        Locale locale = null;
        KMSSUser user = null;
        boolean isDesign = LoginTemplateUtil.isDesignOrPreview(request);
        if (request != null) {
            user = UserUtil.getKMSSUser(request);
        } else {
            user = UserUtil.getKMSSUser();
        }
        // #89649 登录页定制页面获取多语言信息时从url获取
        if (user != null && !user.isAnonymous() && !isDesign) {
            locale = user.getLocale();
        }
        // 如果未取到登录用户的语言，需要取浏览器的语言
        if (locale == null) {
            if (request == null) {
                request = Plugin.currentRequest();
            }
            if (request != null) {
                // 获取URL里的语言信息
                String langStr = request.getParameter("j_lang");
                if (StringUtil.isNull(langStr)) {
                    // 请求参数需要兼容locale
                    langStr = request.getParameter("locale");
                }
                locale = getLocale(langStr);
                if (locale == null) {
                    // 获取user-agent里的语言信息
                    locale = getLocaleByUA(request.getHeader("User-Agent"));
                    if (locale == null) {
                        // 取session里的语言信息
                        locale = (Locale) request.getSession().getAttribute(Globals.LOCALE_KEY);
                        if (locale == null) {
                            // 获取浏览器设置的语言信息
                            locale = request.getLocale();
                            // #65005修复在某些浏览器（如edge）下，header传递了Accept-Language参数，语言获取不正确问题
                            if (locale != null) {
                                String languageTag = locale.toLanguageTag();
                                if (StringUtil.isNotNull(languageTag) && languageTag.split("-").length > 1) {
                                    Map<String, String> langMap = getLangPropertyConfig();
                                    String localStr = langMap.get(languageTag.toLowerCase());
                                    if (StringUtil.isNotNull(localStr)) {
                                        String[] strArr = localStr.split("-");
                                        locale = new Locale(strArr[0], strArr[1]);
                                    } else {
                                        if (languageTag.toLowerCase().indexOf("zh") > -1
                                                && languageTag.toLowerCase().indexOf("hans") > -1) {
                                            locale = new Locale("zh", "CN");
                                        }
                                        if (languageTag.toLowerCase().indexOf("zh") > -1
                                                && languageTag.toLowerCase().indexOf("hant") > -1) {
                                            locale = new Locale("zh", "HK");
                                        }
                                        if (languageTag.toLowerCase().indexOf("en") > -1) {
                                            locale = new Locale("en", "US");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        /**
         * 如果当前预言并没有在多语言支持中，则使用默认语言
         */
        if (locale != null) {
            ArrayList<String> langs = SysLangUtil.getSupportedLangList();
            String localStr = locale.toString().replace('_', '-');

            if("en".equals(localStr)) {
                locale = getLocale("en-US");
            } else if("zh-TW".equals(localStr)) {
                locale = getLocale("zh-HK");
            }

            if (!langs.contains(locale.toString().replace('_', '-'))) {
                locale = null;
            }
        }

        if (locale == null) {
            locale = OFFICIAL_LANG;
        }
        if (request != null) {
            //after use Spring-Session, change the double-checking to conditioned
            Locale _locale = (Locale) request.getSession().getAttribute(Globals.LOCALE_KEY);
            if (_locale == null || !_locale.equals(locale)) {
                // 保存当前语言信息，防止刷新页面后，语言又回到官方语言
                request.getSession().setAttribute(Globals.LOCALE_KEY, locale);
            }
        }
        return locale;
    }

    /**
     * 获取官方的资源值
     *
     * @param key
     * @param bundle
     * @return
     */
    public static String getOfficialString(String key) {
        return getOfficialString(key, null);
    }

    /**
     * 获取指定bundle的官方的资源值
     *
     * @param key
     * @param bundle
     * @return
     */
    public static String getOfficialString(String key, String bundle) {
        return getStringValue(key, bundle, OFFICIAL_LANG);
    }

    public static String getString(HttpServletRequest request, String key, String bundle) {
        return getStringValue(key, bundle, getLocaleByUser(request));
    }

    /**
     * 获取指定语言的资源值
     *
     * @param key
     * @param bundle
     * @return
     */
    public static String getString(HttpSession session, String key) {
        return getString(session, null, key);
    }

    /**
     * 获取指定bundle和语言环境的资源值
     *
     * @param key
     * @param bundle
     * @return
     */
    public static String getString(HttpSession session, String bundle, String key) {
        Locale locale = (Locale) session.getAttribute(Globals.LOCALE_KEY);
        if (locale == null) {
            locale = getLocaleByUser();
        }
        return getStringValue(key, bundle, locale);
    }

    /**
     * 获取指定bundle和语言环境的资源值（原始的取值方式，不适配在线编辑功能）
     *
     * @param key
     * @param bundle
     * @param locale
     * @return
     */
    private static String getStringValueOri(String key, String bundle, Locale locale) {
        if (StringUtil.isNull(key)) {
            return "";
        }
        if (StringUtil.isNull(bundle)) {
            int i = key.indexOf(':');
            if (i > -1) {
                bundle = key.substring(0, i);
                key = key.substring(i + 1);
            }
        }
        String resource;
        if (StringUtil.isNull(bundle)) {
            resource = APPLICATION_RESOURCE_NAME;
        } else {
            resource = "com.landray.kmss." + bundle.replaceAll("-", ".") + "." + APPLICATION_RESOURCE_NAME;
        }
        try {
            java.util.ResourceBundle resourceBundle = null;
            if (locale == null) {
                resourceBundle = java.util.ResourceBundle.getBundle(resource);
            } else {
                resourceBundle = java.util.ResourceBundle.getBundle(resource, locale);
            }

            if (isDebug()) {
                return "[" + resourceBundle.getString(key) + "]";
            } else {
                return resourceBundle.getString(key);
            }
        } catch (Exception e) {
            try {
                if (locale == null) {
                    return null;
                } else {
                    if (isDebug()) {
                        return "{" + java.util.ResourceBundle.getBundle(resource).getString(key) + "}";
                    } else {
                        return java.util.ResourceBundle.getBundle(resource).getString(key);
                    }
                }
            } catch (Exception e2) {
                return null;
            }
        }
    }

    public static String getStringValue(String key, String bundle, Locale locale) {
        if (StringUtil.isNull(key)) {
            return "";
        }

        // 取自定义属性的名称
        if (key.endsWith(SysDictExtendDynamicProperty.CUSTOM_FIELD)) {
            if (locale == null) {
                locale = OFFICIAL_LANG;
            }
            String j_key = locale.getCountry();
            String json = key.replace(SysDictExtendDynamicProperty.CUSTOM_FIELD, "");
            JSONObject obj = JSONObject.fromObject(json);
            String value = "";
            if (obj.containsKey(j_key)) {
                value = obj.getString(j_key);
            }
            if (StringUtil.isNull(value)) {
                if (obj.containsKey(OFFICIAL_LANG.getCountry())) {
                    value = obj.getString(OFFICIAL_LANG.getCountry());
                } else if (obj.containsKey("def")) {
                    value = obj.getString("def");
                }
            }
            return value;
        }

        if (isSafeMode) // 如果是安全模式启动，就不能使用现在的机制，需要使用原始的机制
        {
            return getStringValueOri(key, bundle, locale);
        }
        if (StringUtil.isNull(bundle)) {
            int i = key.indexOf(':');
            if (i > -1) {
                bundle = key.substring(0, i);
                key = key.substring(i + 1);
            }
        }

        String bundleOri = bundle;
        if (StringUtil.isNull(bundle)) {
            bundle = "/";
        } else {
            bundle = "/" + bundle.replaceAll("-", "/") + "/";
        }

        String msg = null;
        if (locale == null) {
            msg = ResourceBundle.getInstance().getString(bundle, key);
        } else {
            msg = ResourceBundle.getInstance().getString(bundle, key, locale);
        }

        // 如果从缓存中未取到数据，可能存在以下问题：
        // 1. 资源文件中没有数据，缓存中没有数据
        // 2. 资源文件中有数据，但缓存没有数据
        // 尽管这种情况比较少，但是为了以防万一，还需要从资源文件中再取一次
        if (StringUtil.isNull(msg)) {
            msg = getStringValueOri(key, bundleOri, locale);
        }

        if (isDebug()) {
			if(isQuicklyEdit()){
				HttpServletRequest request = Plugin.currentRequest();
				JSONArray quicklyEditKeys = (JSONArray) request.getAttribute("quicklyEditKeys");
				if(quicklyEditKeys == null){
					quicklyEditKeys = new JSONArray();
					request.setAttribute("quicklyEditKeys", quicklyEditKeys);
				}
				JSONObject langKey = new JSONObject();
				langKey.put("bundle", bundle);
				langKey.put("key", key);
				quicklyEditKeys.add(langKey);
			}
			if(StringUtil.isNull(msg)){
			    return msg;
            }
            return "[" + msg + "]";
        } else {
            return msg;
        }
    }

    public static boolean isDebug() {
        HttpServletRequest request = Plugin.currentRequest();
        if (request != null) {
            Object isDebug = request.getSession().getAttribute("LANG_TOOLS_DEBUG");
            if (isDebug != null && "true".equals(isDebug.toString())) {
                return true;
            }
        }
        return false;
    }

	public static boolean isQuicklyEdit() {
		HttpServletRequest request = Plugin.currentRequest();
		if (request != null) {
			Object isQuicklyEdit = request.getSession().getAttribute("LANG_QUICKLY_EDIT");
			if (isQuicklyEdit != null && "true".equals(isQuicklyEdit.toString())) {
				return true;
			}
		}
		return false;
	}

    public static String getString(HttpServletRequest request, String key, String bundle, Object[] args) {
        String rtnVal = getString(request, key, bundle);
        for (int i = 0; i < args.length; i++) {
            rtnVal = StringUtil.replace(rtnVal, "{" + i + "}", String.valueOf(args[i]));
        }
        return rtnVal;
    }

    /**
     * 获取指定bundle和语言环境的资源值，并自动将资源值中的{0}替换为arg
     *
     * @param key
     * @param bundle
     * @param locale
     * @param arg
     * @return
     */
    public static String getString(String key, String bundle, Locale locale, Object arg) {
        return getString(key, bundle, locale, new Object[]{arg});
    }

    /**
     * 获取指定bundle和语言环境的资源值，并自动将资源值中的{i}替换为args[i]
     *
     * @param key
     * @param bundle
     * @param locale
     * @param args
     * @return
     */
    public static String getString(String key, String bundle, Locale locale, Object[] args) {
        String rtnVal = getString(key, bundle, locale);
        for (int i = 0; i < args.length; i++) {
            rtnVal = StringUtil.replace(rtnVal, "{" + i + "}", String.valueOf(args[i]));
        }
        return rtnVal;
    }

    /**
     * 获取kmss-config.proerties的信息
     *
     * @param key
     * @return
     */
    public static String getKmssConfigString(String key) {
        try {
            return kmssConfigBundle.getProperty(key);
        } catch (Exception e) {
            return null;
        }
    }

    public static Map<String, String> getKmssUiConfig() {
        Map<String, String> map = new HashMap<String, String>();
        Iterator<Object> iter = kmssConfigBundle.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next().toString();
            if (key.startsWith("kmss.sys.ui")) {
                map.put(key, kmssConfigBundle.getProperty(key));
            }
        }
        return map;
    }

    public static Map<String, String> getKmssConfig(String keyPre) {
        if (StringUtil.isNull(keyPre)) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        Iterator<Object> iter = kmssConfigBundle.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next().toString();
            if (key.startsWith(keyPre)) {
                map.put(key, kmssConfigBundle.getProperty(key));
            }
        }
        return map;
    }

    /**
     * 根据语言设置获取语言locale
     *
     * @param lang
     * @return
     */
    public static Locale getLocale(String lang) {
        if (StringUtil.isNull(lang)) {
            return null;
        }
        if (lang.indexOf(";") > -1) {
            lang = lang.substring(0, lang.indexOf(";"));
        }
        if (StringUtil.isNull(lang)) {
            return null;
        }
        if (lang.indexOf("|") > -1) {
            lang = lang.substring(lang.indexOf("|") + 1);
        }
        String[] langArr = lang.trim().split("-");
        if (langArr.length == 1) {
            return new Locale(langArr[0]);
        }
        if (langArr.length == 2) {
            return new Locale(langArr[0], langArr[1]);
        }
        return new Locale(langArr[0], langArr[1], langArr[2]);
    }

    /**
     * 根据UA信息来获取语言Locale
     */
    private static Map<String, String> _langMapping = new HashMap<String, String>();

    static {
        _langMapping.put("hans", "cn");
        _langMapping.put("hant", "hk");
        _langMapping.put("tw", "hk");// 台湾繁体也当香港繁体处理
    }

    public static Locale getLocaleByUA(String userAgent) {
        Locale locale = null;

        if (StringUtil.isNull(userAgent)) {
            return locale;
        }
        userAgent = userAgent.toLowerCase();
        if (userAgent.indexOf("language") > -1) {
            String language = userAgent.substring(userAgent.lastIndexOf("language") + 9);
            if (language.indexOf(" ") > -1) {
                language = language.substring(0, language.indexOf(" "));
            }
            if (StringUtil.isNotNull(language)) {
                String[] langArr = language.split("[-_]");
                String key = null;
                if (langArr.length >= 1) {
                    key = langArr[0];
                }
                if (langArr.length >= 2) {
                    // 部分客户端language信息的第二个参数并不是指ISO 3166 alpha-2的国家标示，这里做下转换
                    String country = langArr[1];
                    if (_langMapping.containsKey(country)) {
                        country = _langMapping.get(country);
                    }
                    key += "-" + country.toUpperCase();
                }
                locale = getMatchLocale(key);
            }
        }
        return locale;
    }

    private static Locale getMatchLocale(String key) {
        ArrayList<String> langList = SysLangUtil.getSupportedLangList();
        for (String lang : langList) {
            if (lang.indexOf(key) > -1) {
                return getLocale(lang);
            }
        }
        return null;
    }

}
