package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.ding.model.ThirdDingCalendarLog;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;

/**
  * 钉钉日程同步日志
  */
public class ThirdDingCalendarLogForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdEkpCalendarId;

    private String fdDingCalendarId;

    private String fdSynWay;

    private String fdOptType;

    private String fdStatus;

    private String fdApiUrl;

    private String fdReqParam;

    private String fdResult;

    private String fdReqStartTime;

    private String fdResponseStartTime;

    private String fdDealWithTime;

    private String fdErrorMsg;

    private String fdName;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdEkpCalendarId = null;
        fdDingCalendarId = null;
        fdSynWay = null;
        fdOptType = null;
        fdStatus = null;
        fdApiUrl = null;
        fdReqParam = null;
        fdResult = null;
        fdReqStartTime = null;
        fdResponseStartTime = null;
        fdDealWithTime = null;
        fdErrorMsg = null;
        fdName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingCalendarLog> getModelClass() {
        return ThirdDingCalendarLog.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdReqStartTime", new FormConvertor_Common("fdReqStartTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toModelPropertyMap.put("fdResponseStartTime", new FormConvertor_Common("fdResponseStartTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toModelPropertyMap;
    }

    /**
     * EKP日程ID
     */
    public String getFdEkpCalendarId() {
        return this.fdEkpCalendarId;
    }

    /**
     * EKP日程ID
     */
    public void setFdEkpCalendarId(String fdEkpCalendarId) {
        this.fdEkpCalendarId = fdEkpCalendarId;
    }

    /**
     * 钉钉日程ID
     */
    public String getFdDingCalendarId() {
        return this.fdDingCalendarId;
    }

    /**
     * 钉钉日程ID
     */
    public void setFdDingCalendarId(String fdDingCalendarId) {
        this.fdDingCalendarId = fdDingCalendarId;
    }

    /**
     * 同步方向
     */
    public String getFdSynWay() {
        return this.fdSynWay;
    }

    /**
     * 同步方向
     */
    public void setFdSynWay(String fdSynWay) {
        this.fdSynWay = fdSynWay;
    }

    /**
     * 操作
     */
    public String getFdOptType() {
        return this.fdOptType;
    }

    /**
     * 操作
     */
    public void setFdOptType(String fdOptType) {
        this.fdOptType = fdOptType;
    }

    /**
     * 同步状态
     */
    public String getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 同步状态
     */
    public void setFdStatus(String fdStatus) {
        this.fdStatus = fdStatus;
    }

    /**
     * 请求地址
     */
    public String getFdApiUrl() {
        return this.fdApiUrl;
    }

    /**
     * 请求地址
     */
    public void setFdApiUrl(String fdApiUrl) {
        this.fdApiUrl = fdApiUrl;
    }

    /**
     * 请求报文
     */
    public String getFdReqParam() {
        return this.fdReqParam;
    }

    /**
     * 请求报文
     */
    public void setFdReqParam(String fdReqParam) {
        this.fdReqParam = fdReqParam;
    }

    /**
     * 响应报文
     */
    public String getFdResult() {
        return this.fdResult;
    }

    /**
     * 响应报文
     */
    public void setFdResult(String fdResult) {
        this.fdResult = fdResult;
    }

    /**
     * 请求开始时间
     */
    public String getFdReqStartTime() {
        return this.fdReqStartTime;
    }

    /**
     * 请求开始时间
     */
    public void setFdReqStartTime(String fdReqStartTime) {
        this.fdReqStartTime = fdReqStartTime;
    }

    /**
     * 请求结束时间
     */
    public String getFdResponseStartTime() {
        return this.fdResponseStartTime;
    }

    /**
     * 请求结束时间
     */
    public void setFdResponseStartTime(String fdResponseStartTime) {
        this.fdResponseStartTime = fdResponseStartTime;
    }

    /**
     * 处理次数
     */
    public String getFdDealWithTime() {
        return this.fdDealWithTime;
    }

    /**
     * 处理次数
     */
    public void setFdDealWithTime(String fdDealWithTime) {
        this.fdDealWithTime = fdDealWithTime;
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
     * 日程标题
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 日程标题
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
