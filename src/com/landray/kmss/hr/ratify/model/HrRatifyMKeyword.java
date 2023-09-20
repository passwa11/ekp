package com.landray.kmss.hr.ratify.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.hr.ratify.forms.HrRatifyMKeywordForm;

/**
  * 人事流程文档关键字
  */
public class HrRatifyMKeyword extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String docKeyword;

    private HrRatifyMain fdObject;

    @Override
    public Class<HrRatifyMKeywordForm> getFormClass() {
        return HrRatifyMKeywordForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdObject.docSubject", "fdObjectName");
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
     * 审批文档基本信息
     */
    public HrRatifyMain getFdObject() {
        return this.fdObject;
    }

    /**
     * 审批文档基本信息
     */
    public void setFdObject(HrRatifyMain fdObject) {
        this.fdObject = fdObject;
    }
}
