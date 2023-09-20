package com.landray.kmss.sys.organization.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.oms.OMSConfig;
import com.landray.kmss.sys.organization.service.ISysOrgElementBakService;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.UserUtil;

public class SysOrgElementBakAction extends ExtendAction {

	private ISysOrgElementBakService sysOrgElementBakService;

	@Override
	protected ISysOrgElementBakService getServiceImp(HttpServletRequest request) {
		if (sysOrgElementBakService == null) {
            sysOrgElementBakService = (ISysOrgElementBakService) getBean("sysOrgElementBakService");
        }
		return sysOrgElementBakService;
	}

	public ActionForward clean(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-clean", true, getClass());
		KmssMessages messages = new KmssMessages();
		if (UserUtil.getKMSSUser().isAdmin()) {
			try {
				getServiceImp(request).clean();
			} catch (Exception e) {
				messages.addError(e);
			}
		}

		TimeCounter.logCurrentTime("Action-clean", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	public ActionForward backup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-backup", true, getClass());
		KmssMessages messages = new KmssMessages();
		if (UserUtil.getKMSSUser().isAdmin()) {
			try {
				getServiceImp(request).backUp();
			} catch (Exception e) {
				messages.addError(e);
			}
		}

		TimeCounter.logCurrentTime("Action-backup", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	public ActionForward restore(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-restore", true, getClass());
		KmssMessages messages = new KmssMessages();
		OMSConfig config = new OMSConfig();
		String backup = config.getValue("kmss.oms.in.organization.backup");
		if (!"true".equals(backup)) {
			messages.addError(new KmssMessage("没有启用oms接入组织架构备份功能！"));
		} else {
			if (UserUtil.getKMSSUser().isAdmin()) {
				try {
					getServiceImp(request).restore();
				} catch (Exception e) {
					messages.addError(e);
				}
			}
		}

		TimeCounter.logCurrentTime("Action-restore", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

}
