package com.landray.kmss.km.calendar.service;
/**
 * 日程保留期限定时任务
 */
public interface IKmCalendarTimingDeleteService {
	/**
	 * 删除到期日程
	 */
	public void deleteCalendarTiming() throws Exception;
}
