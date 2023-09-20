package com.landray.kmss.km.calendar.dao.hibernate;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.km.calendar.dao.IKmCalendarOutCacheDao;

/**
 * 日程接出缓存数据访问接口实现
 * 
 * @author
 * @version 1.0 2013-10-14
 */
public class KmCalendarOutCacheDaoImp extends BaseDaoImp implements
		IKmCalendarOutCacheDao {

	@Override
    public void deleteByAppKeyAndUuid(String appKey, String uuid)
			throws Exception {

		String hql = "delete KmCalendarOutCache as kmCalendarOutCache where kmCalendarOutCache.fdAppKey=? and kmCalendarOutCache.fdUuid=?";
		Query query = super.getSession().createQuery(hql);
		query.setString(0, appKey);
		query.setString(1, uuid);
		query.executeUpdate();

	}

}
