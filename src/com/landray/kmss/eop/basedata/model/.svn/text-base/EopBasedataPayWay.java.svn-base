package com.landray.kmss.eop.basedata.model;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.eop.basedata.forms.EopBasedataPayWayForm;
import com.landray.kmss.sys.right.interfaces.ExtendAuthTmpModel;
import com.landray.kmss.util.DateUtil;

/**
  * 付款方式
  */
public class EopBasedataPayWay extends ExtendAuthTmpModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private Integer fdStatus;

    private Integer fdOrder;

    private String fdCode;
    
    private Boolean fdIsDefault;
    
    private Boolean fdIsTransfer;
    
    private EopBasedataAccounts fdAccount;
    
    private EopBasedataPayBank fdDefaultPayBank;
    
    private List<EopBasedataCompany> fdCompanyList = new ArrayList<EopBasedataCompany>();

    @Override
    public Class<EopBasedataPayWayForm> getFormClass() {
        return EopBasedataPayWayForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("fdAccount.fdName", "fdAccountName");
            toFormPropertyMap.put("fdAccount.fdId", "fdAccountId");
            toFormPropertyMap.put("fdDefaultPayBank.fdBankName", "fdDefaultPayBankName");
            toFormPropertyMap.put("fdDefaultPayBank.fdBankAccount", "fdDefaultPayBankAccount");
            toFormPropertyMap.put("fdDefaultPayBank.fdId", "fdDefaultPayBankId");
            toFormPropertyMap.put("fdCompanyList", new ModelConvertor_ModelListToString("fdCompanyListIds:fdCompanyListNames", "fdId:fdName"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 付款方式名称
     */
    @Override
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 付款方式名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 状态
     */
    public Integer getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 状态
     */
    public void setFdStatus(Integer fdStatus) {
        this.fdStatus = fdStatus;
    }

    /**
     * 排序号
     */
    public Integer getFdOrder() {
        return this.fdOrder;
    }

    /**
     * 排序号
     */
    public void setFdOrder(Integer fdOrder) {
        this.fdOrder = fdOrder;
    }

    /**
     * 付款方式编码
     */
    public String getFdCode() {
        return this.fdCode;
    }

    /**
     * 付款方式编码
     */
    public void setFdCode(String fdCode) {
        this.fdCode = fdCode;
    }
    
    /**
     * 是否默认默认付款方式
     */
    public Boolean getFdIsDefault() {
		return fdIsDefault;
	}

    /**
     * 是否默认默认付款方式
     */
	public void setFdIsDefault(Boolean fdIsDefault) {
		this.fdIsDefault = fdIsDefault;
	}
	
	/**
     * 是否涉及转账
     */
    public Boolean getFdIsTransfer() {
		return this.fdIsTransfer;
	}
    
    /**
     * 是否涉及转账
     */
    public void setFdIsTransfer(Boolean fdIsTransfer) {
		this.fdIsTransfer = fdIsTransfer;
	}
    
    /**
     * 对应会计科目
     */
    public EopBasedataAccounts getFdAccount() {
        return this.fdAccount;
    }

    /**
     * 对应会计科目
     */
    public void setFdAccount(EopBasedataAccounts fdAccount) {
        this.fdAccount = fdAccount;
    }
    
    /**
     * 付款方式对应默认银行
     */
    public EopBasedataPayBank getFdDefaultPayBank() {
		return fdDefaultPayBank;
	}
    
    /**
     * 付款方式对应默认银行
     */
	public void setFdDefaultPayBank(EopBasedataPayBank fdDefaultPayBank) {
		this.fdDefaultPayBank = fdDefaultPayBank;
	}
	/**
     * 启用公司
     */
    public List<EopBasedataCompany> getFdCompanyList() {
        return this.fdCompanyList;
    }

    /**
     * 启用公司
     */
    public void setFdCompanyList(List<EopBasedataCompany> fdCompanyList) {
        this.fdCompanyList = fdCompanyList;
    }
}
