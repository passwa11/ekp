package com.landray.kmss.sys.organization.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.sys.organization.dao.ISysOMSCacheDao;
import com.landray.kmss.sys.organization.model.SysOMSCache;

/**
 * 创建日期 2006-12-14
 * 
 * @author 吴兵
 */
public class SysOMSCacheDaoImp extends BaseDaoImp implements ISysOMSCacheDao {
	@Override
    public List findSysOrgELements(String className, String appName, int type)
			throws Exception {
		String hsql = "select sysOrgElement from " + className
				+ " sysOrgElement," + SysOMSCache.class.getName()
				+ " sysOMSCache "
				+ "where sysOrgElement.fdId=sysOMSCache.fdOrgElementId "
				+ "and sysOrgElement.fdIsBusiness=?"
				+ "and sysOMSCache.fdAppName='" + appName
				+ "' and sysOMSCache.fdOpType=" + type;
		// fdIsBusiness 是boolean类型，
		return getHibernateTemplate().find(hsql, new Object[]{Boolean.TRUE});
		//return getHibernateTemplate().find(hsql);
	}

	@Override
    public List findSysOrgElementIds(String appName, int type) throws Exception {
		List list = new ArrayList();
		List caches = findList("fdAppName='" + appName + "' and fdOpType="
				+ type, null);
		for (int i = 0; i < caches.size(); i++) {
			list.add(((SysOMSCache) caches.get(i)).getFdOrgElementId());
		}
		return list;
	}

	@Override
    public SysOMSCache findSysOMSCache(String fdElementId, String appName)
			throws Exception {
		String whereBlock = "sysOMSCache.fdOrgElementId='" + fdElementId
				+ "' and sysOMSCache.fdAppName='" + appName + "'";
		List list = findList(whereBlock, null);
		if (list != null && !list.isEmpty()) {
			return (SysOMSCache) list.get(0);
		}
		return null;
	}

	@Override
    public void deleteSysOMSCache(String appName) throws Exception {
		super.getSession().createQuery(
				"delete SysOMSCache where fdAppName=:fdAppName").setString(
				"fdAppName", appName).executeUpdate();
	}

}
