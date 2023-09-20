package com.landray.kmss.third.weixin.work.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinCgUserMapp;

/**
  * 企业互联人员映射
  */
public class ThirdWeixinCgUserMappForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdCorpId;

    private String fdEkpId;

    private String fdIsAvailable;

    private String docCreateTime;

    private String docAlterTime;

    private String fdUserId;

    private String fdOpenUserId;

    private String fdUserName;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdCorpId = null;
        fdEkpId = null;
        fdIsAvailable = null;
        docCreateTime = null;
        docAlterTime = null;
        fdUserId = null;
        fdOpenUserId = null;
        fdUserName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWeixinCgUserMapp> getModelClass() {
        return ThirdWeixinCgUserMapp.class;
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
     * 所属组织
     */
    public String getFdCorpId() {
        return this.fdCorpId;
    }

    /**
     * 所属组织
     */
    public void setFdCorpId(String fdCorpId) {
        this.fdCorpId = fdCorpId;
    }

    /**
     * EKP ID
     */
    public String getFdEkpId() {
        return this.fdEkpId;
    }

    /**
     * EKP ID
     */
    public void setFdEkpId(String fdEkpId) {
        this.fdEkpId = fdEkpId;
    }

    /**
     * 是否有效
     */
    public String getFdIsAvailable() {
        return this.fdIsAvailable;
    }

    /**
     * 是否有效
     */
    public void setFdIsAvailable(String fdIsAvailable) {
        this.fdIsAvailable = fdIsAvailable;
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
     * 企业微信用户ID
     */
    public String getFdUserId() {
        return this.fdUserId;
    }

    /**
     * 企业微信用户ID
     */
    public void setFdUserId(String fdUserId) {
        this.fdUserId = fdUserId;
    }

    /**
     * 用户OpenID
     */
    public String getFdOpenUserId() {
        return this.fdOpenUserId;
    }

    /**
     * 用户OpenID
     */
    public void setFdOpenUserId(String fdOpenUserId) {
        this.fdOpenUserId = fdOpenUserId;
    }

    /**
     * 用户名称
     */
    public String getFdUserName() {
        return this.fdUserName;
    }

    /**
     * 用户名称
     */
    public void setFdUserName(String fdUserName) {
        this.fdUserName = fdUserName;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
