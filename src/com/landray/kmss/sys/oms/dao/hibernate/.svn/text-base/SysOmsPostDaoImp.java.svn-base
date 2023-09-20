package com.landray.kmss.sys.oms.dao.hibernate;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.sys.oms.dao.ISysOmsPostDao;

public class SysOmsPostDaoImp extends BaseDaoImp implements ISysOmsPostDao {

	@Override
	public void deleteHandledOrg() throws Exception {
		// TODO Auto-generated method stub
		Query query = super.getSession()
				.createQuery(
						"delete from com.landray.kmss.sys.oms.model.SysOmsPost where fdHandleStatus=3 or fdHandleStatus=4");
		query.executeUpdate();
	}
}
