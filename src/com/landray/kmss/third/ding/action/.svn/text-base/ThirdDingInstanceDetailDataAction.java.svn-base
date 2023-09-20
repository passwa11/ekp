package com.landray.kmss.third.ding.action;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ding.model.ThirdDingInstanceDetail;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.third.ding.service.IThirdDingInstanceDetailService;

public class ThirdDingInstanceDetailDataAction extends BaseAction {

    private IThirdDingInstanceDetailService thirdDingInstanceDetailService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingInstanceDetailService == null) {
            thirdDingInstanceDetailService = (IThirdDingInstanceDetailService) getBean("thirdDingInstanceDetailService");
        }
        return thirdDingInstanceDetailService;
    }
}
