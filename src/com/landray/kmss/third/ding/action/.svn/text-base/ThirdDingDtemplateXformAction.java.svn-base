package com.landray.kmss.third.ding.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ding.forms.ThirdDingDtemplateXformForm;
import com.landray.kmss.third.ding.model.ThirdDingDtemplateXform;
import com.landray.kmss.third.ding.service.IThirdDingDtemplateXformService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdDingDtemplateXformAction extends ExtendAction {

    private IThirdDingDtemplateXformService thirdDingDtemplateXformService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingDtemplateXformService == null) {
            thirdDingDtemplateXformService = (IThirdDingDtemplateXformService) getBean("thirdDingDtemplateXformService");
        }
        return thirdDingDtemplateXformService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingDtemplateXform.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.ding.model.ThirdDingDtemplateXform.class);
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingDtemplateXformForm thirdDingDtemplateXformForm = (ThirdDingDtemplateXformForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingDtemplateXformService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingDtemplateXformForm;
    }
}
