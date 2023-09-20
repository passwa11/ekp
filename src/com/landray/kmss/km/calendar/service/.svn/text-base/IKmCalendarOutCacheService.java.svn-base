package com.landray.kmss.km.calendar.service;

import java.util.List;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.calendar.model.KmCalendarMain;
import com.landray.kmss.km.calendar.model.KmCalendarOutCache;

/**
 * 日程接出缓存业务对象接口
 * 
 * @author
 * @version 1.0 2013-10-14
 */
public interface IKmCalendarOutCacheService extends IBaseService {

	public void deleteByAppKeyAndUuid(String appKey, String uuid)
			throws Exception;

	public KmCalendarOutCache findByCalendarIdAndAppKey(String calendarId,
			String appKey) throws Exception;

	public List<KmCalendarOutCache> listByPersonAndApp(String personId,
			String appKey) throws Exception;

	public void updateCalendarOutCaches(KmCalendarMain kmCalendarMain,
			String operationType, String personId, String operationAppKey)
			throws Exception;

	public void updateCalendarOutCaches(KmCalendarMain kmCalendarMain,
			String operationType, String personId, String operationAppKey,
			List<String> appKeys) throws Exception;

	public KmCalendarOutCache findByAppUuidAndAppKey(String appUuid,
			String appKey) throws Exception;
}
