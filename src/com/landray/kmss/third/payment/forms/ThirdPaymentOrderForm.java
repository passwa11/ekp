package com.landray.kmss.third.payment.forms;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.payment.model.ThirdPaymentOrder;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
  * 交易订单
  */
public class ThirdPaymentOrderForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdModelName;

    private String fdModelId;

    private String fdKey;

    private String fdOrderDesc;

    private String fdTotalMoney;

    private String fdOrderNo;

    private String fdPayType;

    private String docCreateTime;

    private String docAlterTime;

    private String fdPaymentService;

    private String fdPaymentStatus;

    private String fdCodeUrl;

    private String fdPayTime;

    private String fdPayerId;

    private String fdPayerName;

    private String docCreatorId;

    private String docCreatorName;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdModelName = null;
        fdModelId = null;
        fdKey = null;
        fdOrderDesc = null;
        fdTotalMoney = null;
        fdOrderNo = null;
        fdPayType = null;
        docCreateTime = null;
        docAlterTime = null;
        fdPaymentService = null;
        fdPaymentStatus = null;
        fdCodeUrl = null;
        fdPayTime = null;
        fdPayerId = null;
        fdPayerName = null;
        docCreatorId = null;
        docCreatorName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdPaymentOrder> getModelClass() {
        return ThirdPaymentOrder.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("fdPayTime", new FormConvertor_Common("fdPayTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toModelPropertyMap.put("fdPayerId", new FormConvertor_IDToModel("fdPayer", SysOrgElement.class));
        }
        return toModelPropertyMap;
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
    public String getFdTotalMoney() {
        return this.fdTotalMoney;
    }

    /**
     * 订单总金额
     */
    public void setFdTotalMoney(String fdTotalMoney) {
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
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(String docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 更新时间
     */
    public String getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(String docAlterTime) {
        this.docAlterTime = docAlterTime;
    }

    /**
     * 支付服务
     */
    public String getFdPaymentService() {
        return this.fdPaymentService;
    }

    /**
     * 支付服务
     */
    public void setFdPaymentService(String fdPaymentService) {
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
    public String getFdPayTime() {
        return this.fdPayTime;
    }

    /**
     * 支付时间
     */
    public void setFdPayTime(String fdPayTime) {
        this.fdPayTime = fdPayTime;
    }

    /**
     * 付/收款人
     */
    public String getFdPayerId() {
        return this.fdPayerId;
    }

    /**
     * 付/收款人
     */
    public void setFdPayerId(String fdPayerId) {
        this.fdPayerId = fdPayerId;
    }

    /**
     * 付/收款人
     */
    public String getFdPayerName() {
        return this.fdPayerName;
    }

    /**
     * 付/收款人
     */
    public void setFdPayerName(String fdPayerName) {
        this.fdPayerName = fdPayerName;
    }

    /**
     * 创建人
     */
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 创建人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    /**
     * 创建人
     */
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 创建人
     */
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
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

}
