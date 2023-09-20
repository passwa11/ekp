package com.landray.kmss.sys.portal.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.portal.model.SysPortalPersonDefault;
import com.landray.kmss.sys.portal.service.ISysPortalPersonDefaultService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

/**
 * 
 * 个人默认门户
 */
public class SysPortalPersonDefaultAction extends ExtendAction {
	private ISysPortalPersonDefaultService sysPortalPersonDefaultService;

	@Override
	protected ISysPortalPersonDefaultService getServiceImp(HttpServletRequest request) {
		if (sysPortalPersonDefaultService == null) {
			sysPortalPersonDefaultService = (ISysPortalPersonDefaultService) getBean("sysPortalPersonDefaultService");
		}
		return sysPortalPersonDefaultService;
	}

	public ActionForward setDefault(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-quickAdd", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject msg = new JSONObject();
		try {
			String fdPortalId = request.getParameter("fdPortalId");
			String fdPortalName = request.getParameter("fdPortalName");
			String flag = request.getParameter("flag");
			
			SysPortalPersonDefault sysPortalPersonDefault = getServiceImp(request).getPersonDefaultPortal();
			if (sysPortalPersonDefault == null) {
				sysPortalPersonDefault = new SysPortalPersonDefault();
				sysPortalPersonDefault.setFdPerson(UserUtil.getUser());
				sysPortalPersonDefault.setFdPortalId(fdPortalId);
				sysPortalPersonDefault.setFdPortalName(fdPortalName);
				sysPortalPersonDefault.setFdIsDefault(Boolean.valueOf(flag));
				getServiceImp(request).add(sysPortalPersonDefault);
			} else {
				sysPortalPersonDefault.setFdPortalId(fdPortalId);
				sysPortalPersonDefault.setFdPortalName(fdPortalName);
				sysPortalPersonDefault.setFdIsDefault(Boolean.valueOf(flag));
				getServiceImp(request).update(sysPortalPersonDefault);
			}
			msg.put("msg", ResourceUtil.getString("return.optSuccess"));
			request.setAttribute("lui-source", msg);
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			msg.put("msg", ResourceUtil.getString("return.optFailure"));
			request.setAttribute("lui-source", msg);
		}

		TimeCounter.logCurrentTime("Action-setDefault", false, getClass());
		if (messages.hasError()) {
			return getActionForward("lui-source", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}
	
}
