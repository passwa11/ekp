package com.landray.kmss.sys.attend.service;

import java.util.Date;

import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 * 签到统计
 * 
 * @author linxiuxian
 *
 */
public interface ISysAttendSignStatJobService {
	/**
	 * 系统当天实时定时任务
	 * 
	 * @param jobContext
	 * @throws Exception
	 */
	public void execute(SysQuartzJobContext jobContext) throws Exception;

	/**
	 * 根据日期统计
	 * 
	 * @param date
	 * @throws Exception
	 */
	public void stat(Date date) throws Exception;

}
