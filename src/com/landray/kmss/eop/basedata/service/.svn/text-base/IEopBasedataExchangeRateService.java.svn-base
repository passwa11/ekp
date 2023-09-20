package com.landray.kmss.eop.basedata.service;

import java.util.List;

import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataExchangeRate;

public interface IEopBasedataExchangeRateService extends IEopBasedataBusinessService {

    public abstract List<EopBasedataExchangeRate> findByFdSourceCurrency(EopBasedataCurrency fdSourceCurrency) throws Exception;

    public abstract List<EopBasedataExchangeRate> findByFdTargetCurrency(EopBasedataCurrency fdTargetCurrency) throws Exception;

    /**
     *  获取币种跟公司本位币的汇率
     * @param fdCompany
     * @param fdCurrencyId
     * @return
     * @throws Exception
     */
    public double getRateByAccountCurrency(EopBasedataCompany fdCompany, String fdCurrencyId) throws Exception;
    
    public abstract Double getExchangeRate(String fdCurrencyId, String fdId)  throws Exception;
    public abstract Double getBudgetRate(String fdCurrencyId, String fdId)  throws Exception;

	public abstract Double getBudgetToRate(String fdCurrencyId, String fdCompanyId)  throws Exception;
}
