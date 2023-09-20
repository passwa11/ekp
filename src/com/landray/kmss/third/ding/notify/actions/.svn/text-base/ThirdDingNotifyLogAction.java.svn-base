package com.landray.kmss.third.ding.notify.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ding.notify.forms.ThirdDingNotifyLogForm;
import com.landray.kmss.third.ding.notify.model.ThirdDingNotifyLog;
import com.landray.kmss.third.ding.notify.service.IThirdDingNotifyLogService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdDingNotifyLogAction extends ExtendAction {

	private IThirdDingNotifyLogService thirdDingNotifyLogService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
		if (thirdDingNotifyLogService == null) {
			thirdDingNotifyLogService = (IThirdDingNotifyLogService) getBean(
					"thirdDingNotifyLogService");
        }
		return thirdDingNotifyLogService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingNotifyLog.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
		com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoDate(hqlInfo,
				request,
				com.landray.kmss.third.ding.notify.model.ThirdDingNotifyLog.class);
		com.landray.kmss.third.ding.util.ThirdDingUtil
				.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ThirdDingNotifyLogForm thirdDingNotifyLogForm = (ThirdDingNotifyLogForm) super.createNewForm(
				mapping, form, request, response);
        ((IThirdDingNotifyLogService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
		return thirdDingNotifyLogForm;
    }
}
