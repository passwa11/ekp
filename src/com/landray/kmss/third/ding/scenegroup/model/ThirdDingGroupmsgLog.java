package com.landray.kmss.third.ding.scenegroup.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.third.ding.scenegroup.forms.ThirdDingGroupmsgLogForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
  * 群消息
  */
public class ThirdDingGroupmsgLog extends BaseModel implements InterceptFieldEnabled, IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdTitle;

    private Integer fdResult;

    private String fdUrl;

    private String fdReqData;

    private String fdResData;

    private Date fdReqTime;

    private Date fdResTime;

    private Long fdExpireTime;

    private String fdErrMsg;

    private ThirdDingScenegroupMapp fdGroup;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdDingGroupmsgLogForm> getFormClass() {
        return ThirdDingGroupmsgLogForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdReqTime", new ModelConvertor_Common("fdReqTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdResTime", new ModelConvertor_Common("fdResTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdGroup.fdName", "fdGroupName");
            toFormPropertyMap.put("fdGroup.fdId", "fdGroupId");
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
    public String getFdTitle() {
        return this.fdTitle;
    }

    /**
     * 标题
     */
    public void setFdTitle(String fdTitle) {
        this.fdTitle = fdTitle;
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
     * 接口地址
     */
    public String getFdUrl() {
        return this.fdUrl;
    }

    /**
     * 接口地址
     */
    public void setFdUrl(String fdUrl) {
        this.fdUrl = fdUrl;
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
    public Date getFdReqTime() {
        return this.fdReqTime;
    }

    /**
     * 请求时间
     */
    public void setFdReqTime(Date fdReqTime) {
        this.fdReqTime = fdReqTime;
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
     * 所属群
     */
    public ThirdDingScenegroupMapp getFdGroup() {
        return this.fdGroup;
    }

    /**
     * 所属群
     */
    public void setFdGroup(ThirdDingScenegroupMapp fdGroup) {
        this.fdGroup = fdGroup;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
