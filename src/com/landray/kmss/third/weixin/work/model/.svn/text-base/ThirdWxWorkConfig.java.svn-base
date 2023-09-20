package com.landray.kmss.third.weixin.work.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.weixin.work.forms.ThirdWxWorkConfigForm;

/**
  * 企业微信配置
  */
public class ThirdWxWorkConfig extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private String fdKey;

    private String fdField;

	private String fdValue;

    @Override
    public Class<ThirdWxWorkConfigForm> getFormClass() {
        return ThirdWxWorkConfigForm.class;
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
     * 微信企业标识
     */
    public String getFdKey() {
        return this.fdKey;
    }

    /**
     * 微信企业标识
     */
    public void setFdKey(String fdKey) {
        this.fdKey = fdKey;
    }

    /**
     * 字段名
     */
    public String getFdField() {
        return this.fdField;
    }

    /**
     * 字段名
     */
    public void setFdField(String fdField) {
        this.fdField = fdField;
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
}
