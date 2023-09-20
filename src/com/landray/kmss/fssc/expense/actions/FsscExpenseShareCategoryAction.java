package com.landray.kmss.fssc.expense.actions;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.sys.simplecategory.actions.SysSimpleCategoryAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.fssc.expense.forms.FsscExpenseShareCategoryForm;
import com.landray.kmss.fssc.expense.service.IFsscExpenseShareCategoryService;
import com.landray.kmss.fssc.expense.model.FsscExpenseShareCategory;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.actions.RequestContext;

public class FsscExpenseShareCategoryAction extends SysSimpleCategoryAction {

    private IFsscExpenseShareCategoryService fsscExpenseShareCategoryService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscExpenseShareCategoryService == null) {
            fsscExpenseShareCategoryService = (IFsscExpenseShareCategoryService) getBean("fsscExpenseShareCategoryService");
        }
        return fsscExpenseShareCategoryService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscExpenseShareCategory.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscExpenseShareCategoryForm fsscExpenseShareCategoryForm = (FsscExpenseShareCategoryForm) super.createNewForm(mapping, form, request, response);
        ((IFsscExpenseShareCategoryService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscExpenseShareCategoryForm;
    }
}
