package com.landray.kmss.third.feishu.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import java.util.Date;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.feishu.forms.ThirdFeishuNotifyLogForm;

/**
  * 待办推送日志
  */
public class ThirdFeishuNotifyLog extends BaseModel implements InterceptFieldEnabled {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String docSubject;

    private Date docCreateTime;

    private String fdMessageId;

    private String fdNotifyId;

    private String fdReqData;

    private String fdResData;

    private Date fdRtnTime;

    private Long fdExpireTime;

    private Integer fdResult;

    private String fdUrl;

    private String fdErrMsg;

    private Integer fdType;

    @Override
    public Class<ThirdFeishuNotifyLogForm> getFormClass() {
        return ThirdFeishuNotifyLogForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdRtnTime", new ModelConvertor_Common("fdRtnTime").setDateTimeType(DateUtil.TYPE_DATETIME));
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
     * 消息ID
     */
    public String getFdMessageId() {
        return this.fdMessageId;
    }

    /**
     * 消息ID
     */
    public void setFdMessageId(String fdMessageId) {
        this.fdMessageId = fdMessageId;
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
    public Date getFdRtnTime() {
        return this.fdRtnTime;
    }

    /**
     * 响应时间
     */
    public void setFdRtnTime(Date fdRtnTime) {
        this.fdRtnTime = fdRtnTime;
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
     * 日志类型：1-待办，2-审批
     * @return
     */
    public Integer getFdType() {
        return fdType;
    }

    public void setFdType(Integer fdType) {
        this.fdType = fdType;
    }
}
