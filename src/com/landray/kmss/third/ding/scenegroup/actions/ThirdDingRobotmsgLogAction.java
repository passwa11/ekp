package com.landray.kmss.third.ding.scenegroup.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ding.scenegroup.forms.ThirdDingRobotmsgLogForm;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingRobotmsgLog;
import com.landray.kmss.third.ding.scenegroup.service.IThirdDingRobotmsgLogService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdDingRobotmsgLogAction extends ExtendAction {

    private IThirdDingRobotmsgLogService thirdDingRobotmsgLogService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingRobotmsgLogService == null) {
            thirdDingRobotmsgLogService = (IThirdDingRobotmsgLogService) getBean("thirdDingRobotmsgLogService");
        }
        return thirdDingRobotmsgLogService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingRobotmsgLog.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
		com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoDate(hqlInfo,
				request,
				com.landray.kmss.third.ding.scenegroup.model.ThirdDingRobotmsgLog.class);
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingRobotmsgLogForm thirdDingRobotmsgLogForm = (ThirdDingRobotmsgLogForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingRobotmsgLogService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingRobotmsgLogForm;
    }
}
