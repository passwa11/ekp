package com.landray.kmss.third.weixin.work.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.third.weixin.work.forms.ThirdWeixinNotifyQueErrForm;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinNotifyQueErr;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinNotifyQueErrService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdWeixinNotifyQueErrAction extends ExtendAction {

    private IThirdWeixinNotifyQueErrService thirdWeixinNotifyQueErrService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdWeixinNotifyQueErrService == null) {
            thirdWeixinNotifyQueErrService = (IThirdWeixinNotifyQueErrService) getBean("thirdWeixinNotifyQueErrService");
        }
        return thirdWeixinNotifyQueErrService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdWeixinNotifyQueErr.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.weixin.work.util.ThirdWeixinUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.weixin.work.model.ThirdWeixinNotifyQueErr.class);
        com.landray.kmss.third.weixin.work.util.ThirdWeixinUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdWeixinNotifyQueErrForm thirdWeixinNotifyQueErrForm = (ThirdWeixinNotifyQueErrForm) super.createNewForm(mapping, form, request, response);
        ((IThirdWeixinNotifyQueErrService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdWeixinNotifyQueErrForm;
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
			((IThirdWeixinNotifyQueErrService) getServiceImp(request))
					.updateResend(ids);

		} catch (Exception e) {
			messages.addError(new KmssMessage(null), e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-deleteall", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("success", mapping, form, request,
                    response);
        }
	}
}
