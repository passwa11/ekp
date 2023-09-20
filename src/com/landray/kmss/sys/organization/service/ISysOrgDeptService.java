package com.landray.kmss.sys.organization.service;

/**
 * 部门业务对象接口
 * 
 * @author 叶中奇
 */
public interface ISysOrgDeptService extends ISysOrgElementService {

	/**
	 * 批量调动上级
	 * 
	 * @param deptIds
	 * @param parentId
	 * @throws Exception
	 */
	public void updateParentByDepts(String[] deptIds, String parentId)
			throws Exception;

}
