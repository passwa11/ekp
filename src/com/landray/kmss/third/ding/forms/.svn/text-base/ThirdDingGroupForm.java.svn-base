package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.ding.model.ThirdDingGroup;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 群聊配置表
  */
public class ThirdDingGroupForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdModelName;

    private String fdModelId;

    private String fdGroupId;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdModelName = null;
        fdModelId = null;
        fdGroupId = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingGroup> getModelClass() {
        return ThirdDingGroup.class;
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
