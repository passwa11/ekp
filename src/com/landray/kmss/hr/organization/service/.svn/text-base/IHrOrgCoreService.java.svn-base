package com.landray.kmss.hr.organization.service;

import java.util.List;

import com.landray.kmss.hr.organization.constant.HrOrgConstant;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;

public interface IHrOrgCoreService extends HrOrgConstant {


	/**
	 * 查找某个机构/部门下的所有子，不对岗位进行展开
	 * 
	 * @param element
	 *            机构/部门
	 * @param rtnType
	 *            返回类型，为SysOrgConstant的常量组合
	 * @return 查询结果，为域模型列表
	 * @throws Exception
	 */
	public abstract List findAllChildren(HrOrganizationElement element,
			int rtnType)
			throws Exception;

	/**
	 * 查找（附加特定查询条件）某个机构/部门下的所有子，不对岗位进行展开
	 * 
	 * @param element
	 *            机构/部门
	 * @param rtnType
	 *            返回类型，为SysOrgConstant的常量组合
	 * @param wherBlock
	 *            特定查询条件
	 * @return 查询结果，为域模型列表
	 * @throws Exception
	 */
	public abstract List findAllChildren(HrOrganizationElement element,
			int rtnType,
			String whereBlock)
			throws Exception;

}
