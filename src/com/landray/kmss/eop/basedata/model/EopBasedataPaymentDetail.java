package com.landray.kmss.eop.basedata.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.eop.basedata.forms.EopBasedataPaymentDetailForm;
import com.landray.kmss.common.model.BaseModel;

/**
  * 付款单明细
  */
public class EopBasedataPaymentDetail extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private EopBasedataPayWay fdPayWay;

    private Double fdPaymentMoney;

    private String fdPayeeName;

    private String fdPayeeAccount;

    private String fdPayeeBankName;

    private Date fdPlanPaymentDate;

    private EopBasedataPayBank fdPayBank;

    private EopBasedataCurrency fdCurrency;
    
    private EopBasedataCompany fdCompany;
    
    private Double fdExchangeRate;

    @Override
    public Class<EopBasedataPaymentDetailForm> getFormClass() {
        return EopBasedataPaymentDetailForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdPayWay.fdName", "fdPayWayName");
            toFormPropertyMap.put("fdPayWay.fdId", "fdPayWayId");
            toFormPropertyMap.put("fdPlanPaymentDate", new ModelConvertor_Common("fdPlanPaymentDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdPayBank.fdBankName", "fdPayBankName");
            toFormPropertyMap.put("fdPayBank.fdId", "fdPayBankId");
            toFormPropertyMap.put("fdCurrency.fdName", "fdCurrencyName");
            toFormPropertyMap.put("fdCurrency.fdId", "fdCurrencyId");
            toFormPropertyMap.put("fdCompany.fdName", "fdCompanyName");
            toFormPropertyMap.put("fdCompany.fdId", "fdCompanyId");
        }
        return toFormPropertyMap;
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
     * 付款金额
     */
    public Double getFdPaymentMoney() {
        return this.fdPaymentMoney;
    }

    /**
     * 付款金额
     */
    public void setFdPaymentMoney(Double fdPaymentMoney) {
        this.fdPaymentMoney = fdPaymentMoney;
    }

    /**
     * 收款人名称
     */
    public String getFdPayeeName() {
        return this.fdPayeeName;
    }

    /**
     * 收款人名称
     */
    public void setFdPayeeName(String fdPayeeName) {
        this.fdPayeeName = fdPayeeName;
    }

    /**
     * 收款方账号
     */
    public String getFdPayeeAccount() {
        return this.fdPayeeAccount;
    }

    /**
     * 收款方账号
     */
    public void setFdPayeeAccount(String fdPayeeAccount) {
        this.fdPayeeAccount = fdPayeeAccount;
    }

    /**
     * 收款行名称
     */
    public String getFdPayeeBankName() {
        return this.fdPayeeBankName;
    }

    /**
     * 收款行名称
     */
    public void setFdPayeeBankName(String fdPayeeBankName) {
        this.fdPayeeBankName = fdPayeeBankName;
    }

    /**
     * 预计付款时间
     */
    public Date getFdPlanPaymentDate() {
        return this.fdPlanPaymentDate;
    }

    /**
     * 预计付款时间
     */
    public void setFdPlanPaymentDate(Date fdPlanPaymentDate) {
        this.fdPlanPaymentDate = fdPlanPaymentDate;
    }

    /**
     * 付款银行
     */
    public EopBasedataPayBank getFdPayBank() {
        return this.fdPayBank;
    }

    /**
     * 付款银行
     */
    public void setFdPayBank(EopBasedataPayBank fdPayBank) {
        this.fdPayBank = fdPayBank;
    }

    /**
     * 币种
     */
    public EopBasedataCurrency getFdCurrency() {
        return this.fdCurrency;
    }

    /**
     * 币种
     */
    public void setFdCurrency(EopBasedataCurrency fdCurrency) {
        this.fdCurrency = fdCurrency;
    }

	public EopBasedataCompany getFdCompany() {
		return fdCompany;
	}

	public void setFdCompany(EopBasedataCompany fdCompany) {
		this.fdCompany = fdCompany;
	}

	public Double getFdExchangeRate() {
		return fdExchangeRate;
	}

	public void setFdExchangeRate(Double fdExchangeRate) {
		this.fdExchangeRate = fdExchangeRate;
	}
}
