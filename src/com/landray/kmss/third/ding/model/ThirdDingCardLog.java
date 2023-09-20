package com.landray.kmss.third.ding.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attachment.model.IAttachment;
import java.util.Date;
import com.landray.kmss.third.ding.forms.ThirdDingCardLogForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
  * 卡片日志
  */
public class ThirdDingCardLog extends BaseModel implements InterceptFieldEnabled, IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private Date docCreateTime;

    private String fdCardId;

    private String fdRequest;

    private String fdResponse;

    private String fdRequestUrl;

    private Boolean fdStatus;

    private Date fdRequestTime;

    private Date fdResponseTime;

    private Long fdConsumingTime;

    private String fdModelName;
    private String fdModelId;

    public String getFdModelName() {
        return fdModelName;
    }

    public void setFdModelName(String fdModelName) {
        this.fdModelName = fdModelName;
    }

    public String getFdModelId() {
        return fdModelId;
    }

    public void setFdModelId(String fdModelId) {
        this.fdModelId = fdModelId;
    }

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdDingCardLogForm> getFormClass() {
        return ThirdDingCardLogForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdRequestTime", new ModelConvertor_Common("fdRequestTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdResponseTime", new ModelConvertor_Common("fdResponseTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
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
     * 卡片ID
     */
    public String getFdCardId() {
        return this.fdCardId;
    }

    /**
     * 卡片ID
     */
    public void setFdCardId(String fdCardId) {
        this.fdCardId = fdCardId;
    }

    /**
     * 请求入参
     */
    public String getFdRequest() {
        return (String) readLazyField("fdRequest", this.fdRequest);
    }

    /**
     * 请求入参
     */
    public void setFdRequest(String fdRequest) {
        this.fdRequest = (String) writeLazyField("fdRequest", this.fdRequest, fdRequest);
    }

    /**
     * 请求响应
     */
    public String getFdResponse() {
        return (String) readLazyField("fdResponse", this.fdResponse);
    }

    /**
     * 请求响应
     */
    public void setFdResponse(String fdResponse) {
        this.fdResponse = (String) writeLazyField("fdResponse", this.fdResponse, fdResponse);
    }

    /**
     * 请求地址
     */
    public String getFdRequestUrl() {
        return this.fdRequestUrl;
    }

    /**
     * 请求地址
     */
    public void setFdRequestUrl(String fdRequestUrl) {
        this.fdRequestUrl = fdRequestUrl;
    }

    /**
     * 是否成功
     */
    public Boolean getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 是否成功
     */
    public void setFdStatus(Boolean fdStatus) {
        this.fdStatus = fdStatus;
    }

    /**
     * 请求时间
     */
    public Date getFdRequestTime() {
        return this.fdRequestTime;
    }

    /**
     * 请求时间
     */
    public void setFdRequestTime(Date fdRequestTime) {
        this.fdRequestTime = fdRequestTime;
    }

    /**
     * 响应时间
     */
    public Date getFdResponseTime() {
        return this.fdResponseTime;
    }

    /**
     * 响应时间
     */
    public void setFdResponseTime(Date fdResponseTime) {
        this.fdResponseTime = fdResponseTime;
    }

    /**
     * 请求耗时
     */
    public Long getFdConsumingTime() {
        return this.fdConsumingTime;
    }

    /**
     * 请求耗时
     */
    public void setFdConsumingTime(Long fdConsumingTime) {
        this.fdConsumingTime = fdConsumingTime;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
