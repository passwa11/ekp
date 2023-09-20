package com.landray.kmss.fssc.budgeting.actions;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.fssc.budgeting.forms.FsscBudgetingApprovalLogForm;
import com.landray.kmss.fssc.budgeting.service.IFsscBudgetingApprovalLogService;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingApprovalLog;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.actions.RequestContext;

public class FsscBudgetingApprovalLogAction extends ExtendAction {

    private IFsscBudgetingApprovalLogService fsscBudgetingApprovalLogService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscBudgetingApprovalLogService == null) {
            fsscBudgetingApprovalLogService = (IFsscBudgetingApprovalLogService) getBean("fsscBudgetingApprovalLogService");
        }
        return fsscBudgetingApprovalLogService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscBudgetingApprovalLog.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscBudgetingApprovalLogForm fsscBudgetingApprovalLogForm = (FsscBudgetingApprovalLogForm) super.createNewForm(mapping, form, request, response);
        ((IFsscBudgetingApprovalLogService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscBudgetingApprovalLogForm;
    }
}
