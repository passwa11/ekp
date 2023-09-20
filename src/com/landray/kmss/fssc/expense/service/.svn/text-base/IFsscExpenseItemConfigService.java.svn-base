package com.landray.kmss.fssc.expense.service;

import java.util.List;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.service.IEopBasedataBusinessService;
import com.landray.kmss.fssc.expense.model.FsscExpenseItemConfig;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.fssc.expense.model.FsscExpenseCategory;

public interface IFsscExpenseItemConfigService extends IEopBasedataBusinessService {

    public abstract List<FsscExpenseItemConfig> findByFdCompany(EopBasedataCompany fdCompany) throws Exception;

    public abstract List<FsscExpenseItemConfig> findByFdCategory(FsscExpenseCategory fdCategory) throws Exception;

    public abstract List<FsscExpenseItemConfig> findByFdItemList(EopBasedataExpenseItem fdItemList) throws Exception;
}
