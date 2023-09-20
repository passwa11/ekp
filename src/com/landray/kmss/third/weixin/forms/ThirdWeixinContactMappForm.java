package com.landray.kmss.third.weixin.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.weixin.model.ThirdWeixinContactMapp;

/**
  * 客户映射
  */
public class ThirdWeixinContactMappForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdContactName;

    private String fdContactUserId;

    private String fdExternalId;

    private String fdTagId;

    private String fdTagName;

    private String docCreateTime;

    private String docAlterTime;

    private String fdCorpId;

    private String docCreatorId;

    private String docCreatorName;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdContactName = null;
        fdContactUserId = null;
        fdExternalId = null;
        fdTagId = null;
        fdTagName = null;
        docCreateTime = null;
        docAlterTime = null;
        fdCorpId = null;
        docCreatorId = null;
        docCreatorName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWeixinContactMapp> getModelClass() {
        return ThirdWeixinContactMapp.class;
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
     * 名称
     */
    public String getFdContactName() {
        return this.fdContactName;
    }

    /**
     * 名称
     */
    public void setFdContactName(String fdContactName) {
        this.fdContactName = fdContactName;
    }

    /**
     * 微信客户ID
     */
    public String getFdContactUserId() {
        return this.fdContactUserId;
    }

    /**
     * 微信客户ID
     */
    public void setFdContactUserId(String fdContactUserId) {
        this.fdContactUserId = fdContactUserId;
    }

    /**
     * 外部组织ID
     */
    public String getFdExternalId() {
        return this.fdExternalId;
    }

    /**
     * 外部组织ID
     */
    public void setFdExternalId(String fdExternalId) {
        this.fdExternalId = fdExternalId;
    }

    /**
     * 标签ID
     */
    public String getFdTagId() {
        return this.fdTagId;
    }

    /**
     * 标签ID
     */
    public void setFdTagId(String fdTagId) {
        this.fdTagId = fdTagId;
    }

    /**
     * 标签名称
     */
    public String getFdTagName() {
        return this.fdTagName;
    }

    /**
     * 标签名称
     */
    public void setFdTagName(String fdTagName) {
        this.fdTagName = fdTagName;
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
     * 微信CorpID
     */
    public String getFdCorpId() {
        return this.fdCorpId;
    }

    /**
     * 微信CorpID
     */
    public void setFdCorpId(String fdCorpId) {
        this.fdCorpId = fdCorpId;
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

    public String getFdOrgTypeId() {
        return fdOrgTypeId;
    }

    public void setFdOrgTypeId(String fdOrgTypeId) {
        this.fdOrgTypeId = fdOrgTypeId;
    }

    private String fdOrgTypeId;

    public String getFdIsDelete() {
        return fdIsDelete;
    }

    public void setFdIsDelete(String fdIsDelete) {
        this.fdIsDelete = fdIsDelete;
    }

    private String fdIsDelete;
}
