package com.landray.kmss.third.payment.model;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.payment.forms.ThirdPaymentOrderForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

import java.util.Date;

/**
  * 交易订单
  */
public class ThirdPaymentOrder extends BaseModel implements IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdModelName;

    private String fdModelId;

    private String fdKey;

    private String fdOrderDesc;

    private Double fdTotalMoney;

    private String fdOrderNo;

    private String fdPayType;

    private Date docCreateTime;

    private Date docAlterTime;

    private Integer fdPaymentService;

    private String fdPaymentStatus;

    private String fdCodeUrl;

    private Date fdPayTime;

    private SysOrgElement fdPayer;

    private SysOrgPerson docCreator;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdPaymentOrderForm> getFormClass() {
        return ThirdPaymentOrderForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdPayTime", new ModelConvertor_Common("fdPayTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdPayer.fdName", "fdPayerName");
            toFormPropertyMap.put("fdPayer.fdId", "fdPayerId");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 主文档类型
     */
    public String getFdModelName() {
        return this.fdModelName;
    }

    /**
     * 主文档类型
     */
    public void setFdModelName(String fdModelName) {
        this.fdModelName = fdModelName;
    }

    /**
     * 主文档ID
     */
    public String getFdModelId() {
        return this.fdModelId;
    }

    /**
     * 主文档ID
     */
    public void setFdModelId(String fdModelId) {
        this.fdModelId = fdModelId;
    }

    /**
     * 主文档关键字
     */
    public String getFdKey() {
        return this.fdKey;
    }

    /**
     * 主文档关键字
     */
    public void setFdKey(String fdKey) {
        this.fdKey = fdKey;
    }

    /**
     * 订单描述
     */
    public String getFdOrderDesc() {
        return this.fdOrderDesc;
    }

    /**
     * 订单描述
     */
    public void setFdOrderDesc(String fdOrderDesc) {
        this.fdOrderDesc = fdOrderDesc;
    }

    /**
     * 订单总金额
     */
    public Double getFdTotalMoney() {
        return this.fdTotalMoney;
    }

    /**
     * 订单总金额
     */
    public void setFdTotalMoney(Double fdTotalMoney) {
        this.fdTotalMoney = fdTotalMoney;
    }

    /**
     * 订单号
     */
    public String getFdOrderNo() {
        return this.fdOrderNo;
    }

    /**
     * 订单号
     */
    public void setFdOrderNo(String fdOrderNo) {
        this.fdOrderNo = fdOrderNo;
    }

    /**
     * 支付方式
     */
    public String getFdPayType() {
        return this.fdPayType;
    }

    /**
     * 支付方式
     */
    public void setFdPayType(String fdPayType) {
        this.fdPayType = fdPayType;
    }

    /**
     * 创建时间
     */
    public Date getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 更新时间
     */
    public Date getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(Date docAlterTime) {
        this.docAlterTime = docAlterTime;
    }

    /**
     * 支付服务
     */
    public Integer getFdPaymentService() {
        return this.fdPaymentService;
    }

    /**
     * 支付服务
     */
    public void setFdPaymentService(Integer fdPaymentService) {
        this.fdPaymentService = fdPaymentService;
    }

    /**
     * 交易状态
     */
    public String getFdPaymentStatus() {
        return this.fdPaymentStatus;
    }

    /**
     * 交易状态
     */
    public void setFdPaymentStatus(String fdPaymentStatus) {
        this.fdPaymentStatus = fdPaymentStatus;
    }

    /**
     * 二维码链接
     */
    public String getFdCodeUrl() {
        return this.fdCodeUrl;
    }

    /**
     * 二维码链接
     */
    public void setFdCodeUrl(String fdCodeUrl) {
        this.fdCodeUrl = fdCodeUrl;
    }

    /**
     * 支付时间
     */
    public Date getFdPayTime() {
        return this.fdPayTime;
    }

    /**
     * 支付时间
     */
    public void setFdPayTime(Date fdPayTime) {
        this.fdPayTime = fdPayTime;
    }

    /**
     * 付/收款人
     */
    public SysOrgElement getFdPayer() {
        return this.fdPayer;
    }

    /**
     * 付/收款人
     */
    public void setFdPayer(SysOrgElement fdPayer) {
        this.fdPayer = fdPayer;
    }

    /**
     * 创建人
     */
    public SysOrgPerson getDocCreator() {
        return this.docCreator;
    }

    /**
     * 创建人
     */
    public void setDocCreator(SysOrgPerson docCreator) {
        this.docCreator = docCreator;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }

    public String getFdRelateOrderId() {
        return fdRelateOrderId;
    }

    public void setFdRelateOrderId(String fdRelateOrderId) {
        this.fdRelateOrderId = fdRelateOrderId;
    }

    private String fdRelateOrderId;

    public String getFdPaymentStatusDesc() {
        return fdPaymentStatusDesc;
    }

    public void setFdPaymentStatusDesc(String fdPaymentStatusDesc) {
        this.fdPaymentStatusDesc = fdPaymentStatusDesc;
    }

    private String fdPaymentStatusDesc;

    public String getFdTradeType() {
        return fdTradeType;
    }

    public void setFdTradeType(String fdTradeType) {
        this.fdTradeType = fdTradeType;
    }

    private String fdTradeType = null;

    public JSONObject toJson(){
        JSONObject o = new JSONObject();
        o.put("modelName", fdModelName);
        o.put("modelId",fdModelId);
        o.put("fdKey",fdKey);
        o.put("orderDesc",fdOrderDesc);
        o.put("totalMoney",fdTotalMoney);
        o.put("orderNo",fdOrderNo);
        o.put("payType",fdPayType);
        o.put("createTime",docCreateTime);
        o.put("alterTime",docAlterTime);
        o.put("paymentService",fdPaymentService);
        o.put("paymentStatus",fdPaymentStatus);
        o.put("codeUrl",fdCodeUrl);
        o.put("payTime",fdPayTime);
        o.put("payer",fdPayer);
        o.put("creator",docCreator);
        o.put("relateOrderId",fdRelateOrderId);
        o.put("paymentStatusDesc",fdPaymentStatusDesc);
        o.put("tradeType",fdTradeType);
        return o;
    }
}
