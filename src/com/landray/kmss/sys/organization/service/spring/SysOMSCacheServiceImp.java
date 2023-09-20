package com.landray.kmss.sys.organization.service.spring;

import java.util.List;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.organization.dao.ISysOMSCacheDao;
import com.landray.kmss.sys.organization.model.SysOMSCache;
import com.landray.kmss.sys.organization.service.ISysOMSCacheService;

/**
 * 创建日期 2006-12-14
 * 
 * @author 吴兵
 */
public class SysOMSCacheServiceImp extends BaseServiceImp implements
		ISysOMSCacheService {
	@Override
    public List findSysOrgELements(String className, String appName, int type)
			throws Exception {
		return ((ISysOMSCacheDao) getBaseDao()).findSysOrgELements(className,
				appName, type);
	}

	@Override
    public List findSysOrgElementIds(String appName, int type) throws Exception {
		return ((ISysOMSCacheDao) getBaseDao()).findSysOrgElementIds(appName,
				type);
	}

	@Override
    public SysOMSCache findSysOMSCache(String fdElementId, String appName)
			throws Exception {
		return ((ISysOMSCacheDao) getBaseDao()).findSysOMSCache(fdElementId,
				appName);
	}

	@Override
    public void deleteSysOMSCache(String appName) throws Exception {
		((ISysOMSCacheDao) getBaseDao()).deleteSysOMSCache(appName);
	}

}
