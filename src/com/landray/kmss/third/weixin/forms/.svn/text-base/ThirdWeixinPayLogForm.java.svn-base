package com.landray.kmss.third.weixin.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.weixin.model.ThirdWeixinPayLog;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;

/**
  * 支付接口调用日志
  */
public class ThirdWeixinPayLogForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdReqData;

    private String fdResData;

    private String fdReqDate;

    private String fdResDate;

    private String fdResult;

    private String fdUrl;

    private String fdErrMsg;

    private String fdExpireTime;

    private String fdBody;

    private String fdDetail;

    private String fdOutTradeNo;

    private String fdPrepayId;

    private String fdCodeUrl;

    private String fdMchId;

    private String fdAppId;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdReqData = null;
        fdResData = null;
        fdReqDate = null;
        fdResDate = null;
        fdResult = null;
        fdUrl = null;
        fdErrMsg = null;
        fdExpireTime = null;
        fdBody = null;
        fdDetail = null;
        fdOutTradeNo = null;
        fdPrepayId = null;
        fdCodeUrl = null;
        fdMchId = null;
        fdAppId = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWeixinPayLog> getModelClass() {
        return ThirdWeixinPayLog.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdReqDate", new FormConvertor_Common("fdReqDate").setDateTimeType(DateUtil.TYPE_DATETIME));
            toModelPropertyMap.put("fdResDate", new FormConvertor_Common("fdResDate").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toModelPropertyMap;
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
     * 请求时间
     */
    public String getFdReqDate() {
        return this.fdReqDate;
    }

    /**
     * 请求时间
     */
    public void setFdReqDate(String fdReqDate) {
        this.fdReqDate = fdReqDate;
    }

    /**
     * 响应时间
     */
    public String getFdResDate() {
        return this.fdResDate;
    }

    /**
     * 响应时间
     */
    public void setFdResDate(String fdResDate) {
        this.fdResDate = fdResDate;
    }

    /**
     * 结果
     */
    public String getFdResult() {
        return this.fdResult;
    }

    /**
     * 结果
     */
    public void setFdResult(String fdResult) {
        this.fdResult = fdResult;
    }

    /**
     * 请求地址
     */
    public String getFdUrl() {
        return this.fdUrl;
    }

    /**
     * 请求地址
     */
    public void setFdUrl(String fdUrl) {
        this.fdUrl = fdUrl;
    }

    /**
     * 错误信息
     */
    public String getFdErrMsg() {
        return this.fdErrMsg;
    }

    /**
     * 错误信息
     */
    public void setFdErrMsg(String fdErrMsg) {
        this.fdErrMsg = fdErrMsg;
    }

    /**
     * 请求耗时
     */
    public String getFdExpireTime() {
        return this.fdExpireTime;
    }

    /**
     * 请求耗时
     */
    public void setFdExpireTime(String fdExpireTime) {
        this.fdExpireTime = fdExpireTime;
    }

    /**
     * 商品描述
     */
    public String getFdBody() {
        return this.fdBody;
    }

    /**
     * 商品描述
     */
    public void setFdBody(String fdBody) {
        this.fdBody = fdBody;
    }

    /**
     * 商品详情
     */
    public String getFdDetail() {
        return this.fdDetail;
    }

    /**
     * 商品详情
     */
    public void setFdDetail(String fdDetail) {
        this.fdDetail = fdDetail;
    }

    /**
     * 商户订单号
     */
    public String getFdOutTradeNo() {
        return this.fdOutTradeNo;
    }

    /**
     * 商户订单号
     */
    public void setFdOutTradeNo(String fdOutTradeNo) {
        this.fdOutTradeNo = fdOutTradeNo;
    }

    /**
     * 预支付交易会话标识
     */
    public String getFdPrepayId() {
        return this.fdPrepayId;
    }

    /**
     * 预支付交易会话标识
     */
    public void setFdPrepayId(String fdPrepayId) {
        this.fdPrepayId = fdPrepayId;
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
     * 商户号
     */
    public String getFdMchId() {
        return this.fdMchId;
    }

    /**
     * 商户号
     */
    public void setFdMchId(String fdMchId) {
        this.fdMchId = fdMchId;
    }

    /**
     * 公众账号ID
     */
    public String getFdAppId() {
        return this.fdAppId;
    }

    /**
     * 公众账号ID
     */
    public void setFdAppId(String fdAppId) {
        this.fdAppId = fdAppId;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
