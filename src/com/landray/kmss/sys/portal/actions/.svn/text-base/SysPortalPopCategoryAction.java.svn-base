package com.landray.kmss.sys.portal.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.portal.forms.SysPortalPopCategoryForm;
import com.landray.kmss.sys.portal.model.SysPortalPopCategory;
import com.landray.kmss.sys.portal.service.ISysPortalPopCategoryService;
import com.landray.kmss.sys.simplecategory.actions.SysSimpleCategoryAction;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class SysPortalPopCategoryAction extends SysSimpleCategoryAction {

    private ISysPortalPopCategoryService sysPortalPopCategoryService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (sysPortalPopCategoryService == null) {
            sysPortalPopCategoryService = (ISysPortalPopCategoryService) getBean("sysPortalPopCategoryService");
        }
        return sysPortalPopCategoryService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, SysPortalPopCategory.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SysPortalPopCategoryForm sysPortalPopCategoryForm = (SysPortalPopCategoryForm) super.createNewForm(mapping, form, request, response);
        ((ISysPortalPopCategoryService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return sysPortalPopCategoryForm;
    }
}
