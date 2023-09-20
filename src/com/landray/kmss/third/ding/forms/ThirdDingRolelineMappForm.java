package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.ding.model.ThirdDingRolelineMapp;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 角色线映射
  */
public class ThirdDingRolelineMappForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdDingRoleId;

    private String fdEkpRoleId;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdDingRoleId = null;
        fdEkpRoleId = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingRolelineMapp> getModelClass() {
        return ThirdDingRolelineMapp.class;
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
