package com.landray.kmss.sys.mportal.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.sys.mportal.dao.ISysMportalMenuDao;
import org.hibernate.query.Query;

/**
 * 菜单配置数据访问接口实现
 * 
 * @author
 * @version 1.0 2015-10-08
 */
public class SysMportalMenuDaoImp extends BaseDaoImp implements
		ISysMportalMenuDao {

	@Override
	public void updateEnableNotId(String id) throws Exception {
		Query query = this
				.getHibernateSession()
				.createQuery(
						"update com.landray.kmss.sys.mportal.model.SysMportalMenu sysMportalMenu set fdEnable = :fdEnable where fdId <>:fdId");
		query.setParameter("fdEnable", Boolean.FALSE);
		query.setParameter("fdId", id);
		query.executeUpdate();
	}

}
