package com.landray.kmss.fssc.expense.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.fssc.expense.forms.FsscExpenseInvoiceDetailForm;
import com.landray.kmss.util.DateUtil;

/**
  * 发票明细
  */
public class FsscExpenseInvoiceDetail extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private EopBasedataCompany fdCompany;

    private Boolean fdIsVat;

    private EopBasedataExpenseItem fdExpenseType;

    private String fdInvoiceNumber;

    private Date fdInvoiceDate;

    private Double fdTax;

    private Double fdInvoiceMoney;

    private Double fdNoTaxMoney;

    private Double fdTaxMoney;

    private String fdInvoiceCode;
    
    private String fdInvoiceType;
    
    private String fdCheckStatus;
    
    private String fdState;
    
    private String fdCheckCode;

    private String fdTaxNumber;

    private String fdPurchName;

    private String fdIsCurrent;

    @Override
    public Class<FsscExpenseInvoiceDetailForm> getFormClass() {
        return FsscExpenseInvoiceDetailForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdCompany.fdName", "fdCompanyName");
            toFormPropertyMap.put("fdCompany.fdId", "fdCompanyId");
            toFormPropertyMap.put("fdExpenseType.fdName", "fdExpenseTypeName");
            toFormPropertyMap.put("fdExpenseType.fdId", "fdExpenseTypeId");
            toFormPropertyMap.put("fdInvoiceDate", new ModelConvertor_Common("fdInvoiceDate").setDateTimeType(DateUtil.TYPE_DATE));
        }
        return toFormPropertyMap;
    }

    /**
     * 法人名称
     */
    public EopBasedataCompany getFdCompany() {
        return this.fdCompany;
    }

    /**
     * 法人名称
     */
    public void setFdCompany(EopBasedataCompany fdCompany) {
        this.fdCompany = fdCompany;
    }

    /**
     * 是否增值税发票
     */
    public Boolean getFdIsVat() {
        return this.fdIsVat;
    }

    /**
     * 是否增值税发票
     */
    public void setFdIsVat(Boolean fdIsVat) {
        this.fdIsVat = fdIsVat;
    }

    /**
     * 费用类型
     */
    public EopBasedataExpenseItem getFdExpenseType() {
        return this.fdExpenseType;
    }

    /**
     * 费用类型
     */
    public void setFdExpenseType(EopBasedataExpenseItem fdExpenseType) {
        this.fdExpenseType = fdExpenseType;
    }

    /**
     * 发票编号
     */
    public String getFdInvoiceNumber() {
        return this.fdInvoiceNumber;
    }

    /**
     * 发票编号
     */
    public void setFdInvoiceNumber(String fdInvoiceNumber) {
        this.fdInvoiceNumber = fdInvoiceNumber;
    }

    /**
     * 开票日期
     */
    public Date getFdInvoiceDate() {
        return this.fdInvoiceDate;
    }

    /**
     * 开票日期
     */
    public void setFdInvoiceDate(Date fdInvoiceDate) {
        this.fdInvoiceDate = fdInvoiceDate;
    }

    /**
     * 税率
     */
    public Double getFdTax() {
        return this.fdTax;
    }

    /**
     * 税率
     */
    public void setFdTax(Double fdTax) {
        this.fdTax = fdTax;
    }

    /**
     * 发票金额
     */
    public Double getFdInvoiceMoney() {
        return this.fdInvoiceMoney;
    }

    /**
     * 发票金额
     */
    public void setFdInvoiceMoney(Double fdInvoiceMoney) {
        this.fdInvoiceMoney = fdInvoiceMoney;
    }

    /**
     * 不含税金额
     */
    public Double getFdNoTaxMoney() {
        return this.fdNoTaxMoney;
    }

    /**
     * 不含税金额
     */
    public void setFdNoTaxMoney(Double fdNoTaxMoney) {
        this.fdNoTaxMoney = fdNoTaxMoney;
    }

    /**
     * 税额
     */
    public Double getFdTaxMoney() {
        return this.fdTaxMoney;
    }

    /**
     * 税额
     */
    public void setFdTaxMoney(Double fdTaxMoney) {
        this.fdTaxMoney = fdTaxMoney;
    }

    /**
     * 发票代码
     */
    public String getFdInvoiceCode() {
        return this.fdInvoiceCode;
    }

    /**
     * 发票代码
     */
    public void setFdInvoiceCode(String fdInvoiceCode) {
        this.fdInvoiceCode = fdInvoiceCode;
    }
    
    /**
     * 发票类型
     */
    public String getFdInvoiceType() {
		return fdInvoiceType;
	}
	 /**
     * 发票类型
     */

	public void setFdInvoiceType(String fdInvoiceType) {
		this.fdInvoiceType = fdInvoiceType;
	}
	
	/**
	 * 发票验证状态，0：未验证，1：已验真
	 * @return
	 */
	public String getFdCheckStatus() {
		return fdCheckStatus;
	}
	/**
	 * 发票验证状态，0：未验证，1：已验真
	 * @param fdCheckStatus
	 */
	public void setFdCheckStatus(String fdCheckStatus) {
		this.fdCheckStatus = fdCheckStatus;
	}
	
	/**
	 * 发票状态，0：正常，1：作废，2:红冲，3：失控，4：异常
	 * @param fdState
	 */

	public String getFdState() {
		return fdState;
	}
	/**
	 * 发票状态，0：正常，1：作废，2:红冲，3：失控，4：异常
	 */
	public void setFdState(String fdState) {
		this.fdState = fdState;
	}

	public String getFdCheckCode() {
		return fdCheckCode;
	}

	public void setFdCheckCode(String fdCheckCode) {
		this.fdCheckCode = fdCheckCode;
	}

    /**
     * 购方税号
     * @return
     */
    public String getFdTaxNumber() {
        return fdTaxNumber;
    }

    /**
     * 购方税号
     * @param fdTaxNumber
     */
    public void setFdTaxNumber(String fdTaxNumber) {
        this.fdTaxNumber = fdTaxNumber;
    }

    /**
     * 购方名称
     * @return
     */
    public String getFdPurchName() {
        return fdPurchName;
    }
    /**
     * 购方名称
     * @param fdPurchName
     */
    public void setFdPurchName(String fdPurchName) {
        this.fdPurchName = fdPurchName;
    }

    /**
     * 是否本公司发票
     * @return
     */
    public String getFdIsCurrent() {
        return fdIsCurrent;
    }
    /**
     * 是否本公司发票
     * @param fdIsCurrent
     */
    public void setFdIsCurrent(String fdIsCurrent) {
        this.fdIsCurrent = fdIsCurrent;
    }

}
