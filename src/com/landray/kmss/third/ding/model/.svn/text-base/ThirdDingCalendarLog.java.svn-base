package com.landray.kmss.third.ding.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import net.sf.cglib.transform.impl.InterceptFieldCallback;
import net.sf.cglib.transform.impl.InterceptFieldEnabled;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.third.ding.forms.ThirdDingCalendarLogForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.util.DateUtil;

/**
  * 钉钉日程同步日志
  */
public class ThirdDingCalendarLog extends BaseModel implements  IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdEkpCalendarId;

    private String fdDingCalendarId;

    private String fdSynWay;

    private String fdOptType;

    private Boolean fdStatus;

    private String fdApiUrl;

    private String fdReqParam;

    private String fdResult;

    private Date fdReqStartTime;

    private Date fdResponseStartTime;

    private Integer fdDealWithTime;

    private String fdErrorMsg;

    private String fdName;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdDingCalendarLogForm> getFormClass() {
        return ThirdDingCalendarLogForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdReqStartTime", new ModelConvertor_Common("fdReqStartTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdResponseStartTime", new ModelConvertor_Common("fdResponseStartTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
    public Boolean getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 同步状态
     */
    public void setFdStatus(Boolean fdStatus) {
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
        return (String) readLazyField("fdReqParam", this.fdReqParam);
    }

    /**
     * 请求报文
     */
    public void setFdReqParam(String fdReqParam) {
        this.fdReqParam = (String) writeLazyField("fdReqParam", this.fdReqParam, fdReqParam);
    }

    /**
     * 响应报文
     */
    public String getFdResult() {
        return (String) readLazyField("fdResult", this.fdResult);
    }

    /**
     * 响应报文
     */
    public void setFdResult(String fdResult) {
        this.fdResult = (String) writeLazyField("fdResult", this.fdResult, fdResult);
    }

    /**
     * 请求开始时间
     */
    public Date getFdReqStartTime() {
        return this.fdReqStartTime;
    }

    /**
     * 请求开始时间
     */
    public void setFdReqStartTime(Date fdReqStartTime) {
        this.fdReqStartTime = fdReqStartTime;
    }

    /**
     * 请求结束时间
     */
    public Date getFdResponseStartTime() {
        return this.fdResponseStartTime;
    }

    /**
     * 请求结束时间
     */
    public void setFdResponseStartTime(Date fdResponseStartTime) {
        this.fdResponseStartTime = fdResponseStartTime;
    }

    /**
     * 处理次数
     */
    public Integer getFdDealWithTime() {
        return this.fdDealWithTime;
    }

    /**
     * 处理次数
     */
    public void setFdDealWithTime(Integer fdDealWithTime) {
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
