package com.landray.kmss.eop.basedata.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataPayBank;
import com.landray.kmss.eop.basedata.model.EopBasedataPayWay;
import com.landray.kmss.eop.basedata.model.EopBasedataPaymentDetail;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 付款单明细
  */
public class EopBasedataPaymentDetailForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdPayWayId;

    private String fdPayWayName;

    private String fdPaymentMoney;

    private String fdPayeeName;

    private String fdPayeeAccount;

    private String fdPayeeBankName;

    private String fdPlanPaymentDate;

    private String fdPayBankId;

    private String fdPayBankName;

    private String fdCurrencyId;

    private String fdCurrencyName;
    
    private String fdCompanyId;
    
    private String fdCompanyName;
    
    private String fdExchangeRate;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdPayWayId = null;
        fdPayWayName = null;
        fdPaymentMoney = null;
        fdPayeeName = null;
        fdPayeeAccount = null;
        fdPayeeBankName = null;
        fdPlanPaymentDate = null;
        fdPayBankId = null;
        fdPayBankName = null;
        fdCurrencyId = null;
        fdCurrencyName = null;
        fdCompanyId = null;
        fdCompanyName = null;
        fdExchangeRate = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataPaymentDetail> getModelClass() {
        return EopBasedataPaymentDetail.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdPayWayId", new FormConvertor_IDToModel("fdPayWay", EopBasedataPayWay.class));
            toModelPropertyMap.put("fdPayBankId", new FormConvertor_IDToModel("fdPayBank", EopBasedataPayBank.class));
            toModelPropertyMap.put("fdPlanPaymentDate", new FormConvertor_Common("fdPlanPaymentDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdCurrencyId", new FormConvertor_IDToModel("fdCurrency", EopBasedataCurrency.class));
            toModelPropertyMap.put("fdCompanyId", new FormConvertor_IDToModel("fdCompany", EopBasedataCompany.class));
        }
        return toModelPropertyMap;
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
     * 付款金额
     */
    public String getFdPaymentMoney() {
        return this.fdPaymentMoney;
    }

    /**
     * 付款金额
     */
    public void setFdPaymentMoney(String fdPaymentMoney) {
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
    public String getFdPlanPaymentDate() {
        return this.fdPlanPaymentDate;
    }

    /**
     * 预计付款时间
     */
    public void setFdPlanPaymentDate(String fdPlanPaymentDate) {
        this.fdPlanPaymentDate = fdPlanPaymentDate;
    }

    /**
     * 付款银行
     */
    public String getFdPayBankId() {
        return this.fdPayBankId;
    }

    /**
     * 付款银行
     */
    public void setFdPayBankId(String fdPayBankId) {
        this.fdPayBankId = fdPayBankId;
    }

    /**
     * 付款银行
     */
    public String getFdPayBankName() {
        return this.fdPayBankName;
    }

    /**
     * 付款银行
     */
    public void setFdPayBankName(String fdPayBankName) {
        this.fdPayBankName = fdPayBankName;
    }

    /**
     * 币种
     */
    public String getFdCurrencyId() {
        return this.fdCurrencyId;
    }

    /**
     * 币种
     */
    public void setFdCurrencyId(String fdCurrencyId) {
        this.fdCurrencyId = fdCurrencyId;
    }

    /**
     * 币种
     */
    public String getFdCurrencyName() {
        return this.fdCurrencyName;
    }

    /**
     * 币种
     */
    public void setFdCurrencyName(String fdCurrencyName) {
        this.fdCurrencyName = fdCurrencyName;
    }

	public String getFdCompanyId() {
		return fdCompanyId;
	}

	public void setFdCompanyId(String fdCompanyId) {
		this.fdCompanyId = fdCompanyId;
	}

	public String getFdCompanyName() {
		return fdCompanyName;
	}

	public void setFdCompanyName(String fdCompanyName) {
		this.fdCompanyName = fdCompanyName;
	}

	public String getFdExchangeRate() {
		return fdExchangeRate;
	}

	public void setFdExchangeRate(String fdExchangeRate) {
		this.fdExchangeRate = fdExchangeRate;
	}
}
