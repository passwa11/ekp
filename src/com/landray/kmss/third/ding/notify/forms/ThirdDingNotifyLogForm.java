package com.landray.kmss.third.ding.notify.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.ding.notify.model.ThirdDingNotifyLog;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 待办同步日志
  */
public class ThirdDingNotifyLogForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docSubject;

    private String fdNotifyId;

    private String fdNotifyData;

    private String fdSendTime;

    private String fdRtnMsg;

    private String fdRtnTime;

    private String fdResult;

    private String fdExpireTime;

    private String fdUrl;

    private String fdRequestId;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        docSubject = null;
        fdNotifyId = null;
        fdNotifyData = null;
        fdSendTime = null;
        fdRtnMsg = null;
        fdRtnTime = null;
        fdResult = null;
        fdExpireTime = null;
        fdUrl = null;
        fdRequestId = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingNotifyLog> getModelClass() {
        return ThirdDingNotifyLog.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdSendTime", new FormConvertor_Common("fdSendTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toModelPropertyMap.put("fdRtnTime", new FormConvertor_Common("fdRtnTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toModelPropertyMap;
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
    public String getFdNotifyData() {
        return this.fdNotifyData;
    }

    /**
     * 请求数据
     */
    public void setFdNotifyData(String fdNotifyData) {
        this.fdNotifyData = fdNotifyData;
    }

    /**
     * 请求开始时间
     */
    public String getFdSendTime() {
        return this.fdSendTime;
    }

    /**
     * 请求开始时间
     */
    public void setFdSendTime(String fdSendTime) {
        this.fdSendTime = fdSendTime;
    }

    /**
     * 请求返回信息
     */
    public String getFdRtnMsg() {
        return this.fdRtnMsg;
    }

    /**
     * 请求返回信息
     */
    public void setFdRtnMsg(String fdRtnMsg) {
        this.fdRtnMsg = fdRtnMsg;
    }

    /**
     * 请求返回时间
     */
    public String getFdRtnTime() {
        return this.fdRtnTime;
    }

    /**
     * 请求返回时间
     */
    public void setFdRtnTime(String fdRtnTime) {
        this.fdRtnTime = fdRtnTime;
    }

    /**
     * 请求结果
     */
    public String getFdResult() {
        return this.fdResult;
    }

    /**
     * 请求结果
     */
    public void setFdResult(String fdResult) {
        this.fdResult = fdResult;
    }

    /**
     * 请求耗时
     */
    public String getFdExpireTime() {
        return this.fdExpireTime;
    }

    /**
     * 请求耗时
     */
    public void setFdExpireTime(String fdExpireTime) {
        this.fdExpireTime = fdExpireTime;
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
     * 请求ID
     */
    public String getFdRequestId() {
        return this.fdRequestId;
    }

    /**
     * 请求ID
     */
    public void setFdRequestId(String fdRequestId) {
        this.fdRequestId = fdRequestId;
    }
}
