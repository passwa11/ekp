package com.landray.kmss.km.archives.actions;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.km.archives.forms.KmArchivesUnitForm;
import com.landray.kmss.km.archives.service.IKmArchivesUnitService;
import com.landray.kmss.km.archives.model.KmArchivesUnit;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.actions.RequestContext;

public class KmArchivesUnitAction extends ExtendAction {

    private IKmArchivesUnitService kmArchivesUnitService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (kmArchivesUnitService == null) {
            kmArchivesUnitService = (IKmArchivesUnitService) getBean("kmArchivesUnitService");
        }
        return kmArchivesUnitService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, KmArchivesUnit.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KmArchivesUnitForm kmArchivesUnitForm = (KmArchivesUnitForm) super.createNewForm(mapping, form, request, response);
        ((IKmArchivesUnitService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return kmArchivesUnitForm;
    }
}
