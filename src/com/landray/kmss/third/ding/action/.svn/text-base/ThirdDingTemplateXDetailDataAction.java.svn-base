package com.landray.kmss.third.ding.action;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ding.service.IThirdDingTemplateXDetailService;

public class ThirdDingTemplateXDetailDataAction extends BaseAction {

    private IThirdDingTemplateXDetailService thirdDingTemplateXDetailService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingTemplateXDetailService == null) {
            thirdDingTemplateXDetailService = (IThirdDingTemplateXDetailService) getBean("thirdDingTemplateXDetailService");
        }
        return thirdDingTemplateXDetailService;
    }
}
