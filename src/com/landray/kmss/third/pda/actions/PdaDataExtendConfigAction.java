package com.landray.kmss.third.pda.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.third.pda.forms.PdaDataExtendConfigForm;
import com.landray.kmss.third.pda.model.PdaDataExtendConfig;
import com.landray.kmss.third.pda.service.IPdaDataExtendConfigService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;

public class PdaDataExtendConfigAction extends ExtendAction {

	private IPdaDataExtendConfigService pdaDataExtendConfigService;

	public IPdaDataExtendConfigService getPdaDataExtendConfigService() {
		if (pdaDataExtendConfigService == null) {
			pdaDataExtendConfigService = (IPdaDataExtendConfigService) getBean("pdaDataExtendConfigService");
		}
		return pdaDataExtendConfigService;
	}

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		return getPdaDataExtendConfigService();
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		@SuppressWarnings("unchecked")
		List<PdaDataExtendConfig> list = getPdaDataExtendConfigService().findList(null,
				null);
		form.reset(mapping, request);
		PdaDataExtendConfigForm configForm = (PdaDataExtendConfigForm) form;
		configForm.setPdaDataExtendConfigList(list);
		request.setAttribute(getFormName(configForm, request), configForm);
	}

	@Override
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("edit", mapping, form, request, response);
		}
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).update((IExtendForm) form,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}
	
}
