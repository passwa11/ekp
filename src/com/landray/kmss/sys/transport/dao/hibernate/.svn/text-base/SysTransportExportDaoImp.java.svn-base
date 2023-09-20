package com.landray.kmss.sys.transport.dao.hibernate;

import java.util.List;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.sys.transport.dao.ISysTransportExportDao;

public class SysTransportExportDaoImp extends BaseDaoImp implements
		ISysTransportExportDao
{
	@Override
    public List getAllByModelName(String modelName) throws Exception {
		String hql = "from " + modelName;
		return getHibernateTemplate().find(hql);
	}
}
