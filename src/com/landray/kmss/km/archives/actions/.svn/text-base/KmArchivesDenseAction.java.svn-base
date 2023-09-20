package com.landray.kmss.km.archives.actions;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.km.archives.forms.KmArchivesDenseForm;
import com.landray.kmss.km.archives.service.IKmArchivesDenseService;
import com.landray.kmss.km.archives.model.KmArchivesDense;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.actions.RequestContext;

public class KmArchivesDenseAction extends ExtendAction {

    private IKmArchivesDenseService kmArchivesDenseService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (kmArchivesDenseService == null) {
            kmArchivesDenseService = (IKmArchivesDenseService) getBean("kmArchivesDenseService");
        }
        return kmArchivesDenseService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, KmArchivesDense.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KmArchivesDenseForm kmArchivesDenseForm = (KmArchivesDenseForm) super.createNewForm(mapping, form, request, response);
        ((IKmArchivesDenseService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return kmArchivesDenseForm;
    }
}
