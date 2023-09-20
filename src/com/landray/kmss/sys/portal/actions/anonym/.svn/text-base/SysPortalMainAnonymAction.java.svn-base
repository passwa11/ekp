package com.landray.kmss.sys.portal.actions.anonym;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.portal.model.SysPortalMain;
import com.landray.kmss.sys.portal.model.SysPortalMainPage;
import com.landray.kmss.sys.portal.service.ISysPortalMainService;
import com.landray.kmss.sys.portal.util.PortalUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import edu.emory.mathcs.backport.java.util.Collections;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 页眉匿名页面访问
 * 
 * @author 吴进
 * @version 1.0 20191120
 */
public class SysPortalMainAnonymAction extends ExtendAction {
	protected ISysPortalMainService sysPortalMainService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysPortalMainService == null) {
            sysPortalMainService = (ISysPortalMainService) getBean("sysPortalMainService");
        }
		return sysPortalMainService;
	}
	
	protected ISysPortalMainService getServiceImp() {
		if (sysPortalMainService == null) {
            sysPortalMainService = (ISysPortalMainService) getBean("sysPortalMainService");
        }
		return sysPortalMainService;
	}

	/**
	 * 页眉匿名门户切换
	 * @author 吴进 by 20191119
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward portal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<String[]> ids = getHierarchyIdsAnonymous(request);
		String cpid = PortalUtil.getPortalInfo(request).getPortalId();
		JSONArray json = getPortalTree(null, ids, cpid);
		request.setAttribute("lui-source", json);
		return getActionForward("lui-source", mapping, form, request, response);
	}
	
	private List<String[]> getHierarchyIdsAnonymous(HttpServletRequest request) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(SysPortalMainPage.class.getName());
		hqlInfo.setSelectBlock(" distinct sysPortalMainPage.sysPortalMain.fdHierarchyId,sysPortalMainPage.sysPortalMain.fdId ");
		
		KMSSUser user = UserUtil.getKMSSUser(request);
		if (user != null && !user.isAnonymous()) {
			hqlInfo.setWhereBlock(" sysPortalMainPage.sysPortalMain.fdEnabled = :fdEnabled ");
			hqlInfo.setParameter("fdEnabled", Boolean.TRUE);
		} else {
			hqlInfo.setWhereBlock(" sysPortalMainPage.sysPortalMain.fdEnabled = :fdEnabled AND sysPortalMainPage.sysPortalMain.fdAnonymous = :fdAnonymous ");
			hqlInfo.setParameter("fdEnabled", Boolean.TRUE);
			hqlInfo.setParameter("fdAnonymous", Boolean.TRUE);
		}
		hqlInfo.setGetCount(false);
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, SysAuthConstant.AuthCheck.SYS_NONE);
		List list = getServiceImp().findList(hqlInfo);

		// 有权限范围的门户ID列表
		List<String[]> ids = new ArrayList<String[]>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Object[] objs = (Object[]) list.get(i);
				String[] xxxx = new String[2];
				xxxx[0] = objs[0].toString();
				xxxx[1] = objs[1].toString();
				ids.add(xxxx);
			}
		}
		return ids;
	}
	
	private JSONArray getPortalTree(String portalId, List<String[]> ids, String cpid) throws Exception {
		JSONArray ret = new JSONArray();
		String where = "";
		HQLInfo hqlInfo = new HQLInfo();
		if (StringUtil.isNull(portalId)) {
			where += (" fdParent = null and fdEnabled = :fdEnabled ");
			hqlInfo.setParameter("fdEnabled", Boolean.TRUE);
		} else {
			where += (" fdParent.fdId = '" + portalId + "' and fdEnabled = :fdEnabled ");
			hqlInfo.setParameter("fdEnabled", Boolean.TRUE);
		}
		hqlInfo.setWhereBlock(where);
		hqlInfo.setOrderBy("fdOrder,fdId");
		hqlInfo.setGetCount(false);
		List<?> list = getServiceImp().findList(hqlInfo);
		for (int i = 0; i < list.size(); i++) {
			SysPortalMain main = (SysPortalMain) list.get(i);
			JSONObject m = new JSONObject();
			m.put("fdId", main.getFdId());
			m.put("text", main.getFdName());
			m.put("icon", main.getFdIcon());
			m.put("target", main.getFdTarget());
			m.put("fdOrder", main.getFdOrder());
			if (main.getFdId().equals(cpid)) {
				m.put("selected", true);
			} else {
				m.put("selected", false);
			}
			boolean isShow = false;
			for (int j = 0; j < ids.size(); j++) {
				if (ids.get(j)[0].indexOf(BaseTreeConstant.HIERARCHY_ID_SPLIT
						+ main.getFdId() + BaseTreeConstant.HIERARCHY_ID_SPLIT) >= 0) {
					isShow = true;
					break;
				}
			}
			if (isShow) {
				JSONArray children = getPortalTree(main.getFdId(), ids, cpid);
				if (children != null && !children.isEmpty()
						&& children.size() > 0) {
					m.put("children", children);
				}
				boolean isLink = false;
				for (int j = 0; j < ids.size(); j++) {
					if (ids.get(j)[1].equals(main.getFdId())) {
						isLink = true;
						break;
					}
				}
				if (isLink) {
					m.put("href", "/resource/anonym.jsp?portalId="+ main.getFdId());
				} else {
					m.put("href", "");
				}
				ret.add(m);
			}
		}
		return ret;
	}

	public ActionForward pages(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String portalId = request.getParameter("portalId");
		String pageId = request.getParameter("pageId");

		List<String[]> ids = getHierarchyIds(request);
		if (StringUtil.isNotNull(portalId) && !"null".equals(portalId)) {
			JSONArray json = JSONArray
					.fromObject(getChildren(portalId, ids, request));
			if (!json.isEmpty()) {
				for (int i = 0; i < json.size(); i++) {
					JSONObject obj = json.getJSONObject(i);
					if (obj.getString("fdId").equals(pageId)) {
						obj.put("selected", true);
					} else {
						obj.put("selected", false);
					}
				}
			}
			request.setAttribute("lui-source", json);
		} else {
			JSONArray json = new JSONArray();
			request.setAttribute("lui-source", json);
		}
		return getActionForward("lui-source", mapping, form, request, response);
	}

	private List<String[]> getHierarchyIds(HttpServletRequest request)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(SysPortalMainPage.class.getName());
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck,
				SysAuthConstant.AuthCheck.SYS_NONE);
		hqlInfo.setSelectBlock(
				" distinct sysPortalMainPage.sysPortalMain.fdHierarchyId,sysPortalMainPage.sysPortalMain.fdId ");
		hqlInfo.setWhereBlock(
				" sysPortalMainPage.sysPortalMain.fdEnabled = :fdEnabled ");
		hqlInfo.setParameter("fdEnabled", Boolean.TRUE);
		hqlInfo.setGetCount(false);
		List list = getServiceImp(request).findList(hqlInfo);

		// 有权限范围的门户ID列表
		List<String[]> ids = new ArrayList<String[]>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Object[] objs = (Object[]) list.get(i);
				String[] xxxx = new String[2];
				xxxx[0] = objs[0].toString();
				xxxx[1] = objs[1].toString();
				ids.add(xxxx);
			}
		}
		return ids;
	}

	private JSONArray getChildren(String portalId, List<String[]> ids,
			HttpServletRequest request) throws Exception {
		JSONArray ret = new JSONArray();
		String where = "";
		HQLInfo hqlInfo = new HQLInfo();
		if (StringUtil.isNull(portalId)) {
			where += (" sysPortalMain.fdParent = null and sysPortalMain.fdEnabled = :fdEnabled ");
		} else {
			where += (" sysPortalMain.fdParent.fdId = :portalId and sysPortalMain.fdEnabled = :fdEnabled ");
			hqlInfo.setParameter("portalId", portalId);
		}
		hqlInfo.setParameter("fdEnabled", Boolean.TRUE);
		hqlInfo.setWhereBlock(where);
		hqlInfo.setOrderBy("fdOrder,fdId");
		hqlInfo.setGetCount(false);
		// hqlInfo.setParameter(key, value);
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck,
				SysAuthConstant.AuthCheck.SYS_NONE);
		List list = getServiceImp(request).findList(hqlInfo);
		for (int i = 0; i < list.size(); i++) {
			SysPortalMain main = (SysPortalMain) list.get(i);
			boolean isShow = false;
			for (int j = 0; j < ids.size(); j++) {
				if (ids.get(j)[1].equals(main.getFdId())) {
					isShow = true;
					break;
				}
			}
			if (isShow) {
				JSONObject m = new JSONObject();
				m.put("fdId", main.getFdId());
				m.put("text", main.getFdName());
				m.put("icon", main.getFdIcon());
				m.put("target", main.getFdTarget());
				m.put("fdOrder", main.getFdOrder());
				m.put("href",
						"/resource/anonym.jsp?portalId=" + main.getFdId());
				ret.add(m);
			}
		}
		if (StringUtil.isNotNull(portalId)) {
			List pages = ((ISysPortalMainService) getServiceImp(request))
					.getAnonymousPortalPageList(portalId);
			for (int i = 0; i < pages.size(); i++) {
				SysPortalMainPage page = (SysPortalMainPage) pages.get(i);
				JSONObject m = new JSONObject();
				m.put("fdId", page.getFdId());
				m.put("text", page.getFdName());
				m.put("icon", page.getSysPortalPage().getFdIcon());
				m.put("target", page.getFdTarget());
				m.put("fdOrder", page.getFdOrder());
				m.put("href",
						"/resource/anonym.jsp?pageId=" + page.getFdId());
				m.put("pageType", page.getSysPortalPage() != null
						? page.getSysPortalPage().getFdType() : "1");
				ret.add(m);
			}
		}
		Collections.sort(ret, new Comparator<JSONObject>() {
			@Override
			public int compare(JSONObject a, JSONObject b) {
				if (!a.containsKey("fdOrder") || !b.containsKey("fdOrder")) {
					return 1;
				}
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
}
