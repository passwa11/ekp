package com.landray.kmss.third.weixin.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.third.weixin.forms.ThirdWeixinPayLogForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.util.DateUtil;

/**
  * 支付接口调用日志
  */
public class ThirdWeixinPayLog extends BaseModel implements InterceptFieldEnabled, IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdReqData;

    private String fdResData;

    private Date fdReqDate;

    private Date fdResDate;

    private Integer fdResult;

    private String fdUrl;

    private String fdErrMsg;

    private Long fdExpireTime;

    private String fdBody;

    private String fdDetail;

    private String fdOutTradeNo;

    private String fdPrepayId;

    private String fdCodeUrl;

    private String fdMchId;

    private String fdAppId;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdWeixinPayLogForm> getFormClass() {
        return ThirdWeixinPayLogForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdReqDate", new ModelConvertor_Common("fdReqDate").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdResDate", new ModelConvertor_Common("fdResDate").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 请求报文
     */
    public String getFdReqData() {
        return (String) readLazyField("fdReqData", this.fdReqData);
    }

    /**
     * 请求报文
     */
    public void setFdReqData(String fdReqData) {
        this.fdReqData = (String) writeLazyField("fdReqData", this.fdReqData, fdReqData);
    }

    /**
     * 响应报文
     */
    public String getFdResData() {
        return (String) readLazyField("fdResData", this.fdResData);
    }

    /**
     * 响应报文
     */
    public void setFdResData(String fdResData) {
        this.fdResData = (String) writeLazyField("fdResData", this.fdResData, fdResData);
    }

    /**
     * 请求时间
     */
    public Date getFdReqDate() {
        return this.fdReqDate;
    }

    /**
     * 请求时间
     */
    public void setFdReqDate(Date fdReqDate) {
        this.fdReqDate = fdReqDate;
    }

    /**
     * 响应时间
     */
    public Date getFdResDate() {
        return this.fdResDate;
    }

    /**
     * 响应时间
     */
    public void setFdResDate(Date fdResDate) {
        this.fdResDate = fdResDate;
    }

    /**
     * 结果
     */
    public Integer getFdResult() {
        return this.fdResult;
    }

    /**
     * 结果
     */
    public void setFdResult(Integer fdResult) {
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
        return (String) readLazyField("fdErrMsg", this.fdErrMsg);
    }

    /**
     * 错误信息
     */
    public void setFdErrMsg(String fdErrMsg) {
        this.fdErrMsg = (String) writeLazyField("fdErrMsg", this.fdErrMsg, fdErrMsg);
    }

    /**
     * 请求耗时
     */
    public Long getFdExpireTime() {
        return this.fdExpireTime;
    }

    /**
     * 请求耗时
     */
    public void setFdExpireTime(Long fdExpireTime) {
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
        return (String) readLazyField("fdDetail", this.fdDetail);
    }

    /**
     * 商品详情
     */
    public void setFdDetail(String fdDetail) {
        this.fdDetail = (String) writeLazyField("fdDetail", this.fdDetail, fdDetail);
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
