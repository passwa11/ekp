package com.landray.kmss.third.weixin.work.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.third.weixin.work.forms.ThirdWeixinWorkLivingForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;

/**
  * 微信直播映射表
  */
public class ThirdWeixinWorkLiving extends BaseModel implements IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private Date docCreateTime;

    private String fdModelName;

    private String fdModelId;

    private String fdLivingId;

    private SysOrgPerson docCreator;

    public ThirdWeixinWorkLiving(String fdName, String fdModelName, String fdModelId, String fdLivingId) {
        this.fdName = fdName;
        this.fdModelName = fdModelName;
        this.fdModelId = fdModelId;
        this.fdLivingId = fdLivingId;
    }

    public ThirdWeixinWorkLiving() {
    }

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdWeixinWorkLivingForm> getFormClass() {
        return ThirdWeixinWorkLivingForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
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
    public SysOrgPerson getDocCreator() {
        return this.docCreator;
    }

    /**
     * 创建人
     */
    public void setDocCreator(SysOrgPerson docCreator) {
        this.docCreator = docCreator;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
