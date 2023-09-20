package com.landray.kmss.third.ding.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attachment.model.IAttachment;
import java.util.Date;
import com.landray.kmss.third.ding.forms.ThirdDingCardMappingForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
  * 卡片映射
  */
public class ThirdDingCardMapping extends BaseModel implements InterceptFieldEnabled, IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private String fdCardId;

    private String fdModelId;

    private String fdModelName;

    private String fdReceiverUsers;

    private Date docCreateTime;

    private Date docAlterTime;

    private String fdOutTrackId;

    private String fdFrom;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdDingCardMappingForm> getFormClass() {
        return ThirdDingCardMappingForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
        return (String) readLazyField("fdReceiverUsers", this.fdReceiverUsers);
    }

    /**
     * 接收人
     */
    public void setFdReceiverUsers(String fdReceiverUsers) {
        this.fdReceiverUsers = (String) writeLazyField("fdReceiverUsers", this.fdReceiverUsers, fdReceiverUsers);
    }

    /**
     * 创建时间
     */
    public Date getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 更新时间
     */
    public Date getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(Date docAlterTime) {
        this.docAlterTime = docAlterTime;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
