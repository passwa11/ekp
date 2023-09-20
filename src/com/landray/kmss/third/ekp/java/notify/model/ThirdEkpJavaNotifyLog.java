package com.landray.kmss.third.ekp.java.notify.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.third.ekp.java.notify.forms.ThirdEkpJavaNotifyLogForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
  * 待办推送日志
  */
public class ThirdEkpJavaNotifyLog extends BaseModel implements InterceptFieldEnabled, IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String docSubject;

    private Date docCreateTime;

    private String fdNotifyId;

    private String fdReqData;

    private String fdResData;

    private Date fdResTime;

    private Long fdExpireTime;

    private Integer fdResult;

    private String fdErrMsg;

    private Integer fdMethod;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdEkpJavaNotifyLogForm> getFormClass() {
        return ThirdEkpJavaNotifyLogForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdResTime", new ModelConvertor_Common("fdResTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 标题
     */
    public String getDocSubject() {
        return this.docSubject;
    }

    /**
     * 标题
     */
    public void setDocSubject(String docSubject) {
        this.docSubject = docSubject;
    }

    /**
     * 发送时间
     */
    public Date getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 发送时间
     */
    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
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
     * 请求数据
     */
    public String getFdReqData() {
        return (String) readLazyField("fdReqData", this.fdReqData);
    }

    /**
     * 请求数据
     */
    public void setFdReqData(String fdReqData) {
        this.fdReqData = (String) writeLazyField("fdReqData", this.fdReqData, fdReqData);
    }

    /**
     * 响应数据
     */
    public String getFdResData() {
        return (String) readLazyField("fdResData", this.fdResData);
    }

    /**
     * 响应数据
     */
    public void setFdResData(String fdResData) {
        this.fdResData = (String) writeLazyField("fdResData", this.fdResData, fdResData);
    }

    /**
     * 响应时间
     */
    public Date getFdResTime() {
        return this.fdResTime;
    }

    /**
     * 响应时间
     */
    public void setFdResTime(Date fdResTime) {
        this.fdResTime = fdResTime;
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
     * 请求结果
     */
    public Integer getFdResult() {
        return this.fdResult;
    }

    /**
     * 请求结果
     */
    public void setFdResult(Integer fdResult) {
        this.fdResult = fdResult;
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
     * 动作
     */
    public Integer getFdMethod() {
        return this.fdMethod;
    }

    /**
     * 动作
     */
    public void setFdMethod(Integer fdMethod) {
        this.fdMethod = fdMethod;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
