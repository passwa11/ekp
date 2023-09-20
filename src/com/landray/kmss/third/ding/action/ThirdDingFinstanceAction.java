package com.landray.kmss.third.ding.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ding.forms.ThirdDingFinstanceForm;
import com.landray.kmss.third.ding.model.ThirdDingFinstance;
import com.landray.kmss.third.ding.service.IThirdDingFinstanceService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdDingFinstanceAction extends ExtendAction {

    private IThirdDingFinstanceService thirdDingFinstanceService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingFinstanceService == null) {
            thirdDingFinstanceService = (IThirdDingFinstanceService) getBean("thirdDingFinstanceService");
        }
        return thirdDingFinstanceService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingFinstance.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingFinstanceForm thirdDingFinstanceForm = (ThirdDingFinstanceForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingFinstanceService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingFinstanceForm;
    }
}
