package com.landray.kmss.km.calendar.dao.hibernate;

import java.util.List;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.calendar.dao.IKmCalendarSyncMappingDao;

/**
 * 同步映射关联数据访问接口实现
 * 
 * @author
 * @version 1.0 2013-10-14
 */
public class KmCalendarSyncMappingDaoImp extends BaseDaoImp implements
		IKmCalendarSyncMappingDao {

	@Override
    public void delete(String appKey, String uuid) {
		// TODO 自动生成的方法存根

		String hql = "delete KmCalendarSyncMapping where fdAppKey=? and fdAppUuid=?";
		Query query = super.getSession().createQuery(hql);
		query.setString(0, appKey);
		query.setString(1, uuid);
		query.executeUpdate();

	}

	@Override
    public void delete(String calendarId) {
		// TODO 自动生成的方法存根
		String hql = "delete KmCalendarSyncMapping where fdCalendarId=?";
		Query query = super.getSession().createQuery(hql);
		query.setString(0, calendarId);
		query.executeUpdate();

	}

	@Override
    public void delete(String appKey, String uuid, String calendarId) {
		// TODO 自动生成的方法存根
		String hql = "delete KmCalendarSyncMapping where fdAppKey=? and fdAppUuid=? and fdCalendarId=?";
		Query query = super.getSession().createQuery(hql);
		query.setString(0, appKey);
		query.setString(1, uuid);
		query.setString(2, calendarId);
		query.executeUpdate();
	}

	@Override
    @SuppressWarnings("unchecked")
	public List findList(HQLInfo hqlInfo) throws Exception {
		TimeCounter.logCurrentTime("Dao-findList", true, getClass());
		List rtnList = createHbmQuery(hqlInfo).list();
		TimeCounter.logCurrentTime("Dao-findList", false, getClass());
		return rtnList;
	}

}
