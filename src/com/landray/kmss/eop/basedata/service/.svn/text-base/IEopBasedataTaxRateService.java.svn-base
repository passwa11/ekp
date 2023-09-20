package com.landray.kmss.eop.basedata.service;

import java.util.List;

import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataTaxRate;

public interface IEopBasedataTaxRateService extends IEopBasedataBusinessService {

    public abstract List<EopBasedataTaxRate> findByFdCompany(EopBasedataCompany fdCompany) throws Exception;

    public abstract List<EopBasedataTaxRate> findByFdAccount(EopBasedataAccounts fdAccount) throws Exception;

    public EopBasedataAccounts getEopBasedataAccounts(String fdCompanyId, double fdRate) throws Exception;
}
