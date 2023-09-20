package com.landray.kmss.third.ding.scenegroup.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ding.scenegroup.forms.ThirdDingGroupmsgLogForm;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingGroupmsgLog;
import com.landray.kmss.third.ding.scenegroup.service.IThirdDingGroupmsgLogService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdDingGroupmsgLogAction extends ExtendAction {

    private IThirdDingGroupmsgLogService thirdDingGroupmsgLogService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingGroupmsgLogService == null) {
            thirdDingGroupmsgLogService = (IThirdDingGroupmsgLogService) getBean("thirdDingGroupmsgLogService");
        }
        return thirdDingGroupmsgLogService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingGroupmsgLog.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
		com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoDate(hqlInfo,
				request,
				com.landray.kmss.third.ding.scenegroup.model.ThirdDingGroupmsgLog.class);
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingGroupmsgLogForm thirdDingGroupmsgLogForm = (ThirdDingGroupmsgLogForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingGroupmsgLogService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingGroupmsgLogForm;
    }
}
