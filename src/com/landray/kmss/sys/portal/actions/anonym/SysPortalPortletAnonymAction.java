package com.landray.kmss.sys.portal.actions.anonym;

import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.portal.model.SysPortalMain;
import com.landray.kmss.sys.portal.model.SysPortalMainPage;
import com.landray.kmss.sys.portal.service.ISysPortalMainService;
import com.landray.kmss.sys.portal.service.ISysPortalPortletService;
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
 * 页眉匿名门户访问
 * 
 * @author 吴进
 * @version 1.0 20191120
 */
public class SysPortalPortletAnonymAction extends ExtendAction {
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
	public ActionForward portalNavTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("lui-source", getPortalChildrenAnonymous(null, request));
		return getActionForward("lui-source", mapping, form, request, response);
	}
	
	private JSONArray getPortalChildrenAnonymous(String fdId, HttpServletRequest request) throws Exception {
		String pageId = PortalUtil.getPortalInfo(request).getPortalPageId();
		JSONArray ret = new JSONArray();
		String where = "";
		HQLInfo hqlInfo = new HQLInfo();
		
		KMSSUser user = UserUtil.getKMSSUser(request);
		if (user != null && !user.isAnonymous()) {
			if (StringUtil.isNull(fdId)) {
				where += (" sysPortalMain.fdParent = null and sysPortalMain.fdEnabled = :fdEnabled ");
			} else {
				where += (" sysPortalMain.fdParent.fdId = '" + fdId + "' and sysPortalMain.fdEnabled = :fdEnabled ");
			}
		} else {
			if (StringUtil.isNull(fdId)) {
				where += (" sysPortalMain.fdParent = null and sysPortalMain.fdEnabled = :fdEnabled AND sysPortalMain.fdAnonymous = :fdAnonymous ");
			} else {
				where += (" sysPortalMain.fdParent.fdId = '" + fdId + "' and sysPortalMain.fdEnabled = :fdEnabled AND sysPortalMain.fdAnonymous = :fdAnonymous ");
			}
			hqlInfo.setParameter("fdAnonymous", Boolean.TRUE);
		}
		hqlInfo.setParameter("fdEnabled", Boolean.TRUE);
		hqlInfo.setWhereBlock(where);
		hqlInfo.setOrderBy("fdOrder,fdId");
		hqlInfo.setGetCount(false);
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, SysAuthConstant.AuthCheck.SYS_NONE);
		List list = getSysPortalMainService().findList(hqlInfo);
		for (int i = 0; i < list.size(); i++) {
			JSONObject m = new JSONObject();
			SysPortalMain main = (SysPortalMain) list.get(i);
			String xfdId = main.getFdId();
			m.put("fdId", xfdId);
			m.put("fdType", "portal");
			m.put("text", main.getFdName());
			String fdOrder = main.getFdOrder()==null ? "0" : main.getFdOrder().toString();
			m.put("fdOrder", fdOrder);
			m.put("icon", main.getFdIcon());
			m.put("target", main.getFdTarget());
			m.put("href", "/resource/anonym.jsp?portalId=" + main.getFdId());
			JSONArray chs = getPortalChildrenAnonymous(xfdId, request);
			if (!chs.isEmpty() && chs.size() > 0) {
				m.put("children", chs);
				ret.add(m);
			}
		}
		if (StringUtil.isNotNull(fdId)) {
			List pages = getSysPortalMainService().getAnonymousPortalPageList(fdId);
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
				m.put("href", "/resource/anonym.jsp?pageId=" + page.getFdId());
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

}
