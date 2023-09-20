package com.landray.kmss.third.ding.action;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import com.landray.kmss.third.ding.model.ThirdDingDtemplate;
import com.landray.kmss.third.ding.forms.ThirdDingDtemplateForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.ding.service.IThirdDingDtemplateService;
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

public class ThirdDingDtemplateAction extends ExtendAction {

    private IThirdDingDtemplateService thirdDingDtemplateService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingDtemplateService == null) {
            thirdDingDtemplateService = (IThirdDingDtemplateService) getBean("thirdDingDtemplateService");
        }
        return thirdDingDtemplateService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingDtemplate.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.ding.model.ThirdDingDtemplate.class);
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingDtemplateForm thirdDingDtemplateForm = (ThirdDingDtemplateForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingDtemplateService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingDtemplateForm;
    }
}
