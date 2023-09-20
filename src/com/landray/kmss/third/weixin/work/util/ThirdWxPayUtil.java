package com.landray.kmss.third.weixin.work.util;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.weixin.model.ThirdWeixinPayBl;
import com.landray.kmss.third.weixin.model.ThirdWeixinPayLog;
import com.landray.kmss.third.weixin.model.ThirdWeixinPayOrder;
import com.landray.kmss.third.weixin.pay.sdk.IWXPayDomain;
import com.landray.kmss.third.weixin.pay.sdk.WXPay;
import com.landray.kmss.third.weixin.pay.sdk.WXPayConfig;
import com.landray.kmss.third.weixin.pay.sdk.WXPayConstants;
import com.landray.kmss.third.weixin.pay.sdk.impl.WXPayConfigImp;
import com.landray.kmss.third.weixin.pay.sdk.impl.WXPayDomainImp;
import com.landray.kmss.third.weixin.service.IThirdWeixinPayBlService;
import com.landray.kmss.third.weixin.service.IThirdWeixinPayLogService;
import com.landray.kmss.third.weixin.service.IThirdWeixinPayOrderService;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.work.spi.model.WxworkOmsRelationModel;
import com.landray.kmss.third.weixin.work.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.util.*;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ThirdWxPayUtil {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ThirdWxPayUtil.class);

    private static IThirdWeixinPayBlService thirdWeixinPayBlService;

    private static IThirdWeixinPayBlService getThirdWeixinPayBlService(){
        if(thirdWeixinPayBlService==null){
            thirdWeixinPayBlService = (IThirdWeixinPayBlService) SpringBeanUtil.getBean("thirdWeixinPayBlService");
        }
        return thirdWeixinPayBlService;
    }

    private static IThirdWeixinPayLogService thirdWeixinPayLogService;

    private static IThirdWeixinPayLogService getThirdWeixinPayLogService(){
        if(thirdWeixinPayLogService==null){
            thirdWeixinPayLogService = (IThirdWeixinPayLogService) SpringBeanUtil.getBean("thirdWeixinPayLogService");
        }
        return thirdWeixinPayLogService;
    }

    private static IThirdWeixinPayOrderService thirdWeixinPayOrderService;

    private static IThirdWeixinPayOrderService getThirdWeixinPayOrderService(){
        if(thirdWeixinPayOrderService==null){
            thirdWeixinPayOrderService = (IThirdWeixinPayOrderService) SpringBeanUtil.getBean("thirdWeixinPayOrderService");
        }
        return thirdWeixinPayOrderService;
    }

    private static IWxworkOmsRelationService wxworkOmsRelationService;

    private static IWxworkOmsRelationService getWxworkOmsRelationService(){
        if(wxworkOmsRelationService==null){
            wxworkOmsRelationService = (IWxworkOmsRelationService) SpringBeanUtil.getBean("wxworkOmsRelationService");
        }
        return wxworkOmsRelationService;
    }

    /**
     * 判断是否启用了企业微信支付功能
     * @param config
     */
    private static void checkWxPayEnable(WeixinWorkConfig config){
        if(!"true".equals(config.getWxEnabled())){
            throw new RuntimeException("未启用企业微信集成");
        }
        if(!"true".equals(config.getWxPayEnable())){
            throw new RuntimeException("未启用企业微信支付功能");
        }
    }

    /**
     * 微信支付下单
     *
     * @param modelName 主文档类名
     * @param modelId 主文档ID
     * @param fdKey 业务标识，可为空
     * @param desc  付款描述
     * @param money 金额，以分为单位
     * @param toUser 付款人
     *
     * return 订单单号或者支付二维码地址
     */
    public static JSONObject unifiedorder(String modelName, String modelId, String fdKey, String desc, Integer money, SysOrgPerson toUser, String merchantId, String tradeType) throws Exception {
        if(StringUtil.isNull(modelName)||StringUtil.isNull(modelId) || StringUtil.isNull(desc) || money==null || toUser==null){
            throw new Exception("modelName、modelId、desc、money、toUser 都不能为空");
        }
        if(desc.length()>128){
            throw new Exception("付款描述的长度不能大于128");
        }
        WeixinWorkConfig config = WeixinWorkConfig.newInstance();
        checkWxPayEnable(config);

        ThirdWeixinPayOrder order = getThirdWeixinPayOrderService().findOrder(modelName,modelId,fdKey);
        if(order!=null){
            throw new Exception("已存在该文档的订单，不能重复下单");
        }
        String errorMsg = null;
        Map<String,String> result = new HashMap<>();
        try {
            WxworkOmsRelationModel relationModel = getWxworkOmsRelationService().updateOpenIdByEkpId(toUser.getFdId());
            if(relationModel==null){
                throw new Exception("找不到用户("+toUser.getFdName()+")的微信映射关系，无法下单");
            }
            String openId = relationModel.getFdOpenId();
            if(StringUtil.isNull(merchantId)){
                merchantId = config.getWxPayMchID();
            }
            result = unifiedorder(modelName,modelId,fdKey,config,desc,money,openId,merchantId,tradeType);
            String outTradeNo = result.get("out_trade_no");
            String codeUrl = result.get("code_url");
            JSONObject returnObj = new JSONObject();
            returnObj.put("orderId",outTradeNo);
            returnObj.put("outTradeNo",outTradeNo);
            returnObj.put("codeUrl",codeUrl);
            return returnObj;
        }catch (Exception e){
            errorMsg = e.getMessage();
            throw e;
        }finally {
            addThirdWeixinPayBl(config,modelName,modelId,fdKey,desc,money,toUser,errorMsg,result);
        }
    }

    private static void addThirdWeixinPayBl(WeixinWorkConfig config, String modelName, String modelId, String fdKey, String desc, Integer money, SysOrgPerson toUser,String errorMsg,Map<String,String> result){
        try {
            ThirdWeixinPayBl bl = new ThirdWeixinPayBl();
            bl.setDocCreateTime(new Date());
            bl.setDocCreator(UserUtil.getUser());
            bl.setFdAppid(config.getWxCorpid());
            bl.setFdKey(fdKey);
            bl.setFdBody(desc);
            bl.setFdModelId(modelId);
            bl.setFdModelName(modelName);
            bl.setFdPayPerson(toUser);
            bl.setFdTotalFee(money);
            bl.setFdTradeType("JSAPI");
            if (StringUtil.isNull(errorMsg)) {
                bl.setFdTransactionId(result.get("out_trade_no"));
                bl.setFdReturnData(result.toString());
            } else {
                bl.setFdReturnData(errorMsg);
            }
            getThirdWeixinPayBlService().add(bl);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }

    /**
     * 统一下单
     * @param modelName
     * @param modelId
     * @param fdKey
     * @param config
     * @param desc
     * @param money
     * @param openId
     * @return
     * @throws Exception
     */
    private static Map<String,String> unifiedorder(String modelName, String modelId, String fdKey, WeixinWorkConfig config,String desc, Integer money, String openId, String merchantId, String tradeType) throws Exception {
        ThirdWeixinPayLog thirdWeixinPayLog = new ThirdWeixinPayLog();
        String errorMsg = null;
        Map<String, String> reqData = new HashMap<>();
        Map<String, String> resultMap = new HashMap<>();
        try {
            String id = IDGenerator.generateID();
            IWXPayDomain wxPayDomain = new WXPayDomainImp();
            String key = config.getWxPayKey();
            WXPayConfig wxPayConfig = new WXPayConfigImp(config.getWxCorpid(), merchantId, key, wxPayDomain, config.getWxPayCertFilePath());
            WXPay wxPay = new WXPay(wxPayConfig, ResourceUtil.getKmssConfigString("kmss.urlPrefix")+"/resource/third/wx/payEndpoint.do");
            wxPay.setThirdWeixinPayLog(thirdWeixinPayLog);
            reqData.put("body", desc);
            reqData.put("out_trade_no", id);
            reqData.put("total_fee", money + "");
            if(StringUtil.isNull(tradeType)){
                tradeType = "JSAPI";
            }
            reqData.put("trade_type", tradeType);
            reqData.put("openid", openId);

            resultMap = wxPay.unifiedOrder(reqData);
            if (!resultMap.get("return_code").equals(WXPayConstants.SUCCESS)) {
                throw new Exception("下单失败，响应信息："+resultMap.toString());
            }
            if (!resultMap.get("result_code").equals(WXPayConstants.SUCCESS)) {
                throw new Exception("下单业务失败，响应信息："+resultMap.toString());
            }
            //下单成功则创建一个订单记录
            addThirdWeixinPayOrder(modelName,modelId,fdKey,config,reqData,resultMap,merchantId,tradeType);
            resultMap.put("out_trade_no",id);
            // {nonce_str=MgdUkM1K2N0WKvgm, appid=wx0ba3f3f366212a10, sign=74CC5887053817DE9E6851A8317C58426D34A473C42B0794FA1BA2F97769FD61, trade_type=JSAPI, return_msg=OK, result_code=SUCCESS, mch_id=1580697801, return_code=SUCCESS, prepay_id=wx081051438629057ef600deb5fe5bcc0000}
            return resultMap;
        }catch (Exception e){
            errorMsg = e.getMessage();
            throw e;
        }finally {
            addThirdWeixinPayLog(thirdWeixinPayLog,config,reqData,resultMap,errorMsg,merchantId);
        }
    }

    private static void addThirdWeixinPayLog(ThirdWeixinPayLog thirdWeixinPayLog,WeixinWorkConfig config,Map<String, String> reqData,Map<String, String> resultMap, String errorMsg, String merchantId) throws Exception {
        try {
            thirdWeixinPayLog.setFdAppId(config.getWxCorpid());
            thirdWeixinPayLog.setFdBody(reqData.get("body"));
            thirdWeixinPayLog.setFdMchId(merchantId);
            //thirdWeixinPayLog.setFdReqDate(new Date());
            thirdWeixinPayLog.setFdOutTradeNo(reqData.get("out_trade_no"));
            thirdWeixinPayLog.setFdUrl(new WXPayDomainImp().getDomain(null).getDomain() + WXPayConstants.UNIFIEDORDER_URL_SUFFIX);
            if (errorMsg == null) {
                thirdWeixinPayLog.setFdResult(1);
            } else {
                thirdWeixinPayLog.setFdResult(2);
                thirdWeixinPayLog.setFdErrMsg(errorMsg);
            }
            thirdWeixinPayLog.setFdCodeUrl(resultMap.get("code_url"));
            if (thirdWeixinPayLog.getFdReqDate() != null) {
                thirdWeixinPayLog.setFdExpireTime(thirdWeixinPayLog.getFdResDate().getTime() - thirdWeixinPayLog.getFdReqDate().getTime());
            }
            thirdWeixinPayLog.setFdPrepayId(resultMap.get("prepay_id"));
            getThirdWeixinPayLogService().add(thirdWeixinPayLog);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }

    /**
     * 创建订单记录
     * @param modelName 主文档类名
     * @param modelId 主文档ID
     * @param fdKey 业务标识，可为空
     * @param config
     * @param reqData
     * @param result
     * @throws Exception
     */
    private static String addThirdWeixinPayOrder(String modelName, String modelId, String fdKey, WeixinWorkConfig config,Map<String, String> reqData, Map<String, String> result, String merchantId, String tradeType) throws Exception {
        ThirdWeixinPayOrder order = new ThirdWeixinPayOrder();
        order.setFdId(reqData.get("out_trade_no"));
        order.setDocAlterTime(new Date());
        order.setDocCreateTime(new Date());
        order.setDocCreator(UserUtil.getUser());
        order.setFdAppId(config.getWxCorpid());
        order.setFdBody(reqData.get("body"));
        order.setFdKey(fdKey);
        order.setFdMchId(merchantId);
        order.setFdModelId(modelId);
        order.setFdModelName(modelName);
        order.setFdOpenid(reqData.get("openid"));
        order.setFdTotalFee(Integer.parseInt(reqData.get("total_fee")));
        order.setFdTradeType(tradeType);
        order.setFdPrepayId(result.get("prepay_id"));
        order.setFdCodeUrl(result.get("code_url"));
        return getThirdWeixinPayOrderService().add(order);
    }

    private static Boolean toBoolean(String str){
        if(StringUtil.isNull(str)){
            return null;
        }
        str = str.toLowerCase();
        if("true".equals(str) || "y".equals(str) || "1".equals(str)){
            return true;
        }
        if("false".equals(str) || "n".equals(str) || "0".equals(str)){
            return false;
        }
        return null;
    }

    /**
     * 更新订单支付信息
     * @param modelName
     * @param modelId
     * @param fdKey
     * @return
     * @throws Exception
     */
    public static Map<String,String> updateOrderPayData(String modelName,String modelId, String fdKey) throws Exception {
        Map<String,String> result = orderQuery(modelName,modelId,fdKey);
        if(result==null){
            throw new Exception("获取订单信息失败");
        }
        ThirdWeixinPayOrder order = getThirdWeixinPayOrderService().findOrder(modelName,modelId,fdKey);
        order.setFdTradeTypeReturn(result.get("trade_type"));
        order.setFdBankType(result.get("bank_type"));
        order.setFdFeeTypeReturn(result.get("fee_type"));
        order.setFdCashFeeType(result.get("cash_fee_type"));
        order.setFdCashFee(result.get("cash_fee")==null?null:Integer.parseInt(result.get("cash_fee")));
        order.setFdIsSubscribe(toBoolean(result.get("is_subscribe")));
        order.setFdTotalFeeReturn(result.get("total_fee")==null?null:Integer.parseInt(result.get("total_fee")));
        order.setFdTradeState(result.get("trade_state"));
        order.setFdTradeStateDesc(result.get("trade_state_desc"));
        order.setFdTimeEnd(result.get("time_end"));
        order.setFdTransactionId(result.get("transaction_id"));
        if(order.getFdOpenid()==null){
            String openid = result.get("openid");
            if(StringUtil.isNotNull(openid)){
                order.setFdOpenid(openid);
                WxworkOmsRelationModel relationModel = wxworkOmsRelationService.findByOpenId(openid);
                result.put("ekpId",relationModel==null?null:relationModel.getFdEkpId());
            }
        }
        getThirdWeixinPayOrderService().update(order);
        return result;
    }

    /**
     * 查询订单信息
     * @param modelName 主文档类名
     * @param modelId 主文档ID
     * @param fdKey 业务标识，可为空
     * @return
     * @throws Exception
     */
    public static Map<String,String> orderQuery(String modelName,String modelId, String fdKey) throws Exception {
        if(StringUtil.isNull(modelName)||StringUtil.isNull(modelId)){
            throw new Exception("modelName 和 modelId 不能为空");
        }
        ThirdWeixinPayOrder order = getThirdWeixinPayOrderService().findOrder(modelName,modelId,fdKey);
        if(order==null){
            return null;
        }
        WeixinWorkConfig weixinWorkConfig = WeixinWorkConfig.newInstance();
        checkWxPayEnable(weixinWorkConfig);
        ThirdWeixinPayLog thirdWeixinPayLog = new ThirdWeixinPayLog();
        String errorMsg = null;
        Map<String, String> resultMap = null;
        Map<String, String> reqData = new HashMap<>();
        try {
            IWXPayDomain wxPayDomain = new WXPayDomainImp();
            String key = weixinWorkConfig.getWxPayKey();
            WXPayConfig config = new WXPayConfigImp(weixinWorkConfig.getWxCorpid(), order.getFdMchId(), key, wxPayDomain, null);
            WXPay wxPay = new WXPay(config, ResourceUtil.getKmssConfigString("kmss.urlPrefix") + "/resource/third/wx/payEndpoint.do");
            wxPay.setThirdWeixinPayLog(thirdWeixinPayLog);
            reqData.put("out_trade_no", order.getFdId());
            resultMap = wxPay.orderQuery(reqData);
            //{transaction_id=4200001143202109293221746590, nonce_str=y9hLSQxpdDbO3ohr, trade_state=SUCCESS, bank_type=OTHERS, openid=ocOsnt1e6_X_bfzObkRCubtSNqmU, sign=B66D9E1CCB208E53D62B21568081ED7060158FE06A47DD9526B63215DB247FF0, return_msg=OK, fee_type=CNY, mch_id=1580697801, cash_fee=20, out_trade_no=17bf6f1089a5b4efa2abf1743969d092, cash_fee_type=CNY, appid=wx0ba3f3f366212a10, total_fee=20, trade_state_desc=支付成功, trade_type=JSAPI, result_code=SUCCESS, attach=, time_end=20210929165800, is_subscribe=N, return_code=SUCCESS}
            logger.debug(resultMap.toString());
            return resultMap;
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            errorMsg = e.getMessage();
            throw e;
        }finally {
            addThirdWeixinPayLog(thirdWeixinPayLog,new WeixinWorkConfig(),reqData,resultMap,errorMsg,null);
        }
    }

    private static Map<String,String> closeOrder(String modelName,String modelId,String fdKey) throws Exception {
        if(StringUtil.isNull(modelName)||StringUtil.isNull(modelId)){
            throw new Exception("modelName 和 modelId 不能为空");
        }
        ThirdWeixinPayOrder order = getThirdWeixinPayOrderService().findOrder(modelName,modelId,fdKey);
        if(order==null){
            throw new Exception("找不到对应的订单");
        }
        WeixinWorkConfig weixinWorkConfig = WeixinWorkConfig.newInstance();
        checkWxPayEnable(weixinWorkConfig);
        ThirdWeixinPayLog thirdWeixinPayLog = new ThirdWeixinPayLog();
        String errorMsg = null;
        Map<String, String> resultMap = null;
        Map<String, String> reqData = new HashMap<>();
        try {
            IWXPayDomain wxPayDomain = new WXPayDomainImp();
            WXPayConfig config = new WXPayConfigImp(weixinWorkConfig.getWxCorpid(), order.getFdMchId(), weixinWorkConfig.getWxPayKey(), wxPayDomain, weixinWorkConfig.getWxPayCertFilePath());
            WXPay wxPay = new WXPay(config, ResourceUtil.getKmssConfigString("kmss.urlPrefix") + "/resource/third/wx/payEndpoint.do");
            wxPay.setThirdWeixinPayLog(thirdWeixinPayLog);
            reqData.put("out_trade_no", order.getFdId());
            resultMap = wxPay.closeOrder(reqData);
            return resultMap;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            errorMsg = e.getMessage();
            throw e;
        }finally {
            addThirdWeixinPayLog(thirdWeixinPayLog,new WeixinWorkConfig(),reqData,resultMap,errorMsg,null);
        }

    }

}
