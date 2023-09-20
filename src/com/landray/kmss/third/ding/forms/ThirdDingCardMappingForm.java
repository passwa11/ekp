package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.ding.model.ThirdDingCardMapping;

/**
  * 卡片映射
  */
public class ThirdDingCardMappingForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdCardId;

    private String fdModelId;

    private String fdModelName;

    private String fdReceiverUsers;

    private String docCreateTime;

    private String docAlterTime;

    private String fdOutTrackId;

    private String fdFrom;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdName = null;
        fdCardId = null;
        fdModelId = null;
        fdModelName = null;
        fdReceiverUsers = null;
        docCreateTime = null;
        docAlterTime = null;
        fdOutTrackId = null;
        fdFrom = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingCardMapping> getModelClass() {
        return ThirdDingCardMapping.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
        }
        return toModelPropertyMap;
    }

    /**
     * 钉钉卡片唯一ID
     */
    public String getFdOutTrackId() {
        return fdOutTrackId;
    }

    public void setFdOutTrackId(String fdOutTrackId) {
        this.fdOutTrackId = fdOutTrackId;
    }

    /*
     *业务来源
     */
    public String getFdFrom() {
        return fdFrom;
    }

    public void setFdFrom(String fdFrom) {
        this.fdFrom = fdFrom;
    }

    /**
     * 名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 卡片ID
     */
    public String getFdCardId() {
        return this.fdCardId;
    }

    /**
     * 卡片ID
     */
    public void setFdCardId(String fdCardId) {
        this.fdCardId = fdCardId;
    }

    /**
     * 主文档
     */
    public String getFdModelId() {
        return this.fdModelId;
    }

    /**
     * 主文档
     */
    public void setFdModelId(String fdModelId) {
        this.fdModelId = fdModelId;
    }

    /**
     * 模块
     */
    public String getFdModelName() {
        return this.fdModelName;
    }

    /**
     * 模块
     */
    public void setFdModelName(String fdModelName) {
        this.fdModelName = fdModelName;
    }

    /**
     * 接收人
     */
    public String getFdReceiverUsers() {
        return this.fdReceiverUsers;
    }

    /**
     * 接收人
     */
    public void setFdReceiverUsers(String fdReceiverUsers) {
        this.fdReceiverUsers = fdReceiverUsers;
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
     * 更新时间
     */
    public String getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(String docAlterTime) {
        this.docAlterTime = docAlterTime;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
