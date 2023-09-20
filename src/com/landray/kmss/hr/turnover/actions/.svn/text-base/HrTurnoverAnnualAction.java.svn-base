package com.landray.kmss.hr.turnover.actions;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import com.landray.kmss.hr.turnover.model.HrTurnoverAnnual;
import com.landray.kmss.hr.turnover.forms.HrTurnoverAnnualForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.hr.turnover.service.IHrTurnoverAnnualService;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.web.action.ActionForm;
import javax.servlet.http.HttpServletResponse;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.turnover.util.HrTurnoverUtil;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 年度离职率目标值 Action
  */
public class HrTurnoverAnnualAction extends ExtendAction {

    private IHrTurnoverAnnualService hrTurnoverAnnualService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (hrTurnoverAnnualService == null) {
            hrTurnoverAnnualService = (IHrTurnoverAnnualService) getBean("hrTurnoverAnnualService");
        }
        return hrTurnoverAnnualService;
    }

    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, HrTurnoverAnnual.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.hr.turnover.util.HrTurnoverUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.hr.turnover.model.HrTurnoverAnnual.class);
        com.landray.kmss.hr.turnover.util.HrTurnoverUtil.buildHqlInfoModel(hqlInfo, request);
    }

    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HrTurnoverAnnualForm hrTurnoverAnnualForm = (HrTurnoverAnnualForm) super.createNewForm(mapping, form, request, response);
        ((IHrTurnoverAnnualService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return hrTurnoverAnnualForm;
    }
}
