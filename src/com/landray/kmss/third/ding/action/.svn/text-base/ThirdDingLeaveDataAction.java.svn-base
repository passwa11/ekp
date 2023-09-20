package com.landray.kmss.third.ding.action;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ding.service.IThirdDingLeaveService;

public class ThirdDingLeaveDataAction extends BaseAction {

    private IThirdDingLeaveService thirdDingLeaveService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingLeaveService == null) {
            thirdDingLeaveService = (IThirdDingLeaveService) getBean("thirdDingLeaveService");
        }
        return thirdDingLeaveService;
    }
}
