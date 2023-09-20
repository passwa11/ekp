package com.landray.kmss.third.ding.action;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ding.model.ThirdDingTemplateDetail;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.third.ding.service.IThirdDingTemplateDetailService;

public class ThirdDingTemplateDetailDataAction extends BaseAction {

    private IThirdDingTemplateDetailService thirdDingTemplateDetailService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingTemplateDetailService == null) {
            thirdDingTemplateDetailService = (IThirdDingTemplateDetailService) getBean("thirdDingTemplateDetailService");
        }
        return thirdDingTemplateDetailService;
    }
}
