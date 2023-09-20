package com.landray.kmss.sys.portal.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.portal.forms.SysPortalPopTplCategoryForm;
import com.landray.kmss.sys.portal.model.SysPortalPopTplCategory;
import com.landray.kmss.sys.portal.service.ISysPortalPopTplCategoryService;
import com.landray.kmss.sys.simplecategory.actions.SysSimpleCategoryAction;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class SysPortalPopTplCategoryAction extends SysSimpleCategoryAction {

    private ISysPortalPopTplCategoryService sysPortalPopTplCategoryService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (sysPortalPopTplCategoryService == null) {
            sysPortalPopTplCategoryService = (ISysPortalPopTplCategoryService) getBean("sysPortalPopTplCategoryService");
        }
        return sysPortalPopTplCategoryService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, SysPortalPopTplCategory.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SysPortalPopTplCategoryForm sysPortalPopTplCategoryForm = (SysPortalPopTplCategoryForm) super.createNewForm(mapping, form, request, response);
        ((ISysPortalPopTplCategoryService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return sysPortalPopTplCategoryForm;
    }
}
