package com.landray.kmss.third.wechat.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.third.wechat.forms.WechatMainConfigForm;
import com.landray.kmss.third.wechat.model.WechatMainConfig;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class WechatMainConfigAction extends BaseAction {

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			WechatMainConfigForm configForm = (WechatMainConfigForm) form;
			setConfigForm(configForm);
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("edit");
		}
	}
	
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			WechatMainConfigForm configForm = (WechatMainConfigForm) form;
			setConfigForm(configForm);
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("view");
		}
	}

	private void setConfigForm(WechatMainConfigForm configForm) throws Exception {
		WechatMainConfig config = new WechatMainConfig();
		String modelName = config.getClass().getName();
		configForm.setLwechat_license(config.getLwechat_license());
		configForm.setLwechat_qyDownloadUrl(config.getLwechat_qyDownloadUrl());
		configForm.setLwechat_qyEnable(config.getLwechat_qyEnable());
		configForm.setLwechat_qyNotifyUrl(config.getLwechat_qyNotifyUrl());
		configForm.setLwechat_qyUrl(config.getLwechat_qyUrl());
		configForm.setLwechat_wyEnable(config.getLwechat_wyEnable());
		configForm.setLwechat_wyNotifyUrl(config.getLwechat_wyNotifyUrl());
		configForm.setLwechat_wyUrl(config.getLwechat_wyUrl());
		
		configForm.setLwechat_wyisSendTodo(config.getLwechat_wyisSendTodo());
		configForm.setLwechat_wyisSendView(config.getLwechat_wyisSendView());
		
		configForm.setLwechat_qyisSendTodo(config.getLwechat_qyisSendTodo());
		configForm.setLwechat_qyisSendView(config.getLwechat_qyisSendView());
		if (UserOperHelper.allowLogOper("Action_Find", "*")) {
			UserOperHelper.setModelNameAndModelDesc(modelName,
					config.getModelDesc());
			if (config.getDataMap() != null) {
				for (String field : config.getDataMap().keySet()) {
					UserOperContentHelper.putFind(field, config.getDataMap().get(field), modelName);
				}
			}
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

			WechatMainConfigForm configForm = (WechatMainConfigForm) form;
			WechatMainConfig config = new WechatMainConfig();
			config.setLwechat_license(configForm.getLwechat_license());
			config.setLwechat_qyDownloadUrl(configForm.getLwechat_qyDownloadUrl());
			config.setLwechat_qyEnable(configForm.getLwechat_qyEnable());
			config.setLwechat_qyNotifyUrl(configForm.getLwechat_qyNotifyUrl());
			config.setLwechat_qyUrl(configForm.getLwechat_qyUrl());
			config.setLwechat_wyEnable(configForm.getLwechat_wyEnable());
			config.setLwechat_wyNotifyUrl(configForm.getLwechat_wyNotifyUrl());
			config.setLwechat_wyUrl(configForm.getLwechat_wyUrl());
			
			config.setLwechat_wyisSendTodo(configForm.getLwechat_wyisSendTodo());
			config.setLwechat_wyisSendView(configForm.getLwechat_wyisSendView());
			
			config.setLwechat_qyisSendTodo(configForm.getLwechat_qyisSendTodo());
			config.setLwechat_qyisSendView(configForm.getLwechat_qyisSendView());
			
			config.save();
		} catch (Exception e) {
			messages.addError(e);
		}
		request.setAttribute("SUCCESS_PAGE_AUTO_CLOSE", "false");
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
