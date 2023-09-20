package com.landray.kmss.third.ding.action;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ding.service.IThirdDingBussService;

public class ThirdDingBussDataAction extends BaseAction {

    private IThirdDingBussService thirdDingBussService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingBussService == null) {
            thirdDingBussService = (IThirdDingBussService) getBean("thirdDingBussService");
        }
        return thirdDingBussService;
    }
}
