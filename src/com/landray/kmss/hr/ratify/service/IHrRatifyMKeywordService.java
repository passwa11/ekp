package com.landray.kmss.hr.ratify.service;

import java.util.List;
import com.landray.kmss.hr.ratify.model.HrRatifyMain;
import com.landray.kmss.hr.ratify.model.HrRatifyMKeyword;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IHrRatifyMKeywordService extends IExtendDataService {

    public abstract List<HrRatifyMKeyword> findByFdObject(HrRatifyMain fdObject) throws Exception;
}
