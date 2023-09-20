package com.landray.kmss.eop.basedata.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCacheConfig;

/**
  * 费控缓存存储表
  */
public class EopBasedataCacheConfigForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdProperty;

    private String fdValue;

    private String fdLastLongTime;

    private String fdExpiresIn;

    private String fdRandom;

    private String fdAppType;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdProperty = null;
        fdValue = null;
        fdLastLongTime = null;
        fdExpiresIn = null;
        fdRandom = null;
        fdAppType = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataCacheConfig> getModelClass() {
        return EopBasedataCacheConfig.class;
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
    public String getFdLastLongTime() {
        return this.fdLastLongTime;
    }

    /**
     * 当前值获取时间戳
     */
    public void setFdLastLongTime(String fdLastLongTime) {
        this.fdLastLongTime = fdLastLongTime;
    }

    /**
     * 过期时间（秒）
     */
    public String getFdExpiresIn() {
        return this.fdExpiresIn;
    }

    /**
     * 过期时间（秒）
     */
    public void setFdExpiresIn(String fdExpiresIn) {
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
