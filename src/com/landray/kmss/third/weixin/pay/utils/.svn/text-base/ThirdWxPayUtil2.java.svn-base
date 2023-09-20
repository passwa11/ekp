package com.landray.kmss.third.weixin.pay.utils;

import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.weixin.model.ThirdWeixinPayBl;
import com.landray.kmss.third.weixin.pay.sdk.IWXPayDomain;
import com.landray.kmss.third.weixin.pay.sdk.WXPay;
import com.landray.kmss.third.weixin.pay.sdk.WXPayConfig;
import com.landray.kmss.third.weixin.pay.sdk.impl.WXPayConfigImp;
import com.landray.kmss.third.weixin.pay.sdk.impl.WXPayDomainImp;
import com.landray.kmss.third.weixin.service.IThirdWeixinPayBlService;
import com.landray.kmss.third.weixin.service.IThirdWeixinPayLogService;
import com.landray.kmss.third.weixin.service.IThirdWeixinPayOrderService;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ThirdWxPayUtil2 {

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

    public static void main(String[] args) throws Exception {
        unifiedorder();
        //orderQuery("17bf6f1089a5b4efa2abf1743969d004");
        //closeOrder();
    }

    /**
     *
     * @param modelName 主文档类名
     * @param modelId 主文档ID
     * @param fdKey 业务标识，可为空
     * @param desc  付款描述
     * @param money 金额，以分为单位
     * @param toUser 付款人
     */
    public static void unifiedorder(String modelName, String modelId, String fdKey, String desc, Integer money, SysOrgPerson toUser){
        WeixinWorkConfig config = WeixinWorkConfig.newInstance();

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



    }

    public static void unifiedorder() throws Exception {
        String id = "17bf6f1089a5b4efa2abf1743969d034";
        IWXPayDomain wxPayDomain = new WXPayDomainImp();
        String key = "Landrayruanjiancaiwubu1123456789";
        WXPayConfig config = new WXPayConfigImp("wx0ba3f3f366212a10","1580697801",key,wxPayDomain,null);
        WXPay wxPay = new WXPay(config,"http://www.huangwq.top/resource/third/weixin/thirdWeixinPayCb.do");
        Map<String,String> reqData = new HashMap<>();
        reqData.put("body","企业微信-测试收款4");
        reqData.put("out_trade_no", id);
        reqData.put("total_fee","10");
        reqData.put("notify_url","http://localhost:8080/resource/third/weixin/thirdWeixinPayCb.do");
        reqData.put("trade_type","NATIVE");
        reqData.put("openid","ocOsnt1e6_X_bfzObkRCubtSNqmU");

        Map<String,String> result = wxPay.unifiedOrder(reqData);

        System.out.println(result);
    }

    public static void orderQuery(String id) throws Exception {
        //String id = "17bf6f1089a5b4efa2abf1743969d092";
        IWXPayDomain wxPayDomain = new WXPayDomainImp();
        String key = "Landrayruanjiancaiwubu1123456789";
        WXPayConfig config = new WXPayConfigImp("wx0ba3f3f366212a10","1580697801",key,wxPayDomain,null);
        WXPay wxPay = new WXPay(config,"https://www.huangwq.top/resource/weixin.jsp");
        Map<String,String> reqData = new HashMap<>();
        reqData.put("out_trade_no", id);

        Map<String,String> result = wxPay.orderQuery(reqData);
        wxPay.orderQuery(reqData);
        System.out.println(result);
    }

    public static void closeOrder() throws Exception {
        String id = "17bf6f1089a5b4efa2abf1743969d092";
        IWXPayDomain wxPayDomain = new WXPayDomainImp();
        String key = "Landrayruanjiancaiwubu1123456789";
        WXPayConfig config = new WXPayConfigImp("wx0ba3f3f366212a10","1580697801",key,wxPayDomain,null);
        WXPay wxPay = new WXPay(config,"https://www.huangwq.top/resource/weixin.jsp");
        Map<String,String> reqData = new HashMap<>();
        reqData.put("out_trade_no", id);

        Map<String,String> result = wxPay.orderQuery(reqData);
        wxPay.closeOrder(reqData);
        System.out.println(result);
    }
}
