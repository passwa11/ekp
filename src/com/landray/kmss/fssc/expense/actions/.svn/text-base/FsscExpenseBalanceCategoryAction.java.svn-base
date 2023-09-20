package com.landray.kmss.fssc.expense.actions;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.sys.simplecategory.actions.SysSimpleCategoryAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.fssc.expense.forms.FsscExpenseBalanceCategoryForm;
import com.landray.kmss.fssc.expense.service.IFsscExpenseBalanceCategoryService;
import com.landray.kmss.fssc.expense.model.FsscExpenseBalanceCategory;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.actions.RequestContext;

public class FsscExpenseBalanceCategoryAction extends SysSimpleCategoryAction {

    private IFsscExpenseBalanceCategoryService fsscExpenseBalanceCategoryService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscExpenseBalanceCategoryService == null) {
            fsscExpenseBalanceCategoryService = (IFsscExpenseBalanceCategoryService) getBean("fsscExpenseBalanceCategoryService");
        }
        return fsscExpenseBalanceCategoryService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscExpenseBalanceCategory.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscExpenseBalanceCategoryForm fsscExpenseBalanceCategoryForm = (FsscExpenseBalanceCategoryForm) super.createNewForm(mapping, form, request, response);
        ((IFsscExpenseBalanceCategoryService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscExpenseBalanceCategoryForm;
    }
}
