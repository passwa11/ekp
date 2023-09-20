package com.landray.kmss.fssc.budget.actions;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.sys.simplecategory.actions.SysSimpleCategoryAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.fssc.budget.forms.FsscBudgetAdjustCategoryForm;
import com.landray.kmss.fssc.budget.service.IFsscBudgetAdjustCategoryService;
import com.landray.kmss.fssc.budget.model.FsscBudgetAdjustCategory;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.actions.RequestContext;

public class FsscBudgetAdjustCategoryAction extends SysSimpleCategoryAction {

    private IFsscBudgetAdjustCategoryService fsscBudgetAdjustCategoryService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscBudgetAdjustCategoryService == null) {
            fsscBudgetAdjustCategoryService = (IFsscBudgetAdjustCategoryService) getBean("fsscBudgetAdjustCategoryService");
        }
        return fsscBudgetAdjustCategoryService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscBudgetAdjustCategory.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscBudgetAdjustCategoryForm fsscBudgetAdjustCategoryForm = (FsscBudgetAdjustCategoryForm) super.createNewForm(mapping, form, request, response);
        ((IFsscBudgetAdjustCategoryService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscBudgetAdjustCategoryForm;
    }
}
