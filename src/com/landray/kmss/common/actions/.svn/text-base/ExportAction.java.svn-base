package com.landray.kmss.common.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.sys.authentication.background.IBackgroundAuthService;
import com.landray.kmss.util.HtmlToMht;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;

public class ExportAction extends BaseAction {

	protected IBackgroundAuthService backgroundAuthService;

	public IBackgroundAuthService getBackgroundAuthService() {
		if (backgroundAuthService == null) {
			backgroundAuthService = (IBackgroundAuthService) getBean(
					"backgroundAuthService");
		}
		return backgroundAuthService;
	}

	/**
	 * 导出.mht
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward outputMht(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			HtmlToMht htmlToMht = new HtmlToMht(false);
			htmlToMht.output(request, response);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(
					KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		}
		return null;
	}
}
