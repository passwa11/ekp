package com.landray.kmss.third.ding.scenegroup.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ding.scenegroup.forms.ThirdDingRobotForm;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingRobot;
import com.landray.kmss.third.ding.scenegroup.service.IThirdDingRobotService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdDingRobotAction extends ExtendAction {

    private IThirdDingRobotService thirdDingRobotService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingRobotService == null) {
            thirdDingRobotService = (IThirdDingRobotService) getBean("thirdDingRobotService");
        }
        return thirdDingRobotService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingRobot.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
		com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoDate(hqlInfo,
				request,
				com.landray.kmss.third.ding.scenegroup.model.ThirdDingRobot.class);
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingRobotForm thirdDingRobotForm = (ThirdDingRobotForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingRobotService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingRobotForm;
    }
}
