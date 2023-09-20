package com.landray.kmss.eop.basedata.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.forms.EopBasedataSupplierAccountForm;

/**
  * 收款账户信息
  */
public class EopBasedataSupplierAccount extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdAccountName;

    private String fdBankName;

    private String fdBankNo;

    private String fdBankAccount;
    
    private String fdAccountAreaCode;

	private String fdAccountAreaName;

    private String fdBankSwift;

    private String fdReceiveCompany;

    private String fdReceiveBankName;

    private String fdReceiveBankAddress;

    private String fdInfo;

    private String fdSupplierArea;

    private EopBasedataSupplier docMain;

    private Integer docIndex;

    @Override
    public Class<EopBasedataSupplierAccountForm> getFormClass() {
        return EopBasedataSupplierAccountForm.class;
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
     * 收款账户名
     */
    public String getFdAccountName() {
        return this.fdAccountName;
    }

    /**
     * 收款账户名
     */
    public void setFdAccountName(String fdAccountName) {
        this.fdAccountName = fdAccountName;
    }

    /**
     * 开户行
     */
    public String getFdBankName() {
        return this.fdBankName;
    }

    /**
     * 开户行
     */
    public void setFdBankName(String fdBankName) {
        this.fdBankName = fdBankName;
    }

    /**
     * 联行号
     */
    public String getFdBankNo() {
        return this.fdBankNo;
    }

    /**
     * 联行号
     */
    public void setFdBankNo(String fdBankNo) {
        this.fdBankNo = fdBankNo;
    }

    /**
     * 账号
     */
    public String getFdBankAccount() {
        return this.fdBankAccount;
    }

    /**
     * 账号
     */
    public void setFdBankAccount(String fdBankAccount) {
        this.fdBankAccount = fdBankAccount;
    }
    
    public String getFdAccountAreaCode() {
		return fdAccountAreaCode;
	}

	public void setFdAccountAreaCode(String fdAccountAreaCode) {
		this.fdAccountAreaCode = fdAccountAreaCode;
	}

	public String getFdAccountAreaName() {
		return fdAccountAreaName;
	}

	public void setFdAccountAreaName(String fdAccountAreaName) {
		this.fdAccountAreaName = fdAccountAreaName;
	}

    /**
     * 收款银行swift号
     */
    public String getFdBankSwift() {
        return this.fdBankSwift;
    }

    /**
     * 收款银行swift号
     */
    public void setFdBankSwift(String fdBankSwift) {
        this.fdBankSwift = fdBankSwift;
    }

    /**
     * 收款公司名称
     */
    public String getFdReceiveCompany() {
        return this.fdReceiveCompany;
    }

    /**
     * 收款公司名称
     */
    public void setFdReceiveCompany(String fdReceiveCompany) {
        this.fdReceiveCompany = fdReceiveCompany;
    }

    /**
     * 收款银行名称（境外）
     */
    public String getFdReceiveBankName() {
        return this.fdReceiveBankName;
    }

    /**
     * 收款银行名称（境外）
     */
    public void setFdReceiveBankName(String fdReceiveBankName) {
        this.fdReceiveBankName = fdReceiveBankName;
    }

    /**
     * 收款银行地址（境外）
     */
    public String getFdReceiveBankAddress() {
        return this.fdReceiveBankAddress;
    }

    /**
     * 收款银行地址（境外）
     */
    public void setFdReceiveBankAddress(String fdReceiveBankAddress) {
        this.fdReceiveBankAddress = fdReceiveBankAddress;
    }

    /**
     * 其他信息
     */
    public String getFdInfo() {
        return this.fdInfo;
    }

    /**
     * 其他信息
     */
    public void setFdInfo(String fdInfo) {
        this.fdInfo = fdInfo;
    }

    /**
     * 所在区域 境内、境外标识，0：境内，1：境外
     */
    public String getFdSupplierArea() {
        return this.fdSupplierArea;
    }

    /**
     * 所在区域 境内、境外标识，0：境内，1：境外
     */
    public void setFdSupplierArea(String fdSupplierArea) {
        this.fdSupplierArea = fdSupplierArea;
    }

    /**
     * 客商主表
     */

    public EopBasedataSupplier getDocMain() {
        return this.docMain;
    }
    /**
     * 客商主表
     */
    public void setDocMain(EopBasedataSupplier docMain) {
        this.docMain = docMain;
    }

    public Integer getDocIndex() {
        return this.docIndex;
    }

    public void setDocIndex(Integer docIndex) {
        this.docIndex = docIndex;
    }
}
