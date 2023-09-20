package com.landray.kmss.fssc.expense.forms;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.fssc.expense.model.FsscExpenseInvoiceDetail;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 发票明细
  */
public class FsscExpenseInvoiceDetailForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdCompanyId;

    private String fdCompanyName;

    private String fdIsVat;

    private String fdExpenseTypeId;

    private String fdExpenseTypeName;

    private String fdInvoiceNumber;

    private String fdInvoiceDate;

    private String fdTax;

    private String fdInvoiceMoney;

    private String fdNoTaxMoney;

    private String fdTaxMoney;

    private String fdInvoiceCode;
    
    private String fdInvoiceType;
    
    private String fdCheckStatus;
    
    private String fdState;
    
    private String fdCheckCode;

    private String fdTaxNumber;

    private String fdPurchName;

    private String fdIsCurrent;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdCompanyId = null;
        fdCompanyName = null;
        fdIsVat = null;
        fdExpenseTypeId = null;
        fdExpenseTypeName = null;
        fdInvoiceNumber = null;
        fdInvoiceDate = null;
        fdTax = null;
        fdInvoiceMoney = null;
        fdNoTaxMoney = null;
        fdTaxMoney = null;
        fdInvoiceCode = null;
        fdInvoiceType=null;
        fdCheckStatus=null;
        fdState=null;
        fdCheckCode=null;
        fdTaxNumber=null;
        fdPurchName=null;
        fdIsCurrent=null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscExpenseInvoiceDetail> getModelClass() {
        return FsscExpenseInvoiceDetail.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdCompanyId", new FormConvertor_IDToModel("fdCompany", EopBasedataCompany.class));
            toModelPropertyMap.put("fdExpenseTypeId", new FormConvertor_IDToModel("fdExpenseType", EopBasedataExpenseItem.class));
            toModelPropertyMap.put("fdInvoiceDate", new FormConvertor_Common("fdInvoiceDate").setDateTimeType(DateUtil.TYPE_DATE));
        }
        return toModelPropertyMap;
    }

    /**
     * 法人名称
     */
    public String getFdCompanyId() {
        return this.fdCompanyId;
    }

    /**
     * 法人名称
     */
    public void setFdCompanyId(String fdCompanyId) {
        this.fdCompanyId = fdCompanyId;
    }

    /**
     * 法人名称
     */
    public String getFdCompanyName() {
        return this.fdCompanyName;
    }

    /**
     * 法人名称
     */
    public void setFdCompanyName(String fdCompanyName) {
        this.fdCompanyName = fdCompanyName;
    }

    /**
     * 是否增值税发票
     */
    public String getFdIsVat() {
        return this.fdIsVat;
    }

    /**
     * 是否增值税发票
     */
    public void setFdIsVat(String fdIsVat) {
        this.fdIsVat = fdIsVat;
    }

    /**
     * 费用类型
     */
    public String getFdExpenseTypeId() {
        return this.fdExpenseTypeId;
    }

    /**
     * 费用类型
     */
    public void setFdExpenseTypeId(String fdExpenseTypeId) {
        this.fdExpenseTypeId = fdExpenseTypeId;
    }

    /**
     * 费用类型
     */
    public String getFdExpenseTypeName() {
        return this.fdExpenseTypeName;
    }

    /**
     * 费用类型
     */
    public void setFdExpenseTypeName(String fdExpenseTypeName) {
        this.fdExpenseTypeName = fdExpenseTypeName;
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
    public String getFdInvoiceDate() {
        return this.fdInvoiceDate;
    }

    /**
     * 开票日期
     */
    public void setFdInvoiceDate(String fdInvoiceDate) {
        this.fdInvoiceDate = fdInvoiceDate;
    }

    /**
     * 税率
     */
    public String getFdTax() {
        return this.fdTax;
    }

    /**
     * 税率
     */
    public void setFdTax(String fdTax) {
        this.fdTax = fdTax;
    }

    /**
     * 发票金额
     */
    public String getFdInvoiceMoney() {
    	if(StringUtil.isNull(this.fdInvoiceMoney)){
    		return this.fdInvoiceMoney;
    	}
    	return new DecimalFormat("0.0#").format(Double.valueOf(this.fdInvoiceMoney));
    }

    /**
     * 发票金额
     */
    public void setFdInvoiceMoney(String fdInvoiceMoney) {
        this.fdInvoiceMoney = fdInvoiceMoney;
    }

    /**
     * 不含税金额
     */
    public String getFdNoTaxMoney() {
    	if(StringUtil.isNull(this.fdNoTaxMoney)){
    		return this.fdNoTaxMoney;
    	}
    	return new DecimalFormat("0.0#").format(Double.valueOf(this.fdNoTaxMoney));
    }

    /**
     * 不含税金额
     */
    public void setFdNoTaxMoney(String fdNoTaxMoney) {
        this.fdNoTaxMoney = fdNoTaxMoney;
    }

    /**
     * 税额
     */
    public String getFdTaxMoney() {
    	if(StringUtil.isNull(this.fdTaxMoney)){
    		return this.fdTaxMoney;
    	}
    	return new DecimalFormat("0.0#").format(Double.valueOf(this.fdTaxMoney));
    }

    /**
     * 税额
     */
    public void setFdTaxMoney(String fdTaxMoney) {
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
