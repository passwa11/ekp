package com.landray.kmss.sys.organization.dao;

/**
 * 机构数据访问接口
 * 
 * @author 叶中奇
 */
public interface ISysOrgOrgDao extends ISysOrgElementDao {
	/**
	 * 将部门更改为机构
	 * 
	 * @param deptId
	 * @return
	 * @throws Exception
	 */
	public boolean setDeptToOrg(String deptId) throws Exception;

	public String getOriginalName(String id) throws Exception;
}
