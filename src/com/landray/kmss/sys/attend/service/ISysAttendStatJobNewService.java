package com.landray.kmss.sys.attend.service;

import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 * 日统计
 * 
 * @author linxiuxian
 *
 */
public interface ISysAttendStatJobNewService {

	/**
	 * 系统当天实时定时任务
	 *
	 * @param jobContext
	 * @throws Exception
	 */
	public void execute(SysQuartzJobContext jobContext) throws Exception;

}
