package com.landray.kmss.fssc.expense.service;

import java.util.List;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataVoucherType;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.fssc.expense.model.FsscExpenseBalance;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;

public interface IFsscExpenseBalanceService extends IExtendDataService {

    public abstract List<FsscExpenseBalance> findByFdCompany(EopBasedataCompany fdCompany) throws Exception;

    public abstract List<FsscExpenseBalance> findByFdCostCenter(EopBasedataCostCenter fdCostCenter) throws Exception;

    public abstract List<FsscExpenseBalance> findByFdVoucherType(EopBasedataVoucherType fdVoucherType) throws Exception;

    public abstract List<FsscExpenseBalance> findByFdCurrency(EopBasedataCurrency fdCurrency) throws Exception;
}
