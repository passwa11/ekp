package com.landray.kmss.fssc.expense.forms;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.fssc.expense.model.FsscExpenseOffsetLoan;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 借款信息
  */
public class FsscExpenseOffsetLoanForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docSubject;

    private String fdNumber;

    private String fdLoanMoney;

    private String fdCanOffsetMoney;

    private String fdOffsetMoney;

    private String fdLeftMoney;

    private String fdCurrency;
    
    private String fdLoanId;
    

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        docSubject = null;
        fdNumber = null;
        fdLoanMoney = null;
        fdCanOffsetMoney = null;
        fdOffsetMoney = null;
        fdLeftMoney = null;
        fdCurrency = null;
        fdLoanId = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscExpenseOffsetLoan> getModelClass() {
        return FsscExpenseOffsetLoan.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("fdCurrency");
        }
        return toModelPropertyMap;
    }

    /**
     * 标题
     */
    public String getDocSubject() {
        return this.docSubject;
    }

    /**
     * 标题
     */
    public void setDocSubject(String docSubject) {
        this.docSubject = docSubject;
    }

    /**
     * 单据编号
     */
    public String getFdNumber() {
        return this.fdNumber;
    }

    /**
     * 单据编号
     */
    public void setFdNumber(String fdNumber) {
        this.fdNumber = fdNumber;
    }

    /**
     * 借款金额
     */
    public String getFdLoanMoney() {
    	if(StringUtil.isNull(this.fdLoanMoney)){
    		return this.fdLoanMoney;
    	}
    	return new DecimalFormat("0.0#").format(Double.valueOf(this.fdLoanMoney));
    }

    /**
     * 借款金额
     */
    public void setFdLoanMoney(String fdLoanMoney) {
        this.fdLoanMoney = fdLoanMoney;
    }

    /**
     * 可冲抵金额
     */
    public String getFdCanOffsetMoney() {
    	if(StringUtil.isNull(this.fdCanOffsetMoney)){
    		return this.fdCanOffsetMoney;
    	}
    	return new DecimalFormat("0.0#").format(Double.valueOf(this.fdCanOffsetMoney));
    }

    /**
     * 可冲抵金额
     */
    public void setFdCanOffsetMoney(String fdCanOffsetMoney) {
        this.fdCanOffsetMoney = fdCanOffsetMoney;
    }

    /**
     * 本次冲抵额
     */
    public String getFdOffsetMoney() {
    	if(StringUtil.isNull(this.fdOffsetMoney)){
    		return this.fdOffsetMoney;
    	}
    	return new DecimalFormat("0.0#").format(Double.valueOf(this.fdOffsetMoney));
    }

    /**
     * 本次冲抵额
     */
    public void setFdOffsetMoney(String fdOffsetMoney) {
        this.fdOffsetMoney = fdOffsetMoney;
    }

    /**
     * 剩余金额
     */
    public String getFdLeftMoney() {
    	if(StringUtil.isNull(this.fdLeftMoney)){
    		return this.fdLeftMoney;
    	}
    	return new DecimalFormat("0.0#").format(Double.valueOf(this.fdLeftMoney));
    }

    /**
     * 剩余金额
     */
    public void setFdLeftMoney(String fdLeftMoney) {
        this.fdLeftMoney = fdLeftMoney;
    }

    /**
     * 币种
     */
    public String getFdCurrency() {
        return this.fdCurrency;
    }

    /**
     * 币种
     */
    public void setFdCurrency(String fdCurrency) {
        this.fdCurrency = fdCurrency;
    }

	public String getFdLoanId() {
		return fdLoanId;
	}

	public void setFdLoanId(String fdLoanId) {
		this.fdLoanId = fdLoanId;
	}
}
