package com.landray.kmss.km.imeeting.actions;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 会议Portlet
 */
public class KmImeetingMainPortletAction extends ExtendAction {
	protected IKmImeetingMainService kmImeetingMainService;

	@Override
	protected IKmImeetingMainService getServiceImp(HttpServletRequest request) {
		if (kmImeetingMainService == null) {
            kmImeetingMainService = (IKmImeetingMainService) getBean("kmImeetingMainService");
        }
		return kmImeetingMainService;
	}

	public ActionForward listPortlet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-listPortlet", true, getClass());
		KmssMessages messages = new KmssMessages();
		RequestContext requestCtx = new RequestContext(request);
		String dataview = request.getParameter("dataview");
		Map<String, ?> rtnMap = null;
		try {
			rtnMap = getServiceImp(request).listPortlet(requestCtx);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-listPortlet", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			// 视图展现方式:classic(简单列表)、listtable(列表)
			if ("classic".equals(dataview)) {
				request.setAttribute("lui-source", rtnMap.get("datas"));
				UserOperHelper.setOperSuccess(true);
				return getActionForward("lui-source", mapping, form, request,
						response);
			} else {
				request.setAttribute("queryPage", rtnMap.get("page"));
				return getActionForward("listPortlet", mapping, form, request,
						response);
			}
		}
	}

}
