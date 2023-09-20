package com.landray.kmss.km.signature.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.km.signature.forms.KmSignatureConfigForm;
import com.landray.kmss.km.signature.model.KmSignatureConfig;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 参数设置 Action
 * 
 * @author
 * @version 1.0 2016-07-07
 */
public class KmSignatureConfigAction extends BaseAction {

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			KmSignatureConfigForm configForm = (KmSignatureConfigForm) form;
			KmSignatureConfig forumConfig = new KmSignatureConfig();
			configForm.setFdIsAutoSign(forumConfig.getFdIsAutoSign());
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("edit");
		}
	}

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			KmSignatureConfigForm configForm = (KmSignatureConfigForm) form;
			KmSignatureConfig forumConfig = new KmSignatureConfig();
			forumConfig.setFdIsAutoSign(configForm.getFdIsAutoSign());
			forumConfig.save();
		} catch (Exception e) {
			messages.addError(e);
		}

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
}
