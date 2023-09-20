package com.landray.kmss.third.weixin.work.actions;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinNotifyLog;
import com.landray.kmss.third.weixin.work.forms.ThirdWeixinNotifyLogForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinNotifyLogService;
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

public class ThirdWeixinNotifyLogAction extends ExtendAction {

    private IThirdWeixinNotifyLogService thirdWeixinNotifyLogService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdWeixinNotifyLogService == null) {
            thirdWeixinNotifyLogService = (IThirdWeixinNotifyLogService) getBean("thirdWeixinNotifyLogService");
        }
        return thirdWeixinNotifyLogService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdWeixinNotifyLog.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.weixin.work.util.ThirdWeixinUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.weixin.work.model.ThirdWeixinNotifyLog.class);
        com.landray.kmss.third.weixin.work.util.ThirdWeixinUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdWeixinNotifyLogForm thirdWeixinNotifyLogForm = (ThirdWeixinNotifyLogForm) super.createNewForm(mapping, form, request, response);
        ((IThirdWeixinNotifyLogService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdWeixinNotifyLogForm;
    }
}
