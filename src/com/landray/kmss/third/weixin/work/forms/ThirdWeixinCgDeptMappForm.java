package com.landray.kmss.third.weixin.work.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinCgDeptMapp;

/**
  * 企业互联部门映射
  */
public class ThirdWeixinCgDeptMappForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdCorpId;

    private String fdEkpId;

    private String fdWxDeptId;

    private String fdIsAvailable;

    private String docCreateTime;

    private String docAlterTime;

    private String fdDeptName;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdCorpId = null;
        fdEkpId = null;
        fdWxDeptId = null;
        fdIsAvailable = null;
        docCreateTime = null;
        docAlterTime = null;
        fdDeptName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWeixinCgDeptMapp> getModelClass() {
        return ThirdWeixinCgDeptMapp.class;
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
     * 微信部门ID
     */
    public String getFdWxDeptId() {
        return this.fdWxDeptId;
    }

    /**
     * 微信部门ID
     */
    public void setFdWxDeptId(String fdWxDeptId) {
        this.fdWxDeptId = fdWxDeptId;
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
     * 部门名称
     */
    public String getFdDeptName() {
        return this.fdDeptName;
    }

    /**
     * 部门名称
     */
    public void setFdDeptName(String fdDeptName) {
        this.fdDeptName = fdDeptName;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }

    public String getFdEkpPostId() {
        return fdEkpPostId;
    }

    public void setFdEkpPostId(String fdEkpPostId) {
        this.fdEkpPostId = fdEkpPostId;
    }

    private String fdEkpPostId;
}
