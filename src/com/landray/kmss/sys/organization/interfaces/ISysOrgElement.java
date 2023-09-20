package com.landray.kmss.sys.organization.interfaces;

import java.util.List;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
 * @author yezq
 * 
 */
public interface ISysOrgElement extends IBaseModel {
	/**
	 * @return 组织架构元素类型
	 */
	public abstract Integer getFdOrgType();

	/**
	 * @return 名称
	 */
	public abstract String getFdName();

	/**
	 * @return 编号
	 */
	public abstract String getFdNo();

	/**
	 * @return 排序号
	 */
	public abstract Integer getFdOrder();

	/**
	 * @return 关键字
	 */
	public abstract String getFdKeyword();

	/**
	 * @return 是否有效
	 */
	public abstract Boolean getFdIsAvailable();

	/**
	 * @return 是否业务相关
	 */
	public abstract Boolean getFdIsBusiness();

	/**
	 * @return 备注
	 */
	public abstract String getFdMemo();

	/**
	 * @return 所属机构
	 */
	public abstract SysOrgElement getFdParentOrg();

	/**
	 * @return 上级部门/机构
	 */
	public abstract SysOrgElement getFdParent();

	/**
	 * @return 所属群组
	 */
	public abstract List getFdGroups();

	/**
	 * 获取第level级的领导
	 * 
	 * @param level
	 *            0:直接领导，1：领导的领导，-1：一级领导，以此类推
	 * @return
	 */
	public abstract SysOrgElement getLeader(int level) throws Exception;

	/**
	 * 获取所有领导
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract List getAllLeader() throws Exception;

	/**
	 * 获取第level级的领导，但不包含自己
	 * 
	 * @param level
	 *            0:直接领导，1：领导的领导，-1：一级领导，以此类推
	 * @return
	 */
	public abstract SysOrgElement getMyLeader(int level) throws Exception;

	/**
	 * 获取所有领导，但返回的列表中不包含自己
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getAllMyLeader() throws Exception;

}
