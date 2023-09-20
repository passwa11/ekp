package com.landray.kmss.km.calendar.service;

import java.util.List;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.calendar.model.KmCalendarAuth;
import com.landray.kmss.km.calendar.model.KmCalendarAuthList;

public interface IKmCalendarAuthListService extends IBaseService {

	/**
	 * 新增共享权限list时更新同步的历史日程
	 * 
	 * @param auth
	 * @param authList
	 * @throws Exception
	 */
	public void updateCalendarByAddList(KmCalendarAuth auth,
			KmCalendarAuthList authList) throws Exception;

	/**
	 * 删除共享权限list时更新同步的历史日程
	 * 
	 * @param auth
	 * @param authList
	 * @throws Exception
	 */
	public void updateCalendarByDeleteList(KmCalendarAuth auth,
			KmCalendarAuthList authList) throws Exception;

	/**
	 * 编辑共享权限list时更新同步的历史日程
	 * 
	 * @param auth
	 * @param updateList_pre
	 * @param updateList_after
	 * @throws Exception
	 */
	public void updateCalendarByEditList(KmCalendarAuth auth,
			KmCalendarAuthList updateList_pre,
			KmCalendarAuthList updateList_after) throws Exception;

	public List<KmCalendarAuthList> getPartShareAuthList(List orgIds)
			throws Exception;

}
