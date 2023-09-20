package com.landray.kmss.fssc.expense.forms;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataPayBank;
import com.landray.kmss.eop.basedata.model.EopBasedataPayWay;
import com.landray.kmss.fssc.expense.model.FsscExpenseAccounts;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 收款账户明细
  */
public class FsscExpenseAccountsForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdAccountName;

    private String fdBankName;

    private String fdBankAccount;

    private String fdMoney;

    private String fdAccountId;

    private String fdPayWayId;

    private String fdPayWayName;
    
    private String fdBankId;
    
    private String fdPayBankName;
    
    private String fdCurrencyName;
    
    private String fdCurrencyId;
    
    private String fdExchangeRate;
    
    private String fdAccountAreaCode;

   	private String fdAccountAreaName;
   	
   	private String  fdBankAccountNo;
   	
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdBankAccountNo = null;
        fdAccountName = null;
        fdBankAccount = null;
        fdMoney = null;
        fdAccountId = null;
        fdPayWayId = null;
        fdPayWayName = null;
        fdBankId = null;
        fdPayBankName = null;
        fdBankName = null;
        fdCurrencyName = null;
        fdCurrencyId = null;
        fdAccountAreaCode = null;
        fdAccountAreaName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscExpenseAccounts> getModelClass() {
        return FsscExpenseAccounts.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdPayWayId", new FormConvertor_IDToModel("fdPayWay", EopBasedataPayWay.class));
            toModelPropertyMap.put("fdBankId", new FormConvertor_IDToModel("fdBank", EopBasedataPayBank.class));
            toModelPropertyMap.put("fdCurrencyId", new FormConvertor_IDToModel("fdCurrency", EopBasedataCurrency.class));
        }
        return toModelPropertyMap;
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
    public String getFdMoney() {
    	if(StringUtil.isNull(this.fdMoney)){
    		return this.fdMoney;
    	}
    	return new DecimalFormat("0.0#").format(Double.valueOf(this.fdMoney));
    }

    /**
     * 收款金额
     */
    public void setFdMoney(String fdMoney) {
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
    public String getFdPayWayId() {
        return this.fdPayWayId;
    }

    /**
     * 付款方式
     */
    public void setFdPayWayId(String fdPayWayId) {
        this.fdPayWayId = fdPayWayId;
    }

    /**
     * 付款方式
     */
    public String getFdPayWayName() {
        return this.fdPayWayName;
    }

    /**
     * 付款方式
     */
    public void setFdPayWayName(String fdPayWayName) {
        this.fdPayWayName = fdPayWayName;
    }
    
    /**
     * 支付银行
     */
    public String getFdBankId() {
        return this.fdBankId;
    }

    /**
     * 支付银行
     */
    public void setFdBankId(String fdBankId) {
        this.fdBankId = fdBankId;
    }

	public String getFdCurrencyName() {
		return fdCurrencyName;
	}

	public void setFdCurrencyName(String fdCurrencyName) {
		this.fdCurrencyName = fdCurrencyName;
	}

	public String getFdCurrencyId() {
		return fdCurrencyId;
	}

	public void setFdCurrencyId(String fdCurrencyId) {
		this.fdCurrencyId = fdCurrencyId;
	}

	public String getFdPayBankName() {
		return fdPayBankName;
	}

	public void setFdPayBankName(String fdPayBankName) {
		this.fdPayBankName = fdPayBankName;
	}

	public String getFdExchangeRate() {
		return fdExchangeRate;
	}

	public void setFdExchangeRate(String fdExchangeRate) {
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
