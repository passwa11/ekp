package com.landray.kmss.sys.portal.actions.anonym;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.portal.model.SysPortalHtml;
import com.landray.kmss.sys.portal.service.ISysPortalHtmlService;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 匿名自定义页面 Action
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public class SysPortalHtmlAnonymAction extends ExtendAction {
	protected ISysPortalHtmlService sysPortalHtmlService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysPortalHtmlService == null) {
            sysPortalHtmlService = (ISysPortalHtmlService) getBean("sysPortalHtmlService");
        }
		return sysPortalHtmlService;
	}

	public ActionForward portlet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fdId = request.getParameter("fdId");
		if (StringUtil.isNotNull(fdId)) {
			SysPortalHtml html = (SysPortalHtml) getServiceImp(request).findByPrimaryKey(fdId);
			if(null!=html&&(null==html.getFdAnonymous()?false:html.getFdAnonymous())){
				request.setAttribute("lui-text", html.getFdContent());
			}
		}else{
			HQLInfo info = new HQLInfo();
			info.setWhereBlock(" fdAnonymous = :fdAnonymous ");
			info.setParameter("fdAnonymous", Boolean.TRUE);
			Object obj = getServiceImp(request).findFirstOne(info);
			if (obj != null) {
				SysPortalHtml html = (SysPortalHtml) obj;
				request.setAttribute("lui-text", html.getFdContent());
			} else {
				request.setAttribute("lui-text", "fdId参数为空或无匿名数据");
			}
		}
		return getActionForward("lui-text", mapping, form, request, response);
	}
	

}
