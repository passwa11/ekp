package com.landray.kmss.fssc.expense.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.fssc.expense.forms.FsscExpenseTempDetailForm;
import com.landray.kmss.util.DateUtil;

/**
  * 选择发票信息明细
  */
public class FsscExpenseTempDetail extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdInvoiceType;

    private String fdInvoiceDocId;

    private String fdInvoiceNumber;

    private String fdCompanyId;

    private String fdExpenseTypeId;

    private String fdExpenseTypeName;

    private String fdIsVat;
    
    private Double fdNonDeductMoney;

    private String fdInvoiceCode;

    private Date fdInvoiceDate;

    private Double fdInvoiceMoney;

    private String fdTax;

    private String fdTaxId;

    private Double fdTaxMoney;

    private Double fdNoTaxMoney;

    private FsscExpenseTemp docMain;

    private Integer docIndex;
    
    private String fdCheckCode;
    
    private String fdCheckStatus;
    
    private String fdState;
	
	private String fdTaxNumber;

    private String fdPurchName;
    
    @Override
    public Class<FsscExpenseTempDetailForm> getFormClass() {
        return FsscExpenseTempDetailForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdInvoiceDate", new ModelConvertor_Common("fdInvoiceDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("docMain.fdMainId", "docMainName");
            toFormPropertyMap.put("docMain.fdId", "docMainId");
        }
        return toFormPropertyMap;
    }

    /**
     * 发票类型
     */
    public String getFdInvoiceType() {
        return this.fdInvoiceType;
    }

    /**
     * 发票类型
     */
    public void setFdInvoiceType(String fdInvoiceType) {
        this.fdInvoiceType = fdInvoiceType;
    }

    /**
     * 发票附件ID
     */
    public String getFdInvoiceDocId() {
        return this.fdInvoiceDocId;
    }

    /**
     * 发票附件ID
     */
    public void setFdInvoiceDocId(String fdInvoiceDocId) {
        this.fdInvoiceDocId = fdInvoiceDocId;
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
     * 公司ID
     */
    public String getFdCompanyId() {
        return this.fdCompanyId;
    }

    /**
     * 公司ID
     */
    public void setFdCompanyId(String fdCompanyId) {
        this.fdCompanyId = fdCompanyId;
    }

    /**
     * 费用类型ID
     */
    public String getFdExpenseTypeId() {
        return this.fdExpenseTypeId;
    }

    /**
     * 费用类型ID
     */
    public void setFdExpenseTypeId(String fdExpenseTypeId) {
        this.fdExpenseTypeId = fdExpenseTypeId;
    }

    /**
     * 费用类型名称
     */
    public String getFdExpenseTypeName() {
        return this.fdExpenseTypeName;
    }

    /**
     * 费用类型名称
     */
    public void setFdExpenseTypeName(String fdExpenseTypeName) {
        this.fdExpenseTypeName = fdExpenseTypeName;
    }

    /**
     * 是否可抵扣
     */
    public String getFdIsVat() {
        return this.fdIsVat;
    }

    /**
     * 是否可抵扣
     */
    public void setFdIsVat(String fdIsVat) {
        this.fdIsVat = fdIsVat;
    }
    
    /**
	 * 不可抵扣金额
	 * @return
	 */
	public Double getFdNonDeductMoney() {
		return fdNonDeductMoney;
	}
	/**
	 * 不可抵扣金额
	 * @param fdNoTaxMoney
	 */
	public void setFdNonDeductMoney(Double fdNonDeductMoney) {
		this.fdNonDeductMoney = fdNonDeductMoney;
	}

    /**
     * 发票号码
     */
    public String getFdInvoiceCode() {
        return this.fdInvoiceCode;
    }

    /**
     * 发票号码
     */
    public void setFdInvoiceCode(String fdInvoiceCode) {
        this.fdInvoiceCode = fdInvoiceCode;
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
     * 税率ID
     */
    public String getFdTaxId() {
        return this.fdTaxId;
    }

    /**
     * 税率ID
     */
    public void setFdTaxId(String fdTaxId) {
        this.fdTaxId = fdTaxId;
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

    public FsscExpenseTemp getDocMain() {
        return this.docMain;
    }

    public void setDocMain(FsscExpenseTemp docMain) {
        this.docMain = docMain;
    }

    public Integer getDocIndex() {
        return this.docIndex;
    }

    public void setDocIndex(Integer docIndex) {
        this.docIndex = docIndex;
    }

	public String getFdCheckCode() {
		return fdCheckCode;
	}

	public void setFdCheckCode(String fdCheckCode) {
		this.fdCheckCode = fdCheckCode;
	}
    
	/**
	 * 验真状态
	 * @return
	 */
	public String getFdCheckStatus() {
		return fdCheckStatus;
	}
	/**
	 * 验真状态
	 * @param fdCheckStatus
	 */
	public void setFdCheckStatus(String fdCheckStatus) {
		this.fdCheckStatus = fdCheckStatus;
	}
	
	/**
	 * 发票状态，0：正常，1：作废，2:红冲，3：失控，4：异常
	 * @return
	 */
	public String getFdState() {
		return fdState;
	}
	/**
	 * 发票状态，0：正常，1：作废，2:红冲，3：失控，4：异常
	 * @param fdState
	 */
	public void setFdState(String fdState) {
		this.fdState = fdState;
	}
	
	 /**
     * 购买方纳税识别号
     * @return
     */
    public String getFdTaxNumber() {
        return fdTaxNumber;
    }
    /**
     * 购买方纳税识别号
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

}
