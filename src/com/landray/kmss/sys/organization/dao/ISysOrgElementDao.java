package com.landray.kmss.sys.organization.dao;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
 * 组织架构元素数据访问接口
 * 
 * @author 叶中奇
 */
public interface ISysOrgElementDao extends IBaseDao {
	/**
	 * 格式化组织架构元素，在组织架构类型转换前必须执行
	 * 
	 * @param element
	 *            组织架构元素
	 * @return 组织架构元素
	 * @throws Exception
	 */
	public abstract SysOrgElement format(SysOrgElement element)
			throws Exception;

	/**
	 * 查找某个机构/部门下的所有子的个数，不对岗位进行展开
	 * 
	 * @param element
	 *            机构/部门
	 * @param rtnType
	 *            返回类型，为SysOrgConstant的常量组合
	 * @return 子的个数
	 * @throws Exception
	 */
	public abstract int getAllChildrenCount(SysOrgElement element, int rtnType)
			throws Exception;

	int getAllCount(int rtnType) throws Exception;


	/**
	 * 带附加查询条件的查询组织架构总数
	 * @param rtnType
	 * @param whereBlock
	 * @return
	 * @throws Exception
	 */
	int getAllCount(int rtnType ,String whereBlock) throws Exception;

	/**
	 * 获取注册用户数量（由于计算注册用户数量逻辑有变更，需要单独编写）
	 * @param isExternal
	 * @param exclude
	 * @return
	 * @throws Exception
	 */
	public int getCountByRegistered(Boolean isExternal, Boolean exclude) throws Exception;

	public void setNotToUpdateRelation(Boolean notToUpdateRelation);

	public void updateRelation() throws Exception;

	public void setNotToUpdateHierarchy(Boolean notToUpdateHierarchy);

}
