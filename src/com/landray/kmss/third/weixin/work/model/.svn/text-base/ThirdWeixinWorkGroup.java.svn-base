package com.landray.kmss.third.weixin.work.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.third.weixin.work.forms.ThirdWeixinWorkGroupForm;
import com.landray.kmss.util.AutoHashMap;

/**
  * 企业微信群组映射
  */
public class ThirdWeixinWorkGroup extends BaseModel implements IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    /**
     * 主文档modelName
     */
    private String fdModelName;

    /**
     * 主文档modelId
     */
    private String fdModelId;

    /**
     * 微信群组ID
     */
    private String fdGroupId;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdWeixinWorkGroupForm> getFormClass() {
        return ThirdWeixinWorkGroupForm.class;
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
     * 模块名
     */
    public String getFdModelName() {
        return this.fdModelName;
    }

    /**
     * 模块名
     */
    public void setFdModelName(String fdModelName) {
        this.fdModelName = fdModelName;
    }

    /**
     * 模块ID
     */
    public String getFdModelId() {
        return this.fdModelId;
    }

    /**
     * 模块ID
     */
    public void setFdModelId(String fdModelId) {
        this.fdModelId = fdModelId;
    }

    /**
     * 群组ID
     */
    public String getFdGroupId() {
        return this.fdGroupId;
    }

    /**
     * 群组ID
     */
    public void setFdGroupId(String fdGroupId) {
        this.fdGroupId = fdGroupId;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
