package com.landray.kmss.eop.basedata.actions;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import com.landray.kmss.eop.basedata.model.EopBasedataFund;
import com.landray.kmss.eop.basedata.forms.EopBasedataFundForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.eop.basedata.service.IEopBasedataFundService;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.web.action.ActionForm;
import javax.servlet.http.HttpServletResponse;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.web.action.ActionMapping;

public class EopBasedataFundAction extends ExtendAction {

    private IEopBasedataFundService eopBasedataFundService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataFundService == null) {
            eopBasedataFundService = (IEopBasedataFundService) getBean("eopBasedataFundService");
        }
        return eopBasedataFundService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataFund.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.eop.basedata.util.EopBasedataUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.eop.basedata.model.EopBasedataFund.class);
        com.landray.kmss.eop.basedata.util.EopBasedataUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataFundForm eopBasedataFundForm = (EopBasedataFundForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataFundService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataFundForm;
    }
}
