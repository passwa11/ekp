package com.landray.kmss.fssc.eas.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.fssc.eas.model.FsscEasSwitch;
import com.landray.kmss.web.action.ActionMapping;

/**
  * Eas集成配置
  */
public class FsscEasSwitchForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdValue;

    private String fdProperty;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdValue = null;
        fdProperty = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscEasSwitch> getModelClass() {
        return FsscEasSwitch.class;
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
