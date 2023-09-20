package com.landray.kmss.third.ding.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ding.forms.ThirdDingOmsErrorForm;
import com.landray.kmss.third.ding.model.ThirdDingOmsError;
import com.landray.kmss.third.ding.service.IThirdDingOmsErrorService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdDingOmsErrorAction extends ExtendAction {

    private IThirdDingOmsErrorService thirdDingOmsErrorService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingOmsErrorService == null) {
            thirdDingOmsErrorService = (IThirdDingOmsErrorService) getBean("thirdDingOmsErrorService");
        }
        return thirdDingOmsErrorService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingOmsError.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingOmsErrorForm thirdDingOmsErrorForm = (ThirdDingOmsErrorForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingOmsErrorService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingOmsErrorForm;
    }
}
