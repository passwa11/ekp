package com.landray.kmss.third.welink.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.third.welink.forms.ThirdWelinkNotifyQueueErrForm;
import com.landray.kmss.third.welink.model.ThirdWelinkNotifyQueueErr;
import com.landray.kmss.third.welink.service.IThirdWelinkNotifyQueueErrService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdWelinkNotifyQueueErrAction extends ExtendAction {

    private IThirdWelinkNotifyQueueErrService thirdWelinkNotifyQueueErrService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdWelinkNotifyQueueErrService == null) {
            thirdWelinkNotifyQueueErrService = (IThirdWelinkNotifyQueueErrService) getBean("thirdWelinkNotifyQueueErrService");
        }
        return thirdWelinkNotifyQueueErrService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdWelinkNotifyQueueErr.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.welink.util.ThirdWelinkUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.welink.model.ThirdWelinkNotifyQueueErr.class);
        com.landray.kmss.third.welink.util.ThirdWelinkUtil.buildHqlInfoModel(hqlInfo, request);
		System.out.println(request.getQueryString());
		String flag = request.getParameter("q.fdFlag");
		if (StringUtil.isNull(flag)) {
			String whereBlock = hqlInfo.getWhereBlock();
			if (StringUtil.isNull(whereBlock)) {
				hqlInfo.setWhereBlock("thirdWelinkNotifyQueueErr.fdFlag=1");
			} else {
				hqlInfo.setWhereBlock(
						whereBlock + " and thirdWelinkNotifyQueueErr.fdFlag=1");
			}
		}

    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdWelinkNotifyQueueErrForm thirdWelinkNotifyQueueErrForm = (ThirdWelinkNotifyQueueErrForm) super.createNewForm(mapping, form, request, response);
        ((IThirdWelinkNotifyQueueErrService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdWelinkNotifyQueueErrForm;
    }

	public ActionForward resend(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-resend", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] ids = request.getParameterValues("List_Selected");
			
			((IThirdWelinkNotifyQueueErrService) getServiceImp(request))
					.updateResend(ids);

		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-resend", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("success", mapping, form, request,
                    response);
        }
	}
}
