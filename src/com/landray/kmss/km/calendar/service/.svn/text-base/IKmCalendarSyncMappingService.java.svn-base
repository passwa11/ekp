package com.landray.kmss.km.calendar.service;

import java.util.List;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.calendar.model.KmCalendarSyncMapping;

/**
 * 同步映射关联业务对象接口
 * 
 * @author
 * @version 1.0 2013-10-14
 */
public interface IKmCalendarSyncMappingService extends IBaseService {

	public List<String> findCalendarIds(String appKey, String uuid)
			throws Exception;

	public void delete(String appKey, String uuid) throws Exception;

	public void delete(String appKey, String uuid, String calendarId)
			throws Exception;

	public void deleteByCalendarId(String calendarId) throws Exception;

	public List<KmCalendarSyncMapping> findByCalendarId(String calendarId)
			throws Exception;

	public KmCalendarSyncMapping findByAppKeyAndCalendarId(String appKey,
			String calendarId) throws Exception;

	public String getAppUuid(String appKey, String calendarId) throws Exception;

	public List<String> getOwnerIds(String appKey, String uuid)
			throws Exception;

	public List<String> findCalendarIds(String appKey, String uuid,
			String personId) throws Exception;

}
