package com.landray.kmss.third.ding.xform.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdDingXFormTemplateAction extends ExtendAction {

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 模板中心导航栏
	 */
	@SuppressWarnings("rawtypes")
	public ActionForward listCategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-listCategory", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			IXMLDataBean thirdDingXFormTemplateDataService = (IXMLDataBean) SpringBeanUtil
					.getBean("thirdDingXFormTemplateService");
			List rtnData = thirdDingXFormTemplateDataService
					.getDataList(new RequestContext(request));
			request.setAttribute("lui-source", rtnData);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-listCategory", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	/**
	 * 套件列表导航栏
	 */
	@SuppressWarnings("rawtypes")
	public ActionForward listTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-listTemplates", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			IXMLDataBean thirdDingXFormTemplateDataService = (IXMLDataBean) SpringBeanUtil
					.getBean("thirdDingXFormTemplateService");
			List rtnData = thirdDingXFormTemplateDataService
					.getDataList(new RequestContext(request));
			request.setAttribute("lui-source", rtnData);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-listTemplates", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}
}