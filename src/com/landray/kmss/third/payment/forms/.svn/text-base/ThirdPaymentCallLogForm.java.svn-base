package com.landray.kmss.third.payment.forms;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.payment.model.ThirdPaymentCallLog;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
  * 业务调用日志
  */
public class ThirdPaymentCallLogForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdModelName;

    private String fdModelId;

    private String fdKey;

    private String fdOrderDesc;

    private String fdTotalMoney;

    private String fdOrderNo;

    private String fdPayType;

    private String docCreateTime;

    private String fdPaymentService;

    private String fdCallMethod;

    private String fdReqData;

    private String fdResData;

    private String fdErrorMsg;

    private String fdCallResult;

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
        fdPaymentService = null;
        fdCallMethod = null;
        fdReqData = null;
        fdResData = null;
        fdErrorMsg = null;
        fdCallResult = null;
        docCreatorId = null;
        docCreatorName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdPaymentCallLog> getModelClass() {
        return ThirdPaymentCallLog.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
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
     * 调用接口
     */
    public String getFdCallMethod() {
        return this.fdCallMethod;
    }

    /**
     * 调用接口
     */
    public void setFdCallMethod(String fdCallMethod) {
        this.fdCallMethod = fdCallMethod;
    }

    /**
     * 请求报文
     */
    public String getFdReqData() {
        return this.fdReqData;
    }

    /**
     * 请求报文
     */
    public void setFdReqData(String fdReqData) {
        this.fdReqData = fdReqData;
    }

    /**
     * 响应报文
     */
    public String getFdResData() {
        return this.fdResData;
    }

    /**
     * 响应报文
     */
    public void setFdResData(String fdResData) {
        this.fdResData = fdResData;
    }

    /**
     * 错误信息
     */
    public String getFdErrorMsg() {
        return this.fdErrorMsg;
    }

    /**
     * 错误信息
     */
    public void setFdErrorMsg(String fdErrorMsg) {
        this.fdErrorMsg = fdErrorMsg;
    }

    /**
     * 请求结果
     */
    public String getFdCallResult() {
        return this.fdCallResult;
    }

    /**
     * 请求结果
     */
    public void setFdCallResult(String fdCallResult) {
        this.fdCallResult = fdCallResult;
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
}
