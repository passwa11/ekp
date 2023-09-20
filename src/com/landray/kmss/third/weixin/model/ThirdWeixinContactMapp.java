package com.landray.kmss.third.weixin.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.third.weixin.forms.ThirdWeixinContactMappForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;

/**
  * 客户映射
  */
public class ThirdWeixinContactMapp extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdContactName;

    private String fdContactUserId;

    private String fdExternalId;

    private String fdTagId;

    private String fdTagName;

    private Date docCreateTime;

    private Date docAlterTime;

    private String fdCorpId;

    private SysOrgPerson docCreator;


    @Override
    public Class<ThirdWeixinContactMappForm> getFormClass() {
        return ThirdWeixinContactMappForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
    public SysOrgPerson getDocCreator() {
        return this.docCreator;
    }

    /**
     * 创建人
     */
    public void setDocCreator(SysOrgPerson docCreator) {
        this.docCreator = docCreator;
    }


    public Boolean getFdIsDelete() {
        return fdIsDelete;
    }

    public void setFdIsDelete(Boolean fdIsDelete) {
        this.fdIsDelete = fdIsDelete;
    }

    private Boolean fdIsDelete = false;

    public String getFdOrgTypeId() {
        return fdOrgTypeId;
    }

    public void setFdOrgTypeId(String fdOrgTypeId) {
        this.fdOrgTypeId = fdOrgTypeId;
    }

    private String fdOrgTypeId;
}
