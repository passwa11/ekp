package com.landray.kmss.third.ding.notify.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ding.notify.forms.ThirdDingNotifyMessageForm;
import com.landray.kmss.third.ding.notify.model.ThirdDingNotifyMessage;
import com.landray.kmss.third.ding.notify.service.IThirdDingNotifyMessageService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThirdDingNotifyMessageAction extends ExtendAction {

	private IThirdDingNotifyMessageService thirdDingNotifyMessageService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
		if (thirdDingNotifyMessageService == null) {
			thirdDingNotifyMessageService = (IThirdDingNotifyMessageService) getBean(
					"thirdDingNotifyMessageService");
        }
		return thirdDingNotifyMessageService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingNotifyMessage.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
		com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoDate(hqlInfo,
				request,
				ThirdDingNotifyMessage.class);
		com.landray.kmss.third.ding.util.ThirdDingUtil
				.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ThirdDingNotifyMessageForm thirdDingNotifyMessageForm = (ThirdDingNotifyMessageForm) super.createNewForm(
				mapping, form, request, response);
        ((IThirdDingNotifyMessageService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
		return thirdDingNotifyMessageForm;
    }
}
