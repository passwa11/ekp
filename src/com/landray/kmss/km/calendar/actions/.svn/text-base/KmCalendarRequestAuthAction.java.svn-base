package com.landray.kmss.km.calendar.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.km.calendar.forms.KmCalendarRequestAuthForm;
import com.landray.kmss.km.calendar.model.KmCalendarRequestAuth;
import com.landray.kmss.km.calendar.service.IKmCalendarRequestAuthService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class KmCalendarRequestAuthAction extends ExtendAction {

	protected IKmCalendarRequestAuthService kmCalendarRequestAuthService;

	@Override
	protected IKmCalendarRequestAuthService
			getServiceImp(HttpServletRequest request) {
		if (kmCalendarRequestAuthService == null) {
			kmCalendarRequestAuthService = (IKmCalendarRequestAuthService) getBean(
					"kmCalendarRequestAuthService");
		}
		return kmCalendarRequestAuthService;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmCalendarRequestAuthForm authForm = (KmCalendarRequestAuthForm) form;
		authForm.setFdRequestAuth("authRead");
		return super.createNewForm(mapping, form, request, response);
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		KmCalendarRequestAuth requestAuth = null;
		String fdId = request.getParameter("fdId");
		if (StringUtil.isNotNull(fdId)) {
			requestAuth = (KmCalendarRequestAuth) getServiceImp(request)
					.findByPrimaryKey(fdId);
		} else {
			String userId = UserUtil.getUser().getFdId();
			requestAuth = findKmCalendarRquestAuth(request, userId);
		}
		if (requestAuth != null) {
			UserOperHelper.logFind(requestAuth);
			rtnForm = getServiceImp(request).convertModelToForm(
					(IExtendForm) form, requestAuth,
					new RequestContext(request));
		}
		if (rtnForm != null) {
			request.setAttribute(getFormName(rtnForm, request), rtnForm);
		}
		if (requestAuth != null) {
			request.setAttribute("kmCalendarAuth", requestAuth);
		}
	}

	private KmCalendarRequestAuth findKmCalendarRquestAuth(HttpServletRequest request,
			String userId) throws Exception {
		KmCalendarRequestAuth requestAuth = getServiceImp(request)
				.findByCreateId(userId);
		if (requestAuth == null) {
			requestAuth = new KmCalendarRequestAuth();
			SysOrgPerson docCreator = UserUtil.getUser(userId);
			requestAuth.setDocCreator(docCreator);
			requestAuth.setFdRequestAuth("authRead");
			getServiceImp(request).add(requestAuth);
		}
		return requestAuth;
	}

}