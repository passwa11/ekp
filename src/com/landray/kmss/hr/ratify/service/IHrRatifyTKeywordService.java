package com.landray.kmss.hr.ratify.service;

import java.util.List;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.hr.ratify.model.HrRatifyTKeyword;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IHrRatifyTKeywordService extends IExtendDataService {

    public abstract List<HrRatifyTKeyword> findByFdObject(HrRatifyTemplate fdObject) throws Exception;
}
