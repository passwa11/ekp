package com.landray.kmss.third.ding.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.third.ding.forms.ThirdDingRolelineMappForm;
import com.landray.kmss.util.AutoHashMap;

/**
  * 角色线映射
  */
public class ThirdDingRolelineMapp extends BaseModel implements IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdDingRoleId;

    private String fdEkpRoleId;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdDingRolelineMappForm> getFormClass() {
        return ThirdDingRolelineMappForm.class;
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
     * 钉钉角色ID
     */
    public String getFdDingRoleId() {
        return this.fdDingRoleId;
    }

    /**
     * 钉钉角色ID
     */
    public void setFdDingRoleId(String fdDingRoleId) {
        this.fdDingRoleId = fdDingRoleId;
    }


    /**
     * EKP角色线ID
     */
    public String getFdEkpRoleId() {
        return this.fdEkpRoleId;
    }

    /**
     * EKP角色线ID
     */
    public void setFdEkpRoleId(String fdEkpRoleId) {
        this.fdEkpRoleId = fdEkpRoleId;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
