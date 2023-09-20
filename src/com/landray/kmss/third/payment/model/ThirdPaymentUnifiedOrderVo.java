package com.landray.kmss.third.payment.model;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

import java.io.Serializable;

public class ThirdPaymentUnifiedOrderVo implements Serializable {

    private String modelName;
    private String modelId;
    private String fdKey;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getFdKey() {
        return fdKey;
    }

    public void setFdKey(String fdKey) {
        this.fdKey = fdKey;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(String paymentWay) {
        this.paymentWay = paymentWay;
    }

    public Integer getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(Integer paymentService) {
        this.paymentService = paymentService;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public SysOrgPerson getToUser() {
        return toUser;
    }

    public void setToUser(SysOrgPerson toUser) {
        this.toUser = toUser;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    //必填，订单描述
    private String desc;
    //支付方式，目前只支持wxworkpay
    private String paymentWay;
    //支付服务，参考ThirdPaymentConstant
    private Integer paymentService;
    //支付金额，以分为单位
    private Double money;
    //付款用户
    private SysOrgPerson toUser;
    //商户ID
    private String merchantId;

    public ThirdPaymentUnifiedOrderVo(String modelName, String modelId, String fdKey, String desc, String paymentWay, Integer paymentService, Double money, SysOrgPerson toUser, String merchantId) {
        this.modelName = modelName;
        this.modelId = modelId;
        this.fdKey = fdKey;
        this.desc = desc;
        this.paymentWay = paymentWay;
        this.paymentService = paymentService;
        this.money = money;
        this.toUser = toUser;
        this.merchantId = merchantId;
    }

    public ThirdPaymentUnifiedOrderVo(String modelName, String modelId, String fdKey, String desc, String paymentWay, Integer paymentService, Double money, SysOrgPerson toUser, String merchantId, String tradeType) {
        this(modelName,modelId,fdKey,desc,paymentWay,paymentService,money,toUser,merchantId);
        this.tradeType = tradeType;
    }

   public JSONObject toJSON(){
        JSONObject o = new JSONObject();
        o.put("modelName",modelName);
        o.put("modelId",modelId);
        o.put("fdKey",fdKey);
        o.put("desc",desc);
        o.put("paymentWay",paymentWay);
        o.put("paymentService",paymentService);
        o.put("money",money);
        o.put("toUserId",toUser==null?null:toUser.getFdId());
        o.put("merchantId",merchantId);
        o.put("tradeType",tradeType);
        return o;
    }

    @Override
    public String toString(){
        return toJSON().toString();
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    private String tradeType = "JSAPI";

}
