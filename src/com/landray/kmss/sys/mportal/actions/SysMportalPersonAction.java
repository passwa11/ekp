package com.landray.kmss.sys.mportal.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.mportal.service.ISysMportalPersonService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.UserUtil;

public class SysMportalPersonAction extends ExtendAction {

	protected ISysMportalPersonService SysMportalPersonService;

	@Override
	protected ISysMportalPersonService getServiceImp(
			HttpServletRequest request) {
		if (SysMportalPersonService == null) {
			SysMportalPersonService = (ISysMportalPersonService) getBean("sysMportalPersonService");
		}
		return SysMportalPersonService;
	}

	@Override
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute(
				"lui-source",
				getServiceImp(request).findById(UserUtil.getUser().getFdId(),
						new RequestContext(request)));
	}
}
