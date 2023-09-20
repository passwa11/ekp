package com.landray.kmss.third.ding.model;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.third.ding.forms.ThirdDingRoleCateMappForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;

/**
  * 角色线分类映射
  */
public class ThirdDingRoleCateMapp extends BaseModel implements IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdGroupId;

    private String fdEkpCateId;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdDingRoleCateMappForm> getFormClass() {
        return ThirdDingRoleCateMappForm.class;
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
     * 钉钉角色线分组ID
     */
    public String getFdGroupId() {
        return this.fdGroupId;
    }

    /**
     * 钉钉角色线分组ID
     */
    public void setFdGroupId(String fdGroupId) {
        this.fdGroupId = fdGroupId;
    }

    /**
     * EKP分类ID
     */
    public String getFdEkpCateId() {
        return this.fdEkpCateId;
    }

    /**
     * EKP分类ID
     */
    public void setFdEkpCateId(String fdEkpCateId) {
        this.fdEkpCateId = fdEkpCateId;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
