package com.landray.kmss.sys.mportal.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.Globals;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.mportal.model.SysMportalPage;
import com.landray.kmss.sys.mportal.service.ISysMportalPageService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;

public class SysMportalViewUtil {

	/**
	 * TODO 缓存
	 */
	public static String viewCommonDefault(HttpServletRequest request) throws Exception {
		String url = viewCommonDefaultByLang(request);
		if (StringUtil.isNotNull(url)) {
			return url;
		}
		ISysMportalPageService service = (ISysMportalPageService) SpringBeanUtil
				.getBean("sysMportalPageService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(1);
		hqlInfo.setWhereBlock("sysMportalPage.fdEnabled = :fdEnabled");
		hqlInfo.setParameter("fdEnabled", true);
		hqlInfo.setOrderBy("fdOrder asc, docCreateTime desc");
		Page page = service.findPage(hqlInfo);
		if (!ArrayUtil.isEmpty(page.getList())) {
			SysMportalPage defaultPage = (SysMportalPage) page.getList().get(0);
			url = request.getContextPath()
					+ "/sys/mportal/sys_mportal_page/sysMportalPage.do?method=view&fdId="
					+ defaultPage.getFdId();
			url = StringUtil.setQueryParameter(url, "fdName", defaultPage.getFdName());
		}
		return url;
	}

	public static String viewCommonDefaultByLang(HttpServletRequest request) throws Exception {
		String url = null;
		String lang = getLang(request);
		if (StringUtil.isNotNull(lang)) {
			ISysMportalPageService service = (ISysMportalPageService) SpringBeanUtil.getBean("sysMportalPageService");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setPageNo(1);
			hqlInfo.setRowSize(1);
			hqlInfo.setWhereBlock("sysMportalPage.fdEnabled = :fdEnabled and sysMportalPage.fdLang like :fdLang");
			hqlInfo.setParameter("fdEnabled", true);
			hqlInfo.setParameter("fdLang", lang + "%");
			hqlInfo.setOrderBy("fdOrder asc, docCreateTime desc");
			Page page = service.findPage(hqlInfo);
			if (!ArrayUtil.isEmpty(page.getList())) {
				SysMportalPage defaultPage = (SysMportalPage) page.getList().get(0);
				url = request.getContextPath() + "/sys/mportal/sys_mportal_page/sysMportalPage.do?method=view&fdId="
						+ defaultPage.getFdId();
				url = StringUtil.setQueryParameter(url, "fdName", defaultPage.getFdName());
				return url;
			}
		}
		return url;
	}

	public static String viewDefault(HttpServletRequest request) throws Exception {
		String url = viewCommonDefault(request);
		return url;
	}

	public static String getLang(HttpServletRequest request) {
		String lang = null;
		String langStr = ResourceUtil.getKmssConfigString("kmss.lang.support");
		// 只有开启多语言才会获取语言进行操作
		if (StringUtil.isNotNull(langStr)) {
			lang = request.getParameter("j_lang");
			if (StringUtil.isNull(lang)) {
				Object obj = request.getSession().getAttribute(Globals.LOCALE_KEY);
				if (obj != null) {
                    lang = ((Locale) obj).toLanguageTag().replaceAll("_", "-");
                }
			}
		}
		return lang;
	}
}
