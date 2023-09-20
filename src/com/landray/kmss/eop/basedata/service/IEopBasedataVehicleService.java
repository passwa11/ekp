package com.landray.kmss.eop.basedata.service;

import java.util.List;

import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataVehicle;

public interface IEopBasedataVehicleService extends IEopBasedataBusinessService {

    public abstract List<EopBasedataVehicle> findByFdCompany(EopBasedataCompany fdCompany) throws Exception;
}
