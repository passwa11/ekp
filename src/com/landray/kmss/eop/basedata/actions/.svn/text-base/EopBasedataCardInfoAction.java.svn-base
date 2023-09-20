package com.landray.kmss.eop.basedata.actions;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.forms.EopBasedataCardInfoForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCardInfo;
import com.landray.kmss.eop.basedata.service.IEopBasedataCardInfoService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EopBasedataCardInfoAction extends EopBasedataBusinessAction{

    private IEopBasedataCardInfoService eopBasedataCardInfoService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataCardInfoService == null) {
            eopBasedataCardInfoService = (IEopBasedataCardInfoService) getBean("eopBasedataCardInfoService");
        }
        return eopBasedataCardInfoService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataCardInfo.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.eop.basedata.util.EopBasedataUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.eop.basedata.model.EopBasedataCardInfo.class);
        com.landray.kmss.eop.basedata.util.EopBasedataUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataCardInfoForm eopBasedataCardInfoForm = (EopBasedataCardInfoForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataCardInfoService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataCardInfoForm;
    }
}
