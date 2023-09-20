package com.landray.kmss.third.feishu.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.third.feishu.forms.ThirdFeishuNotifyQueueErrForm;
import com.landray.kmss.third.feishu.model.ThirdFeishuNotifyQueueErr;
import com.landray.kmss.third.feishu.service.IThirdFeishuNotifyQueueErrService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdFeishuNotifyQueueErrAction extends ExtendAction {

    private IThirdFeishuNotifyQueueErrService thirdFeishuNotifyQueueErrService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdFeishuNotifyQueueErrService == null) {
            thirdFeishuNotifyQueueErrService = (IThirdFeishuNotifyQueueErrService) getBean("thirdFeishuNotifyQueueErrService");
        }
        return thirdFeishuNotifyQueueErrService;
    }

	@Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ThirdFeishuNotifyQueueErrForm thirdFeishuNotifyQueueErrForm = (ThirdFeishuNotifyQueueErrForm) super.createNewForm(
				mapping, form, request, response);
		((IThirdFeishuNotifyQueueErrService) getServiceImp(request))
				.initFormSetting((IExtendForm) form,
						new RequestContext(request));
		return thirdFeishuNotifyQueueErrForm;
	}

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdFeishuNotifyQueueErr.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.feishu.util.ThirdFeishuUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.feishu.model.ThirdFeishuNotifyQueueErr.class);
        com.landray.kmss.third.feishu.util.ThirdFeishuUtil.buildHqlInfoModel(hqlInfo, request);
		System.out.println(request.getQueryString());
		String flag = request.getParameter("q.fdFlag");
		if (StringUtil.isNull(flag)) {
			String whereBlock = hqlInfo.getWhereBlock();
			if (StringUtil.isNull(whereBlock)) {
				hqlInfo.setWhereBlock("thirdFeishuNotifyQueueErr.fdFlag=1");
			} else {
				hqlInfo.setWhereBlock(
						whereBlock + " and thirdFeishuNotifyQueueErr.fdFlag=1");
			}
		}

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

			((IThirdFeishuNotifyQueueErrService) getServiceImp(request))
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
