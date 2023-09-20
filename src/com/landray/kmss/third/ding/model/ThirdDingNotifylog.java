package com.landray.kmss.third.ding.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.ding.forms.ThirdDingNotifylogForm;
import com.landray.kmss.util.DateUtil;

/**
  * 钉钉待办日志表
  */
public class ThirdDingNotifylog extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String docSubject;

    private String fdNotifyId;

    private String fdNotifyData;

    private Date fdSendTime;

    private String fdRtnMsg;

    private Date fdRtnTime;

    @Override
    public Class<ThirdDingNotifylogForm> getFormClass() {
        return ThirdDingNotifylogForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdSendTime",
					new ModelConvertor_Common("fdSendTime").setDateTimeType(DateUtil.TYPE_DATETIME));
			toFormPropertyMap.put("fdRtnTime",
					new ModelConvertor_Common("fdRtnTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 消息标题
     */
    public String getDocSubject() {
        return this.docSubject;
    }

    /**
     * 消息标题
     */
    public void setDocSubject(String docSubject) {
        this.docSubject = docSubject;
    }

    /**
     * 消息ID
     */
    public String getFdNotifyId() {
        return this.fdNotifyId;
    }

    /**
     * 消息ID
     */
    public void setFdNotifyId(String fdNotifyId) {
        this.fdNotifyId = fdNotifyId;
    }

    /**
     * 推送的数据
     */
    public String getFdNotifyData() {
        return this.fdNotifyData;
    }

    /**
     * 推送的数据
     */
    public void setFdNotifyData(String fdNotifyData) {
        this.fdNotifyData = fdNotifyData;
    }

    /**
     * 推送时间
     */
    public Date getFdSendTime() {
        return this.fdSendTime;
    }

    /**
     * 推送时间
     */
    public void setFdSendTime(Date fdSendTime) {
        this.fdSendTime = fdSendTime;
    }

    /**
     * 返回信息
     */
    public String getFdRtnMsg() {
        return this.fdRtnMsg;
    }

    /**
     * 返回信息
     */
    public void setFdRtnMsg(String fdRtnMsg) {
        this.fdRtnMsg = fdRtnMsg;
    }

    /**
     * 返回时间
     */
    public Date getFdRtnTime() {
        return this.fdRtnTime;
    }

    /**
     * 返回时间
     */
    public void setFdRtnTime(Date fdRtnTime) {
        this.fdRtnTime = fdRtnTime;
    }
}
