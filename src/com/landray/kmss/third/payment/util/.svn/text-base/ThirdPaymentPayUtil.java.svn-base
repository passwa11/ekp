package com.landray.kmss.third.payment.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.payment.interfaces.ThirdPaymentWayVo;
import com.landray.kmss.third.payment.model.ThirdPaymentMerchant;
import com.landray.kmss.third.payment.model.ThirdPaymentOrder;
import com.landray.kmss.third.payment.model.ThirdPaymentUnifiedOrderVo;
import com.landray.kmss.third.payment.plugin.ThirdPaymentPlugin;
import com.landray.kmss.third.payment.service.IThirdPaymentApiService;
import com.landray.kmss.third.payment.service.IThirdPaymentMerchantService;
import com.landray.kmss.util.SpringBeanUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThirdPaymentPayUtil {

    public static IThirdPaymentApiService thirdPaymentApiService = null;

    public static IThirdPaymentApiService getThirdPaymentApiService(){
        if(thirdPaymentApiService==null){
            thirdPaymentApiService = (IThirdPaymentApiService)SpringBeanUtil.getBean("thirdPaymentApiService");
        }
        return thirdPaymentApiService;
    }

    public static IThirdPaymentMerchantService thirdPaymentMerchantService = null;

    public static IThirdPaymentMerchantService getThirdPaymentMerchantService(){
        if(thirdPaymentMerchantService==null){
            thirdPaymentMerchantService = (IThirdPaymentMerchantService)SpringBeanUtil.getBean("thirdPaymentMerchantService");
        }
        return thirdPaymentMerchantService;
    }

    /**
     * 支付下单
     *
     * @param modelName  主文档类名，不能为空
     * @param modelId    主文档ID，不能为空
     * @param fdKey      业务标识，可为空
     * @param paymentWay   支付方式，不能为空，比如wxworkpay
     * @param desc       支付服务，参考ThirdPaymentConstant
     * @param desc       付款描述，不能为空
     * @param money      金额，以分为单位
     * @param toUser     付款人，可以为空
     * @param merchantId 微信商户ID，可为空
     * @param tradeType  交易途径，JSAPI、NATIVE、APP
     *                   <p>
     *                   return 订单单号或者支付二维码地址
     */
    public static JSONObject unifiedorder(String modelName, String modelId, String fdKey, String paymentWay, Integer paymentService, String desc, Double money, SysOrgPerson toUser, String merchantId, String tradeType) throws Exception{
        return getThirdPaymentApiService().unifiedorder(modelName,modelId,fdKey,paymentWay,paymentService,desc,money,toUser,merchantId,tradeType);
    }

    public static JSONObject unifiedorder(ThirdPaymentUnifiedOrderVo vo) throws Exception{
        return getThirdPaymentApiService().unifiedorder(vo);
    }
    /**
     * 查询订单信息
     * @param modelName 主文档类名
     * @param modelId 主文档ID
     * @param fdKey 业务标识，可为空
     * @return
     * @throws Exception
     */
    public static JSONObject orderQuery(String modelName, String modelId, String fdKey) throws Exception{
        return getThirdPaymentApiService().orderQuery(modelName,modelId,fdKey);
    }

    /**
     * 关闭订单
     * @param modelName 主文档类名
     * @param modelId 主文档ID
     * @param fdKey 业务标识，可为空
     * @return
     * @throws Exception
     */
    public static JSONObject closeOrder(String modelName,String modelId,String fdKey) throws Exception{
        return getThirdPaymentApiService().closeOrder(modelName,modelId,fdKey);
    }


    /**
     * 更新订单信息
     * @param modelName 主文档类名
     * @param modelId 主文档ID
     * @param fdKey 业务标识，可为空
     * @return
     * @throws Exception
     */
    public static JSONObject updateOrder(String modelName,String modelId,String fdKey) throws Exception{
        return getThirdPaymentApiService().updateOrder(modelName,modelId,fdKey);
    }

    /**
     * 返回可以使用的支付方式，key为支付方式的标识(比如 wxworkpay)，value为支付的名称（比如 微信支付）
     * @return
     * @throws Exception
     */
    public static Map<String,String> getEnabledPaymentWays() throws Exception{
        return getThirdPaymentApiService().getEnabledPaymentWays();
    }

    /**
     * 返回当前客户端下可以使用的支付方式，key为支付方式的标识(比如 wxworkpay)，value为支付的名称（比如 微信支付）
     * @return
     * @throws Exception
     */
    public static Map<String,String> getEnabledPaymentWays(HttpServletRequest request) throws Exception{
        Map<String, String> payWays_result = new HashMap<>();
        int clientType = MobileUtil.getClientType(request);
        Map<String, String> payWays = ThirdPaymentPlugin.getEnabledPaymentWays(clientType+"");
        for(String key:payWays.keySet()){
            if(getThirdPaymentApiService().isPayEnabled(key)){
                payWays_result.put(key,payWays.get(key));
            }
        }
        return payWays_result;
    }

    public static JSONArray getMerchants(Boolean isEnable) throws Exception{
        HQLInfo info = new HQLInfo();
        if(isEnable!=null){
            info.setWhereBlock("fdMerchStatus = :status");
            info.setParameter("status",isEnable==true?1:2);
        }
        List<ThirdPaymentMerchant> list = getThirdPaymentMerchantService().findList(info);
        JSONArray array = new JSONArray();
        for(ThirdPaymentMerchant merchant:list){
            array.add(merchant.toJSON());
        }
        return array;
    }

    public static JSONArray getMerchantsAuth(Boolean isEnable, Integer pageNo, Integer rowSize, String merchName) throws Exception{
        HQLInfo info = new HQLInfo();
        String whereBlock = "1=1";
        if(isEnable!=null){
            whereBlock += " and fdMerchStatus = :status";
            info.setParameter("status",isEnable==true?1:2);
        }
        if(merchName!=null){
            whereBlock += " and fdMerchName like :merchName";
            info.setParameter("merchName","%"+merchName+"%");
        }
        info.setWhereBlock(whereBlock);
        info.setPageNo(pageNo);
        info.setRowSize(rowSize);
        List<ThirdPaymentMerchant> list = getThirdPaymentMerchantService().findPage(info).getList();
        JSONArray array = new JSONArray();
        for(ThirdPaymentMerchant merchant:list){
            array.add(merchant.toJSON());
        }
        return array;
    }

    public static JSONObject updateOrderAndPublishEvent(String modelName,String modelId,String fdKey) throws Exception{
        JSONObject o = getThirdPaymentApiService().updateOrder(modelName,modelId,fdKey);
        o.put("modelName",modelName);
        o.put("modelId",modelId);
        o.put("fdKey",fdKey);
        SpringBeanUtil.getApplicationContext().publishEvent(new Event_Common("thirdPaymentOrderCb",o));
        return o;
    }

    /**
     * 返回可以使用的支付方式，key为支付方式的标识(比如 wxworkpay)，value为支付的名称（比如 微信支付）
     * @return
     * @throws Exception
     */
    public static List<ThirdPaymentWayVo> getPaymentWays(HttpServletRequest request) throws Exception{
        return getThirdPaymentApiService().getPaymentWays(request);
    }

    public static ThirdPaymentOrder getOrder(String modelName, String modelId, String fdKey) throws Exception{
        return getThirdPaymentApiService().getOrder(modelName,modelId,fdKey);
    }

    public static JSONObject getOrderJson(String modelName, String modelId, String fdKey) throws Exception{
        ThirdPaymentOrder order = getOrder(modelName,modelId,fdKey);
        if(order==null){
            return null;
        }
        return order.toJson();
    }

}
