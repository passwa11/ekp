/*
 * 创建日期 2006-02-23
 *
 */
package com.landray.kmss.sys.organization.dao;


/**
 * 部门数据访问接口
 * 
 * @author 叶中奇
 */
public interface ISysOrgDeptDao extends ISysOrgElementDao {

	public String getOriginalName(String id) throws Exception;
}
