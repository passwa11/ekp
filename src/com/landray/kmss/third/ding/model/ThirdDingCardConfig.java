package com.landray.kmss.third.ding.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attachment.model.IAttachment;
import java.util.Date;
import com.landray.kmss.third.ding.forms.ThirdDingCardConfigForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
  * 卡片管理
  */
public class ThirdDingCardConfig extends BaseModel implements InterceptFieldEnabled, IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date docCreateTime;

    private Date docAlterTime;

    private String fdName;

    private String fdCardId;

    private String fdModelName;

    private String fdModelNameText;

    private String fdTemplateName;

    private String fdTemplateId;

    private String fdConfig;

    private String fdType;

    private String fdStatus;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdDingCardConfigForm> getFormClass() {
        return ThirdDingCardConfigForm.class;
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
     * 所属模块
     */
    public String getFdModelName() {
        return this.fdModelName;
    }

    /**
     * 所属模块
     */
    public void setFdModelName(String fdModelName) {
        this.fdModelName = fdModelName;
    }

    /**
     * 所属模块名称
     */
    public String getFdModelNameText() {
        return this.fdModelNameText;
    }

    /**
     * 所属模块名称
     */
    public void setFdModelNameText(String fdModelNameText) {
        this.fdModelNameText = fdModelNameText;
    }

    /**
     * 表单模板
     */
    public String getFdTemplateName() {
        return this.fdTemplateName;
    }

    /**
     * 表单模板
     */
    public void setFdTemplateName(String fdTemplateName) {
        this.fdTemplateName = fdTemplateName;
    }

    /**
     * 表单模板ID
     */
    public String getFdTemplateId() {
        return fdTemplateId;
    }

    public void setFdTemplateId(String fdTemplateId) {
        this.fdTemplateId = fdTemplateId;
    }

    /**
     * 参数配置
     */
    public String getFdConfig() {
        return (String) readLazyField("fdConfig", this.fdConfig);
    }

    /**
     * 参数配置
     */
    public void setFdConfig(String fdConfig) {
        this.fdConfig = (String) writeLazyField("fdConfig", this.fdConfig, fdConfig);
    }

    /**
     * 卡片类型
     */
    public String getFdType() {
        return this.fdType;
    }

    /**
     * 卡片类型
     */
    public void setFdType(String fdType) {
        this.fdType = fdType;
    }

    /**
     * 卡片状态
     */
    public String getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 卡片状态
     */
    public void setFdStatus(String fdStatus) {
        this.fdStatus = fdStatus;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
