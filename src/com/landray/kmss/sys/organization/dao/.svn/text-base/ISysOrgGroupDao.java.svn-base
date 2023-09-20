/*
 * 创建日期 2006-02-23
 *
 */
package com.landray.kmss.sys.organization.dao;

import java.util.List;
import java.util.Set;

/**
 * 群组数据访问接口
 * 
 * @author 叶中奇
 */
public interface ISysOrgGroupDao extends ISysOrgElementDao {
	/**
	 * 获取groupIds以及groupIds下的所有群组ID
	 * 
	 * @param groupIds
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public abstract List fetchChildGroupIds(List groupIds) throws Exception;

	/**
	 * 获取groupIds以及groupIds所有所属群组ID
	 * 
	 * @param groupIds
	 * @return
	 * @throws Exception
	 */
	public abstract List fetchParentGroupIds(List groupIds) throws Exception;

	/**
	 * 获取groupIds以及groupIds下的所有群组ID
	 *
	 * @param groupIds
	 * @return
	 * @throws Exception
	 */
	public abstract List fetchChildGroupIds(Set groupIds) throws Exception;
}
