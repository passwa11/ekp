package com.landray.kmss.third.payment.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.payment.forms.ThirdPaymentCallLogForm;
import com.landray.kmss.third.payment.model.ThirdPaymentCallLog;
import com.landray.kmss.third.payment.service.IThirdPaymentCallLogService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThirdPaymentCallLogAction extends ExtendAction {

    private IThirdPaymentCallLogService thirdPaymentCallLogService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdPaymentCallLogService == null) {
            thirdPaymentCallLogService = (IThirdPaymentCallLogService) getBean("thirdPaymentCallLogService");
        }
        return thirdPaymentCallLogService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdPaymentCallLog.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.payment.util.ThirdPaymentUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.payment.model.ThirdPaymentCallLog.class);
        com.landray.kmss.third.payment.util.ThirdPaymentUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdPaymentCallLogForm thirdPaymentCallLogForm = (ThirdPaymentCallLogForm) super.createNewForm(mapping, form, request, response);
        ((IThirdPaymentCallLogService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdPaymentCallLogForm;
    }
}
