package com.landray.kmss.sys.organization.service;

import java.util.List;

/**
 * 机构业务对象接口
 * 
 * @author 叶中奇
 */
public interface ISysOrgOrgService extends ISysOrgElementService {
	/**
	 * 将部门更新为机构
	 * 
	 * @param deptIds
	 * @return
	 * @throws Exception
	 */
	public boolean updateDeptToOrg(String deptId) throws Exception;

	/**
	 * 批量调动上级
	 * 
	 * @param orgIds
	 * @param parentId
	 * @throws Exception
	 */
	public void updateParentByOrgs(String[] orgIds, String parentId)
			throws Exception;
	
	/**
	 * 父部门是否为机构
	 * 
	 * @param deptIds
	 * @return
	 * @throws Exception
	 */
	public boolean parentIsOrg(String deptId) throws Exception;
	
	/**
	 * 获取生态组织无组织类型
	 * @return
	 * @throws Exception
	 */
	public List getUnorganizedTypeOrg() throws Exception;
	
}
