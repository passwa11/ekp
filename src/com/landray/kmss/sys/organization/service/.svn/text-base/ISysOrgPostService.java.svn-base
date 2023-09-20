package com.landray.kmss.sys.organization.service;

import java.util.Date;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;

/**
 * 岗位业务对象接口
 * 
 * @author 叶中奇
 */
public interface ISysOrgPostService extends ISysOrgElementService {

	public String addNewPost(IExtendForm form, RequestContext requestContext)
			throws Exception;

	/**
	 * 批量调动部门
	 * 
	 * @param postIds
	 * @param deptId
	 * @throws Exception
	 */
	public void updateDeptByPosts(String[] postIds, String deptId)
			throws Exception;
	
	
	/**
	 * 批量调动部门
	 * 
	 * @param postIds
	 * @param deptId
	 * @throws Exception
	 */
	public void updateDeptByPosts(String[] postIds, String deptId,RequestContext requestContext)
			throws Exception;
	
	/**
	 * 更新岗位同时更新岗位成员的更新时间
	 * @param id
	 * @throws Exception
	 */
	public void updatePersonsByPost(String id, Date time)throws Exception;
}
