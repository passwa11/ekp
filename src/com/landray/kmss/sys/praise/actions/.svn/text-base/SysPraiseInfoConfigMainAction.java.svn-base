package com.landray.kmss.sys.praise.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.praise.forms.SysPraiseInfoConfigForm;
import com.landray.kmss.sys.praise.forms.SysPraiseInfoConfigMainForm;
import com.landray.kmss.sys.praise.service.ISysPraiseInfoConfigService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class SysPraiseInfoConfigMainAction extends ExtendAction {

	protected ISysPraiseInfoConfigService sysPraiseInfoConfigService;

	@Override
	protected ISysPraiseInfoConfigService getServiceImp(HttpServletRequest request) {
		if (sysPraiseInfoConfigService == null) {
			sysPraiseInfoConfigService = (ISysPraiseInfoConfigService) getBean("sysPraiseInfoConfigService");
		}
		return sysPraiseInfoConfigService;
	}

	@Override
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
							 HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-add", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			ActionForm newForm = createNewForm(mapping, form, request, response);
			SysPraiseInfoConfigMainForm sysPraiseInfoConfigMainForm = (SysPraiseInfoConfigMainForm) form;
			List<SysPraiseInfoConfigForm> configList = getServiceImp(request).updateFindConfigList();
			sysPraiseInfoConfigMainForm.setConfigList(configList);
			if (newForm != form) {
                request.setAttribute(getFormName(newForm, request), newForm);
            }
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-add", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("configEdit", mapping, form, request, response);
		}
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
							  HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			SysPraiseInfoConfigMainForm sysPraiseInfoConfigMainForm = (SysPraiseInfoConfigMainForm) form;
			getServiceImp(request).updateConfig(sysPraiseInfoConfigMainForm, request);

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			String url = "/sys/praise/sys_praise_info_config_main/sysPraiseInfoConfigMain.do?method=add";
			KmssReturnPage.getInstance(request).addMessages(messages).addButton("button.back", url, false)
					.save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	public ActionForward importModule(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-add", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {

			List<JSONObject> moduleList = getServiceImp(request).getShowModule();

			request.setAttribute("moduleList", moduleList);
			int size = moduleList.size();
			int leave = size % 4;
			request.setAttribute("leaveNum", 4 - leave);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-add", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("importInfo", mapping, form, request, response);
		}
	}

	public ActionForward updateCache(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {

			String moduleInfo = request.getParameter("moduleInfo");
			List<JSONObject> rtnObj = getServiceImp(request).updateCache(moduleInfo);
			JSONObject object = new JSONObject();
			object.accumulate("rtnObj", rtnObj);
			response.setCharacterEncoding("utf-8");
			response.getWriter().println(object);
			UserOperHelper.setOperSuccess(true);

		} catch (Exception e) {
			messages.addError(e);
			UserOperHelper.setOperSuccess(false);
		}

		return null;
	}

}
