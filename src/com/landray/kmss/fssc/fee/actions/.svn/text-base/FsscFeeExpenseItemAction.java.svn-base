package com.landray.kmss.fssc.fee.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.actions.EopBasedataBusinessAction;
import com.landray.kmss.fssc.fee.forms.FsscFeeExpenseItemForm;
import com.landray.kmss.fssc.fee.model.FsscFeeExpenseItem;
import com.landray.kmss.fssc.fee.service.IFsscFeeExpenseItemService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class FsscFeeExpenseItemAction extends EopBasedataBusinessAction {

    private IFsscFeeExpenseItemService fsscFeeExpenseItemService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscFeeExpenseItemService == null) {
            fsscFeeExpenseItemService = (IFsscFeeExpenseItemService) getBean("fsscFeeExpenseItemService");
        }
        return fsscFeeExpenseItemService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscFeeExpenseItem.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        String fdCompanyName = request.getParameter("q.fdCompanyName");
        if(StringUtil.isNotNull(fdCompanyName)){
        	hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "fsscFeeExpenseItem.fdCompany.fdName like :fdCompanyName"));
        	hqlInfo.setParameter("fdCompanyName","%"+fdCompanyName+"%");
        }
        String fdTemplateName = request.getParameter("q.fdTemplateName");
        if(StringUtil.isNotNull(fdTemplateName)){
        	hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "fsscFeeExpenseItem.fdTemplate.fdName like :fdTemplateName"));
        	hqlInfo.setParameter("fdTemplateName","%"+fdTemplateName+"%");
        }
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscFeeExpenseItemForm fsscFeeExpenseItemForm = (FsscFeeExpenseItemForm) super.createNewForm(mapping, form, request, response);
        ((IFsscFeeExpenseItemService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscFeeExpenseItemForm;
    }
}
