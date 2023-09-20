package com.landray.kmss.sys.oms.dao.hibernate;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.sys.oms.dao.ISysOmsGroupDao;

public class SysOmsGroupDaoImp extends BaseDaoImp implements ISysOmsGroupDao {

	@Override
	public void deleteHandledOrg() throws Exception {
		// TODO Auto-generated method stub
		Query query = super.getSession()
				.createQuery(
						"delete from com.landray.kmss.sys.oms.model.SysOmsGroup where fdHandleStatus=3 or fdHandleStatus=4");
		query.executeUpdate();
	}
}
