package com.landray.kmss.third.ding.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.ding.forms.ThirdDingTemplateXDetailForm;

/**
  * 流程模板明细
  */
public class ThirdDingTemplateXDetail extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private String fdType;

    private ThirdDingDtemplateXform docMain;

    private String fdDocIndex;

    private Integer docIndex;

    @Override
    public Class<ThirdDingTemplateXDetailForm> getFormClass() {
        return ThirdDingTemplateXDetailForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docMain.fdName", "docMainName");
            toFormPropertyMap.put("docMain.fdId", "docMainId");
            toFormPropertyMap.put("docMain.fdName", "docMainName");
            toFormPropertyMap.put("docMain.fdId", "docMainId");
        }
        return toFormPropertyMap;
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
     * doc_main_id
     */
    public ThirdDingDtemplateXform getDocMain() {
        return this.docMain;
    }

    /**
     * doc_main_id
     */
    public void setDocMain(ThirdDingDtemplateXform docMain) {
        this.docMain = docMain;
    }

    /**
     * 排序
     */
    public String getFdDocIndex() {
        return this.fdDocIndex;
    }

    /**
     * 排序
     */
    public void setFdDocIndex(String fdDocIndex) {
        this.fdDocIndex = fdDocIndex;
    }

    public Integer getDocIndex() {
        return this.docIndex;
    }

    public void setDocIndex(Integer docIndex) {
        this.docIndex = docIndex;
    }
}
