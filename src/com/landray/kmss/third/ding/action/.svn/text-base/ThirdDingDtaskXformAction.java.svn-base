package com.landray.kmss.third.ding.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ding.forms.ThirdDingDtaskXformForm;
import com.landray.kmss.third.ding.model.ThirdDingDtaskXform;
import com.landray.kmss.third.ding.service.IThirdDingDtaskXformService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdDingDtaskXformAction extends ExtendAction {

    private IThirdDingDtaskXformService thirdDingDtaskXformService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingDtaskXformService == null) {
            thirdDingDtaskXformService = (IThirdDingDtaskXformService) getBean("thirdDingDtaskXformService");
        }
        return thirdDingDtaskXformService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingDtaskXform.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.ding.model.ThirdDingDtaskXform.class);
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingDtaskXformForm thirdDingDtaskXformForm = (ThirdDingDtaskXformForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingDtaskXformService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingDtaskXformForm;
    }
}
