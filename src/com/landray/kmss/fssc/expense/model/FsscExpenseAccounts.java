package com.landray.kmss.fssc.expense.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataPayBank;
import com.landray.kmss.eop.basedata.model.EopBasedataPayWay;
import com.landray.kmss.fssc.expense.forms.FsscExpenseAccountsForm;

/**
  * 收款账户明细
  */
public class FsscExpenseAccounts extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdAccountName;

    private String fdBankName;

    private String fdBankAccount;

    private Double fdMoney;

    private String fdAccountId;

    private EopBasedataPayWay fdPayWay;

    private EopBasedataPayBank fdBank;
    
    private EopBasedataCurrency fdCurrency;
    
    private Double fdExchangeRate;
    
    private String fdAccountAreaCode;

	private String fdAccountAreaName;
	
	private String fdBankAccountNo;

    @Override
    public Class<FsscExpenseAccountsForm> getFormClass() {
        return FsscExpenseAccountsForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdPayWay.fdName", "fdPayWayName");
            toFormPropertyMap.put("fdPayWay.fdId", "fdPayWayId");
            toFormPropertyMap.put("fdBank.fdAccountName", "fdPayBankName");
            toFormPropertyMap.put("fdBank.fdId", "fdBankId");
            toFormPropertyMap.put("fdCurrency.fdName", "fdCurrencyName");
            toFormPropertyMap.put("fdCurrency.fdId", "fdCurrencyId");
        }
        return toFormPropertyMap;
    }
    
    /**
     * 银联号
     * @return
     */
    public String getFdBankAccountNo() {
		return fdBankAccountNo;
	}
    
    /**
     * 银联号
     */
	public void setFdBankAccountNo(String fdBankAccountNo) {
		this.fdBankAccountNo = fdBankAccountNo;
	}

    /**
     * 收款人账户名
     */
    public String getFdAccountName() {
        return this.fdAccountName;
    }

    /**
     * 收款人账户名
     */
    public void setFdAccountName(String fdAccountName) {
        this.fdAccountName = fdAccountName;
    }

    /**
     * 收款人开户行
     */
    public String getFdBankName() {
        return this.fdBankName;
    }

    /**
     * 收款人开户行
     */
    public void setFdBankName(String fdBankName) {
        this.fdBankName = fdBankName;
    }

    /**
     * 收款人账号
     */
    public String getFdBankAccount() {
        return this.fdBankAccount;
    }

    /**
     * 收款人账号
     */
    public void setFdBankAccount(String fdBankAccount) {
        this.fdBankAccount = fdBankAccount;
    }

    /**
     * 收款金额
     */
    public Double getFdMoney() {
        return this.fdMoney;
    }

    /**
     * 收款金额
     */
    public void setFdMoney(Double fdMoney) {
        this.fdMoney = fdMoney;
    }

    /**
     * 关联账户ID
     */
    public String getFdAccountId() {
        return this.fdAccountId;
    }

    /**
     * 关联账户ID
     */
    public void setFdAccountId(String fdAccountId) {
        this.fdAccountId = fdAccountId;
    }

    /**
     * 付款方式
     */
    public EopBasedataPayWay getFdPayWay() {
        return this.fdPayWay;
    }

    /**
     * 付款方式
     */
    public void setFdPayWay(EopBasedataPayWay fdPayWay) {
        this.fdPayWay = fdPayWay;
    }

    /**
     * 支付银行
     */
    public EopBasedataPayBank getFdBank() {
        return this.fdBank;
    }

    /**
     * 支付银行
     */
    public void setFdBank(EopBasedataPayBank fdBank) {
        this.fdBank = fdBank;
    }

	public EopBasedataCurrency getFdCurrency() {
		return fdCurrency;
	}

	public void setFdCurrency(EopBasedataCurrency fdCurrency) {
		this.fdCurrency = fdCurrency;
	}

	public Double getFdExchangeRate() {
		return fdExchangeRate;
	}

	public void setFdExchangeRate(Double fdExchangeRate) {
		this.fdExchangeRate = fdExchangeRate;
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
}
