package com.landray.kmss.third.weixin.work.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.third.weixin.work.forms.ThirdWeixinNotifyLogForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
  * 待办推送日志
  */
public class ThirdWeixinNotifyLog extends BaseModel implements InterceptFieldEnabled, IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdNotifyId;

    private String fdSubject;

    private String fdReqData;

    private String fdResData;

    private Date fdReqDate;

    private Date fdResDate;

    private Integer fdResult;

    private String fdUrl;

    private String fdErrMsg;

    private String fdMethod;

    private String fdApiType;

	private Long fdExpireTime;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdWeixinNotifyLogForm> getFormClass() {
        return ThirdWeixinNotifyLogForm.class;
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
     * 待办ID
     */
    public String getFdNotifyId() {
        return this.fdNotifyId;
    }

    /**
     * 待办ID
     */
    public void setFdNotifyId(String fdNotifyId) {
        this.fdNotifyId = fdNotifyId;
    }

    /**
     * 待办标题
     */
    public String getFdSubject() {
        return this.fdSubject;
    }

    /**
     * 待办标题
     */
    public void setFdSubject(String fdSubject) {
        this.fdSubject = fdSubject;
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
     * 请求方法
     */
    public String getFdMethod() {
        return this.fdMethod;
    }

    /**
     * 请求方法
     */
    public void setFdMethod(String fdMethod) {
        this.fdMethod = fdMethod;
    }

    /**
     * 接口类型
     */
    public String getFdApiType() {
        return this.fdApiType;
    }

    /**
     * 接口类型
     */
    public void setFdApiType(String fdApiType) {
        this.fdApiType = fdApiType;
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

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }

    public String getFdCorpId() {
        return fdCorpId;
    }

    public void setFdCorpId(String fdCorpId) {
        this.fdCorpId = fdCorpId;
    }

    private String fdCorpId;

}
