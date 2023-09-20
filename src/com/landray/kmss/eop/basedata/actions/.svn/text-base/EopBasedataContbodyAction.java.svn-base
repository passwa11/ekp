package com.landray.kmss.eop.basedata.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EopBasedataContbodyAction extends ExtendAction {

    private com.landray.kmss.eop.basedata.service.IEopBasedataContbodyService eopBizAgrContbodyService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBizAgrContbodyService == null) {
            eopBizAgrContbodyService = (com.landray.kmss.eop.basedata.service.IEopBasedataContbodyService) getBean("eopBasedataContbodyService");
        }
        return eopBizAgrContbodyService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, com.landray.kmss.eop.basedata.model.EopBasedataContbody.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.eop.basedata.util.EopBasedataUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.eop.basedata.model.EopBasedataContbody.class);
        com.landray.kmss.eop.basedata.util.EopBasedataUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        com.landray.kmss.eop.basedata.forms.EopBasedataContbodyForm eopBasedataContbodyForm = (com.landray.kmss.eop.basedata.forms.EopBasedataContbodyForm) super.createNewForm(mapping, form, request, response);
        ((com.landray.kmss.eop.basedata.service.IEopBasedataContbodyService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataContbodyForm;
    }
}
