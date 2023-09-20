package com.landray.kmss.third.weixin.action;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.module.core.proxy.IDynamicProxy;
import com.landray.kmss.common.module.util.ModuleCenter;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.weixin.model.ThirdWeixinPayCb;
import com.landray.kmss.third.weixin.forms.ThirdWeixinPayCbForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.weixin.model.ThirdWeixinPayLog;
import com.landray.kmss.third.weixin.model.ThirdWeixinPayOrder;
import com.landray.kmss.third.weixin.pay.sdk.WXPayConstants;
import com.landray.kmss.third.weixin.pay.sdk.WXPayUtil;
import com.landray.kmss.third.weixin.service.IThirdWeixinPayCbService;
import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.third.weixin.service.IThirdWeixinPayOrderService;
import com.landray.kmss.third.weixin.work.api.aes.WXBizMsgCrypt;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.util.*;
import com.landray.kmss.util.redis.RedisTemplateUtil;
import com.landray.kmss.web.action.ActionForm;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.weixin.util.ThirdWeixinUtil;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import org.slf4j.Logger;

public class ThirdWeixinPayCbAction extends ExtendAction {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdWeixinPayCbAction.class);

    private IThirdWeixinPayCbService thirdWeixinPayCbService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdWeixinPayCbService == null) {
            thirdWeixinPayCbService = (IThirdWeixinPayCbService) getBean("thirdWeixinPayCbService");
        }
        return thirdWeixinPayCbService;
    }

    public IThirdWeixinPayOrderService getThirdWeixinPayOrderService() {
        if (thirdWeixinPayOrderService == null) {
            thirdWeixinPayOrderService = (IThirdWeixinPayOrderService) getBean("thirdWeixinPayOrderService");
        }
        return thirdWeixinPayOrderService;
    }

    private IThirdWeixinPayOrderService thirdWeixinPayOrderService;

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdWeixinPayCb.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        ThirdWeixinUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.weixin.model.ThirdWeixinPayCb.class);
        ThirdWeixinUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdWeixinPayCbForm thirdWeixinPayCbForm = (ThirdWeixinPayCbForm) super.createNewForm(mapping, form, request, response);
        ((IThirdWeixinPayCbService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdWeixinPayCbForm;
    }

    public ActionForward service(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        logger.debug("-----------企业微信支付回调-----------");
        response.setContentType("text/xml;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        String result = "<xml>\n" +
                "  <return_code><![CDATA[{RETURN_CODE}]]></return_code>\n" +
                "  <return_msg><![CDATA[{RETURN_MSG}]]></return_msg>\n" +
                "</xml>";
        Map<String,String> cbMap = null;
        String content = null;
        try {
            WeixinWorkConfig config = WeixinWorkConfig.newInstance();
            if(!"true".equals(config.getWxPayEnable())){
                result = result.replace("{RETURN_CODE}","SUCCESS").replace("{RETURN_MSG}","OK");
                response.getWriter().write(result.toString());
                return null;
            }
            StringBuffer sb = new StringBuffer();
            InputStream is = request.getInputStream();
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            content = sb.toString();
            logger.debug("报文：" + content);
            String content_md5 = MD5Util.getMD5String(content);
            String redis_key = "weixinpay_cb_"+content_md5;
            Object weixinpay_cb = RedisTemplateUtil.getInstance().get(redis_key);
            if(weixinpay_cb!=null){
                logger.info("重复回调，不进行处理。报文："+content);
                result = result.replace("{RETURN_CODE}","SUCCESS").replace("{RETURN_MSG}","OK");
                response.getWriter().write(result.toString());
                return null;
            }else{
                RedisTemplateUtil.getInstance().set(redis_key,System.currentTimeMillis(),600);
            }
            cbMap = WXPayUtil.xmlToMap(content);
            boolean isSignatureValid = WXPayUtil.isSignatureValid(content, config.getWxPayKey(), WXPayConstants.SignType.HMACSHA256);
            logger.debug("isSignatureValid：" + isSignatureValid);
            if(!isSignatureValid){
                result = result.replace("{RETURN_CODE}","FAIL").replace("{RETURN_MSG}","SignatureValid ERROR");
                response.getWriter().write(result.toString());
                return null;
            }
            String out_trade_no = cbMap.get("out_trade_no");
            if(StringUtil.isNull(out_trade_no)){
                throw new Exception("报文中没有 out_trade_no 。报文："+content);
            }
            ThirdWeixinPayOrder order = (ThirdWeixinPayOrder)getThirdWeixinPayOrderService().findByPrimaryKey(out_trade_no);
            if(order==null){
                logger.warn("找不到对应的订单。报文："+content);
                result = result.replace("{RETURN_CODE}","SUCCESS").replace("{RETURN_MSG}","找不到对应的订单");
                response.getWriter().write(result.toString());
                return null;
            }
            IDynamicProxy staticProxy = ModuleCenter.getStaticProxy("com.landray.kmss.third.payment.util.ThirdPaymentPayUtil");
            if (staticProxy != null) {
                staticProxy.invoke("updateOrderAndPublishEvent",order.getFdModelName(),order.getFdModelId(),order.getFdKey());
            }else{
                throw new Exception("找不到对应的类 com.landray.kmss.third.payment.util.ThirdPaymentPayUtil");
            }
            return null;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            result = result.replace("{RETURN_CODE}","FAIL").replace("{RETURN_MSG}",e.getMessage());
            response.getWriter().write(result.toString());
            return null;
        }finally {
            if(cbMap!=null) {
                addThirdWeixinPayCb(cbMap,content,result);
            }
        }
    }

    private void addThirdWeixinPayCb(Map<String,String> cbMap, String content, String result){
        try {
            ThirdWeixinPayCb cb = new ThirdWeixinPayCb();
            cb.setDocCreateTime(new Date());
            cb.setFdAppid(cbMap.get("appId"));
            cb.setFdMchId(cbMap.get("mch_id"));
            cb.setFdAttach(cbMap.get("attach"));
            cb.setFdBankType(cbMap.get("bank_type"));
            cb.setFdCashFee(cbMap.get("cash_fee") == null ? null : Integer.parseInt(cbMap.get("cash_fee")));
            cb.setFdCashFeeType(cbMap.get("cash_fee_type"));
            cb.setFdCouponCount(cbMap.get("coupon_count") == null ? null : Integer.parseInt(cbMap.get("coupon_count")));
            cb.setFdCouponFee(cbMap.get("coupon_fee") == null ? null : Integer.parseInt(cbMap.get("coupon_fee")));
            cb.setFdCouponFeeOne(cbMap.get("coupon_fee_0") == null ? null : Integer.parseInt(cbMap.get("coupon_fee_0")));
            cb.setFdCouponId(cbMap.get("coupon_id_0"));
            cb.setFdCouponType(cbMap.get("coupon_type_0"));
            cb.setFdDeviceInfo(cbMap.get("device_info"));
            cb.setFdErrCode(cbMap.get("err_code"));
            cb.setFdErrCodeDes(cbMap.get("err_code_des"));
            cb.setFdIsSubscribe(cbMap.get("is_subscribe") == null ? null : Boolean.parseBoolean(cbMap.get("is_subscribe")));
            cb.setFdNonceStr(cbMap.get("nonce_str"));
            cb.setFdOpenid(cbMap.get("openid"));
            cb.setFdOutTradeNo(cbMap.get("out_trade_no"));
            cb.setFdReqData(content);
            cb.setFdResData(result);
            cb.setFdResultCode(cbMap.get("result_code"));
            cb.setFdReturnCode(cbMap.get("return_code"));
            cb.setFdReturnMsg(cbMap.get("return_msg"));
            cb.setFdSettlementTotalFee(cbMap.get("settlement_total_fee") == null ? null : Integer.parseInt(cbMap.get("settlement_total_fee")));
            cb.setFdSign(cbMap.get("sign"));
            cb.setFdSignType(cbMap.get("sign_type"));
            cb.setFdTimeEnd(cbMap.get("time_end"));
            cb.setFdTotalFee(cbMap.get("total_fee") == null ? null : Integer.parseInt(cbMap.get("total_fee")));
            cb.setFdTradeType(cbMap.get("trade_type"));
            cb.setFdTransactionId(cbMap.get("transaction_id"));
            getServiceImp(null).add(cb);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }

    @Override
    protected String getMethodName(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response,
                                   String parameter) throws Exception {
        String keyName = request.getParameter(parameter);
        if (keyName == null || keyName.length() == 0) {
            String method = (String) request.getAttribute("method_0");
            if (com.landray.kmss.sys.authentication.util.StringUtil.isNotNull(method)) {
                return method;
            }
            return "service";
        }
        String methodName = getLookupMapName(request, keyName, mapping);
        return methodName;
    }
}
