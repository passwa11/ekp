package com.landray.kmss.sys.mportal.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.mportal.forms.SysMportalTopmenuAllForm;
import com.landray.kmss.sys.mportal.service.spring.ISysMportalTopmenuService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;

 
/**
 * 
 * @author 
 * @version 1.0 2015-11-13
 */
public class SysMportalTopmenuAction extends BaseAction {
	protected ISysMportalTopmenuService SysMportalTopmenuService;

	protected ISysMportalTopmenuService getTopMenuServiceImp(HttpServletRequest request) {
		if(SysMportalTopmenuService == null){
			SysMportalTopmenuService = (ISysMportalTopmenuService)
				getBean("sysMportalTopmenuService");
		}
		return SysMportalTopmenuService;
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			SysMportalTopmenuAllForm 
				allForm = this.getTopMenuServiceImp(request).getAllMenuForm();
			allForm.setMethod_GET("edit");
			request.setAttribute("sysMportalTopmenuAllForm", allForm);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("edit");
		}
	}
	
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }

			getTopMenuServiceImp(request).updateAll((SysMportalTopmenuAllForm) form);
		} catch (Exception e) {
			log.error("Action-update", e);
			messages.addError(e);
		}
		request.setAttribute("SUCCESS_PAGE_AUTO_CLOSE", "false");
		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return mapping.findForward("edit");
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return mapping.findForward("success");
		}
	}
	
	
	public ActionForward items(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-items", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			request.setAttribute("lui-source", getTopMenuServiceImp(request)
					.toItemData(new RequestContext(request)));
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-items", false, getClass());

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return mapping.findForward("lui-failure");
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return mapping.findForward("lui-source");
		}
	}

}

