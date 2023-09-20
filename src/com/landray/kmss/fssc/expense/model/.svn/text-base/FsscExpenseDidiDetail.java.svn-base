package com.landray.kmss.fssc.expense.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.fssc.expense.forms.FsscExpenseDidiDetailForm;

/**
  * 滴滴用车信息
  */
public class FsscExpenseDidiDetail extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdPassenger;

    private String fdStartPlace;

    private String fdEndPlace;

    private String fdStartTime;

    private String fdEndTime;

    private Double fdMoney;

    private String fdOrderId;

    private String fdCarLevel;

    private FsscExpenseMain docMain;

    private Integer docIndex;

    @Override
    public Class<FsscExpenseDidiDetailForm> getFormClass() {
        return FsscExpenseDidiDetailForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docMain.docSubject", "docMainName");
            toFormPropertyMap.put("docMain.fdId", "docMainId");
        }
        return toFormPropertyMap;
    }

    /**
     * 乘车人
     */
    public String getFdPassenger() {
        return this.fdPassenger;
    }

    /**
     * 乘车人
     */
    public void setFdPassenger(String fdPassenger) {
        this.fdPassenger = fdPassenger;
    }

    /**
     * 出发地
     */
    public String getFdStartPlace() {
        return this.fdStartPlace;
    }

    /**
     * 出发地
     */
    public void setFdStartPlace(String fdStartPlace) {
        this.fdStartPlace = fdStartPlace;
    }

    /**
     * 到达地
     */
    public String getFdEndPlace() {
        return this.fdEndPlace;
    }

    /**
     * 到达地
     */
    public void setFdEndPlace(String fdEndPlace) {
        this.fdEndPlace = fdEndPlace;
    }

    /**
     * 出发时间
     */
    public String getFdStartTime() {
        return this.fdStartTime;
    }

    /**
     * 出发时间
     */
    public void setFdStartTime(String fdStartTime) {
        this.fdStartTime = fdStartTime;
    }

    /**
     * 到达时间
     */
    public String getFdEndTime() {
        return this.fdEndTime;
    }

    /**
     * 到达时间
     */
    public void setFdEndTime(String fdEndTime) {
        this.fdEndTime = fdEndTime;
    }

    /**
     * 金额
     */
    public Double getFdMoney() {
        return this.fdMoney;
    }

    /**
     * 金额
     */
    public void setFdMoney(Double fdMoney) {
        this.fdMoney = fdMoney;
    }

    /**
     * 订单ID
     */
    public String getFdOrderId() {
        return this.fdOrderId;
    }

    /**
     * 订单ID
     */
    public void setFdOrderId(String fdOrderId) {
        this.fdOrderId = fdOrderId;
    }

    /**
     * 车型
     */
    public String getFdCarLevel() {
        return this.fdCarLevel;
    }

    /**
     * 车型
     */
    public void setFdCarLevel(String fdCarLevel) {
        this.fdCarLevel = fdCarLevel;
    }

    public FsscExpenseMain getDocMain() {
        return this.docMain;
    }

    public void setDocMain(FsscExpenseMain docMain) {
        this.docMain = docMain;
    }

    public Integer getDocIndex() {
        return this.docIndex;
    }

    public void setDocIndex(Integer docIndex) {
        this.docIndex = docIndex;
    }
}
