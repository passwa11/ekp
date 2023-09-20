package com.landray.kmss.eop.basedata.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.forms.EopBasedataCacheConfigForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCacheConfig;
import com.landray.kmss.eop.basedata.service.IEopBasedataCacheConfigService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class EopBasedataCacheConfigAction extends ExtendAction {

    private IEopBasedataCacheConfigService eopBasedataCacheConfigService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataCacheConfigService == null) {
            eopBasedataCacheConfigService = (IEopBasedataCacheConfigService) getBean("eopBasedataCacheConfigService");
        }
        return eopBasedataCacheConfigService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataCacheConfig.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.eop.basedata.util.EopBasedataUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.eop.basedata.model.EopBasedataCacheConfig.class);
        com.landray.kmss.eop.basedata.util.EopBasedataUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataCacheConfigForm eopBasedataCacheConfigForm = (EopBasedataCacheConfigForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataCacheConfigService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataCacheConfigForm;
    }
}
