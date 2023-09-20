package com.landray.kmss.sys.attend.service;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.organization.model.SysOrgElement;

import java.util.List;

public interface ISysAttendOrgService extends IBaseService {

	/**
	 * 获取该用户作为部门领导的对应部门列表或拥有查看授权的部门列表
	 * 
	 * @param element
	 * @return
	 */
	public List findDeptsByLeader(SysOrgElement element) throws Exception;

	/**
	 * 获取该用户作为岗位领导的对应的人员列表或拥有查看授权的部门列表
	 * 
	 * @param element
	 * @return
	 * @throws Exception
	 */
	public List findPersonsByLeader(SysOrgElement element)
			throws Exception;

	/**
	 * 获取该用户作为领导的组织架构列表
	 * 
	 * @param element
	 * @return
	 * @throws Exception
	 */
	public List findOrgIdsByLeader(SysOrgElement element) throws Exception;

	/**
	 * 获取其子部门的id，名字，部门领导
	 * 
	 * @param orgList
	 * @return
	 * @throws Exception
	 */
	public List findChildDeptsInfo(List<SysOrgElement> orgList)
			throws Exception;

	public List addressList(RequestContext context) throws Exception;



	/**
	 * 组织ID，转换成人员ID
	 * 不解析部门下的岗位
	 * @param orgList
	 * @return
	 * @throws Exception
	 */
	public List<String> expandToPersonIds(List orgList) throws Exception;

	/**
	 * 获取人员对象
	 * 线程级缓存来处理。调用方需要手动清除缓存
	 * @param personIds 只接收人员ID
	 * @return 返回对应组织下面所有的 人员对象(简易对象，自己new出来的对象)
	 * @throws Exception
	 */
	public List<SysOrgElement> expandToPersonSimple(List<String> personIds) throws Exception ;

	/**
	 * 获取人员对象
	 * @param orgList
	 * @return 返回对应组织下面所有的 人员对象
	 * @throws Exception
	 */
	public List<SysOrgElement> expandToPerson(List orgList) throws Exception;

	/**
	 * 根据人员id获取人员对象
	 * @param personIds 人员id列表
	 * @throws Exception
	 */
	public List<SysOrgElement> getSysOrgElementById(List<String> personIds) throws Exception ;

	/**
	 * 释放数据库连接资源
	 */
	public void release();

}
