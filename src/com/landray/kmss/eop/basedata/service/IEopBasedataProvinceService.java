package com.landray.kmss.eop.basedata.service;

import java.util.List;

import com.landray.kmss.eop.basedata.model.EopBasedataCountry;
import com.landray.kmss.eop.basedata.model.EopBasedataProvince;

public interface IEopBasedataProvinceService extends IEopBasedataBusinessService {

    public abstract List<EopBasedataProvince> findByFdCountry(EopBasedataCountry fdCountry) throws Exception;
}
