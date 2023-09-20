package com.landray.kmss.third.weixin.work.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinWorkLiving;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;

/**
  * 微信直播映射表
  */
public class ThirdWeixinWorkLivingForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String docCreateTime;

    private String fdModelName;

    private String fdModelId;

    private String fdLivingId;

    private String docCreatorId;

    private String docCreatorName;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdName = null;
        docCreateTime = null;
        fdModelName = null;
        fdModelId = null;
        fdLivingId = null;
        docCreatorId = null;
        docCreatorName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWeixinWorkLiving> getModelClass() {
        return ThirdWeixinWorkLiving.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
        }
        return toModelPropertyMap;
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
     * 模块id
     */
    public String getFdModelId() {
        return this.fdModelId;
    }

    /**
     * 模块id
     */
    public void setFdModelId(String fdModelId) {
        this.fdModelId = fdModelId;
    }

    /**
     * 直播id
     */
    public String getFdLivingId() {
        return this.fdLivingId;
    }

    /**
     * 直播id
     */
    public void setFdLivingId(String fdLivingId) {
        this.fdLivingId = fdLivingId;
    }

    /**
     * 创建人
     */
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 创建人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    /**
     * 创建人
     */
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 创建人
     */
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
