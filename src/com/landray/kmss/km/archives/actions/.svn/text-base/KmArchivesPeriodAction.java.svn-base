package com.landray.kmss.km.archives.actions;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.km.archives.forms.KmArchivesPeriodForm;
import com.landray.kmss.km.archives.service.IKmArchivesPeriodService;
import com.landray.kmss.km.archives.model.KmArchivesPeriod;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.actions.RequestContext;

public class KmArchivesPeriodAction extends ExtendAction {

    private IKmArchivesPeriodService kmArchivesPeriodService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (kmArchivesPeriodService == null) {
            kmArchivesPeriodService = (IKmArchivesPeriodService) getBean("kmArchivesPeriodService");
        }
        return kmArchivesPeriodService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, KmArchivesPeriod.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KmArchivesPeriodForm kmArchivesPeriodForm = (KmArchivesPeriodForm) super.createNewForm(mapping, form, request, response);
        ((IKmArchivesPeriodService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return kmArchivesPeriodForm;
    }
}
