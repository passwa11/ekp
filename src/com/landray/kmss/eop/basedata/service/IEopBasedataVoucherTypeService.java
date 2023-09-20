package com.landray.kmss.eop.basedata.service;

import java.util.List;

import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataVoucherType;

public interface IEopBasedataVoucherTypeService extends IEopBasedataBusinessService {

    public abstract List<EopBasedataVoucherType> findByFdCompany(EopBasedataCompany fdCompany) throws Exception;
}
