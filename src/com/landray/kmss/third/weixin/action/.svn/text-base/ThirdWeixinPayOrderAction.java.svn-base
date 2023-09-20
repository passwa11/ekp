package com.landray.kmss.third.weixin.action;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.module.core.proxy.IDynamicProxy;
import com.landray.kmss.common.module.util.ModuleCenter;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.weixin.forms.ThirdWeixinPayOrderForm;
import com.landray.kmss.third.weixin.model.ThirdWeixinPayOrder;
import com.landray.kmss.third.weixin.pay.sdk.WXPayConstants;
import com.landray.kmss.third.weixin.pay.sdk.WXPayUtil;
import com.landray.kmss.third.weixin.service.IThirdWeixinPayOrderService;
import com.landray.kmss.third.weixin.util.ThirdWeixinUtil;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.work.util.ThirdWxPayUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ThirdWeixinPayOrderAction extends ExtendAction {

    private IThirdWeixinPayOrderService thirdWeixinPayOrderService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdWeixinPayOrderService == null) {
            thirdWeixinPayOrderService = (IThirdWeixinPayOrderService) getBean("thirdWeixinPayOrderService");
        }
        return thirdWeixinPayOrderService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdWeixinPayOrder.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        ThirdWeixinUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.weixin.model.ThirdWeixinPayOrder.class);
        ThirdWeixinUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdWeixinPayOrderForm thirdWeixinPayOrderForm = (ThirdWeixinPayOrderForm) super.createNewForm(mapping, form, request, response);
        ((IThirdWeixinPayOrderService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdWeixinPayOrderForm;
    }

    public ActionForward paySign(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-paySign", true, getClass());
        KmssMessages messages = new KmssMessages();
        JSONObject result = new JSONObject();
        try {
            String modelName = request.getParameter("modelName");
            String modelId = request.getParameter("modelId");
            String fdKey = request.getParameter("fdKey");
            if(StringUtil.isNull(modelName) || StringUtil.isNull(modelId)){
                throw new Exception("modelName 和 modelId 不能为空");
            }
            WeixinWorkConfig config = WeixinWorkConfig.newInstance();
            if(!"true".equals(config.getWxEnabled())){
                throw new Exception("未启用企业微信集成");
            }
            if(!"true".equals(config.getWxPayEnable())){
                throw new Exception("未启用企业微信支付功能");
            }
            ThirdWeixinPayOrder order = ((IThirdWeixinPayOrderService)getServiceImp(request)).findOrder(modelName,modelId,fdKey);
            if(order==null){
                throw new Exception("未找到对应的订单");
            }
            String prepayId = order.getFdPrepayId();
            String appId = order.getFdAppId();
            String nonce = WXPayUtil.generateNonceStr();
            String timeStamp = System.currentTimeMillis()/1000+"";
            String package_ = "prepay_id="+prepayId;
            String signType = WXPayConstants.SignType.HMACSHA256.toString();
            Map<String,String> params = new HashMap<>();
            params.put("nonce",nonce);
            params.put("timeStamp",timeStamp);
            params.put("appId",appId);
            params.put("package",package_);
            params.put("signType",signType);
            String key = config.getWxPayKey();
            String paySign = WXPayUtil.generateSignature(params,key,WXPayConstants.SignType.HMACSHA256);

            JSONObject data = new JSONObject();
            data.put("appId", appId);
            data.put("timeStamp", timeStamp);
            data.put("nonceStr", nonce);
            data.put("signType", signType);
            data.put("paySign", paySign);
            data.put("package",package_);
            result.put("data",data);
            result.put("result",true);
            response.setCharacterEncoding("UTF-8");
            if (UserOperHelper.allowLogOper("paySign", "*")) {
                UserOperHelper.logMessage(result.toString());
            }
        } catch (Exception e) {
            messages.addError(e);
            result.put("result",false);
            result.put("errorMsg",e.getMessage());
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result.toString());
        TimeCounter.logCurrentTime("Action-paySign", true, getClass());
        return null;
    }

    public ActionForward updateOrder(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-updateOrder", true, getClass());
        KmssMessages messages = new KmssMessages();
        JSONObject result = new JSONObject();
        try {
            String modelName = request.getParameter("modelName");
            String modelId = request.getParameter("modelId");
            String fdKey = request.getParameter("fdKey");
            if(StringUtil.isNull(modelName) || StringUtil.isNull(modelId)){
                throw new Exception("modelName 和 modelId 不能为空");
            }
            WeixinWorkConfig config = WeixinWorkConfig.newInstance();
            if(!"true".equals(config.getWxEnabled())){
                throw new Exception("未启用企业微信集成");
            }
            if(!"true".equals(config.getWxPayEnable())){
                throw new Exception("未启用企业微信支付功能");
            }
            ThirdWeixinPayOrder order = ((IThirdWeixinPayOrderService)getServiceImp(request)).findOrder(modelName,modelId,fdKey);
            if(order==null){
                throw new Exception("未找到对应的订单");
            }

            //Map resultMap = ThirdWxPayUtil.updateOrderPayData(modelName,modelId,fdKey);
            IDynamicProxy staticProxy = ModuleCenter.getStaticProxy("com.landray.kmss.third.payment.util.ThirdPaymentPayUtil");
            JSONObject data = null;
            if (staticProxy != null) {
                data = (JSONObject) staticProxy.invoke("updateOrder",modelName,modelId,fdKey);
            }else {
                throw new Exception("找不到 com.landray.kmss.third.payment.util.ThirdPaymentPayUtil");
            }
                
            result.put("data",data);
            result.put("result",true);
            response.setCharacterEncoding("UTF-8");
            if (UserOperHelper.allowLogOper("updateOrder", "*")) {
                UserOperHelper.logMessage(result.toString());
            }
        } catch (Exception e) {
            messages.addError(e);
            result.put("result",false);
            result.put("errorMsg",e.getMessage());
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result.toString());
        TimeCounter.logCurrentTime("Action-updateOrder", true, getClass());
        return null;
    }
}
