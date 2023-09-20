package com.landray.kmss.sys.portal.actions;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.portal.model.SysPortalMain;
import com.landray.kmss.sys.portal.model.SysPortalMainPage;
import com.landray.kmss.sys.portal.service.ISysPortalMainService;
import com.landray.kmss.sys.portal.service.ISysPortalPortletService;
import com.landray.kmss.sys.portal.util.EmotionUtil;
import com.landray.kmss.sys.portal.util.PortalUtil;
import com.landray.kmss.sys.portal.util.SysPortalInfo;
import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.util.SysUiConfigUtil;
import com.landray.kmss.sys.ui.xml.model.SysUiFormat;
import com.landray.kmss.sys.ui.xml.model.SysUiLayout;
import com.landray.kmss.sys.ui.xml.model.SysUiOperation;
import com.landray.kmss.sys.ui.xml.model.SysUiPortlet;
import com.landray.kmss.sys.ui.xml.model.SysUiRender;
import com.landray.kmss.sys.ui.xml.model.SysUiTemplate;
import com.landray.kmss.sys.ui.xml.model.SysUiTheme;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.MD5Util;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import edu.emory.mathcs.backport.java.util.Collections;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 系统部件 Action
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public class SysPortalPortletAction extends ExtendAction {
	protected ISysPortalPortletService sysPortalPortletService;

	@Override
	protected ISysPortalPortletService getServiceImp(HttpServletRequest request) {
		if (sysPortalPortletService == null) {
            sysPortalPortletService = (ISysPortalPortletService) getBean("sysPortalPortletService");
        }
		return sysPortalPortletService;
	}

	protected ISysPortalMainService sysPortalMainService;

	protected ISysPortalMainService getSysPortalMainService() {
		if (sysPortalMainService == null) {
            sysPortalMainService = (ISysPortalMainService) getBean("sysPortalMainService");
        }
		return sysPortalMainService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		String whereBlock="";
		String[] __modules = request.getParameterValues("q.__module");
		if (__modules != null) {
			StringBuffer modelNames = new StringBuffer();
			for (String __module : __modules) {
				modelNames.append(__module + ",");
			}
			String modelName = modelNames.substring(0, modelNames.length() - 1);
			hqlInfo.setModelName(modelName);
		}
		String[] __formates = request.getParameterValues("q.__formate");
		if (__formates != null) {
			StringBuffer formates = new StringBuffer();
			for (String formate : __formates) {
				formates.append(formate + ",");
			}
			String fdformates = formates.substring(0, formates.length() - 1);
			whereBlock+= fdformates+"//";
			
		}
		String __keyword = request.getParameter("q.__keyword");
		String __scene = request.getParameter("scene");
		String __fdAnonymous = request.getParameter("q.fdAnonymous");
		if (StringUtil.isNotNull(__fdAnonymous)) {
			hqlInfo.setKey(__fdAnonymous);
		}
		if (StringUtil.isNotNull(__scene)) {
			whereBlock+=__scene;
		}
		hqlInfo.setWhereBlock(whereBlock);
		if (StringUtil.isNotNull(__keyword)) {
			hqlInfo.setSelectBlock(__keyword);
		}
	}

	private JSONArray getPortalChildren(String fdId, HttpServletRequest request)
			throws Exception {
		String pageId = PortalUtil.getPortalInfo(request).getPortalPageId();
		JSONArray ret = new JSONArray();
		String where = "";
		HQLInfo hqlInfo = new HQLInfo();
		if (StringUtil.isNull(fdId)) {
			where += (" sysPortalMain.fdParent = null and sysPortalMain.fdEnabled = :fdEnabled ");
		} else {
			where += (" sysPortalMain.fdParent.fdId = '" + fdId + "' and sysPortalMain.fdEnabled = :fdEnabled ");
		}
		hqlInfo.setParameter("fdEnabled", Boolean.TRUE);
		boolean isAreaEnabled = ISysAuthConstant.IS_AREA_ENABLED;  // 是否启用了集团分级
		if(isAreaEnabled){
			where += (" and sysPortalMain.authArea is not null and sysPortalMain.authArea.fdIsAvailable = :fdIsAvailable ");
			hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		}
		hqlInfo.setWhereBlock(where);
		hqlInfo.setOrderBy("fdOrder,fdId");
		hqlInfo.setGetCount(false);
		List list = getSysPortalMainService().findList(hqlInfo);
		for (int i = 0; i < list.size(); i++) {
			SysPortalMain main = (SysPortalMain) list.get(i);
			if(isAreaEnabled && SysUiConfigUtil.getIsLoginDefaultAreaPortal() && ISysAuthConstant.IS_ISOLATION_ENABLED && !SysAuthAreaUtils.isAvailableModel(main, null)) {
				continue;
			}
			JSONObject m = new JSONObject();
			String xfdId = main.getFdId();
			m.put("fdId", xfdId);
			m.put("fdType", "portal");
			m.put("text", main.getFdName());
			String fdOrder = main.getFdOrder()==null ? "0" : main.getFdOrder().toString();
			m.put("fdOrder", fdOrder);
			m.put("icon", main.getFdIcon());
			m.put("target", main.getFdTarget());
			m.put("href", "/sys/portal/page.jsp?portalId=" + main.getFdId());
			JSONArray chs = getPortalChildren(xfdId, request);
			if (!chs.isEmpty() && chs.size() > 0) {
				m.put("children", chs);
				ret.add(m);
			}
		}
		if (StringUtil.isNotNull(fdId)) {
			List pages = getSysPortalMainService().getPortalPageList(fdId);
			for (int i = 0; i < pages.size(); i++) {
				SysPortalMainPage page = (SysPortalMainPage) pages.get(i);
				JSONObject m = new JSONObject();
				m.put("fdId", page.getFdId());
				m.put("text", page.getFdName());
				String fdOrder = page.getFdOrder()==null ? "0" : page.getFdOrder().toString();
				m.put("fdOrder", fdOrder);
				m.put("icon", page.getFdIcon());
				m.put("target", page.getFdTarget());
				m.put("selected", page.getFdId().equals(pageId));
				m.put("href", "/sys/portal/page.jsp?pageId=" + page.getFdId());
				ret.add(m);
			}
		}
		Collections.sort(ret, new Comparator<JSONObject>() {
			@Override
			public int compare(JSONObject a, JSONObject b) {
				boolean m = (Integer.parseInt(a.getString("fdOrder")) > Integer
						.parseInt(b.getString("fdOrder")));
				if (m) {
                    return 1;
                } else {
                    return -1;
                }
			}
		});
		return ret;
	}

	public ActionForward portalNavTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("lui-source", getPortalChildren(null, request));
		return getActionForward("lui-source", mapping, form, request, response);
	}

	public ActionForward pageNavTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String urlPortalId = request.getParameter("portalId");
		String urlPageId = request.getParameter("pageId");
		SysPortalInfo info = null;
		JSONArray ret = new JSONArray();
		//#109049 页面上直接有门户id时，直接获取
		if(StringUtil.isNotNull(urlPortalId)){
			info=PortalUtil.getPortalInfoById(request,urlPortalId);
			buildPageData(info,ret);
		}else if(StringUtil.isNotNull(urlPageId)){
			info = PortalUtil.getPortalInfoByPageId(request, urlPageId);
			buildPageData(info,ret);
		}else{
			info = PortalUtil.getPortalInfo(request);
			buildPageData(info,ret);
		}
		
		
		Collections.sort(ret, new Comparator<JSONObject>() {
			@Override
			public int compare(JSONObject a, JSONObject b) {
				boolean m = (Integer.parseInt(a.getString("fdOrder")) > Integer
						.parseInt(b.getString("fdOrder")));
				if (m) {
                    return 1;
                } else {
                    return -1;
                }
			}
		});
		request.setAttribute("lui-source", ret);
		return getActionForward("lui-source", mapping, form, request, response);
	}

	private void buildPageData(SysPortalInfo info, JSONArray ret) throws Exception {
		List pages = getSysPortalMainService().getPortalPageList(info.getPortalId());
		String pageId = info.getPortalPageId();
		String pageName = null;
		for (int i = 0; i < pages.size(); i++) {
			SysPortalMainPage page = (SysPortalMainPage) pages.get(i);
			pageName = page.getFdName();
			JSONObject m = new JSONObject();
			m.put("fdId", page.getFdId());
			m.put("text", StringUtil.isNotNull(pageName) ? pageName.trim() : pageName);
			String fdOrder = page.getFdOrder() == null ? "0"
					: page.getFdOrder().toString();
			m.put("fdOrder", fdOrder);
			m.put("icon", page.getFdIcon());
			m.put("target", page.getFdTarget());
			m.put("selected", page.getFdId().equals(pageId));
			m.put("href", "/sys/portal/page.jsp?pageId=" + page.getFdId());
			m.put("pageType", page.getSysPortalPage() != null ? page.getSysPortalPage().getFdType() : "1");
			ret.add(m);
		}
		
	}

	public ActionForward selectSource(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			rowsize = 8;
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("selectSource", mapping, form, request,
					response);
		}
	}

	public ActionForward selectRender(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String scene = request.getParameter("scene");
		String format = request.getParameter("format");
		String keywords = request.getParameter("keywords");
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			List<SysUiRender> list = SysUiPluginUtil.getRenderByFormat(scene,
					format);
			//关键字搜索
			if (StringUtil.isNotNull(keywords)) {
				List<SysUiRender> keyList = new ArrayList<SysUiRender>();
				for (int i = 0; i < list.size(); i++) {
					String fdName = list.get(i).getFdName();
					fdName = fdName.toLowerCase();
					if (fdName.indexOf(keywords.toLowerCase()) != -1) {
						keyList.add(list.get(i));
					}
				}
				list = keyList;
			}
			request.setAttribute("queryList", list);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("selectRender", mapping, form, request,
					response);
		}
	}

	public ActionForward selectTheme(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String keywords = request.getParameter("keywords");
		List<SysUiTheme> list = new ArrayList<SysUiTheme>(SysUiPluginUtil
				.getThemes().values());
		if (StringUtil.isNotNull(keywords)) {
			List<SysUiTheme> keyList = new ArrayList<SysUiTheme>();
			for (int i = 0; i < list.size(); i++) {
				String fdName = list.get(i).getFdName();
				if (fdName.indexOf(keywords) != -1) {
					keyList.add(list.get(i));
				}
			}
			list = keyList;
		}
		request.setAttribute("queryList", list);
		return getActionForward("selectTheme", mapping, form, request, response);
	}

	public ActionForward selectTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String keywords = request.getParameter("keywords");
		List<SysUiTemplate> listt = new ArrayList<SysUiTemplate>();
		List<SysUiTemplate> list = new ArrayList<SysUiTemplate>(SysUiPluginUtil
				.getTemplates().values());
		String scene = request.getParameter("scene");
		String[] scenes = null;
		if (StringUtil.isNotNull(scene)
				&& !"null".equalsIgnoreCase(scene.trim())) {
			scenes = scene.trim().split(";");
		}

		for (int i = 0; i < list.size(); i++) {
			SysUiTemplate template = list.get(i);
			boolean ok = false;
			if (scenes == null || StringUtil.isNull(template.getFdFor())) {
				ok = true;
			} else {
				for (int j = 0; j < scenes.length; j++) {
					String temp = (";" + template.getFdFor() + ";");
					if (temp.indexOf((";" + scenes[j] + ";")) >= 0) {
						ok = true;
						break;
					}
				}
			}
			if (ok) {
				listt.add(template);
			}
		}
		if (StringUtil.isNotNull(keywords)) {
			List<SysUiTemplate> keyList = new ArrayList<SysUiTemplate>();
			for(SysUiTemplate template:listt){
				String fdName = template.getFdName().toLowerCase();
				if (fdName.indexOf(keywords.toLowerCase()) != -1) {
					keyList.add(template);
				}
			}
			listt = keyList;
		}
		request.setAttribute("queryList", listt);
		return getActionForward("selectTemplate", mapping, form, request,
				response);
	}

	public ActionForward portletOperation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONArray json = new JSONArray();
		String sourceId = request.getParameter("sourceId");
		SysUiPortlet portlet = SysUiPluginUtil.getPortletById(sourceId
				.substring(0, sourceId.length() - ".source".length()));
		if(portlet!=null){
			List<SysUiOperation> opts = portlet.getFdOperations();
			if (opts != null && opts.size() > 0) {
				for (int i = 0; i < opts.size(); i++) {
					SysUiOperation operation = opts.get(i);
					JSONObject opt = new JSONObject();
					opt.put("name", ResourceUtil.getMessage(operation.getName()));
					opt.put("key", MD5Util.getMD5String(operation.getHref()));
					json.add(opt);
				}
			}
		}
		request.setAttribute("lui-source", json);
		return getActionForward("lui-source", mapping, form, request, response);
	}

	public ActionForward genHtml(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		String config = request.getParameter("config");
//		String jsp = getServiceImp(request).genHtml(request, config);
//		request.getRequestDispatcher(jsp).forward(request, response);
		return null;
	}

	public ActionForward panelLayout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String scene = request.getParameter("scene");
		String[] scenes = null;
		if (StringUtil.isNotNull(scene)
				&& !"null".equalsIgnoreCase(scene.trim())) {
			scenes = scene.trim().split(";");
		}
		JSONObject json = new JSONObject();
		List<SysUiLayout> list = new ArrayList<SysUiLayout>(SysUiPluginUtil
				.getLayouts().values());
		JSONArray tabpanels = new JSONArray();
		JSONArray panels = new JSONArray();
		JSONArray accordionpanels = new JSONArray();
		JSONArray nonepanel = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			SysUiLayout layout = list.get(i);
			boolean isOk = false;
			if (scenes == null) {
				isOk = true;
			} else if (StringUtil.isNull(layout.getFdFor())) {
				isOk = true;
			} else {
				String temp = ";" + layout.getFdFor() + ";";
				for (int j = 0; j < scenes.length; j++) {
					if (temp.indexOf(";" + scenes[j] + ";") >= 0) {
						isOk = true;
						break;
					}
				}
			}

			if (isOk) {
				if ("tabpanel".equalsIgnoreCase(layout.getFdKind())) {
					JSONObject panel = new JSONObject();
					panel.put("name", layout.getFdName());
					panel.put("id", layout.getFdId());
					panel.put("img", layout.getFdThumb());
					tabpanels.add(panel);
				}
				if ("panel".equalsIgnoreCase(layout.getFdKind())) {
					JSONObject panel = new JSONObject();
					panel.put("name", layout.getFdName());
					panel.put("id", layout.getFdId());
					panel.put("img", layout.getFdThumb());
					panels.add(panel);
				}
				if ("accordionpanel".equalsIgnoreCase(layout.getFdKind())) {
					JSONObject panel = new JSONObject();
					panel.put("name", layout.getFdName());
					panel.put("id", layout.getFdId());
					panel.put("img", layout.getFdThumb());
					accordionpanels.add(panel);
				}
				if ("nonepanel".equalsIgnoreCase(layout.getFdKind())) {
					JSONObject panel = new JSONObject();
					panel.put("name", layout.getFdName());
					panel.put("id", layout.getFdId());
					panel.put("img", layout.getFdThumb());
					nonepanel.add(panel);
				}
			}
		}
		json.put("panel", panels);
		json.put("tabpanel", tabpanels);
		json.put("accordionpanel", accordionpanels);
		json.put("nonepanel", nonepanel);
		request.setAttribute("lui-source", json);
		return getActionForward("lui-source", mapping, form, request, response);
	}

	public ActionForward getConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		JSONObject json = new JSONObject();
		try {
			if (StringUtil.isNull(type)) {
				json.put("error", "类型不能为空");
			} else if ("portlet".equals(type)) {
				json.put("source",
						JSONArray.fromObject(SysUiPluginUtil.getPortlets()));
			} else if ("render".equals(type)) {
				json.put("source",
						JSONArray.fromObject(SysUiPluginUtil.getRenders()));
			} else {
				json.put("error", "类型错误");
			}
			response.getWriter().print(json.toString());
			return null;
		} catch (Exception e) {
			json.put("error", e.getMessage());
			try {
				response.getWriter().print(json.toString());
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 获取模块清单
	 */
	public ActionForward getModules(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getModules", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONArray array = new JSONArray();
			List<String> modules = new ArrayList<String>();
			Collection<SysUiPortlet>  portlets = SysUiPluginUtil.getPortlets().values();
			Iterator<SysUiPortlet> it = portlets.iterator();
			while(it.hasNext()){
				SysUiPortlet x = it.next();
				String key = x.getFdModule();
				String mkey = ResourceUtil.getMessage(key);
				if(!modules.contains(key)){
					modules.add(key);
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("text", mkey);
					jsonObj.put("value", key);
					array.add(jsonObj);
				}
			}
			request.setAttribute("lui-source", array);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-getModules", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}
	
	/**
	 * 获取数据格式
	 */
	public ActionForward getFormates(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getModules", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONArray array = new JSONArray();
			Map<String, SysUiFormat> map=SysUiPluginUtil.getFormats();
			  for (String key : map.keySet()) {
				  	JSONObject jsonObj = new JSONObject();
					jsonObj.put("text", map.get(key).getFdName());
					jsonObj.put("value",key);
					array.add(jsonObj);
				  }
			  request.setAttribute("lui-source", array);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-getModules", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}
	


	public ActionForward getEmotionalPortlet(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String demo = EmotionUtil.getEmotionDatas();
		JSONObject result = new JSONObject();
		JSONArray ret = JSONArray.fromObject(demo);
		// Date date = new Date();

		// for (int i = 0; i < ret.size(); i++) {
		// JSONObject json = (JSONObject) ret.get(i);
		// JSONArray texts = json.getJSONArray("texts");
		// String startTime = json.getString("startTime");
		// String endTime = json.getString("endTime");
		//
		// if (EmotionUtil.isBetweenTime(startTime, endTime)) {
		// DateTimeFormatUtil df = new DateTimeFormatUtil();
		// String datetime = df.getDateTime(date, "yyyy'年'M'月'd'日'");
		// String week = df.getDateTime("$CN$'周'E");
		// result.put("dateTimeText", datetime);
		// result.put("weekText", week);
		// result.put("nowTime", date.getTime());
		// result.put("nowTimeText",
		// DateUtil.convertDateToString(date, "HH:mm:ss"));
		// if (texts != null && texts.size() > 0) {
		// int idx = (int) (Math.random() * texts.size());
		// JSONObject record = (JSONObject) texts.get(idx);
		// result.put("tip", record.getString("value"));
		// }
		// break;
		// }
		//
		// }
		result.put("tips", ret);
		request.setAttribute("lui-source", result);
		return getActionForward("lui-source", mapping, form, request, response);
	}

}
