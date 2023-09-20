package com.landray.kmss.third.weixin.work.actions;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinCgDeptMapp;
import com.landray.kmss.third.weixin.work.forms.ThirdWeixinCgDeptMappForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinCgDeptMappService;
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

public class ThirdWeixinCgDeptMappAction extends ExtendAction {

    private IThirdWeixinCgDeptMappService thirdWeixinCgDeptMappService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdWeixinCgDeptMappService == null) {
            thirdWeixinCgDeptMappService = (IThirdWeixinCgDeptMappService) getBean("thirdWeixinCgDeptMappService");
        }
        return thirdWeixinCgDeptMappService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdWeixinCgDeptMapp.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        ThirdWeixinUtil.buildHqlInfoDate(hqlInfo, request, ThirdWeixinCgDeptMapp.class);
        ThirdWeixinUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdWeixinCgDeptMappForm thirdWeixinCgDeptMappForm = (ThirdWeixinCgDeptMappForm) super.createNewForm(mapping, form, request, response);
        ((IThirdWeixinCgDeptMappService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdWeixinCgDeptMappForm;
    }
}
