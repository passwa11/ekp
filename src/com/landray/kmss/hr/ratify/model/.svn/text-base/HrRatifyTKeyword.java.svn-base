package com.landray.kmss.hr.ratify.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.hr.ratify.forms.HrRatifyTKeywordForm;

/**
  * 人事流程模板关键字
  */
public class HrRatifyTKeyword extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String docKeyword;

    private HrRatifyTemplate fdObject;

    @Override
    public Class<HrRatifyTKeywordForm> getFormClass() {
        return HrRatifyTKeywordForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdObject.fdName", "fdObjectName");
            toFormPropertyMap.put("fdObject.fdId", "fdObjectId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 关键字
     */
    public String getDocKeyword() {
        return this.docKeyword;
    }

    /**
     * 关键字
     */
    public void setDocKeyword(String docKeyword) {
        this.docKeyword = docKeyword;
    }

    /**
     * 审批文档模板
     */
    public HrRatifyTemplate getFdObject() {
        return this.fdObject;
    }

    /**
     * 审批文档模板
     */
    public void setFdObject(HrRatifyTemplate fdObject) {
        this.fdObject = fdObject;
    }
}
