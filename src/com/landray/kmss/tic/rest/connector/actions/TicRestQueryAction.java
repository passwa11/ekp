package com.landray.kmss.tic.rest.connector.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.crypto.codec.Base64;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.tic.rest.connector.forms.TicRestQueryForm;
import com.landray.kmss.tic.rest.connector.model.TicRestMain;
import com.landray.kmss.tic.rest.connector.service.ITicRestMainService;
import com.landray.kmss.tic.rest.connector.service.ITicRestQueryService;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class TicRestQueryAction extends ExtendAction {

	protected ITicRestQueryService TicRestQueryService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (TicRestQueryService == null) {
			TicRestQueryService = (ITicRestQueryService) getBean(
					"ticRestQueryService");
		}
		return TicRestQueryService;
	}

	public ActionForward getResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getResult", true, getClass());
		KmssMessages messages = new KmssMessages();
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			if(request.getParameter("funcId")!=null){
				if(form!=null){
					TicRestQueryForm f=(TicRestQueryForm)form;
					f.setTicRestMainId(request.getParameter("funcId"));
				}
			}
			return getActionForward("query_result", mapping, form, request,
					response);
		}
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String funcId = request.getParameter("funcId");
		hqlInfo.setWhereBlock("ticRestQuery.ticRestMain.fdId=:ticRestMainFdId");
		hqlInfo.setParameter("ticRestMainFdId", funcId);
	}

	public ActionForward reQuery(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		TicRestQueryForm ticRestQueryForm = (TicRestQueryForm) form;
		ITicRestMainService ticRestMainService = (ITicRestMainService) SpringBeanUtil
				.getBean("ticRestMainService");
		TicRestMain ticRestMain = (TicRestMain) ticRestMainService
				.findByPrimaryKey(ticRestQueryForm.getTicRestMainId());
		request.setAttribute("fdQueryParam",
				ticRestQueryForm.getFdQueryParam().replaceAll("\\\"",
						"\\\\\""));
		request.setAttribute("reqParam",
				ticRestMain.getFdReqParam().replaceAll("\\\"", "\\\\\""));

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, ticRestQueryForm,
					request,
					response);
		} else {
			return getActionForward("reQuery", mapping, ticRestQueryForm,
					request,
					response);
		}
	}

	@Override
    public ActionForward save(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			((IExtendForm) form).setFdId(IDGenerator.generateID());
			String fdJsonResultBase64 = request
					.getParameter("fdJsonResultBase64");
			if (StringUtil.isNotNull(fdJsonResultBase64)) {
				fdJsonResultBase64 = new String(
						Base64.decode(fdJsonResultBase64.getBytes("UTF-8")),
						"UTF-8");
				((TicRestQueryForm) form).setFdJsonResult(fdJsonResultBase64);
			}
			getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request,
					response);
		}
	}
}
