package com.landray.kmss.eop.basedata.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.forms.EopBasedataCustomerContactForm;

/**
  * 联系人
  */
public class EopBasedataCustomerContact extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private String fdPosition;

    private String fdPhone;

    private String fdEmail;

    private String fdAddress;

    private String fdRemarks;

    private Boolean fdIsfirst;

    private EopBasedataCustomer docMain;

    private Integer docIndex;

    @Override
    public Class<EopBasedataCustomerContactForm> getFormClass() {
        return EopBasedataCustomerContactForm.class;
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
     * 姓名
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 姓名
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 职务
     */
    public String getFdPosition() {
        return this.fdPosition;
    }

    /**
     * 职务
     */
    public void setFdPosition(String fdPosition) {
        this.fdPosition = fdPosition;
    }

    /**
     * 联系电话
     */
    public String getFdPhone() {
        return this.fdPhone;
    }

    /**
     * 联系电话
     */
    public void setFdPhone(String fdPhone) {
        this.fdPhone = fdPhone;
    }

    /**
     * 电子邮箱
     */
    public String getFdEmail() {
        return this.fdEmail;
    }

    /**
     * 电子邮箱
     */
    public void setFdEmail(String fdEmail) {
        this.fdEmail = fdEmail;
    }

    /**
     * 联系地址
     */
    public String getFdAddress() {
        return this.fdAddress;
    }

    /**
     * 联系地址
     */
    public void setFdAddress(String fdAddress) {
        this.fdAddress = fdAddress;
    }

    /**
     * 备注
     */
    public String getFdRemarks() {
        return this.fdRemarks;
    }

    /**
     * 备注
     */
    public void setFdRemarks(String fdRemarks) {
        this.fdRemarks = fdRemarks;
    }

    /**
     * 第一联系人
     */
    public Boolean getFdIsfirst() {
        return this.fdIsfirst;
    }

    /**
     * 第一联系人
     */
    public void setFdIsfirst(Boolean fdIsfirst) {
        this.fdIsfirst = fdIsfirst;
    }

    public EopBasedataCustomer getDocMain() {
        return this.docMain;
    }

    public void setDocMain(EopBasedataCustomer docMain) {
        this.docMain = docMain;
    }

    public Integer getDocIndex() {
        return this.docIndex;
    }

    public void setDocIndex(Integer docIndex) {
        this.docIndex = docIndex;
    }
}
