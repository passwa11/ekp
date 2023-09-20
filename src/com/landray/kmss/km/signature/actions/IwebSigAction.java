package com.landray.kmss.km.signature.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;

public class IwebSigAction extends ExtendAction {

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		return null;
	}

	@Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return getActionForward("viewIwebSig", mapping, form, request, response);
	}
}
