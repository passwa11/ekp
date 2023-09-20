package com.landray.kmss.third.ding.action;

import com.landray.kmss.third.ding.forms.ThirdDingCardLogForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.ding.model.ThirdDingCardLog;
import com.landray.kmss.third.ding.service.IThirdDingCardLogService;
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

public class ThirdDingCardLogAction extends ExtendAction {

    private IThirdDingCardLogService thirdDingCardLogService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingCardLogService == null) {
            thirdDingCardLogService = (IThirdDingCardLogService) getBean("thirdDingCardLogService");
        }
        return thirdDingCardLogService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingCardLog.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        ThirdDingUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.ding.model.ThirdDingCardLog.class);
        ThirdDingUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingCardLogForm thirdDingCardLogForm = (ThirdDingCardLogForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingCardLogService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingCardLogForm;
    }
}
