package com.landray.kmss.hr.ratify.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.ratify.forms.HrRatifyFeedbackForm;
import com.landray.kmss.hr.ratify.model.HrRatifyFeedback;
import com.landray.kmss.hr.ratify.model.HrRatifyMain;
import com.landray.kmss.hr.ratify.service.IHrRatifyFeedbackService;
import com.landray.kmss.hr.ratify.service.IHrRatifyMainService;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HrRatifyFeedbackAction extends ExtendAction {

    private IHrRatifyFeedbackService hrRatifyFeedbackService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (hrRatifyFeedbackService == null) {
            hrRatifyFeedbackService = (IHrRatifyFeedbackService) getBean("hrRatifyFeedbackService");
        }
        return hrRatifyFeedbackService;
    }

	private IHrRatifyMainService hrRatifyMainService;

	public IHrRatifyMainService
			getHrRatifyMainServiceImp(HttpServletRequest request) {
		if (hrRatifyMainService == null) {
			hrRatifyMainService = (IHrRatifyMainService) getBean(
					"hrRatifyMainService");
		}
		return hrRatifyMainService;
	}

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, HrRatifyFeedback.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.hr.ratify.util.HrRatifyUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.hr.ratify.model.HrRatifyFeedback.class);
        com.landray.kmss.hr.ratify.util.HrRatifyUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HrRatifyFeedbackForm hrRatifyFeedbackForm = (HrRatifyFeedbackForm) super.createNewForm(
				mapping, form, request, response);
		((IHrRatifyFeedbackService) getServiceImp(request)).initFormSetting(
				(IExtendForm) form, new RequestContext(request));
		String mainId = request.getParameter("fdDocId");
		if (StringUtil.isNotNull(mainId)) {
			hrRatifyFeedbackForm.setFdDocId(mainId);
		}
        return hrRatifyFeedbackForm;
    }

	public ActionForward listdata(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.list(mapping, form, request, response);
		return mapping.findForward("listdata");
	}

	/**
	 * 打开阅读页面。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回view页面，否则返回failure页面
	 * @throws Exception
	 */
	@Override
    public ActionForward view(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			form.reset(mapping, request);
			IExtendForm rtnForm = null;
			String id = request.getParameter("fdId");
			if (StringUtil.isNotNull(id)) {
				
				HrRatifyFeedback model = (HrRatifyFeedback) getServiceImp(
						request)
						.findByPrimaryKey(id,
										HrRatifyFeedback.class, true);
				HrRatifyMain main = new HrRatifyMain();
				model.setFdDoc(main);
				if (model != null) {

					rtnForm = getServiceImp(request).convertModelToForm(
							(IExtendForm) form, model,
							new RequestContext(request));
					UserOperHelper.logFind(model);
				}
			}
			if (rtnForm == null) {
				throw new NoRecordException();
			}
			request.setAttribute(getFormName(rtnForm, request), rtnForm);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("view", mapping, form, request, response);
		}
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HrRatifyFeedbackForm mainform = (HrRatifyFeedbackForm) form;
		AttachmentDetailsForm af = (AttachmentDetailsForm) mainform
				.getAttachmentForms().get("feedBackAttachment");
		if (null != af.getAttachmentIds()
				&& af.getAttachmentIds().length() > 0) {
			mainform.setFdHasAttachment("true");
		} else {
			mainform.setFdHasAttachment("false");
		}
		return super.save(mapping, form, request, response);
	}
}
