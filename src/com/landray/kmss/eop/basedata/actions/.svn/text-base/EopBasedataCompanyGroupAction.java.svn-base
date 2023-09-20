package com.landray.kmss.eop.basedata.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.forms.EopBasedataCompanyGroupForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompanyGroup;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyGroupService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class EopBasedataCompanyGroupAction extends EopBasedataBusinessAction {

    private IEopBasedataCompanyGroupService eopBasedataCompanyGroupService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataCompanyGroupService == null) {
            eopBasedataCompanyGroupService = (IEopBasedataCompanyGroupService) getBean("eopBasedataCompanyGroupService");
        }
        return eopBasedataCompanyGroupService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataCompanyGroup.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataCompanyGroupForm eopBasedataCompanyGroupForm = (EopBasedataCompanyGroupForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataCompanyGroupService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataCompanyGroupForm;
    }
}
