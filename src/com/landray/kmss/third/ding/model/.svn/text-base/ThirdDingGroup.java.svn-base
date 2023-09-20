package com.landray.kmss.third.ding.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.ding.forms.ThirdDingGroupForm;

/**
  * 群聊配置表
  */
public class ThirdDingGroup extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdModelName;

    private String fdModelId;

    private String fdGroupId;

    @Override
    public Class<ThirdDingGroupForm> getFormClass() {
        return ThirdDingGroupForm.class;
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
     * 模块name
     */
    public String getFdModelName() {
        return this.fdModelName;
    }

    /**
     * 模块name
     */
    public void setFdModelName(String fdModelName) {
        this.fdModelName = fdModelName;
    }

    /**
     * 文档id
     */
    public String getFdModelId() {
        return this.fdModelId;
    }

    /**
     * 文档id
     */
    public void setFdModelId(String fdModelId) {
        this.fdModelId = fdModelId;
    }

    /**
     * 群id
     */
    public String getFdGroupId() {
        return this.fdGroupId;
    }

    /**
     * 群id
     */
    public void setFdGroupId(String fdGroupId) {
        this.fdGroupId = fdGroupId;
    }
}
