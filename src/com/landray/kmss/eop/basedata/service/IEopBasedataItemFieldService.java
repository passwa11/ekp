package com.landray.kmss.eop.basedata.service;

import java.util.List;

import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataItemField;

public interface IEopBasedataItemFieldService extends IEopBasedataBusinessService {

    public abstract List<EopBasedataItemField> findByFdCompany(EopBasedataCompany fdCompany) throws Exception;

    public abstract List<EopBasedataItemField> findByFdItems(EopBasedataExpenseItem fdItems) throws Exception;
}
