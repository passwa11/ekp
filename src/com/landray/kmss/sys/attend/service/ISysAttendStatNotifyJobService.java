package com.landray.kmss.sys.attend.service;

import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

public interface ISysAttendStatNotifyJobService {

	/**
	 * 不是跨天打卡的考勤组，发送缺卡提醒，执行时间为每天10点
	 * 
	 * @param jobContext
	 * @throws Exception
	 */
	public void execute(SysQuartzJobContext jobContext) throws Exception;

	/**
	 * 跨天打卡的考勤组，发送缺卡提醒，执行时间不固定
	 * 
	 * @param context
	 * @throws Exception
	 */
	public void executeAcross(SysQuartzJobContext context) throws Exception;
}
