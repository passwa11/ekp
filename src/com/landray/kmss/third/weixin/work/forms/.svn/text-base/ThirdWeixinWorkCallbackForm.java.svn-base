package com.landray.kmss.third.weixin.work.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinWorkCallback;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 企业微信回调
  */
public class ThirdWeixinWorkCallbackForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdEventType;

    private String fdEventTypeTip;

    private String fdEventTime;

    private String docContent;

    private String fdIsSuccess;

    private String docCreateTime;

    private String fdErrorInfo;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdEventType = null;
        fdEventTypeTip = null;
        fdEventTime = null;
        docContent = null;
        fdIsSuccess = null;
        docCreateTime = null;
        fdErrorInfo = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWeixinWorkCallback> getModelClass() {
        return ThirdWeixinWorkCallback.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
        }
        return toModelPropertyMap;
    }

    /**
     * 回调事件
     */
    public String getFdEventType() {
        return this.fdEventType;
    }

    /**
     * 回调事件
     */
    public void setFdEventType(String fdEventType) {
        this.fdEventType = fdEventType;
    }

    /**
     * 事件说明
     */
    public String getFdEventTypeTip() {
        return this.fdEventTypeTip;
    }

    /**
     * 事件说明
     */
    public void setFdEventTypeTip(String fdEventTypeTip) {
        this.fdEventTypeTip = fdEventTypeTip;
    }

    /**
     * 回调时间
     */
    public String getFdEventTime() {
        return this.fdEventTime;
    }

    /**
     * 回调时间
     */
    public void setFdEventTime(String fdEventTime) {
        this.fdEventTime = fdEventTime;
    }

    /**
     * 回调内容
     */
    public String getDocContent() {
        return this.docContent;
    }

    /**
     * 回调内容
     */
    public void setDocContent(String docContent) {
        this.docContent = docContent;
    }

    /**
     * 状态
     */
    public String getFdIsSuccess() {
        return this.fdIsSuccess;
    }

    /**
     * 状态
     */
    public void setFdIsSuccess(String fdIsSuccess) {
        this.fdIsSuccess = fdIsSuccess;
    }

    /**
     * 创建时间
     */
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(String docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 错误信息
     */
    public String getFdErrorInfo() {
        return this.fdErrorInfo;
    }

    /**
     * 错误信息
     */
    public void setFdErrorInfo(String fdErrorInfo) {
        this.fdErrorInfo = fdErrorInfo;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
