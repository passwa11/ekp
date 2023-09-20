package com.landray.kmss.km.calendar.service;

import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.calendar.model.KmCalendarAuth;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
 * 日程共享人员业务访问接口
 * 
 * @author
 * @version 1.0 2013-10-14
 */
public interface IKmCalendarAuthService extends IBaseService {

	public Map<String, List<String>> getHierarchyIdsFromReaderAuth(List<String> orgIds) throws Exception;

	public Map<String, List<String>> getHierarchyIdsFromEditorAuth(List<String> orgIds) throws Exception;

	public Map<String, List<String>> getHierarchyIdsFromModifierAuth(List<String> orgIds) throws Exception;

	public KmCalendarAuth findByPerson(String personId) throws Exception;

	public List<KmCalendarAuth> findUserCalendarAuth(List orgIds)
			throws Exception;

	public List<String[]> getReadAuthPersonList() throws Exception;

	public List<String[]> getCreateAuthPersonList() throws Exception;

	public List<String[]> getModifyAuthPersonList() throws Exception;

	/**
	 * 获取指定用户默认关注分组的成员（成员设置的默认可阅读者，或默认可编辑者，或默认可创建者包含当前用户） 如果指定用户为空，则为当前用户
	 * 
	 * @return 返回List类型: totalPersons:全部日程人员 persons:当前分页日程人员
	 */
	public Map<String, List> getDefaultGroupMembers(
			RequestContext requestContext, Boolean loadAll) throws Exception;

	public Map<String, List> getDefaultGroupMembers(
			RequestContext requestContext) throws Exception;

	/**
	 * 将指定人员更新入个人共享设置，重复不更新
	 */
	public void updateAuthByPerson(KmCalendarAuth auth, SysOrgElement person,
			RequestContext requestContext)
			throws Exception;

	/**
	 * 查询我对指定人员(userId)的共享状态,他是否可读、可编辑、可新建我的日程
	 */
	public Map<String, String> getAuthStatusByUserId(String userId)
			throws Exception;

	/**
	 * 操作权限列表时，更新入个人共享设置
	 */
	public void updateByAuthList(KmCalendarAuth auth) throws Exception;

}
