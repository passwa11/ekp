package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.ding.model.ThirdDingNotifylog;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 钉钉待办日志表
  */
public class ThirdDingNotifylogForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docSubject;

    private String fdNotifyId;

    private String fdNotifyData;

    private String fdSendTime;

    private String fdRtnMsg;

    private String fdRtnTime;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        docSubject = null;
        fdNotifyId = null;
        fdNotifyData = null;
        fdSendTime = null;
        fdRtnMsg = null;
        fdRtnTime = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingNotifylog> getModelClass() {
        return ThirdDingNotifylog.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdSendTime",
					new FormConvertor_Common("fdSendTime").setDateTimeType(DateUtil.TYPE_DATETIME));
			toModelPropertyMap.put("fdRtnTime",
					new FormConvertor_Common("fdRtnTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toModelPropertyMap;
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
    public String getFdSendTime() {
        return this.fdSendTime;
    }

    /**
     * 推送时间
     */
    public void setFdSendTime(String fdSendTime) {
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
    public String getFdRtnTime() {
        return this.fdRtnTime;
    }

    /**
     * 返回时间
     */
    public void setFdRtnTime(String fdRtnTime) {
        this.fdRtnTime = fdRtnTime;
    }
}
