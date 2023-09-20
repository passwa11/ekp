package com.landray.kmss.eop.basedata.service;

import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataInputTax;

import java.util.List;

public interface IEopBasedataInputTaxService extends IEopBasedataBusinessService {

    public abstract List<EopBasedataInputTax> findByFdItem(EopBasedataExpenseItem fdItem, Double fdInputTaxRate) throws Exception;

    public abstract List<EopBasedataInputTax> findByFdAccount(EopBasedataAccounts fdAccount) throws Exception;
}
