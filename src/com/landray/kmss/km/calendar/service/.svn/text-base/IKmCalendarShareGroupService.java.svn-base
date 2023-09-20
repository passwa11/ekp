package com.landray.kmss.km.calendar.service;

import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.calendar.model.KmCalendarShareGroup;

/**
 * 日程共享组设置 业务对象接口
 * 
 * @author
 * @version 1.0 2013-10-14
 */
public interface IKmCalendarShareGroupService extends IBaseService {

	/**
	 * 查找指定人员的共享分组
	 * 
	 * @param personId
	 */
	public List<KmCalendarShareGroup> getUserShareGroups(String personId)
			throws Exception;

	/**
	 * 查找指定共享分组的人员
	 * 
	 * @return 返回List类型: totalPersons:全部日程人员 persons:当前分页日程人员
	 */
	public Map<String, List> getShareGroupMembers(
			RequestContext requestContext, Boolean loadAll) throws Exception;

	public Map<String, List> getShareGroupMembers(RequestContext requestContext)
			throws Exception;

	public void saveSendInviteNotify(KmCalendarShareGroup group,
			List notifyTarget,
			RequestContext requestContext) throws Exception;

}
