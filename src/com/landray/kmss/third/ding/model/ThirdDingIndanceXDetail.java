package com.landray.kmss.third.ding.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.ding.forms.ThirdDingIndanceXDetailForm;

/**
  * 流程实例明细表
  */
public class ThirdDingIndanceXDetail extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private String fdType;

    private String fdValue;


    private ThirdDingDinstanceXform docMain;


    private Integer docIndex;

	public Integer getDocIndex() {
		return docIndex;
	}

	public void setDocIndex(Integer docIndex) {
		this.docIndex = docIndex;
	}

	@Override
    public Class<ThirdDingIndanceXDetailForm> getFormClass() {
        return ThirdDingIndanceXDetailForm.class;
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
     * 值
     */
    public String getFdValue() {
        return this.fdValue;
    }

    /**
     * 值
     */
    public void setFdValue(String fdValue) {
        this.fdValue = fdValue;
    }


    /**
     * 流程实例
     */
    public ThirdDingDinstanceXform getDocMain() {
        return this.docMain;
    }

    /**
     * 流程实例
     */
    public void setDocMain(ThirdDingDinstanceXform docMain) {
        this.docMain = docMain;
    }
}
