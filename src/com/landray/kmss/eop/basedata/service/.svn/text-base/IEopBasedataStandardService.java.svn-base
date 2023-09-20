package com.landray.kmss.eop.basedata.service;

import java.util.List;

import com.landray.kmss.eop.basedata.model.EopBasedataArea;
import com.landray.kmss.eop.basedata.model.EopBasedataBerth;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataLevel;
import com.landray.kmss.eop.basedata.model.EopBasedataStandard;
import com.landray.kmss.eop.basedata.model.EopBasedataVehicle;

import net.sf.json.JSONObject;

public interface IEopBasedataStandardService extends IEopBasedataBusinessService {

    public abstract List<EopBasedataStandard> findByFdLevel(EopBasedataLevel fdLevel) throws Exception;

    public abstract List<EopBasedataStandard> findByFdArea(EopBasedataArea fdArea) throws Exception;

    public abstract List<EopBasedataStandard> findByFdVehicle(EopBasedataVehicle fdVehicle) throws Exception;

    public abstract List<EopBasedataStandard> findByFdBerth(EopBasedataBerth fdBerth) throws Exception;

    public abstract List<EopBasedataStandard> findByFdItem(EopBasedataExpenseItem fdItem) throws Exception;

    public abstract List<EopBasedataStandard> findByFdCurrency(EopBasedataCurrency fdCurrency) throws Exception;

    public abstract List<EopBasedataStandard> findByFdCompany(EopBasedataCompany fdCompany) throws Exception;
    
    public abstract JSONObject getStandardData(JSONObject params) throws Exception;
}
