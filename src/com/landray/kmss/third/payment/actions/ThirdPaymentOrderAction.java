package com.landray.kmss.third.payment.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.third.payment.forms.ThirdPaymentOrderForm;
import com.landray.kmss.third.payment.model.ThirdPaymentOrder;
import com.landray.kmss.third.payment.service.IThirdPaymentOrderService;
import com.landray.kmss.third.payment.util.ThirdPaymentPayUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThirdPaymentOrderAction extends ExtendAction {

    private static final Logger logger = org.slf4j.LoggerFactory
            .getLogger(ThirdPaymentOrderAction.class);

    private IThirdPaymentOrderService thirdPaymentOrderService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdPaymentOrderService == null) {
            thirdPaymentOrderService = (IThirdPaymentOrderService) getBean("thirdPaymentOrderService");
        }
        return thirdPaymentOrderService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdPaymentOrder.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.payment.util.ThirdPaymentUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.payment.model.ThirdPaymentOrder.class);
        com.landray.kmss.third.payment.util.ThirdPaymentUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdPaymentOrderForm thirdPaymentOrderForm = (ThirdPaymentOrderForm) super.createNewForm(mapping, form, request, response);
        ((IThirdPaymentOrderService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdPaymentOrderForm;
    }

    public ActionForward getOrderStatus(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-getOrderStatus", true, getClass());
        JSONObject json = new JSONObject();
        json.put("status", 1);
        json.put("msg", "成功");
        try {
            String modelName = request.getParameter("modelName");
            String modelId = request.getParameter("modelId");
            String fdKey = request.getParameter("fdKey");
            ThirdPaymentOrder order = ThirdPaymentPayUtil.getOrder(modelName,modelId,fdKey);
            String paymentStatus = order.getFdPaymentStatus();
            String paymentStatusDesc = order.getFdPaymentStatusDesc();
            JSONObject data = new JSONObject();
            data.put("paymentStatus",paymentStatus);
            data.put("paymentStatusDesc",paymentStatusDesc);
            json.put("data",data);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            json.put("status", 0);
            json.put("msg", e.getMessage());
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(json.toString());
        TimeCounter.logCurrentTime("Action-getOrderStatus", false, getClass());
        return null;
    }
}
