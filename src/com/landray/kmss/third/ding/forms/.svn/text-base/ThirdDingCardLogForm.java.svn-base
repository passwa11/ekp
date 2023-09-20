package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.ding.model.ThirdDingCardLog;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;

/**
  * 卡片日志
  */
public class ThirdDingCardLogForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String docCreateTime;

    private String fdCardId;

    private String fdRequest;

    private String fdResponse;

    private String fdRequestUrl;

    private String fdStatus;

    private String fdRequestTime;

    private String fdResponseTime;

    private String fdConsumingTime;

    private String fdModelName;
    private String fdModelId;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdName = null;
        docCreateTime = null;
        fdCardId = null;
        fdRequest = null;
        fdResponse = null;
        fdRequestUrl = null;
        fdStatus = null;
        fdRequestTime = null;
        fdResponseTime = null;
        fdConsumingTime = null;
        fdModelName = null;
        fdModelId=null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingCardLog> getModelClass() {
        return ThirdDingCardLog.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdRequestTime", new FormConvertor_Common("fdRequestTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toModelPropertyMap.put("fdResponseTime", new FormConvertor_Common("fdResponseTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toModelPropertyMap;
    }

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
        return this.fdRequest;
    }

    /**
     * 请求入参
     */
    public void setFdRequest(String fdRequest) {
        this.fdRequest = fdRequest;
    }

    /**
     * 请求响应
     */
    public String getFdResponse() {
        return this.fdResponse;
    }

    /**
     * 请求响应
     */
    public void setFdResponse(String fdResponse) {
        this.fdResponse = fdResponse;
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
    public String getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 是否成功
     */
    public void setFdStatus(String fdStatus) {
        this.fdStatus = fdStatus;
    }

    /**
     * 请求时间
     */
    public String getFdRequestTime() {
        return this.fdRequestTime;
    }

    /**
     * 请求时间
     */
    public void setFdRequestTime(String fdRequestTime) {
        this.fdRequestTime = fdRequestTime;
    }

    /**
     * 响应时间
     */
    public String getFdResponseTime() {
        return this.fdResponseTime;
    }

    /**
     * 响应时间
     */
    public void setFdResponseTime(String fdResponseTime) {
        this.fdResponseTime = fdResponseTime;
    }

    /**
     * 请求耗时
     */
    public String getFdConsumingTime() {
        return this.fdConsumingTime;
    }

    /**
     * 请求耗时
     */
    public void setFdConsumingTime(String fdConsumingTime) {
        this.fdConsumingTime = fdConsumingTime;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
