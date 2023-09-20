package com.landray.kmss.eop.basedata.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.forms.EopBasedataCacheConfigForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.util.AutoHashMap;

/**
  * 费控缓存存储表
  */
public class EopBasedataCacheConfig extends BaseModel implements IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdProperty;

    private String fdValue;

    private Long fdLastLongTime;

    private Long fdExpiresIn;

    private String fdRandom;

    private String fdAppType;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<EopBasedataCacheConfigForm> getFormClass() {
        return EopBasedataCacheConfigForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 字段属性
     */
    public String getFdProperty() {
        return this.fdProperty;
    }

    /**
     * 字段属性
     */
    public void setFdProperty(String fdProperty) {
        this.fdProperty = fdProperty;
    }

    /**
     * 字段值
     */
    public String getFdValue() {
        return this.fdValue;
    }

    /**
     * 字段值
     */
    public void setFdValue(String fdValue) {
        this.fdValue = fdValue;
    }

    /**
     * 当前值获取时间戳
     */
    public Long getFdLastLongTime() {
        return this.fdLastLongTime;
    }

    /**
     * 当前值获取时间戳
     */
    public void setFdLastLongTime(Long fdLastLongTime) {
        this.fdLastLongTime = fdLastLongTime;
    }

    /**
     * 过期时间（秒）
     */
    public Long getFdExpiresIn() {
        return this.fdExpiresIn;
    }

    /**
     * 过期时间（秒）
     */
    public void setFdExpiresIn(Long fdExpiresIn) {
        this.fdExpiresIn = fdExpiresIn;
    }

    /**
     * 参数随机字符串
     */
    public String getFdRandom() {
        return this.fdRandom;
    }

    /**
     * 参数随机字符串
     */
    public void setFdRandom(String fdRandom) {
        this.fdRandom = fdRandom;
    }

    /**
     * 对应app
     */
    public String getFdAppType() {
        return this.fdAppType;
    }

    /**
     * 对应app
     */
    public void setFdAppType(String fdAppType) {
        this.fdAppType = fdAppType;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
