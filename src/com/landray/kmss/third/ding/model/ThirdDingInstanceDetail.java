package com.landray.kmss.third.ding.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.ding.forms.ThirdDingInstanceDetailForm;

/**
  * 钉钉实例明细表
  */
public class ThirdDingInstanceDetail extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private String fdType;

    private String fdValue;

    private ThirdDingDinstance docMain;

    private Integer docIndex;

    @Override
    public Class<ThirdDingInstanceDetailForm> getFormClass() {
        return ThirdDingInstanceDetailForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docMain.fdName", "docMainName");
            toFormPropertyMap.put("docMain.fdId", "docMainId");
        }
        return toFormPropertyMap;
    }

    /**
     * 控件名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 控件名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 类型
     */
    public String getFdType() {
        return this.fdType;
    }

    /**
     * 类型
     */
    public void setFdType(String fdType) {
        this.fdType = fdType;
    }

    /**
     * 控件值
     */
    public String getFdValue() {
        return this.fdValue;
    }

    /**
     * 控件值
     */
    public void setFdValue(String fdValue) {
        this.fdValue = fdValue;
    }

    public ThirdDingDinstance getDocMain() {
        return this.docMain;
    }

    public void setDocMain(ThirdDingDinstance docMain) {
        this.docMain = docMain;
    }

    public Integer getDocIndex() {
        return this.docIndex;
    }

    public void setDocIndex(Integer docIndex) {
        this.docIndex = docIndex;
    }
}
