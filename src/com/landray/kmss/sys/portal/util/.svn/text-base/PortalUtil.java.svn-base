package com.landray.kmss.sys.portal.util;

import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.portal.forms.SysPortalMainForm;
import com.landray.kmss.sys.portal.model.*;
import com.landray.kmss.sys.portal.service.ISysPortalMainService;
import com.landray.kmss.sys.portal.service.ISysPortalPageService;
import com.landray.kmss.sys.portal.service.ISysPortalPersonDefaultService;
import com.landray.kmss.sys.portal.xml.model.SysPortalFooter;
import com.landray.kmss.sys.portal.xml.model.SysPortalHeader;
import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.util.SysUiConfigUtil;
import com.landray.kmss.sys.ui.xml.model.SysUiPortlet;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.util.comparator.ChinesePinyinComparator;
import com.landray.kmss.web.Globals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PortalUtil {
	private static final Logger logger = LoggerFactory.getLogger(PortalUtil.class);

	/**
	 * XML配置信息存储
	 */
	private static Map<String, SysPortalHeader> headers = new HashMap<String, SysPortalHeader>();
	private static Map<String, SysPortalFooter> footers = new HashMap<String, SysPortalFooter>();

	public static Map<String, SysPortalHeader> getPortalHeaders() {
		return headers;
	}

	public static List getPortalHeaders(HttpServletRequest request) {
		List<SysPortalHeader> list = new ArrayList<SysPortalHeader>();
		if (StringUtil.isNull(request.getParameter("scene"))) {
			list = new ArrayList<SysPortalHeader>(headers.values());
		} else {
			String[] scene = request.getParameter("scene").trim().split(";");
			Iterator<SysPortalHeader> iter = headers.values().iterator();
			while (iter.hasNext()) {
				SysPortalHeader h = iter.next();
				if (StringUtil.isNull(h.getFdFor())) {
					list.add(h);
				} else {
					for (int i = 0; i < scene.length; i++) {
						if ((";" + h.getFdFor() + ";").indexOf(";" + scene[i] + ";") >= 0) {
							list.add(h);
						}
					}
				}
			}
		}
		return list;
	}

	public static List getPortalFooters(HttpServletRequest request) {
		List<SysPortalFooter> list = new ArrayList<SysPortalFooter>();
		if (StringUtil.isNull(request.getParameter("scene"))) {
			list = new ArrayList<SysPortalFooter>(footers.values());
		} else {
			String[] scene = request.getParameter("scene").trim().split(";");
			Iterator<SysPortalFooter> iter = footers.values().iterator();
			while (iter.hasNext()) {
				SysPortalFooter f = iter.next();
				if (StringUtil.isNull(f.getFdFor())) {
					list.add(f);
				} else {
					for (int i = 0; i < scene.length; i++) {
						if ((";" + f.getFdFor() + ";").indexOf(";" + scene[i] + ";") >= 0) {
							list.add(f);
						}
					}
				}
			}
		}
		return list;
	}

	public static Map<String, SysPortalFooter> getPortalFooters() {
		return footers;
	}

	private static final String CURRENT_PORTAL = "landray_current_portal_info";

	private static final String CURRENT_PAGE = "landray_current_page_id";

	public static String getPortalPageId(HttpServletRequest request)
			throws Exception {
		if (request.getSession().getAttribute(CURRENT_PAGE) != null) {
			return request.getSession().getAttribute(CURRENT_PAGE).toString();
		}
		return null;
	}

	/**
	 * 获取门户信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static SysPortalInfo getPortalInfo(HttpServletRequest request) throws Exception {
		SysPortalInfo info = null;
		if (request.getAttribute("sys_portal_page_preview") != null) {
			// 该代码用于页面预览
			return (SysPortalInfo) request.getAttribute("sys_portal_page_preview");
		}

		if (request.getSession().getAttribute(CURRENT_PORTAL) != null) {
			String portalPageId = request.getSession().getAttribute(CURRENT_PORTAL).toString();
			info = getPageCache(portalPageId, null);
			if (info == null) {
				info = getPortalInfoByPageId(request, portalPageId);
			}
		} else {
			// 用户登录后不访问门户而是直接进入2级页面，取能访问的第一个门户
			info = getDefaultPortalInfo(request);
			request.getSession().setAttribute(CURRENT_PORTAL,info.getPortalPageId());
		}

		if (info == null) {
			throw new Exception("获取门户出错");
		}
		if (!"2".equals(info.getPageType())) {
			putPageCache(info.getPortalPageId(), info);
		}
		return info;
	}

	private static void GeneratePortalInfo(SysPortalMainPage page,
			SysPortalInfo info) {
		info.setPortalId(page.getSysPortalMain().getFdId());
		info.setPortalName(page.getSysPortalMain().getFdName());
		info.setPortalLang(page.getSysPortalMain().getFdLang());
		info.setPortalIsQuick(page.getSysPortalMain().getFdIsQuick());
		info.setPortalPageId(page.getFdId());
		info.setPortalPageName(page.getFdName());

		info.setPortalLogo(page.getSysPortalMain().getFdLogo());
		info.setPortalTheme(page.getSysPortalMain().getFdTheme());
		info.setPortalHeaderId(page.getSysPortalMain().getFdHeaderId());
		info.setPortalHeaderVars(page.getSysPortalMain().getFdHeaderVars());
		info.setPortalFooterId(page.getSysPortalMain().getFdFooterId());
		info.setPortalFooterVars(page.getSysPortalMain().getFdFooterVars());
	}
	private static void GeneratePortalInfo(SysPortalMainPage page,
			SysPortalInfo info,String lang) {
		GeneratePortalInfo(page, info);
		if(StringUtil.isNotNull(lang)){
			info.setPortalLang(lang);
		}
	}

	/**
	 * 根据门户ID获取
	 * 
	 * @param portalId
	 * @return
	 * @throws Exception
	 */
	public static SysPortalInfo getPortalInfoById(HttpServletRequest request,
			String portalId) throws Exception {
		String cacheId = (String) request.getSession().getAttribute(
				"kmss_user_portal_info_portalid_" + portalId);
		if (StringUtil.isNotNull(cacheId) && getPageCache(cacheId, null) != null) {
			return getPageCache(cacheId, null);
		} else {
			SysPortalInfo info = new SysPortalInfo();
			ISysPortalMainService service = (ISysPortalMainService) SpringBeanUtil
					.getBean("sysPortalMainService");
			SysPortalMainPage page = service.getPortalInfoById(portalId);
			GeneratePortalInfo(page, info);
			getSysPortalPageInfo(info, page.getSysPortalPage().getFdId());
			request.getSession().setAttribute(
					"kmss_user_portal_info_portalid_" + portalId + "",
					info.getPortalPageId());
			putPageCache(info.getPortalPageId(), info);
			return info;
		}
	}

	/**
	 * 根据门户中某个页面获取，portalPageId是门户与页面中间表的fdId
	 * 
	 * @param portalPageId
	 * @return
	 * @throws Exception
	 */
	public static SysPortalInfo getPortalInfoByPageId(
			HttpServletRequest request, String portalPageId) throws Exception {
		SysPortalInfo info = getPageCache(portalPageId, null);
		if (info == null) {
			info = new SysPortalInfo();
			ISysPortalMainService service = (ISysPortalMainService) SpringBeanUtil
					.getBean("sysPortalMainService");
			SysPortalMainPage page = service.getPortalPageById(portalPageId);
			GeneratePortalInfo(page, info);
			getSysPortalPageInfo(info, page.getSysPortalPage().getFdId());
			putPageCache(info.getPortalPageId(), info);
		}
		return info;
	}

	/**
	 * 获取默认的门户信息，用户登录后的默认门户获取
	 * 
	 * @return
	 * @throws Exception
	 */
	private static SysPortalInfo getDefaultPortalInfo(HttpServletRequest request) throws Exception {
		KMSSUser user = UserUtil.getKMSSUser(request);
		
		// 当前访问的场所ID
		String authAreaId = null;
		
		// 是否启用了集团分级 
		boolean isAreaEnabled = ISysAuthConstant.IS_AREA_ENABLED;  
		// 是否开启 用户漫游到其它场所的同时切换到该场所下的门户[“启用集团分级授权”并且开启 “用户漫游到其它场所的同时切换到该场所下的门户”开关] (门户引擎》门户维护》门户参数)
		boolean isRoamSwitchPortal = isAreaEnabled && SysUiConfigUtil.getIsRoamSwitchPortal();
		// 是否登录到用户设置的默认场所下的门户 (门户引擎》门户维护》门户参数)
		boolean isLoginDefaultAreaPortal = isAreaEnabled && SysUiConfigUtil.getIsLoginDefaultAreaPortal(); 
		
		// 从登录用户session对象中获取用户当前访问的场所
		if( isRoamSwitchPortal || isLoginDefaultAreaPortal ){
			authAreaId = user.getAuthAreaId();
		}
		
		// 如果“用户漫游到其它场所的同时切换到该场所下的门户”开关是关闭状态，且用户进行了切换场所的动作，此时不按场所ID进行门户查询（只有在开关开启状态下切换场所才按照场所ID进行门户查询）
		if( (!isRoamSwitchPortal && user.isCurrentlyInAuthArea()) || (isRoamSwitchPortal&&!user.isCurrentlyInAuthArea()&&!isLoginDefaultAreaPortal) ){
			authAreaId = null;
		}

		// 用户登录时的默认门户 session key
		String user_default_portal_session_key = "kmss_user_default_portal_info";  
		// 用户当前访问的门户 session key（与登录时的默认门户 session key区别在于：如果用户当前是通过切换场所访问门户，则key后面会带上场所ID）
		String user_current_portal_session_key = user_default_portal_session_key; 
		if( isRoamSwitchPortal && user.isCurrentlyInAuthArea() && StringUtil.isNotNull(authAreaId)){
			user_current_portal_session_key+="_"+authAreaId;
		}		
		
		String cacheId = (String) request.getSession().getAttribute(user_current_portal_session_key);
		String lang = getLang(request);
		if (StringUtil.isNotNull(cacheId) && getPageCache(cacheId, lang) != null) {
			return getPageCache(cacheId, lang);
		} else {
			ISysPortalMainService service = (ISysPortalMainService) SpringBeanUtil.getBean("sysPortalMainService");
			SysPortalMainPage page = user.isAnonymous() ? service.getDefaultAnonymousPortalPage(lang, authAreaId): service.getDefaultPortalPage(lang,authAreaId);
			// 当用户切换场所并且未查找到可访问的门户时，返回用户登录时的默认门户(注：切换场所时会将isCurrentlyInAuthArea标识设置为true)
			if(isRoamSwitchPortal && user.isCurrentlyInAuthArea() && StringUtil.isNotNull(authAreaId) && page==null ){
				cacheId = (String) request.getSession().getAttribute(user_default_portal_session_key);
				return getPageCache(cacheId, lang);
			}else{
				if(StringUtil.isNotNull(authAreaId) && page==null){
					page = user.isAnonymous() ? service.getDefaultAnonymousPortalPage(lang,null) : service.getDefaultPortalPage(lang,null);
				}
				SysPortalInfo info = new SysPortalInfo();
				GeneratePortalInfo(page, info,lang);
				getSysPortalPageInfo(info, page.getSysPortalPage().getFdId());
				request.getSession().setAttribute(user_current_portal_session_key,info.getPortalPageId());
				putPageCache(info.getPortalPageId(), info);
				return info;
			}
		}
	}

	public static String getPortalMode(HttpServletRequest request) throws Exception {
		SysPortalInfo info = null;
		String pageId = (String) request.getSession().getAttribute(CURRENT_PAGE);
		try {
			if (StringUtil.isNotNull(pageId)) {
				// 根据门户页面中间表的Id获取
				info = getPortalInfoByPageId(request, pageId);
			} else {
				// 获取当前用户默认门户
				info = getDefaultPortalInfo(request);
			}
			if (info != null && info.getPortalIsQuick() != null) {
				return info.getPortalIsQuick() ? "quick" : "default";
			}
		} catch (Exception e) {
			logger.warn("获取门户是否急速模式出现错误，返回默认模式（default）.");
			return "default";
		}
		return "default";
	}

	/**
	 * 判断是否为T形模板
	 * 
	 * @return boolean
	 */
	public static boolean isTTemplate(HttpServletRequest request) {
		try {
			String pageId = (String) request.getSession()
					.getAttribute(CURRENT_PAGE);
			ISysPortalPageService service = (ISysPortalPageService) SpringBeanUtil
					.getBean("sysPortalPageService");
			SysPortalInfo info = getPortalInfoByPageId(request, pageId);
			SysPortalPage page = (SysPortalPage) service
					.findByPrimaryKey(info.getPageId());
			List<SysPortalPageDetail> pageDetails = page.getPageDetails();
			String docContent = pageDetails.get(0).getDocContent();
			if (docContent.contains("person.home")
					|| docContent.contains("template.t")) {
				return true;
			}
		} catch (Exception e) {
			logger.warn("判断是否为门户特定T型模板错误，返回非T型模板.");
		}
		return false;
	}
	
	private static SysPortalInfo getPortalPersonDefault(HttpServletRequest request) throws Exception {
		SysPortalInfo info = new SysPortalInfo();
		ISysPortalMainService service = (ISysPortalMainService) SpringBeanUtil.getBean("sysPortalMainService");
		ISysPortalPersonDefaultService sysPortalPersonDefaultService = (ISysPortalPersonDefaultService) SpringBeanUtil
				.getBean("sysPortalPersonDefaultService");
		SysPortalPersonDefault fdPersonDefaultPortal = sysPortalPersonDefaultService.getPersonDefaultPortal();
		if (fdPersonDefaultPortal != null && StringUtil.isNotNull(fdPersonDefaultPortal.getFdPortalId())) {
			String fdPersonDefaultPortalId = fdPersonDefaultPortal.getFdPortalId();
			// String fdPersonDefaultPortalId =
			// "16bdb795af83bce8a2da02c4b1cab680";
				SysPortalMainPage page = service.getPortalInfoById(fdPersonDefaultPortalId);
				GeneratePortalInfo(page, info);
				getSysPortalPageInfo(info, page.getSysPortalPage().getFdId());
				request.getSession().setAttribute("kmss_user_portal_info_portalid_" + fdPersonDefaultPortalId + "",
						info.getPortalPageId());
				putPageCache(info.getPortalPageId(), info);
					return info;
		}
		return null;
	}

	public static SysPortalInfo viewDefaultPortalInfo(HttpServletRequest request)
			throws Exception {
		SysPortalInfo info = null;
		String portalId = request.getParameter("portalId");
		String pageId = request.getParameter("pageId");
		String isModule = request.getParameter("j_module");
		String mainPageId = request.getParameter("mainPageId");
		if (StringUtil.isNotNull(pageId)) {
			// 根据门户页面中间表的Id获取
			info = getPortalInfoByPageId(request, pageId);
		} else if (StringUtil.isNotNull(portalId)) {
			// 获取某个指定门户，根据门户ID获取
			info = getPortalInfoById(request, portalId);
		} else if ("true".equals(isModule) || StringUtil.isNotNull(mainPageId)) {
			// 通过.index访问模块,从session中尝试获取portalId
			info = getPortalInfo(request);
		} else {
			// 获取用户自己设置的默认门户
			info = getPortalPersonDefault(request);
			if (info == null) {
				// 获取当前用户默认门户
				info = getDefaultPortalInfo(request);
			}			
		}
		if (info == null) {
			throw new Exception("获取门户出错");
		}
		request.getSession().setAttribute(CURRENT_PAGE, info.getPortalPageId());
		request.getSession().setAttribute(CURRENT_PORTAL, info.getPortalPageId());
		if (!"2".equals(info.getPageType())) {
			putPageCache(info.getPortalPageId(), info);
		}
		return info;
	}

	public static void getSysPortalPageInfo(SysPortalInfo info, String fdId)
			throws Exception {
		// 根据制定id获取
		ISysPortalPageService service = (ISysPortalPageService) SpringBeanUtil
				.getBean("sysPortalPageService");
		SysPortalPage page = (SysPortalPage) service.findByPrimaryKey(fdId);
		info.setPageId(page.getFdId());
		info.setUsePortal(page.getFdUsePortal());

		info.setPageMd5(page.getPageDetails().get(0).getFdMd5());
		info.setPageFooterId(page.getPageDetails().get(0).getFdFooter());
		info.setPageFooterVars(page.getPageDetails().get(0).getFdFooterVars());
		info.setPageHeaderId(page.getPageDetails().get(0).getFdHeader());
		info.setPageHeaderVars(page.getPageDetails().get(0).getFdHeaderVars());
		info.setPageLogo(page.getPageDetails().get(0).getFdLogo());
		info.setPageGuideId(page.getPageDetails().get(0).getFdGuide());
		info.setPageGuideCfg(page.getPageDetails().get(0).getFdGuideCfg());

		info.setPageType(page.getFdType());
		info.setPageUrl(page.getFdUrl());
		info.setPageName(page.getFdName());
		info.setPageTitle(page.getFdTitle());
		info.setPageIcon(page.getFdIcon());
		info.setPageTheme(page.getFdTheme());
	}
	
	public static void setSysPortalPageLang(HttpServletRequest request,String portalId)
			throws Exception {
		if(SysLangUtil.isLangEnabled()){
			//设置默认语言
			ISysPortalMainService service = (ISysPortalMainService) SpringBeanUtil
					.getBean("sysPortalMainService");
			if(null!=service.findByPrimaryKey(portalId)){
				SysPortalMain sysPortalMain = (SysPortalMain) service.findByPrimaryKey(portalId);
				if(null!=sysPortalMain.getFdLang()&&!"".equals(sysPortalMain.getFdLang())){
					HttpSession session = request.getSession();
					KMSSUser user = UserUtil.getKMSSUser((HttpServletRequest) request);
					Locale newLocale = ResourceUtil.getLocale(sysPortalMain.getFdLang());
					if (newLocale != null) {
						user.setLocale(newLocale);
						session.setAttribute(Globals.LOCALE_KEY, newLocale);
					} else {
						session.setAttribute(Globals.LOCALE_KEY, user.getLocale());
					}
				}
			}
		}
	}

	public static String getPortalPageJspPath(SysPortalInfo info)
			throws Exception {
		ISysPortalPageService service = (ISysPortalPageService) SpringBeanUtil
				.getBean("sysPortalPageService");
		if (service.existPageFile(info.getPageId(), info.getPageMd5())) {
			return service.pageJspPath(info.getPageId(), info.getPageMd5());
		} else {
			SysPortalPage page = (SysPortalPage) service.findByPrimaryKey(info
					.getPageId());
			String path = service.createFile(page.getPageDetails().get(0));
			Thread.sleep(5000);
			return path;
		}
	}
	
	/**
	 * 匿名门户路径
	 * @author 吴进 by 20191202
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public static String getPortalPageJspPathAnonym(SysPortalInfo info) throws Exception {
		ISysPortalPageService service = (ISysPortalPageService) SpringBeanUtil.getBean("sysPortalPageService");
		if (service.existPageFileAnonym(info.getPageId(), info.getPageMd5())) {
			return service.pageJspPathAnonym(info.getPageId(), info.getPageMd5());
		} else {
			SysPortalPage page = (SysPortalPage) service.findByPrimaryKey(info.getPageId());
			String path = service.createFileAnonym(page.getPageDetails().get(0));
			Thread.sleep(5000);
			return path;
		}
	}

	/**
	 * 获取XML配置所有Portlet的模块名称，排序除重
	 * 
	 * @return
	 */
	public static List<Map<String, String>> getPortalModules() {
		List<Map<String, String>> modules = new ArrayList<Map<String, String>>();
		Collection<SysUiPortlet> portlets = SysUiPluginUtil.getPortlets()
				.values();
		List<String> temp = new ArrayList<String>();
		Iterator<SysUiPortlet> it = portlets.iterator();
		while (it.hasNext()) {
			SysUiPortlet x = it.next();
			final String key = x.getFdModule();
			if (!temp.contains(key)) {
				temp.add(key);
				final String mkey = ResourceUtil.getMessage(key);
				modules.add(new HashMap() {
					{
						put("id", key);
						put("name", mkey);
					}
				});
			}
		}
		Collections.sort(modules, new Comparator<Map<String, String>>() {
			@Override
			public int compare(Map<String, String> a, Map<String, String> b) {
				return ChinesePinyinComparator.compare(a.get("name"), b
						.get("name"));
			}
		});
		return modules;
	}

	private static SysPortalInfo getPageCache(String key, String lang) {
		KmssCache cacheInfo = new KmssCache(PortalUtil.class);
		SysPortalInfo sysPortalInfo = (SysPortalInfo) cacheInfo.get(key);
		
		// 判断是否有切换语言
		if (StringUtil.isNotNull(lang) && sysPortalInfo != null) {
			// 如果有切换语言，需要重新获取数据库中的门户
			if (!lang.equals(sysPortalInfo.getPortalLang())) {
				sysPortalInfo = null;
			}
		}
		return sysPortalInfo;
	}

	private static void putPageCache(String key, SysPortalInfo pageInfo) {
		KmssCache cacheInfo = new KmssCache(PortalUtil.class);
		if (cacheInfo.get(key) == null) {
			cacheInfo.put(key, pageInfo);
		}
	}
	
	public static void clearPageCache(){
		KmssCache cacheInfo = new KmssCache(PortalUtil.class);
		cacheInfo.clear();
	}
	
	public static String getLang(HttpServletRequest request) {
		String lang = null;
		String langStr = ResourceUtil.getKmssConfigString("kmss.lang.support");
		// 只有开启多语言才会获取语言进行操作
		if (StringUtil.isNotNull(langStr)) {
			lang = request.getParameter("j_lang");
			if (StringUtil.isNull(lang)) {
				Object obj = request.getSession()
						.getAttribute(Globals.LOCALE_KEY);
				if (obj != null) {
                    lang = ((Locale) obj).toLanguageTag().replaceAll("_", "-");
                }
			}
		}
		return lang;
	}
	
	/**
	 * 为门户生成一个多语言下拉框，语种从admin.do中获取，外加“不限”
	 * @param request
	 * @param propertyName
	 * @return
	 */
	public static String getLangHtml(HttpServletRequest request, String propertyName) {
		StringBuffer sb = new StringBuffer();
		SysPortalMainForm form = (SysPortalMainForm) request.getAttribute("sysPortalMainForm");
		String value = form.getFdLang();

		sb.append("<select name=\"").append(StringUtil.escape(propertyName)).append("\" ").append(">");
		sb.append("<option value");
		if (StringUtil.isNull(value)) {
			sb.append(" selected");
		}
		sb.append(">" + ResourceUtil.getString(request.getSession(), "portlet.var.date.unlimited") + "</option>");

		String langStr = ResourceUtil.getKmssConfigString("kmss.lang.support");
		if (StringUtil.isNotNull(langStr)) {
			String[] langArr = langStr.trim().split(";");
			for (int i = 0; i < langArr.length; i++) {
				String[] langInfo = langArr[i].split("\\|");
				sb.append("<option value=\"").append(langInfo[1]).append("\"");
				if (langInfo[1].equals(value)) {
					sb.append(" selected");
				}
				sb.append(">").append(langInfo[0]).append("</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	public static String getPortalNoticeUrl() {
		return "/sys/portal/sys_portal_notice/import/sysPortalNotice_view.jsp";
	}

	/**
	 * 获取系统支持的语言
	 */
	public static Map getSupportLang() {
		Map<String, String> map = new HashMap<String, String>();
		if (!SysLangUtil.isLangEnabled()) {
			return map;
		}
		List list = SysLangUtil.getSupportedLangList();
		Map<String, String> m = SysLangUtil.getSupportedLangs();
		for (String key : m.keySet()) {
			String value = m.get(key);
			for (int i = 0; i < list.size(); i++) {
				String lang = (String) list.get(i);
				if (lang.indexOf(key) > -1) {
					map.put(lang, value);
					break;
				}
			}
		}
		return map;
	}

	public static String formatUrl(HttpServletRequest request, String url) {
		// bad hack
		String[] hackParameters = new String[] { "j_aside", "j_iframe", "j_rIframe" };
		for (String parameter : hackParameters) {
			String parameterValue = request.getParameter(parameter);
			if (StringUtil.isNotNull(parameterValue)) {
				url = setUrlParameter(url, parameter, parameterValue);
			}
		}
		return url;
	}

	private static String setUrlParameter(String url, String param, String value) {
		String pattern = "([\\?&]" + param + "=)[^&]*";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(url);
		if (m.find()) {
			url = url.replace(pattern, "$1" + value);
		} else {
			url += (url.indexOf("?") == -1 ? "?" : "&") + param + "=" + value;
		}
		return url;
	}
	
	/**************** 匿名门户 Start ************************************************************/
	/**
	 * 匿名门户
	 * @author 吴进 by 20191115
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static SysPortalInfo viewAnonymousPortalInfo(HttpServletRequest request) throws Exception {
		SysPortalInfo info = null;
		String portalId = request.getParameter("portalId");
		String pageId = request.getParameter("pageId");
		if (StringUtil.isNotNull(pageId)) {
			// 根据门户页面中间表的Id获取
			info = getAnonymousPortalInfoByPageId(request, pageId);
		} else if (StringUtil.isNotNull(portalId)) {
			// 获取某个指定匿名门户，根据门户ID获取
			info = getAnonymousPortalInfoById(request, portalId);
		} else {
			// todo 获取当前用户默认匿名门户，没有匿名门户就拿匿名页面
			info = getAnonymousPortalInfo(request);
		}
		
		if (info == null) {
			// 没有匿名门户要跳到登录首页
			info = new SysPortalInfo();
			info.setPageType("2");
			info.setPageUrl(request.getContextPath().toString() + "/login.jsp");
		}
		request.getSession().setAttribute(CURRENT_PAGE, info.getPortalPageId());
		request.getSession().setAttribute(CURRENT_PORTAL, info.getPortalPageId());
		request.getSession().removeAttribute("j_redirectto");
		if (!"2".equals(info.getPageType())) {
			putPageCache(info.getPortalPageId(), info);
		}
		return info;
	}
	
	/**
	 * 获取默认的匿名门户信息
	 * @author 吴进 by 20191115
	 * @return
	 * @throws Exception
	 */
	private static SysPortalInfo getAnonymousPortalInfo(HttpServletRequest request) throws Exception {
		// 匿名门户 session key
		String user_anonymous_portal_session_key = "kmss_user_anonymous_portal_info";  
		
		String cacheId = (String) request.getSession().getAttribute(user_anonymous_portal_session_key);
		String lang = getLang(request);
		if (StringUtil.isNotNull(cacheId) && getPageCache(cacheId, lang) != null) {
			return getPageCache(cacheId, lang);
		} else {
			ISysPortalMainService service = (ISysPortalMainService) SpringBeanUtil.getBean("sysPortalMainService");
			SysPortalMainPage page = service.getAnonymousPortalPage(lang);
			if (page == null) {
				cacheId = (String) request.getSession().getAttribute(user_anonymous_portal_session_key);
				return getPageCache(cacheId, lang);
			} else {
				SysPortalInfo info = new SysPortalInfo();
				GeneratePortalInfo(page, info, lang);
				getSysPortalPageInfo(info, page.getSysPortalPage().getFdId());
				request.getSession().setAttribute(user_anonymous_portal_session_key, info.getPortalPageId());
				putPageCache(info.getPortalPageId(), info);
				return info;
			}
		}
	}
	
	/**
	 * 根据匿名门户ID获取
	 * 
	 * @param portalId
	 * @return
	 * @throws Exception
	 */
	private static SysPortalInfo getAnonymousPortalInfoById(HttpServletRequest request, String portalId) throws Exception {
		String cacheId = (String) request.getSession().getAttribute("kmss_anonymous_portal_info_portalid_" + portalId);
		if (StringUtil.isNotNull(cacheId) && getPageCache(cacheId, null) != null) {
			return getPageCache(cacheId, null);
		} else {
			SysPortalInfo info = new SysPortalInfo();
			ISysPortalMainService service = (ISysPortalMainService) SpringBeanUtil.getBean("sysPortalMainService");
			SysPortalMainPage page = service.getAnonymousPortalPageById(portalId);
			GeneratePortalInfo(page, info);
			getSysPortalPageInfo(info, page.getSysPortalPage().getFdId());
			request.getSession().setAttribute(
					"kmss_anonymous_portal_info_portalid_" + portalId + "",
					info.getPortalPageId());
			putPageCache(info.getPortalPageId(), info);
			return info;
		}
	}
	
	/**
	 * 根据匿名门户中某个页面获取，portalPageId是门户与页面中间表的fdId
	 * 
	 * @param portalPageId
	 * @return
	 * @throws Exception
	 */
	private static SysPortalInfo getAnonymousPortalInfoByPageId(HttpServletRequest request, String portalPageId) throws Exception {
		SysPortalInfo info = getPageCache(portalPageId, null);
		if (info == null) {
			info = new SysPortalInfo();
			ISysPortalMainService service = (ISysPortalMainService) SpringBeanUtil.getBean("sysPortalMainService");
			SysPortalMainPage page = service.getAnonymousPortalPageByPageId(portalPageId);
			GeneratePortalInfo(page, info);
			getSysPortalPageInfo(info, page.getSysPortalPage().getFdId());
			putPageCache(info.getPortalPageId(), info);
		}
		return info;
	}
	
	/**************** 匿名门户 End ************************************************************/

	/**
	 * 自定义-转义html字符方法
	 * org.apache.commons.lang.StringEscapeUtils.escapeHtml 方法转义生僻字会造成乱码的情况 add by zhouwen 20220209
	 * @param source
	 * @return
	 */
	public static String htmlEncode2Portal(String source) {
		if (source == null) {
			return "";
		}
		String html = "";
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < source.length(); i++) {
			char c = source.charAt(i);
			switch (c) {
				case '<':
					buffer.append("&lt;");
					break;
				case '>':
					buffer.append("&gt;");
					break;
				case '&':
					buffer.append("&amp;");
					break;
				case '"':
					buffer.append("&quot;");
					break;
				case 10:
				case 13:
					break;
				default:
					buffer.append(c);
			}
		}
		html = buffer.toString();
		return html;
	}
}
