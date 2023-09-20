package com.landray.kmss.fssc.budgeting.actions;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.fssc.budgeting.forms.FsscBudgetingEffectAuthForm;
import com.landray.kmss.fssc.budgeting.service.IFsscBudgetingEffectAuthService;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingEffectAuth;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.actions.RequestContext;

public class FsscBudgetingEffectAuthAction extends ExtendAction {

    private IFsscBudgetingEffectAuthService fsscBudgetingEffectAuthService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscBudgetingEffectAuthService == null) {
            fsscBudgetingEffectAuthService = (IFsscBudgetingEffectAuthService) getBean("fsscBudgetingEffectAuthService");
        }
        return fsscBudgetingEffectAuthService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscBudgetingEffectAuth.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscBudgetingEffectAuthForm fsscBudgetingEffectAuthForm = (FsscBudgetingEffectAuthForm) super.createNewForm(mapping, form, request, response);
        ((IFsscBudgetingEffectAuthService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscBudgetingEffectAuthForm;
    }
}
