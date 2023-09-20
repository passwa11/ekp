package com.landray.kmss.third.weixin.work.actions;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinCgUserMapp;
import com.landray.kmss.third.weixin.work.forms.ThirdWeixinCgUserMappForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinCgUserMappService;
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

public class ThirdWeixinCgUserMappAction extends ExtendAction {

    private IThirdWeixinCgUserMappService thirdWeixinCgUserMappService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdWeixinCgUserMappService == null) {
            thirdWeixinCgUserMappService = (IThirdWeixinCgUserMappService) getBean("thirdWeixinCgUserMappService");
        }
        return thirdWeixinCgUserMappService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdWeixinCgUserMapp.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        ThirdWeixinUtil.buildHqlInfoDate(hqlInfo, request, ThirdWeixinCgUserMapp.class);
        ThirdWeixinUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdWeixinCgUserMappForm thirdWeixinCgUserMappForm = (ThirdWeixinCgUserMappForm) super.createNewForm(mapping, form, request, response);
        ((IThirdWeixinCgUserMappService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdWeixinCgUserMappForm;
    }
}
