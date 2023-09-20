package com.landray.kmss.third.ding.action;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ding.service.IThirdDingOvertimeService;

public class ThirdDingOvertimeDataAction extends BaseAction {

    private IThirdDingOvertimeService thirdDingOvertimeService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingOvertimeService == null) {
            thirdDingOvertimeService = (IThirdDingOvertimeService) getBean("thirdDingOvertimeService");
        }
        return thirdDingOvertimeService;
    }
}
