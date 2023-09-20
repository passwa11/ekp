package com.landray.kmss.fssc.fee.actions;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.fssc.fee.forms.FsscFeeMappForm;
import com.landray.kmss.fssc.fee.service.IFsscFeeMappService;
import com.landray.kmss.fssc.fee.model.FsscFeeMapp;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.actions.RequestContext;

public class FsscFeeMappAction extends ExtendAction {

    private IFsscFeeMappService fsscFeeMappService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscFeeMappService == null) {
            fsscFeeMappService = (IFsscFeeMappService) getBean("fsscFeeMappService");
        }
        return fsscFeeMappService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscFeeMapp.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscFeeMappForm fsscFeeMappForm = (FsscFeeMappForm) super.createNewForm(mapping, form, request, response);
        ((IFsscFeeMappService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscFeeMappForm;
    }
}
