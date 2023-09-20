package com.landray.kmss.third.ding.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.ding.forms.ThirdDingBussForm;

/**
  * 出差明细
  */
public class ThirdDingBuss extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdDate;

    private String fdDuration;

    private ThirdDingLeavelog docMain;

    private Integer docIndex;

    @Override
    public Class<ThirdDingBussForm> getFormClass() {
        return ThirdDingBussForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docMain.fdEkpUserid", "docMainName");
            toFormPropertyMap.put("docMain.fdId", "docMainId");
        }
        return toFormPropertyMap;
    }

    /**
     * 日期
     */
    public String getFdDate() {
        return this.fdDate;
    }

    /**
     * 日期
     */
    public void setFdDate(String fdDate) {
        this.fdDate = fdDate;
    }

    /**
     * 天数
     */
    public String getFdDuration() {
        return this.fdDuration;
    }

    /**
     * 天数
     */
    public void setFdDuration(String fdDuration) {
        this.fdDuration = fdDuration;
    }

    public ThirdDingLeavelog getDocMain() {
        return this.docMain;
    }

    public void setDocMain(ThirdDingLeavelog docMain) {
        this.docMain = docMain;
    }

    public Integer getDocIndex() {
        return this.docIndex;
    }

    public void setDocIndex(Integer docIndex) {
        this.docIndex = docIndex;
    }
}
