package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.ding.model.ThirdDingCardConfig;

/**
  * 卡片管理
  */
public class ThirdDingCardConfigForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docCreateTime;

    private String docAlterTime;

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
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        docCreateTime = null;
        docAlterTime = null;
        fdName = null;
        fdCardId = null;
        fdModelName = null;
        fdModelNameText = null;
        fdTemplateName = null;
        fdTemplateId = null;
        fdConfig = null;
        fdType = null;
        fdStatus = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingCardConfig> getModelClass() {
        return ThirdDingCardConfig.class;
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
        return this.fdConfig;
    }

    /**
     * 参数配置
     */
    public void setFdConfig(String fdConfig) {
        this.fdConfig = fdConfig;
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
