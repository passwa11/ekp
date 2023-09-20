package com.landray.kmss.third.feishu.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import com.landray.kmss.third.feishu.forms.ThirdFeishuMsgMappForm;

/**
  * 消息卡片映射
  */
public class ThirdFeishuMsgMapp extends BaseModel
		implements InterceptFieldEnabled {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdNotifyId;

    private String fdMessageId;

    private String fdUserId;

    private String fdPersonId;

    private String fdTodoSubject;

    private String fdContent;

    private String fdLang;

    @Override
    public Class<ThirdFeishuMsgMappForm> getFormClass() {
        return ThirdFeishuMsgMappForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
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
     * 飞书用户ID
     */
    public String getFdUserId() {
        return this.fdUserId;
    }

    /**
     * 飞书用户ID
     */
    public void setFdUserId(String fdUserId) {
        this.fdUserId = fdUserId;
    }

    /**
     * EKP用户ID
     */
    public String getFdPersonId() {
        return this.fdPersonId;
    }

    /**
     * EKP用户ID
     */
    public void setFdPersonId(String fdPersonId) {
        this.fdPersonId = fdPersonId;
    }

    /**
     * 待办标题
     */
    public String getFdTodoSubject() {
        return this.fdTodoSubject;
    }

    /**
     * 待办标题
     */
    public void setFdTodoSubject(String fdTodoSubject) {
        this.fdTodoSubject = fdTodoSubject;
    }

    /**
     * 消息内容
     */
    public String getFdContent() {
        return (String) readLazyField("fdContent", this.fdContent);
    }

    /**
     * 消息内容
     */
    public void setFdContent(String fdContent) {
        this.fdContent = (String) writeLazyField("fdContent", this.fdContent, fdContent);
    }

    /**
     * 语言
     */
    public String getFdLang() {
        return this.fdLang;
    }

    /**
     * 语言
     */
    public void setFdLang(String fdLang) {
        this.fdLang = fdLang;
    }


}
