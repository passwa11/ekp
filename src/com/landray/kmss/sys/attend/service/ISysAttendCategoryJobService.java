package com.landray.kmss.sys.attend.service;

import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 * @author linxiuxian
 *
 */
public interface ISysAttendCategoryJobService {

	public void execute(SysQuartzJobContext jobContext) throws Exception;

	/**
	 * 会议模块签到组状态维护
	 * 
	 * @param jobContext
	 * @throws Exception
	 */
	public void updateStatus(SysQuartzJobContext jobContext) throws Exception;
}
