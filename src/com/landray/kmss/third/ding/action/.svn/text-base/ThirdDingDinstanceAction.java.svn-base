package com.landray.kmss.third.ding.action;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import com.landray.kmss.third.ding.model.ThirdDingDinstance;
import com.landray.kmss.third.ding.forms.ThirdDingDinstanceForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.ding.service.IThirdDingDinstanceService;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.web.action.ActionForm;
import javax.servlet.http.HttpServletResponse;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdDingDinstanceAction extends ExtendAction {

    private IThirdDingDinstanceService thirdDingDinstanceService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingDinstanceService == null) {
            thirdDingDinstanceService = (IThirdDingDinstanceService) getBean("thirdDingDinstanceService");
        }
        return thirdDingDinstanceService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingDinstance.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.ding.model.ThirdDingDinstance.class);
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingDinstanceForm thirdDingDinstanceForm = (ThirdDingDinstanceForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingDinstanceService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingDinstanceForm;
    }
}
