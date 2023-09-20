package com.landray.kmss.fssc.fee.service;

import java.util.List;

import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.service.IEopBasedataBusinessService;
import com.landray.kmss.fssc.fee.model.FsscFeeExpenseItem;
import com.landray.kmss.fssc.fee.model.FsscFeeTemplate;

public interface IFsscFeeExpenseItemService extends IEopBasedataBusinessService {

    public abstract List<FsscFeeExpenseItem> findByFdTemplate(FsscFeeTemplate fdTemplate) throws Exception;

    public abstract List<FsscFeeExpenseItem> findByFdCompany(EopBasedataCompany fdCompany) throws Exception;

    public abstract List<FsscFeeExpenseItem> findByFdItemList(EopBasedataExpenseItem fdItemList) throws Exception;
}
