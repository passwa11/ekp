package com.landray.kmss.third.ding.notify.queue.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.third.ding.notify.queue.model.ThirdDingNotifyQueueError;
import com.landray.kmss.third.ding.notify.queue.service.IThirdDingNotifyQueueErrorService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdDingNotifyQueueErrorAction extends ExtendAction {

	private IThirdDingNotifyQueueErrorService thirdDingNotifyQueueErrorService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
		if (thirdDingNotifyQueueErrorService == null) {
			thirdDingNotifyQueueErrorService = (IThirdDingNotifyQueueErrorService) getBean(
					"thirdDingNotifyQueueErrorService");
        }
		return thirdDingNotifyQueueErrorService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingNotifyQueueError.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
		com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoDate(hqlInfo,
				request, ThirdDingNotifyQueueError.class);
		com.landray.kmss.third.ding.util.ThirdDingUtil
				.buildHqlInfoModel(hqlInfo, request);
		String flag = request.getParameter("q.fdFlag");
		if (StringUtil.isNull(flag)) {
			String whereBlock = hqlInfo.getWhereBlock();
			if (StringUtil.isNull(whereBlock)) {
				hqlInfo.setWhereBlock("thirdDingNotifyQueueError.fdFlag='1'");
			} else {
				hqlInfo.setWhereBlock(
						whereBlock
								+ " and thirdDingNotifyQueueError.fdFlag='1'");
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
			((IThirdDingNotifyQueueErrorService) getServiceImp(request))
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
