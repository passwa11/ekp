package com.landray.kmss.eop.basedata.service;

import java.util.List;
import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataReceiverType;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IEopBasedataReceiverTypeService extends IExtendDataService {

    public abstract List<EopBasedataReceiverType> findByFdAccounts(EopBasedataAccounts fdAccounts) throws Exception;
}
