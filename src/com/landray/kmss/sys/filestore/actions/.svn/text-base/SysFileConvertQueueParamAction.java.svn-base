package com.landray.kmss.sys.filestore.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.filestore.forms.SysFileConvertQueueParamForm;
import com.landray.kmss.sys.filestore.service.ISysFileConvertQueueService;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class SysFileConvertQueueParamAction extends ExtendAction {

	protected ISysFileConvertQueueService sysFileConvertQueueService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysFileConvertQueueService == null) {
			sysFileConvertQueueService = (ISysFileConvertQueueService) SpringBeanUtil
					.getBean("sysFileConvertQueueService");
		}
		return sysFileConvertQueueService;
	}

	public ActionForward saveQueueParam(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ISysFileConvertQueueService queueService = (ISysFileConvertQueueService) getServiceImp(request);
		KmssMessages messages = new KmssMessages();
		try {
			queueService.saveQueueParam((SysFileConvertQueueParamForm) form);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	public ActionForward setting(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		ISysFileConvertQueueService queueService = (ISysFileConvertQueueService) getServiceImp(request);
		SysFileConvertQueueParamForm queueParamForm = queueService.getParamForm(request.getParameter("queueIds"));
		SysFileConvertQueueParamForm targetForm = (SysFileConvertQueueParamForm) form;
		copyQueueParams(queueParamForm, targetForm);
		if (StringUtil.isNotNull(targetForm.getQueueIds())) {
			return getActionForward("setting", mapping, form, request, response);
		} else {
			messages.addError(new KmssMessage("sys-filestore:error.queueparam"));
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, targetForm, request, response);
		}
	}

	private void copyQueueParams(SysFileConvertQueueParamForm queueParamForm, SysFileConvertQueueParamForm targetForm) {
		targetForm.setPicResolution(queueParamForm.getPicResolution());
		targetForm.setPicRectangle(queueParamForm.getPicRectangle());
		targetForm.setQueueIds(queueParamForm.getQueueIds());
		targetForm.setHighFidelity(queueParamForm.getHighFidelity());
		targetForm.setContainsHighFidelity(queueParamForm.getContainsHighFidelity());
		targetForm.setConverterType(queueParamForm.getConverterType());
	}
}
