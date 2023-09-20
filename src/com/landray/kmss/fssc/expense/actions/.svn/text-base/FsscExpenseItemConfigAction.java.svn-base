package com.landray.kmss.fssc.expense.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.fssc.expense.forms.FsscExpenseItemConfigForm;
import com.landray.kmss.fssc.expense.model.FsscExpenseItemConfig;
import com.landray.kmss.fssc.expense.service.IFsscExpenseItemConfigService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class FsscExpenseItemConfigAction extends ExtendAction {

    private IFsscExpenseItemConfigService fsscExpenseItemConfigService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscExpenseItemConfigService == null) {
            fsscExpenseItemConfigService = (IFsscExpenseItemConfigService) getBean("fsscExpenseItemConfigService");
        }
        return fsscExpenseItemConfigService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscExpenseItemConfig.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        String fdCompanyName = request.getParameter("q.fdCompanyName");
        if(StringUtil.isNotNull(fdCompanyName)){
        	hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "fsscExpenseItemConfig.fdCompany.fdName like :fdCompanyName"));
        	hqlInfo.setParameter("fdCompanyName","%"+fdCompanyName+"%");
        }
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscExpenseItemConfigForm fsscExpenseItemConfigForm = (FsscExpenseItemConfigForm) super.createNewForm(mapping, form, request, response);
        ((IFsscExpenseItemConfigService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscExpenseItemConfigForm;
    }
}
