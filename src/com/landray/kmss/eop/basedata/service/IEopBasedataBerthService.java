package com.landray.kmss.eop.basedata.service;

import java.util.List;

import com.landray.kmss.eop.basedata.model.EopBasedataBerth;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataVehicle;

public interface IEopBasedataBerthService extends IEopBasedataBusinessService {

    public abstract List<EopBasedataBerth> findByFdVehicle(EopBasedataVehicle fdVehicle) throws Exception;

    public abstract List<EopBasedataBerth> findByFdCompany(EopBasedataCompany fdCompany) throws Exception;
}
