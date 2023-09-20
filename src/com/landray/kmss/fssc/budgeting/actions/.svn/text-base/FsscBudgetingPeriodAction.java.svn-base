package com.landray.kmss.fssc.budgeting.actions;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.fssc.budgeting.forms.FsscBudgetingPeriodForm;
import com.landray.kmss.fssc.budgeting.service.IFsscBudgetingPeriodService;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingPeriod;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.actions.RequestContext;

public class FsscBudgetingPeriodAction extends ExtendAction {

    private IFsscBudgetingPeriodService fsscBudgetingPeriodService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscBudgetingPeriodService == null) {
            fsscBudgetingPeriodService = (IFsscBudgetingPeriodService) getBean("fsscBudgetingPeriodService");
        }
        return fsscBudgetingPeriodService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscBudgetingPeriod.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscBudgetingPeriodForm fsscBudgetingPeriodForm = (FsscBudgetingPeriodForm) super.createNewForm(mapping, form, request, response);
        ((IFsscBudgetingPeriodService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscBudgetingPeriodForm;
    }
}
