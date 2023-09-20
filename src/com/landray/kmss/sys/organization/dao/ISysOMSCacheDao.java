package com.landray.kmss.sys.organization.dao;

import java.util.List;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.organization.model.SysOMSCache;

/**
 * 创建日期 2006-12-14
 * 
 * @author 吴兵
 */
public interface ISysOMSCacheDao extends IBaseDao {
	/**
	 * 与中间表关联取组织机构元素
	 * 
	 * @param className
	 *            SysOrgElement的子类名
	 * @param appName
	 *            OMS接出应用名
	 * @return SysOrgElement的子类对象集合
	 * @throws Exception
	 */
	public List findSysOrgELements(String className, String appName, int type)
			throws Exception;

	public List findSysOrgElementIds(String appName, int type) throws Exception;

	/**
	 * 返回OMS记录
	 * 
	 * @param fdElementId
	 * @param appName
	 * @return SysOMSCache
	 * @throws Exception
	 */
	public SysOMSCache findSysOMSCache(String fdElementId, String appName)
			throws Exception;

	/**
	 * 根据应用名批量删除记录
	 * 
	 * @param appName
	 * @throws Exception
	 */
	public void deleteSysOMSCache(String appName) throws Exception;
}
