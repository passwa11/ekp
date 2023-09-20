package com.landray.kmss.fssc.expense.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.fssc.expense.model.FsscExpenseTemp;
import com.landray.kmss.fssc.expense.model.FsscExpenseTempDetail;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

/**
  * 选择发票信息明细
  */
public class FsscExpenseTempDetailForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdInvoiceType;

    private String fdInvoiceDocId;

    private String fdInvoiceNumber;

    private String fdCompanyId;

    private String fdExpenseTypeId;

    private String fdExpenseTypeName;

    private String fdIsVat;
    
    private String fdNonDeductMoney;

    private String fdInvoiceCode;

    private String fdInvoiceDate;

    private String fdInvoiceMoney;

    private String fdTax;

    private String fdTaxId;

    private String fdTaxMoney;

    private String fdNoTaxMoney;

    private String docMainId;

    private String docMainName;

    private String docIndex;

    private FormFile file;

    private String fdImportType;
    
    private String fdThisFlag;
    
    private String fdCheckCode;

    private String fdTaxNumber;

    private String fdPurchName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdInvoiceType = null;
        fdInvoiceDocId = null;
        fdInvoiceNumber = null;
        fdCompanyId = null;
        fdExpenseTypeId = null;
        fdExpenseTypeName = null;
        fdIsVat = null;
        fdNonDeductMoney= null;
        fdInvoiceCode = null;
        fdInvoiceDate = null;
        fdInvoiceMoney = null;
        fdTax = null;
        fdTaxId = null;
        fdTaxMoney = null;
        fdNoTaxMoney = null;
        fdThisFlag=null;
        docIndex = null;
        fdCheckCode=null;
        fdTaxNumber=null;
        fdPurchName=null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscExpenseTempDetail> getModelClass() {
        return FsscExpenseTempDetail.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdInvoiceDate", new FormConvertor_Common("fdInvoiceDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("docMainId", new FormConvertor_IDToModel("docMain", FsscExpenseTemp.class));
        }
        return toModelPropertyMap;
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
	public String getFdNonDeductMoney() {
		return fdNonDeductMoney;
	}
	/**
	 * 不可抵扣金额
	 * @param fdNoTaxMoney
	 */
	public void setFdNonDeductMoney(String fdNonDeductMoney) {
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
     * 发票金额
     */
    public String getFdInvoiceMoney() {
        return this.fdInvoiceMoney;
    }

    /**
     * 发票金额
     */
    public void setFdInvoiceMoney(String fdInvoiceMoney) {
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
    public String getFdTaxMoney() {
        return this.fdTaxMoney;
    }

    /**
     * 税额
     */
    public void setFdTaxMoney(String fdTaxMoney) {
        this.fdTaxMoney = fdTaxMoney;
    }

    /**
     * 不含税金额
     */
    public String getFdNoTaxMoney() {
        return this.fdNoTaxMoney;
    }

    /**
     * 不含税金额
     */
    public void setFdNoTaxMoney(String fdNoTaxMoney) {
        this.fdNoTaxMoney = fdNoTaxMoney;
    }

    public String getDocMainId() {
        return this.docMainId;
    }

    public void setDocMainId(String docMainId) {
        this.docMainId = docMainId;
    }

    public String getDocMainName() {
        return this.docMainName;
    }

    public void setDocMainName(String docMainName) {
        this.docMainName = docMainName;
    }

    public String getDocIndex() {
        return this.docIndex;
    }

    public void setDocIndex(String docIndex) {
        this.docIndex = docIndex;
    }

    public FormFile getFile() {
        return this.file;
    }

    public void setFile(FormFile file) {
        this.file = file;
    }

    public String getFdImportType() {
        return this.fdImportType;
    }

    public void setFdImportType(String fdImportType) {
        this.fdImportType = fdImportType;
    }

    /**
     * 页面标识，传输到后台
     * @return
     */
	public String getFdThisFlag() {
		return fdThisFlag;
	}

	public void setFdThisFlag(String fdThisFlag) {
		this.fdThisFlag = fdThisFlag;
	}

	public String getFdCheckCode() {
		return fdCheckCode;
	}

	public void setFdCheckCode(String fdCheckCode) {
		this.fdCheckCode = fdCheckCode;
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
