package com.landray.kmss.eop.basedata.service;

import java.util.List;

import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataErpPerson;

public interface IEopBasedataErpPersonService extends IEopBasedataBusinessService {

    public abstract List<EopBasedataErpPerson> findByFdCompany(EopBasedataCompany fdCompany) throws Exception;

    public EopBasedataErpPerson getEopBasedataErpPersonByFdPersonId(String fdPersonId) throws Exception;
}
