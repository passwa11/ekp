package com.landray.kmss.third.ding.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ding.forms.ThirdDingSendDingForm;
import com.landray.kmss.third.ding.model.ThirdDingSendDing;
import com.landray.kmss.third.ding.service.IThirdDingSendDingService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdDingSendDingAction extends ExtendAction {

    private IThirdDingSendDingService thirdDingSendDingService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingSendDingService == null) {
            thirdDingSendDingService = (IThirdDingSendDingService) getBean("thirdDingSendDingService");
        }
        return thirdDingSendDingService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingSendDing.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.ding.model.ThirdDingSendDing.class);
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingSendDingForm thirdDingSendDingForm = (ThirdDingSendDingForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingSendDingService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingSendDingForm;
    }
}
