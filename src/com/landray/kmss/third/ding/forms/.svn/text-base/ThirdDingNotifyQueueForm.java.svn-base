package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.ding.model.ThirdDingNotifyQueue;

/**
  * 通知队列
  */
public class ThirdDingNotifyQueueForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docSubject;

    private String fdAppType;

    private String fdTodoId;

    private String fdUserids;

    private String fdEndTime;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        docSubject = null;
        fdAppType = null;
        fdTodoId = null;
        fdUserids = null;
        fdEndTime = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingNotifyQueue> getModelClass() {
        return ThirdDingNotifyQueue.class;
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
     * 消息类型
     */
    public String getFdAppType() {
        return this.fdAppType;
    }

    /**
     * 消息类型
     */
    public void setFdAppType(String fdAppType) {
        this.fdAppType = fdAppType;
    }

    /**
     * 待办Id
     */
    public String getFdTodoId() {
        return this.fdTodoId;
    }

    /**
     * 待办Id
     */
    public void setFdTodoId(String fdTodoId) {
        this.fdTodoId = fdTodoId;
    }

    /**
     * 钉钉id
     */
    public String getFdUserids() {
        return this.fdUserids;
    }

    /**
     * 钉钉id
     */
    public void setFdUserids(String fdUserids) {
        this.fdUserids = fdUserids;
    }

    /**
     * 结束时间
     */
    public String getFdEndTime() {
        return this.fdEndTime;
    }

    /**
     * 结束时间
     */
    public void setFdEndTime(String fdEndTime) {
        this.fdEndTime = fdEndTime;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
