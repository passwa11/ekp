package com.landray.kmss.eop.basedata.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.forms.EopBasedataSwitchForm;

/**
  * 开关设置
  */
public class EopBasedataSwitch extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdValue;

    private String fdProperty;

    @Override
    public Class<EopBasedataSwitchForm> getFormClass() {
        return EopBasedataSwitchForm.class;
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
     * 字段属性名称
     */
    public String getFdProperty() {
        return this.fdProperty;
    }

    /**
     * 字段属性名称
     */
    public void setFdProperty(String fdProperty) {
        this.fdProperty = fdProperty;
    }
}
