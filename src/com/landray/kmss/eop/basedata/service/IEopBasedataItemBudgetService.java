package com.landray.kmss.eop.basedata.service;

import java.util.List;

import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataItemBudget;

public interface IEopBasedataItemBudgetService extends IEopBasedataBusinessService {

    public abstract List<EopBasedataItemBudget> findByFdCompany(EopBasedataCompany fdCompany) throws Exception;

    public abstract List<EopBasedataItemBudget> findByFdCategory(EopBasedataBudgetScheme fdCategory) throws Exception;

    public abstract List<EopBasedataItemBudget> findByFdItems(EopBasedataExpenseItem fdItems) throws Exception;
}
