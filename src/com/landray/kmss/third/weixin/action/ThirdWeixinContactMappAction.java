package com.landray.kmss.third.weixin.action;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import com.landray.kmss.third.weixin.model.ThirdWeixinContactMapp;
import com.landray.kmss.third.weixin.forms.ThirdWeixinContactMappForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.weixin.service.IThirdWeixinContactMappService;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.web.action.ActionForm;
import javax.servlet.http.HttpServletResponse;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.weixin.util.ThirdWeixinUtil;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdWeixinContactMappAction extends ExtendAction {

    private IThirdWeixinContactMappService thirdWeixinContactMappService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdWeixinContactMappService == null) {
            thirdWeixinContactMappService = (IThirdWeixinContactMappService) getBean("thirdWeixinContactMappService");
        }
        return thirdWeixinContactMappService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdWeixinContactMapp.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        ThirdWeixinUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.weixin.model.ThirdWeixinContactMapp.class);
        ThirdWeixinUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdWeixinContactMappForm thirdWeixinContactMappForm = (ThirdWeixinContactMappForm) super.createNewForm(mapping, form, request, response);
        ((IThirdWeixinContactMappService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdWeixinContactMappForm;
    }
}
