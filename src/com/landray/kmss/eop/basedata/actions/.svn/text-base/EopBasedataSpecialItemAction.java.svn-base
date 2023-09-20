package com.landray.kmss.eop.basedata.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.forms.EopBasedataSpecialItemForm;
import com.landray.kmss.eop.basedata.model.EopBasedataSpecialItem;
import com.landray.kmss.eop.basedata.service.IEopBasedataSpecialItemService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class EopBasedataSpecialItemAction extends EopBasedataBusinessAction {

    private IEopBasedataSpecialItemService eopBasedataSpecialItemService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataSpecialItemService == null) {
            eopBasedataSpecialItemService = (IEopBasedataSpecialItemService) getBean("eopBasedataSpecialItemService");
        }
        return eopBasedataSpecialItemService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
    	String where = hqlInfo.getWhereBlock();
    	String fdCompanyId = request.getParameter("fdCompanyId");
    	if(StringUtil.isNotNull(fdCompanyId)){
    		where = StringUtil.linkString(where, " and ", "eopBasedataSpecialItem.fdCompany.fdId=:fdCompanyId");
    		hqlInfo.setParameter("fdCompanyId",fdCompanyId);
    	}
    	hqlInfo.setWhereBlock(where);
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataSpecialItem.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataSpecialItemForm eopBasedataSpecialItemForm = (EopBasedataSpecialItemForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataSpecialItemService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataSpecialItemForm;
    }
}
