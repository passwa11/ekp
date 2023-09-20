package com.landray.kmss.eop.basedata.service;

import java.util.List;

import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;

public interface IEopBasedataExpenseItemService extends IEopBasedataBusinessService {

    public abstract List<EopBasedataExpenseItem> findByDocParent(EopBasedataExpenseItem fdParent) throws Exception;

    public abstract List<EopBasedataExpenseItem> findByFdCompany(EopBasedataCompany fdCompany) throws Exception;

    public abstract List<EopBasedataExpenseItem> findByFdBudgetItems(EopBasedataBudgetItem fdBudgetItems) throws Exception;

    public abstract List<EopBasedataExpenseItem> findByFdAccounts(EopBasedataAccounts fdAccounts) throws Exception;
}
