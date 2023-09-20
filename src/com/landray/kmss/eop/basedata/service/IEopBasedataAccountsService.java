package com.landray.kmss.eop.basedata.service;

import java.util.List;

import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;

public interface IEopBasedataAccountsService extends IEopBasedataBusinessService {

    public abstract List<EopBasedataAccounts> findByDocParent(EopBasedataAccounts docParent) throws Exception;

    public abstract List<EopBasedataAccounts> findByFdCompany(EopBasedataCompany fdCompany) throws Exception;

    public abstract EopBasedataAccounts getEopBasedataAccountsByCode(String fdCode) throws Exception;
}
