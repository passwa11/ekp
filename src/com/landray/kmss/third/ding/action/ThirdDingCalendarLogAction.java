package com.landray.kmss.third.ding.action;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import com.landray.kmss.third.ding.model.ThirdDingCalendarLog;
import com.landray.kmss.third.ding.forms.ThirdDingCalendarLogForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.ding.service.IThirdDingCalendarLogService;
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

public class ThirdDingCalendarLogAction extends ExtendAction {

    private IThirdDingCalendarLogService thirdDingCalendarLogService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingCalendarLogService == null) {
            thirdDingCalendarLogService = (IThirdDingCalendarLogService) getBean("thirdDingCalendarLogService");
        }
        return thirdDingCalendarLogService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingCalendarLog.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        ThirdDingUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.ding.model.ThirdDingCalendarLog.class);
        ThirdDingUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingCalendarLogForm thirdDingCalendarLogForm = (ThirdDingCalendarLogForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingCalendarLogService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingCalendarLogForm;
    }
}
