package com.landray.kmss.third.weixin.work.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinWorkGroup;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 企业微信群组记录
  */
public class ThirdWeixinWorkGroupForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdModelName;

    private String fdModelId;

    private String fdGroupId;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdModelName = null;
        fdModelId = null;
        fdGroupId = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWeixinWorkGroup> getModelClass() {
        return ThirdWeixinWorkGroup.class;
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
