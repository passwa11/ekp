package com.landray.kmss.fssc.expense.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.fssc.expense.forms.FsscExpenseOffsetLoanForm;

/**
  * 借款信息
  */
public class FsscExpenseOffsetLoan extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String docSubject;

    private String fdLoanId;

    private String fdNumber;

    private Double fdLoanMoney;

    private Double fdCanOffsetMoney;

    private Double fdOffsetMoney;

    private Double fdLeftMoney;

    private String fdCurrency;

    @Override
    public Class<FsscExpenseOffsetLoanForm> getFormClass() {
        return FsscExpenseOffsetLoanForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
        }
        return toFormPropertyMap;
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
     * 借款单据ID
     */
    public String getFdLoanId() {
        return this.fdLoanId;
    }

    /**
     * 借款单据ID
     */
    public void setFdLoanId(String fdLoanId) {
        this.fdLoanId = fdLoanId;
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
    public Double getFdLoanMoney() {
        return this.fdLoanMoney;
    }

    /**
     * 借款金额
     */
    public void setFdLoanMoney(Double fdLoanMoney) {
        this.fdLoanMoney = fdLoanMoney;
    }

    /**
     * 可冲抵金额
     */
    public Double getFdCanOffsetMoney() {
        return this.fdCanOffsetMoney;
    }

    /**
     * 可冲抵金额
     */
    public void setFdCanOffsetMoney(Double fdCanOffsetMoney) {
        this.fdCanOffsetMoney = fdCanOffsetMoney;
    }

    /**
     * 本次冲抵额
     */
    public Double getFdOffsetMoney() {
        return this.fdOffsetMoney;
    }

    /**
     * 本次冲抵额
     */
    public void setFdOffsetMoney(Double fdOffsetMoney) {
        this.fdOffsetMoney = fdOffsetMoney;
    }

    /**
     * 剩余金额
     */
    public Double getFdLeftMoney() {
        return this.fdLeftMoney;
    }

    /**
     * 剩余金额
     */
    public void setFdLeftMoney(Double fdLeftMoney) {
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
}
