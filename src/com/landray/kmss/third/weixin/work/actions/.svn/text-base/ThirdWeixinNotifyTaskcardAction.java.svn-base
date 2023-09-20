package com.landray.kmss.third.weixin.work.actions;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinNotifyTaskcard;
import com.landray.kmss.third.weixin.work.forms.ThirdWeixinNotifyTaskcardForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinNotifyTaskcardService;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.web.action.ActionForm;
import javax.servlet.http.HttpServletResponse;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.weixin.work.util.ThirdWeixinUtil;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdWeixinNotifyTaskcardAction extends ExtendAction {

    private IThirdWeixinNotifyTaskcardService thirdWeixinNotifyTaskcardService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdWeixinNotifyTaskcardService == null) {
            thirdWeixinNotifyTaskcardService = (IThirdWeixinNotifyTaskcardService) getBean("thirdWeixinNotifyTaskcardService");
        }
        return thirdWeixinNotifyTaskcardService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdWeixinNotifyTaskcard.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.weixin.work.util.ThirdWeixinUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.weixin.work.model.ThirdWeixinNotifyTaskcard.class);
        com.landray.kmss.third.weixin.work.util.ThirdWeixinUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdWeixinNotifyTaskcardForm thirdWeixinNotifyTaskcardForm = (ThirdWeixinNotifyTaskcardForm) super.createNewForm(mapping, form, request, response);
        ((IThirdWeixinNotifyTaskcardService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdWeixinNotifyTaskcardForm;
    }
}
