package com.landray.kmss.third.feishu.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.feishu.model.ThirdFeishuMsgMapp;

/**
  * 消息卡片映射
  */
public class ThirdFeishuMsgMappForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdNotifyId;

    private String fdMessageId;

    private String fdUserId;

    private String fdPersonId;

    private String fdTodoSubject;

    private String fdContent;

    private String fdLang;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdNotifyId = null;
        fdMessageId = null;
        fdUserId = null;
        fdPersonId = null;
        fdTodoSubject = null;
        fdContent = null;
        fdLang = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdFeishuMsgMapp> getModelClass() {
        return ThirdFeishuMsgMapp.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
        }
        return toModelPropertyMap;
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
        return this.fdContent;
    }

    /**
     * 消息内容
     */
    public void setFdContent(String fdContent) {
        this.fdContent = fdContent;
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

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
