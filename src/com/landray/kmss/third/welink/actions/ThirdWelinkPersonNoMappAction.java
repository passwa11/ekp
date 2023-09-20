package com.landray.kmss.third.welink.actions;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import com.landray.kmss.third.welink.model.ThirdWelinkPersonNoMapp;
import com.landray.kmss.third.welink.forms.ThirdWelinkPersonNoMappForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.welink.service.IThirdWelinkPersonNoMappService;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.web.action.ActionForm;
import javax.servlet.http.HttpServletResponse;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.welink.util.ThirdWelinkUtil;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdWelinkPersonNoMappAction extends ExtendAction {

    private IThirdWelinkPersonNoMappService thirdWelinkPersonNoMappService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdWelinkPersonNoMappService == null) {
            thirdWelinkPersonNoMappService = (IThirdWelinkPersonNoMappService) getBean("thirdWelinkPersonNoMappService");
        }
        return thirdWelinkPersonNoMappService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdWelinkPersonNoMapp.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.welink.util.ThirdWelinkUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.welink.model.ThirdWelinkPersonNoMapp.class);
        com.landray.kmss.third.welink.util.ThirdWelinkUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdWelinkPersonNoMappForm thirdWelinkPersonNoMappForm = (ThirdWelinkPersonNoMappForm) super.createNewForm(mapping, form, request, response);
        ((IThirdWelinkPersonNoMappService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdWelinkPersonNoMappForm;
    }
}
