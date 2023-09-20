package com.landray.kmss.km.imeeting.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.km.imeeting.dao.IKmImeetingSyncBindDao;

/**
 * 同步绑定信息数据访问接口实现
 * 
 * @author
 * @version 1.0 2013-10-14
 */
public class KmImeetingSyncBindDaoImp extends BaseDaoImp implements
		IKmImeetingSyncBindDao {

	@Override
    public int delete(String fdAppKey, String personId) throws Exception {
		String hql = "delete from KmImeetingSyncBind where fdAppKey=? and fdOwner.fdId=?";
		return getHibernateTemplate().bulkUpdate(hql,
				new Object[] { fdAppKey, personId });
	}

}
