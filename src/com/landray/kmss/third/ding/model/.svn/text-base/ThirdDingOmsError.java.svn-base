package com.landray.kmss.third.ding.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.ding.forms.ThirdDingOmsErrorForm;

/**
  * 同步异常表
  */
public class ThirdDingOmsError extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdOms;

    private String fdOper;

    private String fdEkpName;

    private String fdEkpId;

    private String fdEkpType;

    private String fdDingName;

    private String fdDingId;

    private String fdDingType;

    private String fdDesc;

    @Override
    public Class<ThirdDingOmsErrorForm> getFormClass() {
        return ThirdDingOmsErrorForm.class;
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
     * 同步方
     */
    public String getFdOms() {
        return this.fdOms;
    }

    /**
     * 同步方
     */
    public void setFdOms(String fdOms) {
        this.fdOms = fdOms;
    }

    /**
     * 操作标志
     */
    public String getFdOper() {
        return this.fdOper;
    }

    /**
     * 操作标志
     */
    public void setFdOper(String fdOper) {
        this.fdOper = fdOper;
    }

    /**
     * EKP组织名称
     */
    public String getFdEkpName() {
        return this.fdEkpName;
    }

    /**
     * EKP组织名称
     */
    public void setFdEkpName(String fdEkpName) {
        this.fdEkpName = fdEkpName;
    }

    /**
     * EKP组织
     */
    public String getFdEkpId() {
        return this.fdEkpId;
    }

    /**
     * EKP组织
     */
    public void setFdEkpId(String fdEkpId) {
        this.fdEkpId = fdEkpId;
    }

    /**
     * EKP组织类型
     */
    public String getFdEkpType() {
        return this.fdEkpType;
    }

    /**
     * EKP组织类型
     */
    public void setFdEkpType(String fdEkpType) {
        this.fdEkpType = fdEkpType;
    }

    /**
     * 钉钉组织名称
     */
    public String getFdDingName() {
        return this.fdDingName;
    }

    /**
     * 钉钉组织名称
     */
    public void setFdDingName(String fdDingName) {
        this.fdDingName = fdDingName;
    }

    /**
     * 钉钉组织
     */
    public String getFdDingId() {
        return this.fdDingId;
    }

    /**
     * 钉钉组织
     */
    public void setFdDingId(String fdDingId) {
        this.fdDingId = fdDingId;
    }

    /**
     * 钉钉组织类型
     */
    public String getFdDingType() {
        return this.fdDingType;
    }

    /**
     * 钉钉组织类型
     */
    public void setFdDingType(String fdDingType) {
        this.fdDingType = fdDingType;
    }

    /**
     * 异常描述
     */
    public String getFdDesc() {
        return this.fdDesc;
    }

    /**
     * 异常描述
     */
    public void setFdDesc(String fdDesc) {
        this.fdDesc = fdDesc;
    }
}
