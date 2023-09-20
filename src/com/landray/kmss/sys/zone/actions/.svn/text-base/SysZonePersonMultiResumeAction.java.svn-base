package com.landray.kmss.sys.zone.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.zone.service.ISysZonePersonMultiResumeService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;

public class SysZonePersonMultiResumeAction extends BaseAction {
	
	
	protected IBaseService service;
	
	public  IBaseService getServiceImp(HttpServletRequest request) {
		if(null == service) {
			service = (IBaseService)this.getBean("sysZonePersonMultiResumeService");
		}
		return service;
	}
	
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		return form;
	}
	
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-add", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			ActionForm newForm = createNewForm(mapping, form, request, response);
			if (newForm != form) {
                request.setAttribute("sysZonePersonMultiResumeForm", newForm);
            }
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-add", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("edit");
		}
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			((ISysZonePersonMultiResumeService)getServiceImp(request))
						.addResumes((IExtendForm) form, new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return mapping.findForward("edit");
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return  mapping.findForward("success");
		}
	}
	
	public ActionForward isLoginNameExist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-isLoginNameExist", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String fdLoginName = request.getParameter("fdLoginName");
			ISysOrgPersonService orgService = (ISysOrgPersonService) SpringBeanUtil
					.getBean("sysOrgPersonService");
			Boolean isExist = orgService
					.getBaseDao()
					.getHibernateSession()
					.createQuery(
							"select fdId from " + SysOrgPerson.class.getName()
									+ " where fdLoginName=:fdLoginName")
					.setParameter("fdLoginName", fdLoginName).list().size() > 0;
			JSONObject obj = new JSONObject();
			obj.put("isExist", isExist);
			request.setAttribute("lui-source",obj);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-isLoginNameExist", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return  mapping.findForward("failure");
		} else {
			return  mapping.findForward("lui-source");
		}
	}
	
	
}
