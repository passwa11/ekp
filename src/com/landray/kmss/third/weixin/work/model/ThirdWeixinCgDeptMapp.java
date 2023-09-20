package com.landray.kmss.third.weixin.work.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.third.weixin.work.forms.ThirdWeixinCgDeptMappForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.util.DateUtil;

/**
  * 企业互联部门映射
  */
public class ThirdWeixinCgDeptMapp extends BaseModel implements IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdCorpId;

    private String fdEkpId;

    public String getFdEkpPostId() {
        return fdEkpPostId;
    }

    public void setFdEkpPostId(String fdEkpPostId) {
        this.fdEkpPostId = fdEkpPostId;
    }

    private String fdEkpPostId;

    private String fdWxDeptId;

    private Boolean fdIsAvailable;

    private Date docCreateTime;

    private Date docAlterTime;

    private String fdDeptName;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdWeixinCgDeptMappForm> getFormClass() {
        return ThirdWeixinCgDeptMappForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
    public Boolean getFdIsAvailable() {
        return this.fdIsAvailable;
    }

    /**
     * 是否有效
     */
    public void setFdIsAvailable(Boolean fdIsAvailable) {
        this.fdIsAvailable = fdIsAvailable;
    }

    /**
     * 创建时间
     */
    public Date getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 更新时间
     */
    public Date getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(Date docAlterTime) {
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
}
