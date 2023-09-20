package com.landray.kmss.third.ding.service;

import com.landray.kmss.km.calendar.model.KmCalendarSyncMapping;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

import java.util.List;

public interface IThirdDingCalendarService extends IExtendDataService {

    public boolean updateCalendarIdConvert(List<KmCalendarSyncMapping> kmCalendarSyncMappings,String unionId);
}
