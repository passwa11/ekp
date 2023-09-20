package com.landray.kmss.fssc.k3.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.fssc.k3.forms.FsscK3SwitchForm;

public class FsscK3Switch extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdValue;

    private String fdProperty;

    @Override
    public Class<FsscK3SwitchForm> getFormClass() {
        return FsscK3SwitchForm.class;
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

