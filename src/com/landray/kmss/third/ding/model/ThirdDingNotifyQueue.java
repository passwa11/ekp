package com.landray.kmss.third.ding.model;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.third.ding.forms.ThirdDingNotifyQueueForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;

/**
  * 通知队列
  */
public class ThirdDingNotifyQueue extends BaseModel implements InterceptFieldEnabled, IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String docSubject;

    private String fdAppType;

    private String fdTodoId;

    private String fdUserids;

    private Long fdEndTime;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdDingNotifyQueueForm> getFormClass() {
        return ThirdDingNotifyQueueForm.class;
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
        return (String) readLazyField("fdUserids", this.fdUserids);
    }

    /**
     * 钉钉id
     */
    public void setFdUserids(String fdUserids) {
        this.fdUserids = (String) writeLazyField("fdUserids", this.fdUserids, fdUserids);
    }

    /**
     * 结束时间
     */
    public Long getFdEndTime() {
        return this.fdEndTime;
    }

    /**
     * 结束时间
     */
    public void setFdEndTime(Long fdEndTime) {
        this.fdEndTime = fdEndTime;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
