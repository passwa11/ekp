package com.landray.kmss.eop.basedata.service;

import java.util.List;

import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataItemAccount;

public interface IEopBasedataItemAccountService extends IEopBasedataBusinessService {

    public abstract List<EopBasedataItemAccount> findByFdCompany(EopBasedataCompany fdCompany) throws Exception;

    public abstract List<EopBasedataItemAccount> findByFdExpenseItem(EopBasedataExpenseItem fdExpenseItem) throws Exception;

    public abstract List<EopBasedataItemAccount> findByFdAmortize(EopBasedataAccounts fdAmortize) throws Exception;

    public abstract List<EopBasedataItemAccount> findByFdAccruals(EopBasedataAccounts fdAccruals) throws Exception;

    public abstract EopBasedataAccounts getEopBasedataAccounts(String fdExpenseItemId) throws Exception;

	public abstract EopBasedataAccounts getFsscAccrualsAccounts(String fdExpenseItemId)  throws Exception;
}
