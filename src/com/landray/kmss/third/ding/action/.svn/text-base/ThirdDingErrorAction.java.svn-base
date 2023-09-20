package com.landray.kmss.third.ding.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ding.forms.ThirdDingErrorForm;
import com.landray.kmss.third.ding.model.ThirdDingError;
import com.landray.kmss.third.ding.service.IThirdDingErrorService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdDingErrorAction extends ExtendAction {

    private IThirdDingErrorService thirdDingErrorService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingErrorService == null) {
            thirdDingErrorService = (IThirdDingErrorService) getBean("thirdDingErrorService");
        }
        return thirdDingErrorService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingError.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingErrorForm thirdDingErrorForm = (ThirdDingErrorForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingErrorService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingErrorForm;
    }
}
