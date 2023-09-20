package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.ding.model.ThirdDingRoleCateMapp;

/**
  * 角色线分类映射
  */
public class ThirdDingRoleCateMappForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdGroupId;

    private String fdEkpCateId;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdGroupId = null;
        fdEkpCateId = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingRoleCateMapp> getModelClass() {
        return ThirdDingRoleCateMapp.class;
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
