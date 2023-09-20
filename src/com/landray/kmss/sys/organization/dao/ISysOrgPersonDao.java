/*
 * 创建日期 2006-02-23
 *
 */
package com.landray.kmss.sys.organization.dao;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
 * 个人数据访问接口
 * 
 * @author 叶中奇
 */
public interface ISysOrgPersonDao extends ISysOrgElementDao {
	/**
	 * @param modelObj
	 * @throws Exception
	 *             在资源哪边启用或注销用户（设为无效、有效用户），通过此方法不处理对象关系
	 */
	public void updatePerson(IBaseModel modelObj) throws Exception;

	public void abandonPerson(IBaseModel modelObj) throws Exception;
	
	/**
	 * 获取数据库中原始的密码数据，因为hibernate会缓存数据，所以使用SQL直接去数据库查询
	 * @param person
	 * @return
	 * @throws Exception
	 */
	public String getOriginalPassword(SysOrgPerson person) throws Exception;
}
