package com.landray.kmss.km.calendar.dao;

import com.landray.kmss.common.dao.IBaseDao;

/**
 * 同步映射关联数据访问接口
 * 
 * @author
 * @version 1.0 2013-10-14
 */
public interface IKmCalendarSyncMappingDao extends IBaseDao {

	public void delete(String appKey, String uuid);

	public void delete(String appKey, String uuid, String personId);

	public void delete(String calendarId);

}
