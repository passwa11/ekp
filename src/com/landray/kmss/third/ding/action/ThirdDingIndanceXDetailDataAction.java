package com.landray.kmss.third.ding.action;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ding.service.IThirdDingIndanceXDetailService;

public class ThirdDingIndanceXDetailDataAction extends BaseAction {

    private IThirdDingIndanceXDetailService thirdDingIndanceXDetailService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingIndanceXDetailService == null) {
            thirdDingIndanceXDetailService = (IThirdDingIndanceXDetailService) getBean("thirdDingIndanceXDetailService");
        }
        return thirdDingIndanceXDetailService;
    }
}
