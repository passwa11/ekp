package com.landray.kmss.third.ding.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ding.forms.ThirdDingCategoryXformForm;
import com.landray.kmss.third.ding.model.ThirdDingCategoryXform;
import com.landray.kmss.third.ding.service.IThirdDingCategoryXformService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdDingCategoryXformAction extends ExtendAction {

    private IThirdDingCategoryXformService thirdDingCategoryXformService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingCategoryXformService == null) {
            thirdDingCategoryXformService = (IThirdDingCategoryXformService) getBean("thirdDingCategoryXformService");
        }
        return thirdDingCategoryXformService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingCategoryXform.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.ding.model.ThirdDingCategoryXform.class);
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingCategoryXformForm thirdDingCategoryXformForm = (ThirdDingCategoryXformForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingCategoryXformService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingCategoryXformForm;
    }
}
